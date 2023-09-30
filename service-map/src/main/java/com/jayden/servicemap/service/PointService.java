package com.jayden.servicemap.service;


import com.jayden.internelcommon.dto.ResponseResult;
import com.jayden.internelcommon.request.PointRequest;
import com.jayden.servicemap.remote.PointClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PointService {

    @Autowired
    PointClient pointClient;

    public ResponseResult upload(PointRequest pointRequest){

        return pointClient.upload(pointRequest);
    }
}
