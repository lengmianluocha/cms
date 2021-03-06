package com.pcms.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.pcms.domain.Moive;
import com.pcms.domain.MoiveFail;
import com.pcms.domain.Pageable;
import com.pcms.domain.RequestMoive;
import com.pcms.service.FileService;
import com.pcms.service.MoiveService;
import com.pcms.service.impl.RedisService;
import com.pcms.service.impl.XiaoTokenService;
import com.pcms.util.*;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import sun.nio.cs.UnicodeEncoder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 链接失效、求电影、等信息展示
 */
@RestController
public class MoiveRequestFailController {

    @Autowired
    private MoiveService moiveService;

    @Autowired
    private FileService fileService;

    @Autowired
    private RedisService redisService;

    @Autowired
    private XiaoTokenService xiaoTokenService;

    private Logger logger = LoggerFactory.getLogger(MoiveRequestFailController.class);

    @RequestMapping("/moive/failListView")
    public ModelAndView listView(Model model, HttpSession session, ModelAndView mav) {
        String s = (String) session.getAttribute("user");
        mav.addObject("username", s);
        mav.setViewName("moive/failList");
        return mav;
    }

    @RequestMapping("/moive/urgeMoreListView")
    public ModelAndView urge(Model model, HttpSession session, ModelAndView mav) {
        String s = (String) session.getAttribute("user");
        mav.addObject("username", s);
        mav.setViewName("moive/urgeMoreList");
        return mav;
    }

    @ResponseBody
    @RequestMapping(value = "/moive/urgeMoreList")
    public JSONObject searchurgeMoreList(HttpServletRequest request, HttpServletResponse response) {

        Integer draw = Integer.parseInt(request.getParameter("draw"));
        Integer length = Integer.parseInt(request.getParameter("length"));
        Integer start = Integer.parseInt(request.getParameter("start"));

        int pageSize = length;
        Map param = new HashMap();
        param.put("currentPage", start);
        param.put("pageSize", pageSize);
        param.put("failType", MoiveFail.FAILTYPE_URGEMORE);
        param.put("status", MoiveFail.HANLING);

        String search = request.getParameter("search[value]");
        if (StringUtils.isNotBlank(search)) {
            param.put("mnamelike", search);
        }

        int count = moiveService.getMoiveFailCountByParam(param);

        List<MoiveFail> moives = moiveService.searchMoiveFailByParam(param);

        Pageable<MoiveFail> pageable = new Pageable<>();
        pageable.setData(moives);
        pageable.setDraw(draw);
        pageable.setTotal(Long.parseLong(count + ""));
        return JSONObject.parseObject(JSONObject.toJSONString(pageable));
    }

    @ResponseBody
    @RequestMapping(value = "/moive/failList")
    public JSONObject searchMoiveListByParam(HttpServletRequest request, HttpServletResponse response) {

        Integer draw = Integer.parseInt(request.getParameter("draw"));
        Integer length = Integer.parseInt(request.getParameter("length"));
        Integer start = Integer.parseInt(request.getParameter("start"));

        int pageSize = length;
        Map param = new HashMap();
        param.put("currentPage", start);
        param.put("pageSize", pageSize);
        param.put("failType", MoiveFail.FAILTYPE_INVAILD);
        param.put("status", MoiveFail.HANLING);

        String search = request.getParameter("search[value]");
        if (StringUtils.isNotBlank(search)) {
            param.put("mnamelike", search);
        }

        int count = moiveService.getMoiveFailCountByParam(param);

        List<MoiveFail> moives = moiveService.searchMoiveFailByParam(param);

        Pageable<MoiveFail> pageable = new Pageable<>();
        pageable.setData(moives);
        pageable.setDraw(draw);
        pageable.setTotal(Long.parseLong(count + ""));
        return JSONObject.parseObject(JSONObject.toJSONString(pageable));
    }


