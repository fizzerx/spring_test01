package com.example.ims_20200716.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

@RestController
public class getGroupByNameController {

    @Autowired
    JdbcTemplate jdbcTemplate;


    @RequestMapping("searchGroupByName")
    public Map<String, Object> getGroupByName(@RequestBody Map<String, Object> reqParams,
                                              HttpServletRequest httpServletRequest){
        Map<String, Object> resMap = new HashMap<String, Object>();
        Map<String, Object> REQ_HEAD= (Map<String, Object>) reqParams.get("REQ_HEAD");
        Map<String, Object> BEQ_BODY = (Map<String, Object>) reqParams.get("BEQ_BODY");
        if(BEQ_BODY != null){
            String groupName = (String)BEQ_BODY.get("groupName");
            Integer page = (Integer) BEQ_BODY.get("page");
            Integer pageSize = (Integer) BEQ_BODY.get("pageSize");
            String transServiceCode = (String) REQ_HEAD.get("TransServiceCode");
            String sql = "";
            String countSql = "";
            if("searchGroupByName".equals(transServiceCode)){
                sql = "select CUST_GRP_ID,CUST_GRP_NAME ,CUST_GRP_SCOPE ,CUST_GRP_SOURCE ,EXECUTIVE_STATUS ,CREATER , CREATE_DATE , CREATE_TIME ,LAST_MODIFIER,LAST_MODIFY_DATE, LAST_MODIFY_TIME from IMS_TRN_GROUP_INFO where CUST_GRP_NAME like '%" + groupName.trim() + "%' limit " +(page-1)*pageSize+","+pageSize+"";
                countSql = "select COUNT(0) AS COUNT from IMS_TRN_GROUP_INFO where CUST_GRP_NAME like '%" + groupName.trim() + "%'";
            } else if("searchTemplateByName".equals(transServiceCode)) {
                sql = "select TEMPLATE_ID, TEMPLATE_NAME, CREATER, CREATE_DATE, USER_NUMBER from IMS_TRN_ANALYTICAL_TEMPLATE where TEMPLATE_NAME like '%" + groupName.trim() + "%' limit " +(page-1)*pageSize+","+pageSize+"";
                countSql = "select COUNT(0) AS COUNT from IMS_TRN_ANALYTICAL_TEMPLATE where TEMPLATE_NAME like '%" + groupName.trim() + "%'";
            } else if("searchGroupAnalysisByName".equals(transServiceCode)) {
                sql = "select A.GRP_ANAL_ID,A.GRP_ANAL_NAME,A.CUST_GRP_ID,B.CUST_GRP_NAME,A.EXECUTIVE_STATUS,A.CREATER,A.CREATE_DATE from IMS_TRN_GROUP_ANALYTICAL A left join IMS_TRN_GROUP_INFO B on A.CUST_GRP_ID = B.CUST_GRP_ID where A.GRP_ANAL_NAME like '%" + groupName.trim() + "%' limit " +(page-1)*pageSize+","+pageSize+"";
                countSql = "select COUNT(0) AS COUNT from IMS_TRN_GROUP_ANALYTICAL where GRP_ANAL_NAME like '%" + groupName.trim() + "%'";
            }
            List<Map<String, Object>> queryList = jdbcTemplate.queryForList(sql);
            List<Map<String, Object>> lableList = new ArrayList<Map<String, Object>>();
            if(queryList != null &&
                    queryList.size() > 0){
                if("searchGroupByName".equals(transServiceCode)){
                    for (Map<String, Object> groupMap : queryList) {
                        String custGrpId = (String)groupMap.get("CUST_GRP_ID");
                        String custGrpName = (String)groupMap.get("CUST_GRP_NAME");
                        String custGrpScope = (String)groupMap.get("CUST_GRP_SCOPE");
                        String custGrpSource = (String)groupMap.get("CUST_GRP_SOURCE");
                        String executiveStatus = (String)groupMap.get("EXECUTIVE_STATUS");
                        String creater = (String)groupMap.get("CREATER");
                        String createDate = groupMap.get("CREATE_DATE").toString();
                        String lastModifier = (String)groupMap.get("LAST_MODIFIER");
                        String lastModifyDate = groupMap.get("LAST_MODIFY_DATE").toString();

                        lableList.add(getGroupList(custGrpId, custGrpName,
                                custGrpScope, custGrpSource, executiveStatus, creater, createDate,
                                 lastModifier, lastModifyDate));
                    }
                } else if("searchTemplateByName".equals(transServiceCode)) {
                    for (Map<String, Object> groupMap : queryList) {
                        String TEMPLATE_ID = (String)groupMap.get("TEMPLATE_ID");
                        String TEMPLATE_NAME = (String)groupMap.get("TEMPLATE_NAME");
                        String CREATER = (String)groupMap.get("CREATER");
                        String CREATE_DATE = groupMap.get("CREATE_DATE").toString();
                        int USER_NUMBER = (int)groupMap.get("USER_NUMBER");

                        lableList.add(getTemplateList(TEMPLATE_ID, TEMPLATE_NAME,
                                CREATER, CREATE_DATE, USER_NUMBER));
                    }
                } else if("searchGroupAnalysisByName".equals(transServiceCode)){
                    for (Map<String, Object> groupMap : queryList) {
                        String GRP_ANAL_ID = (String)groupMap.get("GRP_ANAL_ID");
                        String GRP_ANAL_NAME = (String)groupMap.get("GRP_ANAL_NAME");
                        String CUST_GRP_ID = (String)groupMap.get("CUST_GRP_ID");
                        String CUST_GRP_NAME = (String)groupMap.get("CUST_GRP_NAME");
                        String EXECUTIVE_STATUS = (String)groupMap.get("EXECUTIVE_STATUS");
                        String CREATER = (String)groupMap.get("CREATER");
                        String CREATE_DATE = groupMap.get("CREATE_DATE").toString();

                        lableList.add(getGroupAnalyticalList(GRP_ANAL_ID, GRP_ANAL_NAME,
                                CUST_GRP_ID, CUST_GRP_NAME, EXECUTIVE_STATUS, CREATER, CREATE_DATE));
                    }
                }

            } else {
                resMap.put("respCode", "0");
                resMap.put("msg", "查无记录！");
                return resMap;
            }

            Map<String, Object> countMap = jdbcTemplate.queryForMap(countSql);
            Long count = 0L;
            if (countMap != null) {
                count = (Long) countMap.get("COUNT");
            }
            resMap.put("respCode", "0");
            resMap.put("msg", "Successfully!");
            resMap.put("count", count);
            resMap.put("page", page);
            resMap.put("pageSize", pageSize);
            resMap.put("lableInfo", lableList);
        }
        return resMap;
    }

