package com.jayden.apiboss.controller;


import com.jayden.apiboss.service.DriverCarBindingRelationshipService;
import com.jayden.internelcommon.dto.DriverCarBindingRelationship;
import com.jayden.internelcommon.dto.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