    @ResponseBody
    @RequestMapping(value = "/moive/failupdate")
    public JSONObject updateMoiveFailByParam(@RequestParam("id") String id, @RequestParam("title") String title, @RequestParam("failid") String failid, @RequestParam("panurl") String panurl) {

        JSONObject result = new JSONObject();

        Moive moive = new Moive();
        try {

            MoiveFail moiveFail = new MoiveFail();
            moiveFail.setId(Integer.parseInt(failid));
            moiveFail.setStatus(MoiveFail.HANLED);
            //更新 moviefail 表信息
            moiveService.updateMoviveFail(moiveFail);

            //删除记录
            moiveService.deleteByPrimaryKey(Long.parseLong(id));
            //删除文件
            String filePath = PcmsConst.FILEPATH + "/" + id + ".html";
            fileService.deleFile(filePath);

            //重新 insert
            String ran = RandomNumber.randomKey(6);
            long idnew = Long.parseLong(ran);
            moive.setId(idnew);
            moive.setMname(title);
            String[] pram = panurl.split("提取码：");

            if (pram.length != 2) {
                throw new Exception("参数异常，上送url格式不对。");
            }

            moive.setPanurl(pram[0].replace("链接：", ""));
            moive.setPanpwd(pram[1].replace("复制这段内容后打开百度网盘手机App，操作更方便哦", ""));
            moive.setMurl(PcmsConst.url + idnew + ".html");
            moive.setUpdatetime(DateUtil.getCurTimestamp());
            moiveService.insertSelective(moive);

            Map map = new HashMap();
            map.put("moive", moive);
            fileService.genFile(map);

            result.put("desc", "");
            result.put("result", "success");

        } catch (Exception e) {
            e.printStackTrace();
            result.put(PcmsConst.RESPCODE, "999999");
            result.put(PcmsConst.RESPMSD, "系统异常");
            return result;
        }
        result.put(PcmsConst.RESPCODE, "000000");
        result.put(PcmsConst.RESPMSD, "成功");
        return result;
    }


    @ResponseBody
    @RequestMapping(value = "/moive/ignore")
    public JSONObject ignore(HttpServletRequest request, HttpServletResponse response) {
        String id = request.getParameter("id");
        String ids = request.getParameter("ids");


        JSONObject result = new JSONObject();

        try {

            if (StringUtils.isNotBlank(ids)) {
                String[] idstr = ids.split(",");
                for (int i = 0; i < idstr.length; i++) {
                    MoiveFail moiveFail = new MoiveFail();
                    moiveFail.setId(Integer.parseInt(idstr[i]));
                    moiveFail.setStatus(MoiveFail.INGORE);
                    //更新 moviefail 表信息
                    moiveService.updateMoviveFail(moiveFail);
                }

            } else {
                MoiveFail moiveFail = new MoiveFail();
                moiveFail.setId(Integer.parseInt(id));
                moiveFail.setStatus(MoiveFail.INGORE);
                //更新 moviefail 表信息
                moiveService.updateMoviveFail(moiveFail);
            }
            result.put("desc", "");
            result.put("result", "success");
        } catch (Exception e) {
            e.printStackTrace();
            result.put(PcmsConst.RESPCODE, "999999");
            result.put(PcmsConst.RESPMSD, "系统异常");
            return result;
        }
        result.put(PcmsConst.RESPCODE, "000000");
        result.put(PcmsConst.RESPMSD, "成功");
        return result;
    }


    /**
     * 后台 求电影列表页展示
     *
     * @param model
     * @param session
     * @param mav
     * @return
     */
    @RequestMapping("/moive/requestListView")
    public ModelAndView requestListView(Model model, HttpSession session, ModelAndView mav) {
        String s = (String) session.getAttribute("user");
        mav.addObject("username", s);
        mav.setViewName("moive/requestList");
        return mav;
    }

