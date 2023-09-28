package com.jayden.servicemap.service;


import com.jayden.servicemap.remote.MapDirectionClient;
import com.jayden.internelcommon.dto.ResponseResult;
import com.jayden.internelcommon.response.DirectionResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class DirectionService {

    @Autowired
    private MapDirectionClient mapDirectionClient;

    /**
     * 根据起点经纬度和终点经纬获取距离（米）和时长（分钟）
     * @param depLongitude
     * @param depLatitude
     * @param destLongitude
     * @param destLatitude
     * @return
     */
    public ResponseResult driving(String depLongitude, String depLatitude, String destLongitude, String destLatitude){
        // 调用第三方地图接口
        DirectionResponse direction = mapDirectionClient.direction(depLongitude, depLatitude, destLongitude, destLatitude);

        return ResponseResult.success(direction);
    }
}