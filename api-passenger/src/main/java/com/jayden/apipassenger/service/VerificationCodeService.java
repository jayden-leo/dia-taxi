package com.jayden.apipassenger.service;

import com.jayden.apipassenger.remote.ServiceVerificationCodeClient;
import com.jayen.internelcommon.dto.ResponseResult;
import com.jayen.internelcommon.response.NumberCodeResponse;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class VerificationCodeService {

    @Autowired
    private ServiceVerificationCodeClient serviceVerificationCodeClient;

    public String generateCode(String passengerPhone){

        // 调用验证码服务
        System.out.println("调用验证码服务");
        ResponseResult<NumberCodeResponse> numberCodeResponse = serviceVerificationCodeClient.getNumberCode(6);
        int numberCode = numberCodeResponse.getData().getNumberCode();
        System.out.println("api-passenger code : "+numberCodeResponse.getData().getNumberCode());


        // 存入redis
        System.out.println("存入redis");


        // 返回值（json）
        JSONObject result = new JSONObject();
        result.put("code",1);
        result.put("message","success");
        return result.toString();
    }
}
