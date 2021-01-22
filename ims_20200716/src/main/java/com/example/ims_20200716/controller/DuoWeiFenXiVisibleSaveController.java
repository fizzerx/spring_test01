package com.example.ims_20200716.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class DuoWeiFenXiVisibleSaveController {
    @Autowired
    JdbcTemplate jdbcTemplate;

    @RequestMapping("duoWeiFenXiVisibleSave")
    public Map<String, Object> tongjiVisibleSave(@RequestBody Map<String, Object> reqParams, HttpServletRequest httpServletRequest){
        Map<String, Object> respMap = new HashMap<String, Object>();
        Map<String, Object> queyrResultMap = new HashMap<String, Object>();
        Map<String, Object> BEQ_BODY = new HashMap<String, Object>();
        BEQ_BODY = (Map<String, Object>)reqParams.get("BEQ_BODY");
        if(BEQ_BODY != null) {
            System.out.println(BEQ_BODY.toString());
            String analysisNameInputUpDate = (String) BEQ_BODY.get("analysisNameInputUpDate");
            List<Map<String, Object>> dynamicTags = (List<Map<String, Object>>) BEQ_BODY.get("dynamicTags");
            String tongJiFangShi = (String) BEQ_BODY.get("tongJiFangShi");
            String tongJiZhiBiao = (String) BEQ_BODY.get("tongJiZhiBiao");
            String getAllGroupInfoModel = (String) BEQ_BODY.get("getAllGroupInfoModel");
            String CUST_GRP_ID = (String) BEQ_BODY.get("CUST_GRP_ID");
            String grpAnalyStatus = (String) BEQ_BODY.get("grpAnalyStatus");
            String formaDate = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
            // 生成ID
            String GRP_ANAL_ID = "";
            String sql = "";
            GRP_ANAL_ID = getId("AL");
            sql = "insert into IMS_TRN_GROUP_ANALYTICAL(GRP_ANAL_ID,GRP_ANAL_NAME,CUST_GRP_ID,EXECUTIVE_STATUS,CONTRAST_GRP_ID,CREATER, CREATE_DATE) values(" +
                    "'"+GRP_ANAL_ID+"','"+analysisNameInputUpDate+"','"+CUST_GRP_ID+"','N','"+getAllGroupInfoModel+"','admin','"+formaDate+"')";
            jdbcTemplate.update(sql);

            String MODEL_ID = getId("M") + "0";

            String sql2 = "insert into IMS_TRN_ANALYTICAL_MODEL(MODEL_ID,TEMPLATE_ID,GRP_ANAL_STATUS," +
                    "STATISTICAL_INDICATORS,STATISTICAL_METHODS) values('"+MODEL_ID+"', '"+GRP_ANAL_ID+"'," +
                    "'"+grpAnalyStatus+"','"+tongJiZhiBiao+"','"+tongJiFangShi+"')";
            jdbcTemplate.update(sql2);
            for(Map<String, Object> dynamicTagsMap : dynamicTags){
                String sql3 = "insert into IMS_TEL_MODEL_LABLE(MODEL_ID,LABLE_ID) values('"+MODEL_ID+"','"+dynamicTagsMap.get("LABLE_ID")+"')";
                jdbcTemplate.update(sql3);
            }
        }
        respMap.put("groupInfo", queyrResultMap);
        respMap.put("respCode", "0");
        respMap.put("msg", "成功！");

        return respMap;
    }

    public String getId(String hd){
        StringBuffer id = new StringBuffer();
        id.append(hd);
        id.append(System.currentTimeMillis());
        return id.toString();
    }
}
