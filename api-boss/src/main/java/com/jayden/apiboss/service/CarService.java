package com.jayden.apiboss.service;


import com.jayden.apiboss.remote.ServiceDriverUserClient;
import com.jayden.internelcommon.dto.Car;
import com.jayden.internelcommon.dto.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CarService {

    @Autowired
    ServiceDriverUserClient serviceDriverUserClient;

    public ResponseResult addCar(Car car){
        return serviceDriverUserClient.addCar(car);
    }
}
