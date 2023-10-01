package com.jayden.apipassenger.service;

import com.jayden.apipassenger.remote.ServicePassengerUserClient;
import com.jayden.internelcommon.dto.PassengerUser;
import com.jayden.internelcommon.dto.ResponseResult;
import com.jayden.internelcommon.dto.TokenResult;
import com.jayden.internelcommon.util.JwtUtils;
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
        TokenResult tokenResult = JwtUtils.checkToken(accessToken);
        String phone = tokenResult.getPhone();
        log.info("phone: "+phone);

        // 根据手机号查询用户信息
        ResponseResult<PassengerUser> userByPhone = servicePassengerUserClient.getUserByPhone(phone);


        return ResponseResult.success(userByPhone.getData());
    }
}
