package com.pcms.controller;

import com.alibaba.fastjson.JSONObject;
import com.pcms.domain.Moive;
import com.pcms.domain.Pageable;
import com.pcms.service.FileService;
import com.pcms.service.MoiveService;
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

@RestController
public class MoiveController {

    @Autowired
    private MoiveService moiveService;

    @Autowired
    private FileService fileService;

    @ResponseBody
    @RequestMapping("/moive/addview")
    public ModelAndView addView(Model model, ModelAndView mav, HttpSession session) {

        String s = (String) session.getAttribute("user");
        mav.addObject("username", s);
        mav.setViewName("moive/add");
        return mav;
    }

    @RequestMapping("/moive/list")
    public ModelAndView listView(Model model, HttpSession session, ModelAndView mav) {

        String s = (String) session.getAttribute("user");

        mav.addObject("username", s);
        mav.setViewName("moive/list");
        return mav;
    }

    @ResponseBody
    @RequestMapping("/moive/add")
    public JSONObject addMovie(@RequestParam("id") String id, @RequestParam("title") String title, @RequestParam("cont") String cont, @RequestParam("tags") String tags, @RequestParam("panurl") String panurl, @RequestParam("panpwd") String panpwd, HttpServletRequest request, HttpServletResponse response) {
        JSONObject result = new JSONObject();
        try {
            if (StringUtils.isBlank(id)) {
                String ran = RandomNumber.randomKey(6);
                long id2 = Long.parseLong(ran);
                Moive moive = new Moive();
                moive.setMname(title);
                moive.setAbstracts(cont);
                moive.setPanurl(panurl);
                moive.setPanpwd(panpwd);
                moive.setMurl(PcmsConst.url + id + ".html");
                moive.setId(id2);

                moiveService.insert(moive);
                Map param = new HashMap();
                param.put("moive", moive);

                fileService.genFile(param);
            } else {
                Long id3 = Long.parseLong(id);
                Moive moiveOld = moiveService.selectByPrimaryKey(id3);

                fileService.deleFile(moiveOld.getMurl());

                Moive moive = new Moive();
                moive.setMname(title);
                moive.setAbstracts(cont);
                moive.setPanurl(panurl);
                moive.setPanpwd(panpwd);
                moive.setMurl(PcmsConst.url + id + ".html");
                moive.setId(id3);
                moiveService.updateByPrimaryKeySelective(moive);
                Map param = new HashMap();
                param.put("moive", moive);
                fileService.genFile(param);
            }
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
    @RequestMapping("/moive/updateView")
    public ModelAndView updateMovieView(HttpServletRequest request, HttpServletResponse response, ModelAndView mav) {

        try {
            String idStr = request.getParameter("id");

            if (StringUtils.isBlank(idStr)) {
                mav.setViewName("redirect:/blank");
            }

            Long id = Long.parseLong(idStr);

            Moive moive = moiveService.selectByPrimaryKey(id);

            if (moive == null) {

            }
            //fileService.deleFile(moive.getMurl());
            mav.addObject(moive);
        } catch (Exception e) {
            e.printStackTrace();
        }
        mav.setViewName("moive/update");
        return mav;
    }


    @ResponseBody
    @RequestMapping(value = "/moive/list2")
    public JSONObject searchMoiveListByParam(HttpServletRequest request, HttpServletResponse response) {

        Integer draw = Integer.parseInt(request.getParameter("draw"));
        Integer length = Integer.parseInt(request.getParameter("length"));
        Integer start = Integer.parseInt(request.getParameter("start"));

        int pageSize = length;
        Map param = new HashMap();
        param.put("currentPage", start);
        param.put("pageSize", pageSize);

        String search = request.getParameter("search[value]");
        if(StringUtils.isNotBlank(search)){
            param.put("mnamelike",search);
        }




        int count = moiveService.getMoiveCountByParam(param);

        List<Moive> moives = moiveService.searchMoiveByParam(param);

        Pageable<Moive> pageable = new Pageable<>();
        pageable.setData(moives);
        pageable.setDraw(draw);
        pageable.setTotal(Long.parseLong(count + ""));

        return JSONObject.parseObject(JSONObject.toJSONString(pageable));
    }

    @ResponseBody
    @RequestMapping(value = "/moive/reset")
    public JSONObject moiveReset(HttpServletRequest request, HttpServletResponse response) {
        JSONObject rep = new JSONObject();

        int pageSize = 10;
        Map param = new HashMap();

        int count = moiveService.getMoiveCountByParam(param);
        int pages = count % pageSize == 0 ? count / pageSize : count / pageSize + 1;

        for (int i = 1; i <= pages; i++) {
            int start = (i - 1) * pageSize;
            param.put("currentPage", start);
            param.put("pageSize", pageSize);

            List<Moive> moives = moiveService.searchMoiveByParam(param);

            for (Moive moive : moives) {
                try {
                    String ran = RandomNumber.randomKey(6);
                    long id = Long.parseLong(ran);

                    moive.setMname(moive.getMname());
                    moive.setAbstracts(moive.getAbstracts());
                    moive.setPanurl(moive.getPanurl());
                    moive.setPanpwd(moive.getPanpwd());
                    moive.setMurl(PcmsConst.url + id + ".html");
                    moive.setId(id);
                    Map map = new HashMap();
                    map.put("moive", moive);
                    moiveService.updateByMoiveName(moive);
                    fileService.genFile(map);
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                }
            }
        }

        return rep;
    }

    @ResponseBody
    @RequestMapping("/moive/get")
    public String getMoiveByParam(@RequestParam("id") String id) {

        Map param = new HashMap();
        param.put("id", id);

        Moive moive = moiveService.getMoiveByParam(param);
        return JSONObject.toJSONString(moive);

    }

    @ResponseBody
    @RequestMapping("/moive/update")
    public String updateMoiveByParam(HttpServletRequest request, HttpServletResponse response) {

        JSONObject rep = new JSONObject();

        Moive moive = new Moive();

        String data = request.getParameter("data");
        if(StringUtils.isBlank(data)){

        }
        JSONObject moiveReq=JSONObject.parseObject(data);
        String id = moiveReq.getString("id");
        String name = moiveReq.getString("title");
        String abstracts = moiveReq.getString("abstracts");
        String panurl = moiveReq.getString("panurl");
        String panpwd =moiveReq.getString("panpwd");

        Moive old = moiveService.selectByPrimaryKey(Long.parseLong(id));

        moive.setId(old.getId());
        moive.setMname(name);
        moive.setAbstracts(abstracts);
        moive.setPanpwd(panpwd);
        moive.setPanurl(panurl);
        moiveService.updateByPrimaryKeySelective(moive);

        fileService.deleFile(old.getMurl());

        Map map = new HashMap();
        map.put("moive", moive);
        fileService.genFile(map);

        rep.put("desc","");
        rep.put("result","success");

        return rep.toJSONString();

    }


}
