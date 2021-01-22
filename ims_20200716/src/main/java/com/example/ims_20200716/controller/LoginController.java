package com.example.ims_20200716.controller;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

@RestController
public class LoginController {

    @RequestMapping("login")
    public Map<String, Object> login(@RequestBody Map<String, Object> loginParams, HttpServletRequest request) {
        Map<String, Object> respMap = new HashMap<String, Object> ();
        Map<String, String> REQ_HEAD = new HashMap<String, String>();
        Map<String, String> BEQ_BODY = new HashMap<String, String>();
        try {
            REQ_HEAD = (Map)loginParams.get("REQ_HEAD");
            BEQ_BODY = (Map)loginParams.get("BEQ_BODY");
            respMap.put("respCode", "0");
            respMap.put("msg", "成功！");
        } catch(EmptyStackException em) {
            em.printStackTrace();
            respMap.put("respCode", "999999");
            respMap.put("msg", "请求参数有误！");
        } catch(Exception e) {
            e.printStackTrace();
            respMap.put("respCode", "999999");
            respMap.put("msg", "处理异常，请联系技术人员！");
        }

        return respMap;
    }

}
