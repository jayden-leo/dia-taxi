package com.jayden.serviceprice.service;

import com.jayden.internelcommon.constant.CommonStatusEnum;
import com.jayden.internelcommon.dto.PriceRule;
import com.jayden.internelcommon.dto.ResponseResult;
import com.jayden.internelcommon.request.ForecastPriceDTO;
import com.jayden.internelcommon.response.DirectionResponse;
import com.jayden.serviceprice.mapper.PriceRuleMapper;
import com.jayden.serviceprice.remote.ServiceMapClient;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Log4j2
public class PriceService {

    @Autowired
    private ServiceMapClient serviceMapClient;

    @Autowired
    private PriceRuleMapper priceRuleMapper;

    public ResponseResult forecastPrice(String depLongitude, String depLatitude, String destLongitude, String destLatitude, String cityCode, String vehicleType) {
        log.info("1. 调用service-map服务，获得大概距离和时间");
        ForecastPriceDTO forecastPriceDTO = new ForecastPriceDTO();
        forecastPriceDTO.setDepLongitude(depLongitude);
        forecastPriceDTO.setDepLatitude(depLatitude);
        forecastPriceDTO.setDestLatitude(destLatitude);
        forecastPriceDTO.setDestLongitude(destLongitude);
        ResponseResult<DirectionResponse> direction = serviceMapClient.direction(forecastPriceDTO);
        Integer distance = direction.getData().getDistance();
        Integer duration = direction.getData().getDuration();
        log.info("距离：" + distance + ", 时长：" + duration);

        log.info("2. 读取计价规则");
        Map<String, Object> queryMap = new HashMap<>();
        queryMap.put("city_code", "110000");
        queryMap.put("vehicle_type", "1");
        List<PriceRule> priceRules = priceRuleMapper.selectByMap(queryMap);

        if (priceRules.size() == 0) {
            return ResponseResult.fail(CommonStatusEnum.PRICE_RULE_EMPTY.getCode(), CommonStatusEnum.PRICE_RULE_EMPTY.getValue());
        }
        PriceRule priceRule = priceRules.get(0);
        System.out.println(priceRule.toString());
        System.out.println("调用PriceService");

        DirectionResponse directionResponse = new DirectionResponse();
        directionResponse.setDistance(distance);
        directionResponse.setDuration(duration);
        return ResponseResult.success(directionResponse);
    }
}
