package com.jayden.apiboss.controller;


import com.jayden.apiboss.service.DriverUserService;
import com.jayden.internelcommon.dto.DriverUser;
import com.jayden.internelcommon.dto.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DriverUserController {

    @Autowired
    private DriverUserService driverUserService;

    /**
     * 添加司机
     * @param driverUser
     * @return
     */
    @PostMapping("/driver-user")
    public ResponseResult addDriverUser(@RequestBody DriverUser driverUser){
        return driverUserService.addDriverUser(driverUser);
    }

//    /**
//     * 修改司机
//     * @param driverUser
//     * @return
//     */
//    @PutMapping("/driver-user")
//    public ResponseResult updateDriverUser(@RequestBody DriverUser driverUser){
//        return driverUserService.updateDriverUser(driverUser);
//    }
//
//
//    @Autowired
//    CarService carService;
//
//    @PostMapping("/car")
//    public ResponseResult car(@RequestBody Car car){
//        return carService.addCar(car);
//    }
}
