package com.pcms.controller;

import com.alibaba.fastjson.JSONObject;
import com.pcms.domain.Moive;
import com.pcms.domain.MoiveFail;
import com.pcms.domain.Pageable;
import com.pcms.domain.RequestMoive;
import com.pcms.service.MoiveService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
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

    @RequestMapping("/moive/failListView")
    public ModelAndView listView(Model model, HttpSession session, ModelAndView mav) {
        String s = (String) session.getAttribute("user");
        mav.addObject("username", s);
        mav.setViewName("moive/failList");
        return mav;
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


}
