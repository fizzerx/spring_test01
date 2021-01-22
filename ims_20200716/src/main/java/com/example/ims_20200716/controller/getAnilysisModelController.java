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
public class getAnilysisModelController {

    @Autowired
    JdbcTemplate jdbcTemplate;

    @RequestMapping("queryTemplateById")
    public Map<String, Object> getFenXiMuBan(@RequestBody Map<String, Object> reqParams, HttpServletRequest httpServletRequest){
        Map<String, Object> respMap = new HashMap<String, Object>();
        Map<String, Object> REQ_HEAD= (Map<String, Object>) reqParams.get("REQ_HEAD");
        Map<String, Object> BEQ_BODY = (Map<String, Object>) reqParams.get("BEQ_BODY");
        String queryTemplateId = (String)BEQ_BODY.get("queryTemplateId");
        String sql = "select A.MODEL_ID, A.GRP_ANAL_STATUS, A.STATISTICAL_INDICATORS,A.STATISTICAL_METHODS,B.TEMPLATE_ID, B.TEMPLATE_NAME from IMS_TRN_ANALYTICAL_MODEL A left join IMS_TRN_ANALYTICAL_TEMPLATE B on A.TEMPLATE_ID = B.TEMPLATE_ID where B.TEMPLATE_ID = ?" ;
        List<Map<String, Object>> queryList =  jdbcTemplate.queryForList(sql, queryTemplateId);

        List<Map<String, Object>> respList = new ArrayList<Map<String, Object>>();
        String MODEL_ID ="";
        String GRP_ANAL_STATUS ="";
        String STATISTICAL_INDICATORS ="";
        String STATISTICAL_METHODS ="";
        String TEMPLATE_ID ="";
        String TEMPLATE_NAME ="";
        if(queryList != null && queryList.size()>0){
            for(Map<String, Object> queryMap : queryList){
                MODEL_ID = (String)queryMap.get("MODEL_ID");
                GRP_ANAL_STATUS = (String)queryMap.get("GRP_ANAL_STATUS");
                STATISTICAL_INDICATORS = (String)queryMap.get("STATISTICAL_INDICATORS");
                STATISTICAL_METHODS = (String)queryMap.get("STATISTICAL_METHODS");

                List<Map<String, Object>> lableNameList = jdbcTemplate.queryForList("select A.LABLE_NAME from IMS_CFG_LABLE_INFO A left join IMS_TEL_MODEL_LABLE B on A.LABLE_ID = B.LABLE_ID where B.MODEL_ID = ?", MODEL_ID);

                respList.add(getMap(MODEL_ID,GRP_ANAL_STATUS,STATISTICAL_INDICATORS,STATISTICAL_METHODS,lableNameList));
            }
            TEMPLATE_ID = (String)queryList.get(0).get("TEMPLATE_ID");
            TEMPLATE_NAME = (String)queryList.get(0).get("TEMPLATE_NAME");
        }
        respMap.put("templateId", TEMPLATE_ID);
        respMap.put("templateName", TEMPLATE_NAME);
        respMap.put("respList", respList);
        respMap.put("respCode", "0");
        respMap.put("msg", "成功！");
        return respMap;
    }

    public Map<String, Object> getMap(String MODEL_ID, String GRP_ANAL_STATUS,
                                      String STATISTICAL_INDICATORS, String STATISTICAL_METHODS,
                                      List<Map<String, Object>> lableNameList){
        Map<String, Object> respMap = new HashMap<String, Object>();
        respMap.put("modelId", MODEL_ID);
        respMap.put("grpAnalStatus", GRP_ANAL_STATUS);
        respMap.put("statisticalIndicators", STATISTICAL_INDICATORS);
        respMap.put("statistical", STATISTICAL_METHODS);
        respMap.put("lableNameList", lableNameList);
        return respMap;
    }
}
