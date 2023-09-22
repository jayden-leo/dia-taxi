package com.jayden.servicepassengeruser.service;

import com.jayden.servicepassengeruser.dto.PassengerUser;
import com.jayden.servicepassengeruser.mapper.PassengerUserMapper;
import com.jayen.internelcommon.dto.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

@Service
public class UserService {

    @Autowired
    private PassengerUserMapper passengerUserMapper;

    public ResponseResult loginOrReg(String passengerPhone) {
        System.out.println("userService 被调用 手机号：" + passengerPhone);
        // 根据手机号查询用户信息
        HashMap<String, Object> map = new HashMap<>();
        map.put("passenger_phone", passengerPhone);
        List<PassengerUser> passengerUsers = passengerUserMapper.selectByMap(map);
        System.out.println(passengerUsers.size() == 0 ? "无记录" : passengerUsers.get(0).getPassengerPhone());
        // 判断用户信息是否存在

        //如果不沉溺在，插入用户信息

        return ResponseResult.success();
    }
}
