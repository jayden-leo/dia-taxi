package com.jayden.servicedriveruser.controller;


import com.jayden.internelcommon.dto.DriverCarBindingRelationship;
import com.jayden.internelcommon.dto.ResponseResult;
import com.jayden.servicedriveruser.service.DriverCarBindingRelationshipService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author Jayden
 * @since 2023-09-30
 */
@RestController
@RequestMapping("/driver-car-binding-relationship")
public class DriverCarBindingRelationshipController {

    @Autowired
    DriverCarBindingRelationshipService driverCarBindingRelationshipService;

    @PostMapping("/bind")
    public ResponseResult bind(@RequestBody DriverCarBindingRelationship driverCarBindingRelationship){

        return driverCarBindingRelationshipService.bind(driverCarBindingRelationship);
    }

    @PostMapping("/unbind")
    public ResponseResult unbind(@RequestBody DriverCarBindingRelationship driverCarBindingRelationship){
        return driverCarBindingRelationshipService.unbind(driverCarBindingRelationship);

    }

}
