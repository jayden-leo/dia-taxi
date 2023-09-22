package com.jayden.servicepassengeruser.service;

import com.jayen.internelcommon.dto.ResponseResult;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    public ResponseResult loginOrReg(String passengerPhone) {
        System.out.println("userService 被调用 手机号：" + passengerPhone);
        // 根据手机号查询用户信息

        // 判断用户信息是否存在

        //如果不沉溺在，插入用户信息

        return ResponseResult.success();
    }
}