    /**
     * 分装客群内容
     * @param custGrpId
     * @param custGrpName
     * @param custGrpScope
     * @param custGrpSource
     * @param executiveStatus
     * @param creater
     * @param createDate
     * @param lastModifier
     * @param lastModifyDate
     * @return
     */
    public Map<String, Object> getGroupList( String custGrpId, String custGrpName,
                                             String custGrpScope, String custGrpSource, String executiveStatus, String creater, String createDate,
                                             String lastModifier, String lastModifyDate){
        Map<String, Object> returnMap = new HashMap<String, Object>();

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
    public Map<String, Object> getTemplateList(String TEMPLATE_ID, String TEMPLATE_NAME,
                                               String CREATER, String CREATE_DATE, int USER_NUMBER){
        Map<String, Object> returnMap = new HashMap<String, Object>();
        returnMap.put("templateId", TEMPLATE_ID);
        returnMap.put("templateName", TEMPLATE_NAME);
        returnMap.put("creater", CREATER);
        returnMap.put("createDate", CREATE_DATE);
        returnMap.put("userNumber", USER_NUMBER);
        return returnMap;
    }
    /**
     * 分装客群分析内容
     * @return
     */
    public Map<String, Object> getGroupAnalyticalList(String GRP_ANAL_ID, String GRP_ANAL_NAME,
                                                      String CUST_GRP_ID, String CUST_GRP_NAME,
                                                      String EXECUTIVE_STATUS, String CREATER,
                                                      String CREATE_DATE){
        Map<String, Object> returnMap = new HashMap<String, Object>();
        returnMap.put("grpAnalId", GRP_ANAL_ID);
        returnMap.put("grpAnalName", GRP_ANAL_NAME);
        returnMap.put("custGrpId", CUST_GRP_ID);
        returnMap.put("custGrpName", CUST_GRP_NAME);
        returnMap.put("executiveStatus", EXECUTIVE_STATUS);
        returnMap.put("create", CREATER);
        returnMap.put("createDate", CREATE_DATE);
        return returnMap;
    }
}
