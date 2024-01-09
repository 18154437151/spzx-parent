package com.atguigu.spzx.manager.controller;

import com.atguigu.spzx.manager.service.OrderStatisticsService;
import com.atguigu.spzx.model.dto.order.OrderStatisticsDto;
import com.atguigu.spzx.model.vo.common.Result;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@CrossOrigin
@RestController
@RequestMapping("/admin/system/orderSta")
@Tag(name = "订单统计")  // controller类的描述
public class OrderStatisticsController {
    @Autowired
    private OrderStatisticsService orderStatisticsService;

    @Operation(summary = "查询柱状图统计数据")
    @GetMapping("/getOrderSta")
    public Result getOrderSta(OrderStatisticsDto orderStatisticsDto){
        Map map = orderStatisticsService.getOrderSta(orderStatisticsDto);  // map={dataList + countList}
        return Result.ok(map);
    }
}
