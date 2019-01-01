package com.pcms.controller.api;

import com.alibaba.fastjson.JSONObject;
import com.pcms.domain.Moive;
import com.pcms.domain.MoiveFail;
import com.pcms.domain.RequestMoive;
import com.pcms.handler.AtsNormalMessageHandler;
import com.pcms.service.CrawlerService;
import com.pcms.service.MoiveService;
import com.pcms.util.DateUtil;
import com.pcms.util.PcmsConst;
import com.pcms.util.RandomNumber;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import org.weixin4j.WeixinException;
import org.weixin4j.spi.DefaultMessageHandler;
import org.weixin4j.util.TokenUtil;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * 提供外部调用，搜索查询
 */
@RestController
public class WXController {

    private Logger logger = LoggerFactory.getLogger(WXController.class);

    @Autowired
    private AtsNormalMessageHandler atsNormalMessageHandler;

    @Autowired
    private MoiveService moiveService;

    @Autowired
    private CrawlerService crawlerService;

    //第一次调用，用于微信服务器验证
    @RequestMapping(value = "/wx", method = RequestMethod.GET)
    public void WXGET(HttpServletRequest request, HttpServletResponse response) throws IOException {

        String signature = request.getParameter("signature");
        String timestamp = request.getParameter("timestamp");
        String nonce = request.getParameter("nonce");
        String echostr = request.getParameter("echostr");

        String tToken = TokenUtil.get();

        //成为开发者验证
        //确认此次GET请求来自微信服务器，原样返回echostr参数内容，则接入生效，成为开发者成功，否则接入失败
        if (TokenUtil.checkSignature(tToken, signature, timestamp, nonce)) {
            response.getWriter().write(echostr);
        }
    }

    @RequestMapping(value = "/wx", method = RequestMethod.POST)
    public void WXPOST(HttpServletRequest request, HttpServletResponse response) throws IOException {
        logger.info("进入wx方法。。。。。。");
        //消息来源可靠性验证
        String signature = request.getParameter("signature");// 微信加密签名
        String timestamp = request.getParameter("timestamp");// 时间戳
        String nonce = request.getParameter("nonce");       // 随机数
        //Token为weixin4j.properties中配置的Token
        String token = TokenUtil.get();

        //确认此次GET请求来自微信服务器，原样返回echostr参数内容，则接入生效，成为开发者成功，否则接入失败
        if (!TokenUtil.checkSignature(token, signature, timestamp, nonce)) {
            logger.error("验签不通过");
            //消息不可靠，直接返回
            response.getWriter().write("");
            return;
        }
        //用户每次向公众号发送消息、或者产生自定义菜单点击事件时，响应URL将得到推送
        try {
            response.setCharacterEncoding("UTF-8");
            response.setContentType("text/xml");
            //获取POST流
            ServletInputStream in = request.getInputStream();
            //非注解方式，依然采用消息处理工厂模式调用
            //IMessageHandler messageHandler = HandlerFactory.getMessageHandler();

            DefaultMessageHandler messageHandler = new DefaultMessageHandler(atsNormalMessageHandler, null);
            //处理输入消息，返回结果
            String xml = messageHandler.invoke(in);

            logger.info("响应:" + xml);
            //返回结果
            response.getWriter().write(xml);
        } catch (IOException | WeixinException ex) {
            response.getWriter().write("");
        }
    }


    /**
     * 求电影页面
     *
     * @param mav
     * @return
     */
    @RequestMapping("/moive/qmoive")
    public ModelAndView requestListView(ModelAndView mav, HttpServletRequest request) {
        String token = RandomNumber.ConfirmId(10);
        request.getSession().setAttribute("token", token);
        mav.addObject("token", token);
        mav.setViewName("moive/moiveNotFound");
        return mav;
    }


    /**
     * 求电影 后台处理
     *
     * @param mav
     * @param request
     * @return
     */
    @RequestMapping(value = "/moive/request", method = RequestMethod.POST)
    @ResponseBody
    public ModelAndView requestOrAdvice(ModelAndView mav, HttpServletRequest request) {
        //TODO 获取微信用户的信息
        String name = request.getParameter("moiveName");
        String desc = request.getParameter("moiveDesc");
        String token = request.getParameter("token");

        try {

            String tokens = (String) request.getSession().getAttribute("token");

            if (!StringUtils.equals(token, tokens)) {
                mav.setViewName("moive/error");
                return mav;
            }

            if (StringUtils.isBlank(name)) {
                mav.setViewName("moive/error");
                return mav;
            }

            Map param = new HashMap();
            param.put("mname", name);
            RequestMoive hasRequest = moiveService.getRequestMoiveByParam(param);
            if (hasRequest != null) {
                hasRequest.setCounter(hasRequest.getCounter() + 1);
                moiveService.updateByPrimaryKeySelective(hasRequest);
                mav.setViewName("moive/success");
                return mav;
            }

            RequestMoive requestMoive = new RequestMoive();
            requestMoive.setMoivename(name);
            requestMoive.setMoivedesc(desc);
            requestMoive.setCounter(1);
            requestMoive.setStatus(PcmsConst.RequestMoive.STATUS_INIT);
            requestMoive.setUpdatetime(DateUtil.getCurTimestamp());
            moiveService.insertRequestMoive(requestMoive);
            mav.setViewName("moive/success");

        } catch (Exception e) {
            e.printStackTrace();
            mav.setViewName("moive/error");
        }
        return mav;

    }


