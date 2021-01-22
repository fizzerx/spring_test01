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
public class GetAnalysisModelNameController {

    @Autowired
    JdbcTemplate jdbcTemplate;

    @RequestMapping("getAnalysisModelName")
    public Map<String, Object> getAnalysisModelName(@RequestBody Map<String, Object> reqParams, HttpServletRequest httpServletRequest){
       Map<String, Object> respMap = new HashMap<String, Object>();
       Map<String, Object> REQ_HEAD= (Map<String, Object>) reqParams.get("REQ_HEAD");
       Map<String, Object> BEQ_BODY = (Map<String, Object>) reqParams.get("BEQ_BODY");
       String sql = "select TEMPLATE_ID,TEMPLATE_NAME from IMS_TRN_ANALYTICAL_TEMPLATE";
       List<Map<String, Object>> queryList = jdbcTemplate.queryForList(sql);
       List<Map<String, Object>> returnList = new ArrayList<Map<String, Object>>();
       if(queryList != null && queryList.size() > 0){
           String templateId = "";
           String templateName = "";
           for(Map<String, Object> queryData : queryList){
               templateId = (String) queryData.get("TEMPLATE_ID");
               templateName = (String) queryData.get("TEMPLATE_NAME");
               returnList.add(getAnalysisModelNameMap(templateId, templateName));
           }
       }
       respMap.put("respCode", "0");
       respMap.put("msg", "Successfully!");
       respMap.put("lableInfo", returnList);
       return respMap;
    }

    public Map<String, Object> getAnalysisModelNameMap(String templateId, String templateName){
        Map<String, Object> returnMap = new HashMap<String, Object>();
        returnMap.put("templateId", templateId);
        returnMap.put("templateName", templateName);
        return  returnMap;
    }

}
