package com.example.ims_20200716.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class GetDimInfoController {
    @Autowired
    JdbcTemplate jdbcTemplate;

    @RequestMapping("/getDimInfo")
    public Map<String, Object> getDimInfo(HttpServletRequest httpServletRequest){
        Map<String, Object> respMap = new HashMap<String, Object>();
        List<Map<String, Object>> respDimInfo = new ArrayList<Map<String, Object>>();

        respDimInfo = jdbcTemplate.queryForList("select LABLE_ID, LABLE_NAME from IMS_CFG_LABLE_INFO where LABLE_TYPE = 'class'");

        respMap.put("respDimInfo", respDimInfo);
        respMap.put("respCode", 0);
        return respMap;
    }
}
