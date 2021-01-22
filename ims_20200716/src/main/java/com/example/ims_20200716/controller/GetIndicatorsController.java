package com.example.ims_20200716.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class GetIndicatorsController {
    @Autowired
    JdbcTemplate jdbcTemplate;

    @RequestMapping("/getIndicators")
    public Map<String, Object> getIndicators(@RequestBody Map<String, Object> getScreenContionParams, HttpServletRequest request) {
        Map<String, Object> resMap = new HashMap<String, Object>();
        Map<String, Object> REQ_HEAD= (Map<String, Object>) getScreenContionParams.get("REQ_HEAD");
        Map<String, Object> BEQ_BODY = (Map<String, Object>) getScreenContionParams.get("BEQ_BODY");
        String sql = "select LABLE_ID,LABLE_NAME from IMS_CFG_LABLE_INFO where LABLE_TYPE='num'";
        List<Map<String, Object>> getIndicatorsList = jdbcTemplate.queryForList(sql);

        resMap.put("respIndicators", getIndicatorsList);
        resMap.put("respCode", 0);
        return resMap;
    }

}
