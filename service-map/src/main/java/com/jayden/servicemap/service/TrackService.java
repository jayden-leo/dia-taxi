package com.jayden.servicemap.service;

import com.jayden.internelcommon.dto.ResponseResult;
import com.jayden.internelcommon.response.TrackResponse;
import com.jayden.servicemap.remote.TrackClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TrackService {

    @Autowired
    TrackClient trackClient;

    public ResponseResult<TrackResponse> add(String tid){

        return trackClient.add(tid);
    }
}
