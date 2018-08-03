package com.pcms.controller.api;

import com.alibaba.fastjson.JSONObject;
import com.pcms.domain.Moive;
import com.pcms.service.MoiveService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 提供外部调用，搜索查询
 */
@RestController()
public class SearchController {

    @Autowired
    private MoiveService moiveService;

    @RequestMapping(value = "/moive/search",method= RequestMethod.GET)
    public String SearchMovie(@RequestParam("keyword") String keyword) {

        Map map = new HashMap();
        map.put("mnamelike", keyword);

        List<Moive> list = moiveService.searchMoiveByParam(map);

        return JSONObject.toJSONString(list);
    }

}
