package com.jayden.serviceverificationcode.controller;

import com.jayden.internelcommon.dto.ResponseResult;
import com.jayden.internelcommon.response.NumberCodeResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class NumberCodeController {

    @GetMapping("/numberCode/{size}")
    public ResponseResult numberCode(@PathVariable("size") int size) {
        // 生成验证码
        double random = (Math.random() * 9 + 1) * Math.pow(10, size - 1);
        int numberCode = (int) random;
        NumberCodeResponse response = new NumberCodeResponse();
        response.setNumberCode(numberCode);
        return ResponseResult.success(response);
    }
}
