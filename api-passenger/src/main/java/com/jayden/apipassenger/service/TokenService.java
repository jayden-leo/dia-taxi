package com.jayden.apipassenger.service;

import com.jayden.internelcommon.constant.CommonStatusEnum;
import com.jayden.internelcommon.constant.TokenConstants;
import com.jayden.internelcommon.dto.ResponseResult;
import com.jayden.internelcommon.dto.TokenResult;
import com.jayden.internelcommon.response.TokenResponse;
import com.jayden.internelcommon.util.JWTUtils;
import com.jayden.internelcommon.util.RedisPrefixUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
public class TokenService {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    public ResponseResult refreshToken(String refreshTokenSrc) {
        // 解析refreshToken
        TokenResult tokenResult = JWTUtils.checkToken(refreshTokenSrc);
        if (tokenResult == null) {
            return ResponseResult.fail(CommonStatusEnum.TOKEN_ERROR.getCode(), CommonStatusEnum.TOKEN_ERROR.getValue());
        }
        String phone = tokenResult.getPhone();
        String identity = tokenResult.getIdentity();

        // 读取redis中的refreshToken
        String refreshTokenKey = RedisPrefixUtils.generatorTokenKey(phone, identity, TokenConstants.REFRESH_TOKEN_TYPE);
        String refreshTokenRedis = stringRedisTemplate.opsForValue().get(refreshTokenKey);
        // 校验refreshToken
        if ((StringUtils.isBlank(refreshTokenRedis)) || (!refreshTokenSrc.trim().equals(refreshTokenRedis.trim()))) {
            return ResponseResult.fail(CommonStatusEnum.TOKEN_ERROR.getCode(), CommonStatusEnum.TOKEN_ERROR.getValue());
        }

        // 生成双Token
        String refreshToken = JWTUtils.generatorToken(phone, identity, TokenConstants.REFRESH_TOKEN_TYPE);
        String accessToken = JWTUtils.generatorToken(phone, identity, TokenConstants.ACCESS_TOKEN_TYPE);

        String accessTokenKey = RedisPrefixUtils.generatorTokenKey(phone,identity,TokenConstants.ACCESS_TOKEN_TYPE);

        stringRedisTemplate.opsForValue().set(accessTokenKey,accessToken,30, TimeUnit.DAYS);
        stringRedisTemplate.opsForValue().set(refreshTokenKey,refreshToken,31, TimeUnit.DAYS);
        // 相应
        TokenResponse tokenResponse = new TokenResponse();
        tokenResponse.setAccessToken(accessToken);
        tokenResponse.setFreshToken(refreshToken);
        return ResponseResult.success(tokenResponse);
    }
}
