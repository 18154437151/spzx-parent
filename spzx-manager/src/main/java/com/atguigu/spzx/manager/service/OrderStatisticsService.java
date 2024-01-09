package com.atguigu.spzx.manager.service;

import com.atguigu.spzx.model.dto.order.OrderStatisticsDto;

import java.util.Map;

public interface OrderStatisticsService {
    Map getOrderSta(OrderStatisticsDto orderStatisticsDto);
}
