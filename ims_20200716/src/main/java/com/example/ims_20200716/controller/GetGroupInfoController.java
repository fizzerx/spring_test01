package com.example.ims_20200716.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.sql.*;
import java.util.*;
import java.util.Date;

@RestController
public class GetGroupInfoController {

    @Autowired
    JdbcTemplate jdbcTemplate;

    @RequestMapping("getGroupInfo")
    public Map<String, Object> getLableInfoByLableName(@RequestBody Map<String, Object> loginParams, HttpServletRequest request) {
        Map<String, Object> respMap = new HashMap<String, Object>();
        Map<String, Object> REQ_HEAD = new HashMap<String, Object>();
        Map<String, Object> BEQ_BODY = new HashMap<String, Object>();
        try {
            REQ_HEAD = (Map) loginParams.get("REQ_HEAD");
            BEQ_BODY = (Map) loginParams.get("BEQ_BODY");
            Integer page = (Integer) BEQ_BODY.get("page");
            Integer pageSize = (Integer) BEQ_BODY.get("pageSize");

            String sql = "select CUST_GRP_ID,CUST_GRP_NAME ,CUST_GRP_SCOPE ,CUST_GRP_SOURCE ,EXECUTIVE_STATUS ,CREATER , CREATE_DATE , CREATE_TIME ,LAST_MODIFIER,LAST_MODIFY_DATE, LAST_MODIFY_TIME from IMS_TRN_GROUP_INFO limit "+(page-1)*pageSize+","+pageSize+"";

            List<Map<String, Object>> queryList = jdbcTemplate.queryForList(sql);
            List<Map<String, String>> lableList = new ArrayList<Map<String, String>>();
            if(queryList != null) {
                for (Map<String, Object> groupMap : queryList) {
                    String custGrpId = (String)groupMap.get("CUST_GRP_ID");
                    String custGrpName = (String)groupMap.get("CUST_GRP_NAME");
                    String custGrpScope = (String)groupMap.get("CUST_GRP_SCOPE");
                    String custGrpSource = (String)groupMap.get("CUST_GRP_SOURCE");
                    String executiveStatus = (String)groupMap.get("EXECUTIVE_STATUS");
                    String creater = (String)groupMap.get("CREATER");
                    String createDate = ((Date)groupMap.get("CREATE_DATE")).toString();
                    String lastModifier = (String)groupMap.get("LAST_MODIFIER");
                    String lastModifyDate = groupMap.get("LAST_MODIFY_DATE").toString();

                    lableList.add(getGroupList(custGrpId, custGrpName,
                            custGrpScope, custGrpSource, executiveStatus, creater, createDate,
                           lastModifier, lastModifyDate));
                }
            }
            String countSql = "select COUNT(0) AS COUNT from IMS_TRN_GROUP_INFO";
            Map<String, Object> countMap = jdbcTemplate.queryForMap(countSql);
            Long count = 0L;
            if (countMap != null) {
                count = (Long) countMap.get("COUNT");
            }
            respMap.put("count", count);
            respMap.put("page", page);
            respMap.put("pageSize", pageSize);
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




    public Map<String, String> getGroupList( String custGrpId, String custGrpName,
                                            String custGrpScope, String custGrpSource, String executiveStatus, String creater, String createDate,
                                             String lastModifier, String lastModifyDate){
        Map<String, String> returnMap = new HashMap<String, String>();

        returnMap.put("custGrpId", custGrpId);
        returnMap.put("custGrpName", custGrpName);
        returnMap.put("custGrpScope", custGrpScope);
        returnMap.put("custGrpSource", custGrpSource);
        returnMap.put("executiveStatus", executiveStatus);
        returnMap.put("creater", creater);
        returnMap.put("createDate", createDate);
        returnMap.put("lastModifier", lastModifier);
        returnMap.put("lastModifyDate", lastModifyDate);
        return returnMap;
    }
}
