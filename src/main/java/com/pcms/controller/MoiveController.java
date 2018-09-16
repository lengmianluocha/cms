package com.pcms.controller;

import com.alibaba.fastjson.JSONObject;
import com.pcms.domain.Moive;
import com.pcms.domain.Pageable;
import com.pcms.domain.RequestMoive;
import com.pcms.service.FileService;
import com.pcms.service.MoiveService;
import com.pcms.util.PcmsConst;
import com.pcms.util.RandomNumber;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.*;
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
    public JSONObject addMovie(@RequestParam("title") String title, @RequestParam("cont") String cont, @RequestParam("tags") String tags, @RequestParam("panurl") String panurl, @RequestParam("panpwd") String panpwd, HttpServletRequest request, HttpServletResponse response) {
        JSONObject result = new JSONObject();
        try {
                String ran = RandomNumber.randomKey(6);
                long id2 = Long.parseLong(ran);
                Moive moive = new Moive();
                moive.setMname(title);
                moive.setAbstracts(cont);
                moive.setPanurl(panurl);
                moive.setPanpwd(panpwd);
                moive.setMurl(PcmsConst.url + id2 + ".html");
                moive.setId(id2);

                moiveService.insert(moive);
                Map param = new HashMap();
                param.put("moive", moive);

                fileService.genFile(param);
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
        if (StringUtils.isNotBlank(search)) {
            param.put("mnamelike", search);
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
                    //删除movie
                    moiveService.deleteByPrimaryKey(moive.getId());
                    //删除文件
                    String filePath = PcmsConst.FILEPATH + "/" + moive.getId() + ".html";
                    fileService.deleFile(filePath);
                    //重新 insert
//                    String ran = RandomNumber.randomKey(6);
//                    long id = Long.parseLong(ran);
//                    moive.setId(id);
                    moive.setMname(moive.getMname());
                    moive.setAbstracts(moive.getAbstracts());
                    moive.setPanurl(moive.getPanurl());
                    moive.setPanpwd(moive.getPanpwd());
                    moive.setMurl(PcmsConst.url + moive.getId() + ".html");
                    moive.setId(moive.getId());
                    Map map = new HashMap();
                    map.put("moive", moive);
                    moiveService.insertSelective(moive);
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
        if (StringUtils.isBlank(data)) {

        }
        JSONObject moiveReq = JSONObject.parseObject(data);
        String id = moiveReq.getString("id");
        String name = moiveReq.getString("title");
        String abstracts = moiveReq.getString("abstracts");
        String panurl = moiveReq.getString("panurl");
        String panpwd = moiveReq.getString("panpwd");

        //删除记录
        moiveService.deleteByPrimaryKey(Long.parseLong(id));
        //删除文件
        String filePath = PcmsConst.FILEPATH + "/" + id + ".html";
        fileService.deleFile(filePath);

        //重新 insert
        String ran = RandomNumber.randomKey(6);
        long idnew = Long.parseLong(ran);
        moive.setId(idnew);
        moive.setMname(name);
        moive.setAbstracts(abstracts);
        moive.setPanpwd(panpwd);
        moive.setPanurl(panurl);
        moive.setMurl(PcmsConst.url + idnew + ".html");
        moiveService.insertSelective(moive);

        Map map = new HashMap();
        map.put("moive", moive);
        fileService.genFile(map);

        rep.put("desc", "");
        rep.put("result", "success");

        return rep.toJSONString();
    }


    public ModelAndView requestInfo(HttpServletRequest request, HttpServletResponse response,ModelAndView mav) {


        String type = request.getParameter("requestType");

        //tring tToken = RandomNumber.ConfirmId(10);


        if (StringUtils.equals(PcmsConst.REQUESTTYPE_NOTFOUNDMOIVE, type)) {
            mav.setViewName("moive/moiveNotFound");
        } else if (StringUtils.equals(PcmsConst.REQUESTTYPE_MOIVELINKNOTUSE, type)) {
            mav.setViewName("moive/linkInvalid");
        }


        return mav;
    }


    public String requestOrAdvice(HttpServletRequest request, HttpServletResponse response) {

        JSONObject rep = new JSONObject();

        //TODO 获取微信用户的信息

        String name = request.getParameter("moiveName");

        RequestMoive requestMoive = new RequestMoive();
        requestMoive.setMoivename(name);
        requestMoive.setStatus(PcmsConst.RequestMoive.STATUS_INIT);

        moiveService.insertRequestMoive(requestMoive);


        rep.put("desc", "");
        rep.put("result", "success");
        return rep.toJSONString();

    }

    @RequestMapping(value = "/moive/upload", method = RequestMethod.POST)
    @ResponseBody
    public String upload(@RequestParam(value = "file",required = false) MultipartFile file) {
        JSONObject rep = new JSONObject();
        if (!file.isEmpty()) {
            try {
                // 这里只是简单例子，文件直接输出到项目路径下。
                // 实际项目中，文件需要输出到指定位置，需要在增加代码处理。
                // 还有关于文件格式限制、文件大小限制，详见：中配置。
                BufferedOutputStream out = new BufferedOutputStream(
                        new FileOutputStream(new File(file.getOriginalFilename())));
                out.write(file.getBytes());
                out.flush();
                out.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                rep.put("desc", "上传失败");
                rep.put("result", "failed");
            } catch (IOException e) {
                e.printStackTrace();
                rep.put("desc", "上传失败");
                rep.put("result", "failed");
            }

            rep.put("desc", "上传成功");
            rep.put("result", "success");

        } else {
            rep.put("desc", "上传失败，因为文件是空的.");
            rep.put("result", "failed");
        }

        return rep.toJSONString();

    }


}
