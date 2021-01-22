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

@RestController
public class getImsTrnScreenContionController {


    @Autowired
    JdbcTemplate jdbcTemplate;

    @RequestMapping("/getScreenContion")
    public Map<String, Object> getScreenContion(@RequestBody Map<String, Object> getScreenContionParams, HttpServletRequest request) {
        Map<String, Object> resMap = new HashMap<String, Object>();
        Map<String, Object> REQ_HEAD= (Map<String, Object>) getScreenContionParams.get("REQ_HEAD");
        Map<String, Object> BEQ_BODY = (Map<String, Object>) getScreenContionParams.get("BEQ_BODY");
        String sereenId = (String)BEQ_BODY.get("sereenId");;
        String sql = "select * from IMS_TRN_SCREEN_CONDITION where SCREEN_ID = ?";
        Object [] paramsReq = new Object[1];
        paramsReq[0] = sereenId;
        List<Map<String, Object>> getScreenCconList = jdbcTemplate.queryForList(sql, paramsReq);
        if(getScreenCconList != null && getScreenCconList.size() > 0) {
            for (Map<String, Object> screenCon : getScreenCconList) {
                resMap.put("screenId",screenCon.get("SCREEN_ID"));
                resMap.put("screenLableId",screenCon.get("SCREEN_LABLE_ID"));
                resMap.put("countNumber",screenCon.get("COUNT_NUMBER"));
                resMap.put("abscissaIndex",screenCon.get("ABSCISSA_INDEX"));
                resMap.put("ordinateIndex",screenCon.get("ORDINATE_IDEX"));
            }
        }
        return resMap;
    }

}
