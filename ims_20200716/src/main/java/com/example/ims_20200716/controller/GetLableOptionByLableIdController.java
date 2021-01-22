package com.example.ims_20200716.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

@RestController
public class GetLableOptionByLableIdController {


    @Autowired
    JdbcTemplate jdbcTemplate;
    @RequestMapping("getLableOptionByLableId")
    public Map<String, Object> getLableOptionByLableId(@RequestBody Map<String, Object> reqParams, HttpServletRequest request){
        Map<String, Object> respMap = new HashMap<String, Object>();
        Map<String, Object> REQ_HEAD= (Map<String, Object>) reqParams.get("REQ_HEAD");
        Map<String, Object> BEQ_BODY = (Map<String, Object>) reqParams.get("BEQ_BODY");
        String lableId = (String)BEQ_BODY.get("lableId");
//        String tongJiFangShi = (String)BEQ_BODY.get("tongJiFangShi");
//        String tongJiZhiBiao = (String)BEQ_BODY.get("tongJiZhiBiao");
//        String getAllGroupInfoModel = (String)BEQ_BODY.get("getAllGroupInfoModel");
//        String custGrpId = (String)BEQ_BODY.get("custGrpId");
        String sql = "select * from IMS_CFG_LABLE_OPTION where LABLE_ID = ?";
        Object [] paramsReq = new Object[1];
        paramsReq[0] = lableId;
        List<Map<String, Object>> getScreenCconList = jdbcTemplate.queryForList(sql, paramsReq);
        int[] dangQianKeQun = null;
        int[] duiBiKeQun = null;
        // 查询当前客群和对比客群的统计数据
        if(getScreenCconList != null && getScreenCconList.size() > 0){
            dangQianKeQun = new int[getScreenCconList.size()];
            duiBiKeQun = new int[getScreenCconList.size()];
            Random random = new Random();
            for(int i = 0;i < getScreenCconList.size(); i++){
                Map<String, Object> getScreenMap= getScreenCconList.get(i);
                dangQianKeQun[i] = random.nextInt(1000000)%(1000000-1000+1) + 1000;
                duiBiKeQun[i] = random.nextInt(1000000)%(1000000-1000+1) + 1000;
            }
        }
        respMap.put("dangQianKeQun", dangQianKeQun);
        respMap.put("duiBiKeQun", duiBiKeQun);
        respMap.put("maxCount", 1000000);
        respMap.put("interval", 100000);
        respMap.put("lableOptionInfo", getScreenCconList);
        respMap.put("respCode", "0");
        respMap.put("msg", "成功！");
        return respMap;
    }

}
