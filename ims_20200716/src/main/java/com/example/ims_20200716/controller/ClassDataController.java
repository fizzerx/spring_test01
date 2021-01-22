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
public class ClassDataController {

    @Autowired
    JdbcTemplate jdbcTemplate;

    @RequestMapping("getClassInfo")
    public Map<String, Object> login(@RequestBody Map<String, Object> loginParams, HttpServletRequest request) {
        Map<String, Object> respMap = new HashMap<String, Object>();

        Map<String, String> REQ_HEAD = new HashMap<String, String>();
        Map<String, String> BEQ_BODY = new HashMap<String, String>();
        try {
            REQ_HEAD = (Map)loginParams.get("REQ_HEAD");
            BEQ_BODY = (Map)loginParams.get("BEQ_BODY");
            List<Map<String, String>> classList = new ArrayList<Map<String, String>>();
            // 注册JDBC
            String sql = "select * from ims_cfg_lable_class";
            List<Map<String, Object>> queryList = jdbcTemplate.queryForList(sql);
            if(queryList != null) {
                for(Map<String, Object> classData : queryList) {
                    String classId = (String)classData.get("LABLE_CLASS_ID");
                    String className = (String)classData.get("LABLE_CLASS_NAME");
                    classList.add(getClassList(classId, className));
                }
            }
            respMap.put("classData", classList);
            respMap.put("respCode", "0");
            respMap.put("msg", "Successful!");
        } catch(EmptyStackException em) {
            em.printStackTrace();
            respMap.put("respCode", "999999");
            respMap.put("msg", "请求参数有误！");
        } catch(Exception e) {
            e.printStackTrace();
            respMap.put("respCode", "999999");
            respMap.put("msg", "处理异常，请联系技术人员！");
        }        return respMap;
    }


    public Map<String, String> getClassList(String classId, String className){
        Map<String, String> returnMap = new HashMap<String, String>();
        returnMap.put("lableClassId", classId);
        returnMap.put("lableClassName", className);
        return returnMap;
    }
}
