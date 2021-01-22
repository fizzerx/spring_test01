package com.example.ims_20200716.controller;

import org.apache.ibatis.mapping.ResultMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class SearchGroupByIdController {
    @Autowired
    JdbcTemplate jdbcTemplate;

    @RequestMapping("searchGroupById")
    public Map<String, Object> searchGroupById(@RequestBody Map<String, Object> reqParams, HttpServletRequest httpServletRequest){
        Map<String, Object> respMap = new HashMap<String, Object>();
        Map<String, Object> queyrResultMap = new HashMap<String, Object>();
        Map<String, Object> REQ_HEAD = new HashMap<String, Object>();
        Map<String, Object> BEQ_BODY = new HashMap<String, Object>();
        BEQ_BODY = (Map<String, Object>)reqParams.get("BEQ_BODY");
        REQ_HEAD = (Map<String, Object>)reqParams.get("BEQ_BODY");;
        List<Map<String, Object>> selectGroupAnalytical = new ArrayList<Map<String, Object>>();
        List<Map<String, Object>> groupAnalyList = new ArrayList<Map<String, Object>>();
        if(BEQ_BODY != null) {

            String custGrpId = (String) BEQ_BODY.get("custGrpId");
            Object[] queryParams = {custGrpId};
            String sql = "select CUST_GRP_ID,CUST_GRP_NAME ,CUST_GRP_SCOPE ,CUST_GRP_SOURCE ,EXECUTIVE_STATUS ,CREATER , CREATE_DATE , CREATE_TIME ,LAST_MODIFIER,LAST_MODIFY_DATE, LAST_MODIFY_TIME from IMS_TRN_GROUP_INFO where CUST_GRP_ID=?";
            try{
                queyrResultMap = jdbcTemplate.queryForMap(sql, queryParams);

                // 查询分析信息
                selectGroupAnalytical = jdbcTemplate.queryForList("select * from IMS_TRN_GROUP_ANALYTICAL where CUST_GRP_ID = '"+custGrpId+"'");
                if(selectGroupAnalytical != null && selectGroupAnalytical.size() > 0){
                    for(Map<String, Object> selectGroupMap : selectGroupAnalytical){
                        List<Map<String, Object>> modelList = new ArrayList<Map<String, Object>>();
                        // 通过分析ID分析模块
                        List<Map<String, Object>> selectAnalyticalModel = jdbcTemplate.queryForList("select * from IMS_TRN_ANALYTICAL_MODEL where TEMPLATE_ID ='"+ selectGroupMap.get("GRP_ANAL_ID") +"'");

                        for(Map<String, Object> selectNanlyticalMap :  selectAnalyticalModel){
                            // 通过模块ID 查询维度
                            List<Map<String, Object>> selectModelLableList = jdbcTemplate.queryForList("select A.MODEL_ID, A.LABLE_ID, B.LABLE_NAME from IMS_TEL_MODEL_LABLE A inner join IMS_CFG_LABLE_INFO B ON A.LABLE_ID = B.LABLE_ID where A.MODEL_ID = '"+selectNanlyticalMap.get("MODEL_ID")+"'");
                            selectNanlyticalMap.put("selectModelLableList", selectModelLableList);
                            modelList.add(selectNanlyticalMap);
                        }
                        selectGroupMap.put("modelList",modelList);
                        groupAnalyList.add(selectGroupMap);
                    }
                }
            }catch (EmptyResultDataAccessException em){
                em.printStackTrace();
            }
        }
        respMap.put("groupInfo", queyrResultMap);
        respMap.put("groupAnalyList", groupAnalyList);
        respMap.put("respCode", "0");
        respMap.put("msg", "成功！");

        return respMap;
    }


}