    /**
     * 链接失效 后台处理
     *
     * @param mav
     * @return
     */
    @RequestMapping("/moive/minvalid")
    public ModelAndView moiveinvalid(ModelAndView mav, HttpServletRequest request) {
        JSONObject result = new JSONObject();
        String name = request.getParameter("moiveName");

        if (StringUtils.isBlank(name)) {
            result.put(PcmsConst.RESPCODE, "999999");
            result.put(PcmsConst.RESPMSD, "系统异常");
            mav.setViewName("moive/error");
            return mav;
        }

        Map map = new HashMap<>();
        map.put("mname", name);
        map.put("failType", MoiveFail.FAILTYPE_INVAILD);

        try {
            MoiveFail moiveFailold = moiveService.getMoiveFailByParam(map);

            if (moiveFailold != null) {
                //请求判断，判断电影是否失效
                //TODO 改造成异步
                Moive moive = moiveService.getMoiveByParam(map);
                if (moive != null) {
                    boolean res = crawlerService.isOk(moive.getPanurl());
                    if (!res) {
                        Integer counter = moiveFailold.getCounter();
                        moiveFailold.setCounter(counter + 1);
                        moiveService.updateMoviveFail(moiveFailold);
                    }
                }
            } else {
                //请求判断，判断电影是否失效
                //TODO 改造成异步
                Moive moive = moiveService.getMoiveByParam(map);
                if (moive != null) {
                    boolean res = crawlerService.isOk(moive.getPanurl());
                    if (!res) {
                        MoiveFail moiveFail = new MoiveFail();
                        moiveFail.setCounter(1);
                        moiveFail.setFailtype(MoiveFail.FAILTYPE_INVAILD);
                        moiveFail.setMoivename(name);
                        moiveFail.setStatus(MoiveFail.HANLING);
                        moiveFail.setUpdatetime(DateUtil.getCurTimestamp());
                        moiveService.insertMoiveFail(moiveFail);
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            result.put(PcmsConst.RESPCODE, "999999");
            result.put(PcmsConst.RESPMSD, "系统异常");
            mav.setViewName("moive/error");
        }
        result.put(PcmsConst.RESPCODE, "000000");
        result.put(PcmsConst.RESPMSD, "成功");
        mav.setViewName("moive/success");
        return mav;
    }

    /**
     * 催更 后台处理
     *
     * @param mav
     * @return
     */
    @RequestMapping("/moive/murge")
    public ModelAndView moiveurge(ModelAndView mav, HttpServletRequest request) {
        JSONObject result = new JSONObject();
        String name = request.getParameter("moiveName");

        if (StringUtils.isBlank(name)) {
            result.put(PcmsConst.RESPCODE, "999999");
            result.put(PcmsConst.RESPMSD, "系统异常");
            mav.setViewName("moive/error");
            return mav;
        }

        Map map = new HashMap<>();
        map.put("mname", name);
        map.put("failType", MoiveFail.FAILTYPE_URGEMORE);

        try {
            MoiveFail moiveFailold = moiveService.getMoiveFailByParam(map);

            if (moiveFailold != null) {
                MoiveFail moiveFail = new MoiveFail();
                Integer counter = moiveFailold.getCounter();
                moiveFailold.setCounter(counter + 1);
                moiveService.updateMoviveFail(moiveFail);
            } else {
                MoiveFail moiveFail = new MoiveFail();
                moiveFail.setCounter(1);
                moiveFail.setFailtype(MoiveFail.FAILTYPE_URGEMORE);
                moiveFail.setMoivename(name);
                moiveFail.setStatus(MoiveFail.HANLING);
                moiveFail.setUpdatetime(DateUtil.getCurTimestamp());
                //moiveFail.setWxname();
                moiveService.insertMoiveFail(moiveFail);
            }
        } catch (Exception e) {
            e.printStackTrace();
            result.put(PcmsConst.RESPCODE, "999999");
            result.put(PcmsConst.RESPMSD, "系统异常");
            mav.setViewName("moive/error");
        }
        result.put(PcmsConst.RESPCODE, "000000");
        result.put(PcmsConst.RESPMSD, "成功");
        mav.setViewName("moive/success");
        return mav;

    }
}

