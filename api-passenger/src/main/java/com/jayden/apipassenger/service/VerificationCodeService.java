package com.jayden.apipassenger.service;

import com.jayden.apipassenger.remote.ServiceVerificationCodeClient;
import com.jayen.internelcommon.dto.ResponseResult;
import com.jayen.internelcommon.response.NumberCodeResponse;
import com.jayen.internelcommon.response.TokenResponse;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
public class VerificationCodeService {

    @Autowired
    private ServiceVerificationCodeClient serviceVerificationCodeClient;

    private String verificationCodePrefix = "passenger-verification-code-";

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    /**
     * 生成验证码
     * @param passengerPhone
     * @return
     */
    public ResponseResult generateCode(String passengerPhone) {
        // 调用验证码服务
        ResponseResult<NumberCodeResponse> numberCodeResponse = serviceVerificationCodeClient.getNumberCode(6);
        int numberCode = numberCodeResponse.getData().getNumberCode();

        // 存入redis
        String key = verificationCodePrefix + passengerPhone;
        stringRedisTemplate.opsForValue().set(key, numberCode + "", 2, TimeUnit.MINUTES);

        // 通过短信服务商，将对应的验证码发送到手机上。阿里短信服务、腾讯短信同、华信、容联。
        return ResponseResult.success("");
    }

    /**
     * 校验验证码
     * @param passengerPhone
     * @param verificationCode
     * @return
     */
    public ResponseResult checkCode(String passengerPhone, String verificationCode) {
        // 根据手机号，取redis读取验证码
        System.out.println("根据手机号，取redis读取验证码");
        // 校验验证码
        System.out.println("校验验证码");
        // 判断原来是否由用户，并进行对应的处理
        System.out.println("判断原来是否由用户");
        // 颁发令牌
        System.out.println("颁发令牌");

        // 响应
        TokenResponse tokenResponse = new TokenResponse();
        tokenResponse.setToken("token");
        return ResponseResult.success(tokenResponse);
    }
}
