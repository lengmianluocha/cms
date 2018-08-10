package com.pcms.controller;

import com.alibaba.fastjson.JSONObject;
import com.pcms.domain.Moive;
import com.pcms.service.FileService;
import com.pcms.service.MoiveService;
import com.pcms.util.PcmsConst;
import com.pcms.util.RandomNumber;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

@Controller
public class MoiveController {

    @Autowired
    private MoiveService moiveService;

    @Autowired
    private FileService fileService;

    @RequestMapping("/moive/addview")
    public String LoginPage(Model model) {
        return "/moive/add";
    }

    @ResponseBody
    @RequestMapping("/moive/add")
    public JSONObject addMovie(@RequestParam("title") String title, @RequestParam("cont") String cont, @RequestParam("tags") String tags, @RequestParam("panurl") String panurl, @RequestParam("panpwd") String panpwd, HttpServletRequest request, HttpServletResponse response) {

        JSONObject result = new JSONObject();
        try {
            String ran = RandomNumber.randomKey(6);
            long id = Long.parseLong(ran);
            Moive moive = new Moive();
            moive.setMname(title);
            moive.setAbstracts(cont);
            moive.setPanurl(panurl);
            moive.setPanpwd(panpwd);
            moive.setMurl(PcmsConst.url+id+".html");
            moive.setId(id);

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
}
