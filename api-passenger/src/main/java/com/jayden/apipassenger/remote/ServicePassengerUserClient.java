package com.jayden.apipassenger.remote;

import com.jayden.internelcommon.dto.ResponseResult;
import com.jayden.internelcommon.request.VerificationCodeDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient("service-passenger-user")
public interface ServicePassengerUserClient {
    @RequestMapping(method = RequestMethod.POST,value = "/user")
    public ResponseResult loginOrRegister(@RequestBody VerificationCodeDTO verificationCodeDTO);

    @RequestMapping(method = RequestMethod.GET,value = "/user/{phone}")
    public ResponseResult getUserByPhone(@PathVariable("phone") String passengerPhone);
}
