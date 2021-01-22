package com.example.ims_20200716.controller;

import com.mysql.cj.util.StringUtils;
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
public class DeleteGroupController {

    @Autowired
    JdbcTemplate jdbcTemplate;
    @RequestMapping("/deleteGroup")
    public Map<String, Object> deleteGroup(@RequestBody Map<String, Object> deleteParams, HttpServletRequest request) {

        Map<String, Object> resMap = new HashMap<String, Object>();
        Map<String, Object> REQ_HEAD= (Map<String, Object>) deleteParams.get("REQ_HEAD");
        Map<String, Object> BEQ_BODY = (Map<String, Object>) deleteParams.get("BEQ_BODY");
        String transServiceCode = (String) REQ_HEAD.get("TransServiceCode");

        if(BEQ_BODY != null){
            Object deleteId = BEQ_BODY.get("deleteId");
            String sql = "";
            if(transServiceCode.equals("deleteGroup")){ // 客群
                sql = "delete from IMS_TRN_GROUP_INFO where CUST_GRP_ID = ?";
            } else if(transServiceCode.equals("deleAnalytical")){ // 客群分析
                // 查询筛选条件表
                List<Map<String, Object>> fetChAll = jdbcTemplate.queryForList("select * from IMS_TRN_ANALYTICAL_MODEL where TEMPLATE_ID ='"+deleteId+"'");
                if(!fetChAll.isEmpty() && fetChAll.size() > 0){
                    for(Map<String, Object> analyTicalMap : fetChAll){
                        jdbcTemplate.update("delete from IMS_TEL_MODEL_LABLE where MODEL_ID ='"+analyTicalMap.get("MODEL_ID")+"'");
                    }
                }
                // 删除筛选条件
                jdbcTemplate.update("delete from IMS_TRN_ANALYTICAL_MODEL where TEMPLATE_ID = '"+deleteId+"'");
                sql = "delete from IMS_TRN_GROUP_ANALYTICAL where GRP_ANAL_ID = ?";

            } else if(transServiceCode.equals("deleTemplate")){ // 模板
                sql = "delete from IMS_TRN_ANALYTICAL_TEMPLATE where TEMPLATE_ID = ?";
            }

            if(StringUtils.isNullOrEmpty((String) deleteId)) {
                resMap.put("respCode", "-1");
                resMap.put("msg", "客群ID为空!");
                return resMap;
            }
            int deleteNum = jdbcTemplate.update(sql, deleteId);
            if(deleteNum > 0) {
                resMap.put("respCode", "0");
                resMap.put("msg", "成功！");
            } else {
                resMap.put("respCode", "-1");
                resMap.put("msg", "删除失败！");
                return resMap;
            }
        }
        return resMap;
    }
}
