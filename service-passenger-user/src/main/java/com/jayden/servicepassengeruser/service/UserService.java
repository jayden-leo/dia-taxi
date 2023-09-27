package com.jayden.servicepassengeruser.service;

import com.jayden.servicepassengeruser.mapper.PassengerUserMapper;
import com.jayen.internelcommon.constant.CommonStatusEnum;
import com.jayen.internelcommon.dto.PassengerUser;
import com.jayen.internelcommon.dto.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
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
        // 判断用户信息是否存在,如果不存在，插入用户信息
        if (passengerUsers.size()==0){
            PassengerUser passengerUser = new PassengerUser();
            passengerUser.setPassengerName("张三");
            passengerUser.setPassengerGender((byte) 0);
            passengerUser.setPassengerPhone(passengerPhone);
            passengerUser.setState((byte) 0);
            LocalDateTime now = LocalDateTime.now();
            passengerUser.setGmtCreate(now);
            passengerUser.setGmtModified(now);

            passengerUserMapper.insert(passengerUser);
        }

        return ResponseResult.success();
    }

    public ResponseResult getUserByPhone(String passengerPhone) {
        // 根据手机号查询用户信息
        HashMap<String, Object> map = new HashMap<>();
        map.put("passenger_phone", passengerPhone);
        List<PassengerUser> passengerUsers = passengerUserMapper.selectByMap(map);
        if (passengerUsers.size()==0){
            return ResponseResult.fail(CommonStatusEnum.USER_NOT_EXISTS.getCode(),CommonStatusEnum.USER_NOT_EXISTS.getValue());
        }else{
            PassengerUser passengerUser = passengerUsers.get(0);
            return ResponseResult.success(passengerUser);
        }
    }
}
