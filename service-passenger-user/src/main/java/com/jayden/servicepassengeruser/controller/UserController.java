package com.jayden.servicepassengeruser.controller;


import com.jayden.servicepassengeruser.service.UserService;
import com.jayen.internelcommon.dto.ResponseResult;
import com.jayen.internelcommon.request.VerificationCodeDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/user")
    public ResponseResult loginOrReg(@RequestBody VerificationCodeDTO verificationCodeDTO){
        String passengerPhone = verificationCodeDTO.getPassengerPhone();
        System.out.println("passengerPhone: "+passengerPhone);
        return userService.loginOrReg(passengerPhone);
    }

    @GetMapping("/user/{phone}")
    public ResponseResult getUser(@PathVariable("phone")String passengerPhone){
        return userService.getUserByPhone(passengerPhone);
    }
}
