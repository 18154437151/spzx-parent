package com.atguigu.spzx.manager.service.impl;

import com.atguigu.spzx.manager.mapper.OrderStatisticsMapper;
import com.atguigu.spzx.manager.service.OrderStatisticsService;
import com.atguigu.spzx.model.dto.order.OrderStatisticsDto;
import com.atguigu.spzx.model.entity.order.OrderStatistics;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class OrderStatisticsServiceImpl implements OrderStatisticsService {
    @Autowired
    private OrderStatisticsMapper orderStatisticsMapper;
    @Override
    public Map getOrderSta(OrderStatisticsDto orderStatisticsDto) {
        // 1.从统计表中查询到目标数据 select * from xxx
        List<OrderStatistics> orderStatisticsList = orderStatisticsMapper.selectList(orderStatisticsDto);
        // 2.在内存中拆分成两个集合，封装成一个map
        // Date转成字符串
        List<String> dateList = orderStatisticsList.stream().map(orderStatistics -> {
            return new DateTime(orderStatistics.getOrderDate()).toString("yyyy-MM-dd");
        }).collect(Collectors.toList());
        List<BigDecimal> countList = orderStatisticsList.stream().map(orderStatistics -> {
            return orderStatistics.getTotalAmount();
        }).collect(Collectors.toList());
        HashMap hashMap = new HashMap();
        hashMap.put("dateList",dateList);
        hashMap.put("countList",countList);
        return hashMap;
    }
}
