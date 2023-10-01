package com.jayden.serviceorder.controller;


import com.jayden.internelcommon.dto.OrderInfo;
import com.jayden.internelcommon.dto.ResponseResult;
import com.jayden.internelcommon.request.OrderRequest;
import com.jayden.serviceorder.service.OrderInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author cpf
 * @since 2022-10-10
 */
@RestController
@RequestMapping("/order")
@Slf4j
public class OrderInfoController {

    @Autowired
    OrderInfoService orderInfoService;

    /**
     * 创建订单
     * @param orderRequest
     */
    @PostMapping("/add")
    public ResponseResult add(@RequestBody OrderRequest orderRequest, HttpServletRequest httpServletRequest) {
        // 测试通过，通过header获取deviceCode
//        String deviceCode = httpServletRequest.getHeader(HeaderParamConstants.DEVICE_CODE);
//        orderRequest.setDeviceCode(deviceCode);

        log.info("service-order" + orderRequest.getAddress());
        return orderInfoService.add(orderRequest);
    }

    /**
     * 订单详情
     * @param orderId
     */
    @GetMapping("/detail")
    public ResponseResult<OrderInfo> detail(Long orderId) {
        return orderInfoService.detail(orderId);
    }

    /**
     * 去接乘客
     * @param orderRequest
     */
    @PostMapping("/to-pick-up-passenger")
    public ResponseResult changeStatus(@RequestBody OrderRequest orderRequest) {

        return orderInfoService.toPickUpPassenger(orderRequest);
    }

    /**
     * 到达乘客上车点
     * @param orderRequest
     */
    @PostMapping("/arrived-departure")
    public ResponseResult arrivedDeparture(@RequestBody OrderRequest orderRequest) {
        return orderInfoService.arrivedDeparture(orderRequest);
    }

    /**
     * 司机接到乘客
     * @param orderRequest
     */
    @PostMapping("/pick-up-passenger")
    public ResponseResult pickUpPassenger(@RequestBody OrderRequest orderRequest) {
        return orderInfoService.pickUpPassenger(orderRequest);
    }

    /**
     * 乘客到达目的地，行程终止
     * @param orderRequest
     */
    @PostMapping("/passenger-getoff")
    public ResponseResult passengerGetoff(@RequestBody OrderRequest orderRequest) {
        return orderInfoService.passengerGetoff(orderRequest);
    }

    /**
     * 司机发起收款
     * @param orderRequest
     */
    @PostMapping("/push-pay-info")
    public ResponseResult pushPayInfo(@RequestBody OrderRequest orderRequest) {
        return orderInfoService.pushPayInfo(orderRequest);
    }

    /**
     * 支付完成
     * @param orderRequest
     */
    @PostMapping("/pay")
    public ResponseResult pay(@RequestBody OrderRequest orderRequest) {
        return orderInfoService.pay(orderRequest);
    }

    /**
     * 订单取消
     * @param orderId
     * @param identity
     */
    @PostMapping("/cancel")
    public ResponseResult cancel(Long orderId, String identity) {

        return orderInfoService.cancel(orderId, identity);
    }

    @GetMapping("/current")
    public ResponseResult current(String phone, String identity) {
        return orderInfoService.current(phone, identity);
    }
}
