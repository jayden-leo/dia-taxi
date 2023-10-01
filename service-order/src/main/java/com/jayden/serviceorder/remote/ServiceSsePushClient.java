package com.jayden.serviceorder.remote;


import com.jayden.internelcommon.request.PushRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient("service-sse-push")
public interface ServiceSsePushClient {

    @PostMapping(value = "/push")
    public String push(@RequestBody PushRequest pushRequest);
}
