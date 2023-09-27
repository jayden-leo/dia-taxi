package com.jayden.apipassenger.controller;

import com.jayden.apipassenger.service.UserService;
import com.jayen.internelcommon.dto.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/users")
    public ResponseResult getUser(HttpServletRequest request){
        // 从http请求中获取 accessToken
        String accessToken = request.getHeader("Authorization");
        // 根据accessToken查询
        return userService.getUserByAccessToken(accessToken);
    }
}
