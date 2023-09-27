package com.jayden.apipassenger.service;

import com.jayen.internelcommon.dto.PassengerUser;
import com.jayen.internelcommon.dto.ResponseResult;
import com.jayen.internelcommon.dto.TokenResult;
import com.jayen.internelcommon.util.JWTUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class UserService {

    public ResponseResult getUserByAccessToken(String accessToken){
        // 解析accessToken， 拿到手机号
        log.info("accessToken: "+accessToken);
        TokenResult tokenResult = JWTUtils.checkToken(accessToken);
        String phone = tokenResult.getPhone();
        log.info("phone: "+phone);
        String identity = tokenResult.getIdentity();

        // 根据手机号查询用户信息
        PassengerUser passengerUser = new PassengerUser();
        passengerUser.setPassengerName("张三");
        passengerUser.setProfilePhoto("头像");

        return ResponseResult.success(passengerUser);
    }
}
