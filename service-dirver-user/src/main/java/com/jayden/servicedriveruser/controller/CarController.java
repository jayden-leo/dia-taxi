package com.jayden.servicedriveruser.controller;


import com.jayden.internelcommon.dto.Car;
import com.jayden.internelcommon.dto.ResponseResult;
import com.jayden.servicedriveruser.service.CarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.stereotype.Controller;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author Jayden
 * @since 2023-09-30
 */
@Controller
@RequestMapping
public class CarController {

    @Autowired
    CarService carService;

    @PostMapping("/cars")
    public ResponseResult addCar(@RequestBody Car car){
        return carService.addCar(car);
    }

    @GetMapping("/cars")
    public ResponseResult<Car> getCarById(Long carId){

        return carService.getCarById(carId);
    }
}
