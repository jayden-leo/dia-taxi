package com.jayden.apipassenger.service;

import net.sf.json.JSONObject;
import org.springframework.stereotype.Service;

@Service
public class VerificationCodeService {

    public String generateCode(String passengerPhone){

        // 调用验证码服务
        System.out.println("调用验证码服务");
        String code = "111111";

        // 存入redis
        System.out.println("存入redis");


        // 返回值（json）
        JSONObject result = new JSONObject();
        result.put("code",1);
        result.put("message","success");
        return result.toString();
    }
}
