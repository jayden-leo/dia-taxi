package com.jayden.servicemap.controller;

import com.jayden.internelcommon.dto.ResponseResult;
import com.jayden.servicemap.service.DicDistrictService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DicDistrictController {
    @Autowired
    private DicDistrictService districtService;

    @GetMapping("/district")
    public ResponseResult initDicDistrict(String keywords){

        return districtService.initDicDistrict(keywords);
    }

}