    /**
     * 求电影列表页 展示数据ajax请求
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/moive/requestList")
    public JSONObject searchRequestListByParam(HttpServletRequest request) {

        Integer draw = Integer.parseInt(request.getParameter("draw"));
        Integer length = Integer.parseInt(request.getParameter("length"));
        Integer start = Integer.parseInt(request.getParameter("start"));

        int pageSize = length;
        Map param = new HashMap();
        param.put("currentPage", start);
        param.put("pageSize", pageSize);
        param.put("status",PcmsConst.RequestMoive.STATUS_INIT);

        String search = request.getParameter("search[value]");
        if (StringUtils.isNotBlank(search)) {
            param.put("mnamelike", search);
        }

        int count = moiveService.getMoiveRequestCountByParam(param);

        List<RequestMoive> moives = moiveService.SearchMoiveRequestByParam(param);

        Pageable<RequestMoive> pageable = new Pageable<>();
        pageable.setData(moives);
        pageable.setDraw(draw);
        pageable.setTotal(Long.parseLong(count + ""));
        return JSONObject.parseObject(JSONObject.toJSONString(pageable));
    }

    /**
     * 用户求电影数据 删除
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/moive/reqdelete")
    public JSONObject delete(HttpServletRequest request) {

        String id = request.getParameter("id");
        String ids = request.getParameter("ids");

        JSONObject result = new JSONObject();
        try {
            if (StringUtils.isNotBlank(ids)) {
                String[] idstr = ids.split(",");
                for (int i = 0; i < idstr.length; i++) {
                    Integer idi = Integer.parseInt(idstr[i]);
                    moiveService.deleteRequestMoiveByPrimaryKey(idi);
                }
            } else {
                Integer idi = Integer.parseInt(id);
                moiveService.deleteRequestMoiveByPrimaryKey(idi);
            }
        } catch (NumberFormatException e) {
            e.printStackTrace();
            result.put(PcmsConst.RESPCODE, "999999");
            result.put(PcmsConst.RESPMSD, "系统异常");
            return result;
        }
        result.put(PcmsConst.RESPCODE, "000000");
        result.put(PcmsConst.RESPMSD, "成功");
        return result;
    }

    /**
     * 标记 用户求电影数据 状态为已处理
     *
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/moive/reqDone")
    public JSONObject reqDone(HttpServletRequest request) {

        String id = request.getParameter("id");
        JSONObject result = new JSONObject();
        try {

            Integer idi = Integer.parseInt(id);

            Map map = new HashMap();
            map.put("id",idi);
            RequestMoive moive =moiveService.getRequestMoiveByParam(map);
            moive.setId(idi);
            moive.setStatus(PcmsConst.RequestMoive.STATUS_HASHANDLE);
            moiveService.updateByPrimaryKeySelective(moive);


            String requestList =redisService.hget(RedisConts.REQUEST_MOIVE_KEY,UnicodeUtil.string2Unicode(moive.getMoivename()));
            if(StringUtils.isNotBlank(requestList)){
                logger.info("从redis 中取出的数据为:"+requestList);
                JSONArray requestArray = JSONArray.parseArray(requestList);
                for(int i=0;i<requestArray.size();i++){
                    String str =  requestArray.getString(i);
                    JSONObject req = JSONObject.parseObject(str);
                    String requestUserId=req.getString("requestUserId");
                    long expire=req.getLong("expire");
                    long now = new Date().getTime();
                    if(now<expire){
                        //发送请求给用户
                        JSONObject resp = new JSONObject();
                        resp.put("touser",requestUserId);
                        resp.put("msgtype","text");
                        JSONObject text = new JSONObject();
                        text.put("content","资源已更，公众号再次输入");
                        resp.put("text",text);

                        //响应用户请求
                        String url = xiaoTokenService.getAccessToken();
                        if(StringUtils.isNotBlank(url)){
                            String result1= HttpClient.doPostForJson("https://api.weixin.qq.com/cgi-bin/message/custom/send?access_token="+url,resp.toJSONString());
                            logger.info("实时回复用户请求信息信息: "+result1);
                        }
                    }
                }
            }

        } catch (NumberFormatException e) {
            e.printStackTrace();
            result.put(PcmsConst.RESPCODE, "999999");
            result.put(PcmsConst.RESPMSD, "系统异常");
            return result;
        }

        result.put(PcmsConst.RESPCODE, "000000");
        result.put(PcmsConst.RESPMSD, "成功");
        return result;
    }


}
