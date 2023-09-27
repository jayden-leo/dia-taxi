package com.jayden.apipassenger.controller;

import com.jayden.apipassenger.service.TokenService;
import com.jayen.internelcommon.dto.ResponseResult;
import com.jayen.internelcommon.response.TokenResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TokenController {

    @Autowired
    private TokenService tokenService;

    @PostMapping("/token-refresh")
    public ResponseResult responseResult(@RequestBody TokenResponse tokenResponse){
        String refreshTokenSrc = tokenResponse.getFreshToken();
        System.out.println("原来的 refreshToken："+refreshTokenSrc);
        return tokenService.refreshToken(refreshTokenSrc);
    }

}
