package com.jayden.serviceprice.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.jayden.internelcommon.constant.CommonStatusEnum;
import com.jayden.internelcommon.dto.PriceRule;
import com.jayden.internelcommon.dto.ResponseResult;
import com.jayden.internelcommon.request.ForecastPriceDTO;
import com.jayden.internelcommon.response.BigDecimalUtils;
import com.jayden.internelcommon.response.DirectionResponse;
import com.jayden.internelcommon.response.ForecastPriceResponse;
import com.jayden.serviceprice.mapper.PriceRuleMapper;
import com.jayden.serviceprice.remote.ServiceMapClient;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
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

        log.info("3. 根据距离和时间，以及计价规则计算价格");
        double price = getPrice(distance, duration, priceRule);

        ForecastPriceResponse forecastPriceResponse = new ForecastPriceResponse();
        forecastPriceResponse.setPrice(price);
        return ResponseResult.success(forecastPriceResponse);
    }

    /**
     * 计算实际价格
     *
     * @param distance
     * @param duration
     * @param cityCode
     * @param vehicleType
     * @return
     */
    public ResponseResult<Double> calculatePrice(Integer distance, Integer duration, String cityCode, String vehicleType) {
        // 查询计价规则
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("city_code", cityCode);
        queryWrapper.eq("vehicle_type", vehicleType);
        queryWrapper.orderByDesc("fare_version");

        List<PriceRule> priceRules = priceRuleMapper.selectList(queryWrapper);
        if (priceRules.size() == 0) {
            return ResponseResult.fail(CommonStatusEnum.PRICE_RULE_EMPTY.getCode(), CommonStatusEnum.VERIFICATION_CODE_ERROR.getValue());
        }

        PriceRule priceRule = priceRules.get(0);

        log.info("根据距离、时长和计价规则，计算价格");

        double price = getPrice(distance, duration, priceRule);
        return ResponseResult.success(price);
    }

    /**
     * 根据距离、时长 和计价规则，计算最终价格
     *
     * @param distance  距离
     * @param duration  时长
     * @param priceRule 计价规则
     * @return
     */
    public static double getPrice(Integer distance, Integer duration, PriceRule priceRule) {
        double price = 0;

        // 起步价
        double startFare = priceRule.getStartFare();
        price = BigDecimalUtils.add(price, startFare);

        // 里程费
        // 总里程 m
        double distanceMile = BigDecimalUtils.divide(distance, 1000);
        // 起步里程
        double startMile = (double) priceRule.getStartMile();
        double distanceSubtract = BigDecimalUtils.substract(distanceMile, startMile);
        // 最终收费的里程数 km
        double mile = distanceSubtract < 0 ? 0 : distanceSubtract;
        // 计程单价 元/km
        double unitPricePerMile = priceRule.getUnitPricePerMile();
        // 里程价格
        double mileFare = BigDecimalUtils.multiply(mile, unitPricePerMile);
        price = BigDecimalUtils.add(price, mileFare);

        // 时长费
        // 时长的分钟数
        double timeMinute = BigDecimalUtils.divide(duration, 60);
        // 计时单价
        double unitPricePerMinute = priceRule.getUnitPricePerMinute();

        // 时长费用
        double timeFare = BigDecimalUtils.multiply(timeMinute, unitPricePerMinute);
        price = BigDecimalUtils.add(price, timeFare);

        BigDecimal priceBigDecimal = new BigDecimal(price);
        priceBigDecimal = priceBigDecimal.setScale(2, BigDecimal.ROUND_HALF_UP);

        return priceBigDecimal.doubleValue();
    }

    public static void main(String[] args) {
        PriceRule priceRule = new PriceRule();
        priceRule.setUnitPricePerMile(1.8);
        priceRule.setUnitPricePerMinute(0.5);
        priceRule.setStartFare(10.0);
        priceRule.setStartMile(3);

        System.out.println(getPrice(6500, 1800, priceRule));
    }
}
