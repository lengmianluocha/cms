package com.pcms.controller;

import com.alibaba.fastjson.JSONObject;
import com.pcms.domain.Moive;
import com.pcms.domain.MoiveFail;
import com.pcms.domain.Pageable;
import com.pcms.domain.RequestMoive;
import com.pcms.service.FileService;
import com.pcms.service.MoiveService;
import com.pcms.util.DateUtil;
import com.pcms.util.PcmsConst;
import com.pcms.util.RandomNumber;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
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
        param.put("failType",MoiveFail.FAILTYPE_URGEMORE);
        param.put("status",MoiveFail.HANLING);

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
        param.put("failType",MoiveFail.FAILTYPE_INVAILD);
        param.put("status",MoiveFail.HANLING);

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
    public JSONObject updateMoiveFailByParam(@RequestParam("id") String id, @RequestParam("title") String title, @RequestParam("failid") String failid, @RequestParam("panurl") String panurl, HttpServletRequest request, HttpServletResponse response) {

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

            if(pram.length!=2){
                throw new Exception("参数异常，上送url格式不对。");
            }

            moive.setPanurl(pram[0].replace("链接：",""));
            moive.setPanpwd(pram[1].replace("复制这段内容后打开百度网盘手机App，操作更方便哦",""));
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
    public JSONObject ignore(@RequestParam("id") String id, HttpServletRequest request, HttpServletResponse response) {

        JSONObject result = new JSONObject();

        try {

            MoiveFail moiveFail = new MoiveFail();
            moiveFail.setId(Integer.parseInt(id));
            moiveFail.setStatus(MoiveFail.INGORE);
            //更新 moviefail 表信息
            moiveService.updateMoviveFail(moiveFail);

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




    @RequestMapping("/moive/requestListView")
    public ModelAndView requestListView(Model model, HttpSession session, ModelAndView mav) {
        String s = (String) session.getAttribute("user");
        mav.addObject("username", s);
        mav.setViewName("moive/requestList");
        return mav;
    }

    @ResponseBody
    @RequestMapping(value = "/moive/requestList")
    public JSONObject searchRequestListByParam(HttpServletRequest request, HttpServletResponse response) {

        Integer draw = Integer.parseInt(request.getParameter("draw"));
        Integer length = Integer.parseInt(request.getParameter("length"));
        Integer start = Integer.parseInt(request.getParameter("start"));

        int pageSize = length;
        Map param = new HashMap();
        param.put("currentPage", start);
        param.put("pageSize", pageSize);

        String search = request.getParameter("search[value]");
        if (StringUtils.isNotBlank(search)) {
            param.put("mnamelike", search);
        }

        int count = moiveService.getMoiveRequestCountByParam(param);

        List<RequestMoive> moives = moiveService.MoiveRequestByParam(param);

        Pageable<RequestMoive> pageable = new Pageable<>();
        pageable.setData(moives);
        pageable.setDraw(draw);
        pageable.setTotal(Long.parseLong(count + ""));
        return JSONObject.parseObject(JSONObject.toJSONString(pageable));
    }

    @ResponseBody
    @RequestMapping(value = "/moive/reqdelete")
    public JSONObject delete(HttpServletRequest request, HttpServletResponse response) {

        String id = request.getParameter("id");
        JSONObject result = new JSONObject();
        try {
            Integer idi = Integer.parseInt(id);
            moiveService.deleteRequestMoiveByPrimaryKey(idi);
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

    @ResponseBody
    @RequestMapping(value = "/moive/reqDone")
    public JSONObject reqDone(HttpServletRequest request, HttpServletResponse response) {

        String id = request.getParameter("id");
        JSONObject result = new JSONObject();
        try {

            Integer idi = Integer.parseInt(id);
            RequestMoive moive = new RequestMoive();
            moive.setId(idi);
            moive.setStatus(PcmsConst.RequestMoive.STATUS_HASHANDLE);
            moiveService.updateByPrimaryKeySelective(moive);

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
