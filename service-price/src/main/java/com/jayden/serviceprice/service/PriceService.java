package com.jayden.serviceprice.service;

import com.jayden.internelcommon.dto.ResponseResult;
import org.springframework.stereotype.Service;

@Service
public class PriceService {
    public ResponseResult forecastPrice(String depLongitude, String depLatitude, String destLongitude, String destLatitude, String cityCode, String vehicleType) {
        System.out.println(depLongitude);
        System.out.println(depLatitude);
        System.out.println(destLongitude);
        System.out.println(destLatitude);
        System.out.println(cityCode);
        System.out.println(vehicleType);
        System.out.println("调用PriceService");
        return ResponseResult.success();
    }
}
