package com.jayden.apipassenger.service;

import com.jayden.apipassenger.remote.ServicePassengerUserClient;
import com.jayen.internelcommon.dto.PassengerUser;
import com.jayen.internelcommon.dto.ResponseResult;
import com.jayen.internelcommon.dto.TokenResult;
import com.jayen.internelcommon.request.VerificationCodeDTO;
import com.jayen.internelcommon.util.JWTUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class UserService {

    @Autowired
    private ServicePassengerUserClient servicePassengerUserClient;

    public ResponseResult getUserByAccessToken(String accessToken){
        // 解析accessToken， 拿到手机号
        log.info("accessToken: "+accessToken);
        TokenResult tokenResult = JWTUtils.checkToken(accessToken);
        String phone = tokenResult.getPhone();
        log.info("phone: "+phone);

        // 根据手机号查询用户信息
        ResponseResult<PassengerUser> userByPhone = servicePassengerUserClient.getUserByPhone(phone);


        return ResponseResult.success(userByPhone.getData());
    }
}
