package com.example.ims_20200716.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

@RestController
public class GroupAnalyListController {
    @Autowired
    JdbcTemplate jdbcTemplate;

    @RequestMapping("groupAnalyListMethod")
    public Map<String, Object> searchGroupById(@RequestBody Map<String, Object> reqParams, HttpServletRequest httpServletRequest){
        Map<String, Object> respMap = new HashMap<String, Object>();
        Map<String, Object> BEQ_BODY = new HashMap<String, Object>();
        BEQ_BODY = (Map<String, Object>)reqParams.get("BEQ_BODY");
        List<Map<String, Object>> selectGroupAnalytical = new ArrayList<Map<String, Object>>();
        List<Map<String, Object>> groupAnalyList = new ArrayList<Map<String, Object>>();
        if(BEQ_BODY != null) {
            String custGrpId = (String) BEQ_BODY.get("custGrpId");
            String custGrpName = (String) BEQ_BODY.get("custGrpName");
            String duiBiKeQunName = "";
            try{
                // 查询分析信息
                selectGroupAnalytical = jdbcTemplate.queryForList("select A.GRP_ANAL_ID,A.GRP_ANAL_NAME,A.CUST_GRP_ID,A.EXECUTIVE_STATUS,A.CONTRAST_GRP_ID from IMS_TRN_GROUP_ANALYTICAL A " +
                        "  where A.CUST_GRP_ID = '"+custGrpId+"'");
                if(selectGroupAnalytical != null && selectGroupAnalytical.size() > 0){
                    for(Map<String, Object> selectGroupMap : selectGroupAnalytical){
                        System.out.println(selectGroupMap.get("CONTRAST_GRP_ID"));
                        if(!selectGroupMap.get("CONTRAST_GRP_ID").toString().isEmpty()){
                            Map<String,Object> duiBiKeQunMap = jdbcTemplate.queryForMap("select CUST_GRP_NAME from IMS_TRN_GROUP_INFO where CUST_GRP_ID = '"+selectGroupMap.get("CONTRAST_GRP_ID")+"'");
                            duiBiKeQunName = duiBiKeQunMap.get("CUST_GRP_NAME").toString();
                        }

                        List<Map<String, Object>> modelList = new ArrayList<Map<String, Object>>();
                        // 通过分析ID分析模块
                        List<Map<String, Object>> selectAnalyticalModel = jdbcTemplate.queryForList("select MODEL_ID," +
                                "TEMPLATE_ID,GRP_ANAL_STATUS,STATISTICAL_INDICATORS,STATISTICAL_METHODS from IMS_TRN_ANALYTICAL_MODEL where TEMPLATE_ID ='"+ selectGroupMap.get("GRP_ANAL_ID") +"'");
                        List<Map<String, Object>> SelectModelList = new ArrayList<Map<String, Object>>();
                        for(Map<String, Object> selectNanlyticalMap :  selectAnalyticalModel){
                            String tongJiZhiBiaoName = "";
                            // 通过统计指标查询统计指标中文名
                            if(!selectNanlyticalMap.get("STATISTICAL_INDICATORS").toString().isEmpty()){
                                Map<String,Object> tongJiZhiBiaoMap = jdbcTemplate.queryForMap("select LABLE_NAME from IMS_CFG_LABLE_INFO where LABLE_ID ='"+selectNanlyticalMap.get("STATISTICAL_INDICATORS")+"'");

                                if(!tongJiZhiBiaoMap.isEmpty() && !tongJiZhiBiaoMap.get("LABLE_NAME").toString().isEmpty()){
                                    tongJiZhiBiaoName = tongJiZhiBiaoMap.get("LABLE_NAME").toString();
                                }
                            }

                            selectNanlyticalMap.put("tongJiZhiBiaoName", tongJiZhiBiaoName);
                            // 通过模块ID 查询维度
                            List<Map<String, Object>> selectModelLableList = jdbcTemplate.queryForList("select A.MODEL_ID, A.LABLE_ID,B.LABLE_CLASS_ID, B.LABLE_NAME from IMS_TEL_MODEL_LABLE A inner join IMS_CFG_LABLE_INFO B ON A.LABLE_ID = B.LABLE_ID where A.MODEL_ID = '"+selectNanlyticalMap.get("MODEL_ID")+"'");
                            List<String>  lableNameList = new ArrayList<String>();
                            List<Map<String, Object>> weiDuList = new ArrayList<Map<String, Object>>();
                            Set<String> rowAndColumnSet = new HashSet<String>();

                            List<Map<String, Object>> tableDataList = new ArrayList<Map<String, Object>>();
                            if(selectModelLableList != null && selectModelLableList.size() > 0){

                                for(Map<String, Object> selectModelMap : selectModelLableList){
                                    Object strArr = new Object[]{selectModelMap.get("LABLE_CLASS_ID"), selectModelMap.get("LABLE_ID")};
                                    selectModelMap.put("nameOneArr",strArr);
                                    String lableId = selectModelMap.get("LABLE_ID").toString();
                                    String lableName = selectModelMap.get("LABLE_NAME").toString();
                                    lableNameList.add(lableName);
                                    Map<String, Object> weiduMap = new HashMap<String, Object>();

                                    int[] dangQianKeQun = null;
                                    int[] duiBiKeQun = null;
                                    String sql = "select * from IMS_CFG_LABLE_OPTION where LABLE_ID = ?";
                                    Object [] paramsReq = new Object[1];
                                    paramsReq[0] = lableId;
                                    List<Map<String, Object>> lableOptionList = jdbcTemplate.queryForList(sql, paramsReq);
                                    weiduMap.put("weiDuName", lableName);
                                    //维度的选项集合
                                    weiduMap.put("lableOptionList", lableOptionList);
                                    weiDuList.add(weiduMap);
                                    List<String> optinList = new ArrayList<String>();
                                    // 查询当前客群和对比客群的统计数据
                                    if(lableOptionList != null && lableOptionList.size() > 0){
                                        dangQianKeQun = new int[lableOptionList.size()];
                                        duiBiKeQun = new int[lableOptionList.size()];
                                        Random random = new Random();
                                        Map<String, Object> tableRowMap = new HashMap<String, Object>();
                                        for(int i = 0;i < lableOptionList.size(); i++){
                                            optinList.add(lableOptionList.get(i).get("LABLE_DTL_NAME").toString());
                                            Map<String, Object> getScreenMap= lableOptionList.get(i);
                                            dangQianKeQun[i] = random.nextInt(1000000)%(1000000-1000+1) + 1000;
                                            duiBiKeQun[i] = random.nextInt(1000000)%(1000000-1000+1) + 1000;
                                        }
                                    }
                                    selectModelMap.put("duiBiKeQunName", duiBiKeQunName);
                                    selectModelMap.put("dangQianKeQunName", custGrpName);
                                    selectModelMap.put("optinList", optinList);
                                    selectModelMap.put("dangQianKeQun", dangQianKeQun);
                                    selectModelMap.put("duiBiKeQun", duiBiKeQun);
                                    selectModelMap.put("maxCount", 1000000);

                                    SelectModelList.add(selectModelMap);
                                    selectNanlyticalMap.put("selectModelLableList", SelectModelList);
                                }
                            }
                            selectNanlyticalMap.put("lableNameList",lableNameList);
                            selectNanlyticalMap.put("duiBiKeQunName",duiBiKeQunName);
                            selectNanlyticalMap.put("dangQianKeQunName",custGrpName);
                            String[] rowAndColumnList = null;
                            // 封装多维
                            if("2".equals(selectNanlyticalMap.get("GRP_ANAL_STATUS")) && weiDuList.size() == 2){
                                Map<String, Object> weiduMap_1 = weiDuList.get(0);
                                List<Map<String, Object>> weiduList_1 = (List<Map<String, Object>>)weiduMap_1.get("lableOptionList");
                                Map<String, Object> weiduMap_2 = weiDuList.get(1);
                                List<Map<String, Object>> weiduList_2 = (List<Map<String, Object>>)weiduMap_2.get("lableOptionList");
                                String rowAndColumn_1 = "";
                                if(weiduList_1.size() >= weiduList_2.size()){
                                    rowAndColumnList = new String[weiduList_2.size() + 1];
                                    rowAndColumn_1 = weiduMap_1.get("weiDuName") + "/" + weiduMap_2.get("weiDuName");
                                    rowAndColumnList[0] = rowAndColumn_1;
                                    int rowIndex = 1;
                                    for(Map<String, Object> weiDuOp2:weiduList_2){
                                        rowAndColumnList[rowIndex] = weiDuOp2.get("LABLE_DTL_NAME").toString();
                                        rowIndex++;
                                    }
                                    for(Map<String, Object> weiDUOp1: weiduList_1){
                                        Map<String, Object> tableDataMap = new HashMap<String, Object>();
                                        int i =0;
                                        tableDataMap.put(i+"", weiDUOp1.get("LABLE_DTL_NAME"));
                                        for(Map<String, Object> weiDuOp2:weiduList_2){
                                            i++;
                                            tableDataMap.put(i+"", 1000+"("+duiBiKeQunName+")," + 2000+"("+custGrpName+")" );
                                        }
                                        tableDataList.add(tableDataMap);
                                    }
                                } else {
                                    rowAndColumnList = new String[weiduList_1.size() + 1];
                                    rowAndColumn_1 = weiduMap_2.get("weiDuName") + "/" + weiduMap_1.get("weiDuName");
                                    rowAndColumnList[0] = rowAndColumn_1;
                                    int rowIndex = 1;
                                    for(Map<String, Object> weiDuOp:weiduList_1){
                                        rowAndColumnList[rowIndex] = weiDuOp.get("LABLE_DTL_NAME").toString();
                                        rowIndex++;
                                    }
                                    for(Map<String, Object> weiDUOp1: weiduList_2){
                                        Map<String, Object> tableDataMap = new HashMap<String, Object>();
                                        int i =0;
                                        tableDataMap.put(i+"", weiDUOp1.get("LABLE_DTL_NAME"));
                                        for(Map<String, Object> weiDuOp2:weiduList_1){
                                            i++;
                                            tableDataMap.put(i+"", 1000+"("+duiBiKeQunName+")," + 2000+"("+custGrpName+")" );
                                        }
                                        tableDataList.add(tableDataMap);
                                    }

                                }
                            }
                            selectNanlyticalMap.put("tableDataList", tableDataList);
                            selectNanlyticalMap.put("rowAndColumn", rowAndColumnList);
                            System.out.println("rowAndColumnList:"+rowAndColumnList);
                            modelList.add(selectNanlyticalMap);
                        }
                        selectGroupMap.put("modelList",modelList);
                        groupAnalyList.add(selectGroupMap);
                    }
                }
            } catch (EmptyResultDataAccessException em){
                em.printStackTrace();
            }
        }
        respMap.put("groupAnalyList", groupAnalyList);
        respMap.put("respCode", "0");
        respMap.put("msg", "成功！");

        return respMap;
    }
}
