package com.jayden.apipassenger.service;

import com.jayden.apipassenger.remote.ServicePassengerUserClient;
import com.jayden.apipassenger.remote.ServiceVerificationCodeClient;
import com.jayden.internelcommon.constant.CommonStatusEnum;
import com.jayden.internelcommon.constant.IdentityConstants;
import com.jayden.internelcommon.constant.TokenConstants;
import com.jayden.internelcommon.dto.ResponseResult;
import com.jayden.internelcommon.request.VerificationCodeDTO;
import com.jayden.internelcommon.response.NumberCodeResponse;
import com.jayden.internelcommon.response.TokenResponse;
import com.jayden.internelcommon.util.JWTUtils;
import com.jayden.internelcommon.util.RedisPrefixUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
public class VerificationCodeService {

    @Autowired
    private ServiceVerificationCodeClient serviceVerificationCodeClient;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    private ServicePassengerUserClient servicePassengerUserClient;

    /**
     * 生成验证码
     *
     * @param passengerPhone
     * @return
     */
    public ResponseResult generateCode(String passengerPhone) {
        // 调用验证码服务
        ResponseResult<NumberCodeResponse> numberCodeResponse = serviceVerificationCodeClient.getNumberCode(6);
        int numberCode = numberCodeResponse.getData().getNumberCode();

        // 存入redis
        String key = RedisPrefixUtils.generatorKeyByPhone(passengerPhone);
        stringRedisTemplate.opsForValue().set(key, numberCode + "", 2, TimeUnit.MINUTES);

        // 通过短信服务商，将对应的验证码发送到手机上。阿里短信服务、腾讯短信同、华信、容联。
        return ResponseResult.success("");
    }

    /**
     * 校验验证码
     *
     * @param passengerPhone
     * @param verificationCode
     * @return
     */
    public ResponseResult checkCode(String passengerPhone, String verificationCode) {
        // 根据手机号，取redis读取验证码
        // 生成key
        String key = RedisPrefixUtils.generatorKeyByPhone(passengerPhone);
        // 根据key获取value
        String codeRedis = stringRedisTemplate.opsForValue().get(key);
        System.out.println("redis中的value：" + codeRedis);
        // 校验验证码
        if (StringUtils.isBlank(codeRedis)) {
            return ResponseResult.fail(CommonStatusEnum.VERIFICATION_CODE_ERROR.getCode()).setMessage(CommonStatusEnum.VERIFICATION_CODE_ERROR.getValue());
        }
        if (!verificationCode.trim().equals(codeRedis)) {
            return ResponseResult.fail(CommonStatusEnum.VERIFICATION_CODE_ERROR.getCode()).setMessage(CommonStatusEnum.VERIFICATION_CODE_ERROR.getValue());
        }
        // 判断原来是否由用户，并进行对应的处理
        VerificationCodeDTO verificationCodeDTO = new VerificationCodeDTO();
        verificationCodeDTO.setPassengerPhone(passengerPhone);
        verificationCodeDTO.setVerificationCode(verificationCode);
        servicePassengerUserClient.loginOrRegister(verificationCodeDTO);

        // 颁发令牌
        String accessToken = JWTUtils.generatorToken(passengerPhone, IdentityConstants.PASSENGER_IDENTITY, TokenConstants.ACCESS_TOKEN_TYPE);
        String refreshToken = JWTUtils.generatorToken(passengerPhone, IdentityConstants.PASSENGER_IDENTITY, TokenConstants.REFRESH_TOKEN_TYPE);

        // 将token存入到redis中
        String accessTokenKey = RedisPrefixUtils.generatorTokenKey(passengerPhone, IdentityConstants.PASSENGER_IDENTITY, TokenConstants.ACCESS_TOKEN_TYPE);
        stringRedisTemplate.opsForValue().set(accessTokenKey,accessToken,30,TimeUnit.DAYS);
        String refreshTokenKey = RedisPrefixUtils.generatorTokenKey(passengerPhone, IdentityConstants.PASSENGER_IDENTITY, TokenConstants.REFRESH_TOKEN_TYPE);
        stringRedisTemplate.opsForValue().set(refreshTokenKey,refreshToken,31,TimeUnit.DAYS);
        // 相应
        TokenResponse tokenResponse = new TokenResponse();
        tokenResponse.setAccessToken(accessToken);
        tokenResponse.setFreshToken(refreshToken);
        return ResponseResult.success(tokenResponse);
    }


}
