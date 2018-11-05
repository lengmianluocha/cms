package com.pcms.controller;

import com.alibaba.fastjson.JSONObject;
import com.pcms.domain.Moive;
import com.pcms.domain.Pageable;
import com.pcms.service.FileService;
import com.pcms.service.MoiveService;
import com.pcms.util.DateUtil;
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

    @RequestMapping("/moive/list")
    public ModelAndView listView(Model model, HttpSession session, ModelAndView mav) {
        String s = (String) session.getAttribute("user");
        mav.addObject("username", s);
        mav.setViewName("moive/list");
        return mav;
    }

    @ResponseBody
    @RequestMapping("/moive/add")
    public JSONObject addMovie(@RequestParam("title") String title, @RequestParam("tags") String tags, @RequestParam("panurl") String panurl, HttpServletRequest request, HttpServletResponse response) {
        JSONObject result = new JSONObject();
        try {
            String ran = RandomNumber.randomKey(6);
            long id2 = Long.parseLong(ran);
            Moive moive = new Moive();
            moive.setMname(title);

            String[] pram = panurl.split("提取码：");
            moive.setPanurl(pram[0].replace("链接：",""));
            moive.setPanpwd(pram[1].replace("复制这段内容后打开百度网盘手机App，操作更方便哦",""));
            moive.setMurl(PcmsConst.url + id2 + ".html");
            moive.setId(id2);
            moive.setUpdatetime(DateUtil.getCurTimestamp());

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
                    moive.setMname(moive.getMname());
                    moive.setAbstracts(moive.getAbstracts());
                    moive.setPanurl(moive.getPanurl());
                    moive.setPanpwd(moive.getPanpwd());
                    moive.setMurl(PcmsConst.url + moive.getId() + ".html");
                    moive.setId(moive.getId());
                    moive.setUpdatetime(DateUtil.getCurTimestamp());
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

    @RequestMapping(value = "/moive/search", method = RequestMethod.GET)
    public String SearchMovie(@RequestParam("keyword") String keyword) {

        Map map = new HashMap();
        map.put("mnamelike", keyword);

        List<Moive> list = moiveService.searchMoiveByParam(map);

        return JSONObject.toJSONString(list);
    }

    @RequestMapping(value = "/moive/search", method = RequestMethod.POST)
    public String SearchMovie2(@RequestParam("keyword") String keyword) {

        Map map = new HashMap();
        map.put("mnamelike", keyword);

        List<Moive> list = moiveService.searchMoiveByParam(map);

        return JSONObject.toJSONString(list);
    }

    @ResponseBody
    @RequestMapping("/moive/get")
    public String getMoiveById(@RequestParam("id") String id) {

        Map param = new HashMap();
        param.put("id", id);

        Moive moive = moiveService.getMoiveByParam(param);
        return JSONObject.toJSONString(moive);

    }

    @ResponseBody
    @RequestMapping("/moive/getByName")
    public String getMoiveByName(@RequestParam("name") String name) {

        Map map = new HashMap();
        map.put("mname", name);

        Moive moive = moiveService.getMoiveByParam(map);
        return JSONObject.toJSONString(moive);

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
            mav.addObject(moive);
        } catch (Exception e) {
            e.printStackTrace();
        }
        mav.setViewName("moive/update");
        return mav;
    }

    @ResponseBody
    @RequestMapping("/moive/update")
    public JSONObject updateMoiveByParam(@RequestParam("id") String id, @RequestParam("title") String title, @RequestParam("panpwd") String panpwd, @RequestParam("panurl") String panurl, HttpServletRequest request, HttpServletResponse response) {

        JSONObject result = new JSONObject();

        Moive moive = new Moive();
        try {
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
            moive.setPanpwd(panpwd);
            moive.setPanurl(panurl);
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

    @RequestMapping(value = "/moive/upload", method = RequestMethod.POST)
    @ResponseBody
    public String upload(@RequestParam(value = "file", required = false) MultipartFile file) {
        JSONObject rep = new JSONObject();
        if (!file.isEmpty()) {
            try {
                // 这里只是简单例子，文件直接输出到项目路径下。
                // 实际项目中，文件需要输出到指定位置，需要在增加代码处理。
                // 还有关于文件格式限制、文件大小限制，详见：中配置。
                BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream(new File("/Users/feng/excel" + file.getOriginalFilename())));

                out.write(file.getBytes());
                out.flush();
                out.close();
                moiveService.parseExcelFile(new File(PcmsConst.FILEPATH + file.getOriginalFilename()));
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


    @ResponseBody
    @RequestMapping(value = "/moive/delete")
    public JSONObject delete(HttpServletRequest request, HttpServletResponse response) {

        String id = request.getParameter("id");

        JSONObject result = new JSONObject();

        try {
            Long idi = Long.parseLong(id);

            moiveService.deleteByPrimaryKey(idi);

            String filePath = PcmsConst.FILEPATH + "/" + idi + ".html";
            fileService.deleFile(filePath);
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
