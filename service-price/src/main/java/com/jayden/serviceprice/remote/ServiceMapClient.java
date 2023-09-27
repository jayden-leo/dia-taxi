package com.jayden.serviceprice.remote;


import com.jayden.internelcommon.dto.ResponseResult;
import com.jayden.internelcommon.request.ForecastPriceDTO;
import com.jayden.internelcommon.response.DirectionResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient("service-map")
public interface ServiceMapClient {

    @RequestMapping(method = RequestMethod.GET,value = "/direction/driving")
    public ResponseResult<DirectionResponse> direction(@RequestBody ForecastPriceDTO forecastPriceDTO);
}
