package com.example.ims_20200716.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.sql.*;
import java.util.*;

@RestController
public class LableInfoController {

    @Autowired
    JdbcTemplate jdbcTemplate;

    @RequestMapping("getLableInfoByClassId")
    public Map<String, Object> getLableInfoByClassId(@RequestBody Map<String, Object> loginParams, HttpServletRequest request) {
        Map<String, Object> respMap = new HashMap<String, Object>();
        Map<String, String> REQ_HEAD = new HashMap<String, String>();
        Map<String, String> BEQ_BODY = new HashMap<String, String>();

        try {
            REQ_HEAD = (Map) loginParams.get("REQ_HEAD");
            BEQ_BODY = (Map) loginParams.get("BEQ_BODY");
            String lableClassId = BEQ_BODY.get("lableClassId");
            String sql = "select LABLE_ID, LABLE_NAME, LABLE_CLASS_ID,LABLE_TYPE from IMS_CFG_LABLE_INFO where LABLE_CLASS_ID = " + lableClassId + "";
            List<Map<String, Object>> qqueryList = jdbcTemplate.queryForList(sql);
            List<Map<String, String>> lableList = new ArrayList<Map<String, String>>();
            for(Map<String, Object> qMap : qqueryList) {
                String lableId = (String)qMap.get("LABLE_ID");
                String lableName = (String)qMap.get("LABLE_NAME");
                String returnlableCLassId = (String)qMap.get("LABLE_CLASS_ID");
                String lableType = (String)qMap.get("LABLE_TYPE");
                lableList.add(getLableList(lableId, lableName, returnlableCLassId,lableType));
            }

            respMap.put("lableInfo", lableList);

            respMap.put("respCode", "0");
            respMap.put("msg", "成功！");
        } catch (EmptyStackException em) {
            em.printStackTrace();
            respMap.put("respCode", "999999");
            respMap.put("msg", "请求参数有误！");
        } catch (Exception e) {
            e.printStackTrace();
            respMap.put("respCode", "999999");
            respMap.put("msg", "处理异常，请联系技术人员！");
        }
        return respMap;
    }

    @RequestMapping("getLableInfoByLableName")
    public Map<String, Object> getLableInfoByLableName(@RequestBody Map<String, Object> loginParams, HttpServletRequest request) {
        Map<String, Object> respMap = new HashMap<String, Object>();
        Map<String, String> REQ_HEAD = new HashMap<String, String>();
        Map<String, String> BEQ_BODY = new HashMap<String, String>();

        try {
            REQ_HEAD = (Map) loginParams.get("REQ_HEAD");
            BEQ_BODY = (Map) loginParams.get("BEQ_BODY");
            String lableName = BEQ_BODY.get("lableName");
            String sql = "select LABLE_ID, LABLE_NAME, LABLE_CLASS_ID,LABLE_TYPE from IMS_CFG_LABLE_INFO where LABLE_NAME like '%" + lableName.trim() + "%'";
            List<Map<String, Object>> queryList = jdbcTemplate.queryForList(sql);
            List<Map<String, String>> lableList = new ArrayList<Map<String, String>>();
            if(queryList != null) {
                for (Map<String, Object> lableData : queryList) {
                    String lableId = (String)lableData.get("LABLE_ID");
                    String retrurnLableName = (String)lableData.get("LABLE_NAME");
                    String returnlableCLassId = (String)lableData.get("LABLE_CLASS_ID");
                    String lableType = (String)lableData.get("LABLE_TYPE");
                    lableList.add(getLableList(lableId, retrurnLableName, returnlableCLassId,lableType));
                }
            }
            
            respMap.put("lableInfo", lableList);

            respMap.put("respCode", "0");
            respMap.put("msg", "成功！");
        } catch (EmptyStackException em) {
            em.printStackTrace();
            respMap.put("respCode", "999999");
            respMap.put("msg", "请求参数有误！");
        } catch (Exception e) {
            e.printStackTrace();
            respMap.put("respCode", "999999");
            respMap.put("msg", "处理异常，请联系技术人员！");
        }
        return respMap;
    }




    public Map<String, String> getLableList(String classId, String className, String lableClassId, String lableType){
        Map<String, String> returnMap = new HashMap<String, String>();
        returnMap.put("lableId", classId);
        returnMap.put("lableName", className);
        returnMap.put("lableClassId", lableClassId);
        returnMap.put("lableType", lableType);
        return returnMap;
    }
}
