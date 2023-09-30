package com.jayden.servicedriveruser.service;

import com.jayden.internelcommon.dto.DriverUserWorkStatus;
import com.jayden.internelcommon.dto.ResponseResult;
import com.jayden.servicedriveruser.mapper.DriverUserWorkStatusMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class DriverUserWorkStatusService {
    @Autowired
    DriverUserWorkStatusMapper driverUserWorkStatusMapper;

    public ResponseResult changeWorkStatus(Long driverId, Integer workStatus){

        Map<String,Object> queryMap = new HashMap<>();
        queryMap.put("driver_id",driverId);
        List<DriverUserWorkStatus> driverUserWorkStatuses = driverUserWorkStatusMapper.selectByMap(queryMap);
        DriverUserWorkStatus driverUserWorkStatus = driverUserWorkStatuses.get(0);

        driverUserWorkStatus.setWorkStatus(workStatus);

        driverUserWorkStatusMapper.updateById(driverUserWorkStatus);

        return ResponseResult.success("");

    }

    public ResponseResult<DriverUserWorkStatus> getWorkStatus(Long driverId) {
        Map<String,Object> queryMap = new HashMap<>();
        queryMap.put("driver_id",driverId);
        List<DriverUserWorkStatus> driverUserWorkStatuses = driverUserWorkStatusMapper.selectByMap(queryMap);
        DriverUserWorkStatus driverUserWorkStatus = driverUserWorkStatuses.get(0);

        return ResponseResult.success(driverUserWorkStatus);

    }
}
