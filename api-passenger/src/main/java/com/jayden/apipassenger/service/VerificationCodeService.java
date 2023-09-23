package com.jayden.apipassenger.service;

import com.jayden.apipassenger.remote.ServicePassengerUserClient;
import com.jayden.apipassenger.remote.ServiceVerificationCodeClient;
import com.jayen.internelcommon.constant.CommonStatusEnum;
import com.jayen.internelcommon.constant.IdentityConstant;
import com.jayen.internelcommon.dto.ResponseResult;
import com.jayen.internelcommon.request.VerificationCodeDTO;
import com.jayen.internelcommon.response.NumberCodeResponse;
import com.jayen.internelcommon.response.TokenResponse;
import com.jayen.internelcommon.util.JWTUtils;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
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

    @Autowired
    private ServicePassengerUserClient servicePassengerUserClient;

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
        String key = generatorKeyByPhone(passengerPhone);
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
        // 生成key
        String key = generatorKeyByPhone(passengerPhone);
        // 根据key获取value
        String codeRedis = stringRedisTemplate.opsForValue().get(key);
        System.out.println("redis中的value："+codeRedis);
        // 校验验证码
        if (StringUtils.isBlank(codeRedis)){
            return ResponseResult.fail(CommonStatusEnum.VERIFICATION_CODE_ERROR.getCode()).setMessage(CommonStatusEnum.VERIFICATION_CODE_ERROR.getValue());
        }
        if (!verificationCode.trim().equals(codeRedis)){
            return ResponseResult.fail(CommonStatusEnum.VERIFICATION_CODE_ERROR.getCode()).setMessage(CommonStatusEnum.VERIFICATION_CODE_ERROR.getValue());
        }
        // 判断原来是否由用户，并进行对应的处理
        VerificationCodeDTO verificationCodeDTO = new VerificationCodeDTO();
        verificationCodeDTO.setPassengerPhone(passengerPhone);
        verificationCodeDTO.setVerificationCode(verificationCode);
        servicePassengerUserClient.loginOrRegister(verificationCodeDTO);

        // 颁发令牌
        TokenResponse tokenResponse = new TokenResponse();
        String token = JWTUtils.generatorToken(passengerPhone, IdentityConstant.PASSENGER_IDENTITY);
        tokenResponse.setToken(token);

        return ResponseResult.success(tokenResponse);
    }

    /**
     * 根据手机号生成key
     * @param passengerPhone
     * @return
     */
    private String generatorKeyByPhone(String passengerPhone){
        return verificationCodePrefix+passengerPhone;
    }
}
