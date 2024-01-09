package com.atguigu.spzx.manager.task;

import com.atguigu.spzx.manager.mapper.OrderInfoMapper;
import com.atguigu.spzx.manager.mapper.OrderStatisticsMapper;
import com.atguigu.spzx.model.entity.order.OrderStatistics;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class OrderStaTask {
    @Autowired
    private OrderInfoMapper orderInfoMapper;
    @Autowired
    private OrderStatisticsMapper orderStatisticsMapper;
    // 每十秒执行一次
    @Scheduled(cron = "0 0 2 * * ?")
    public void task1(){
        // 1.先从订单表中统计前一天的数据，返回OrderStatistics
//        String date = "2024-01-07";
        // 如何获取前一天的年月日？jodaTime
        String date = new DateTime().plusDays(-1).toString("yyyy-MM-dd");
        OrderStatistics orderStatistics = orderInfoMapper.getPreDateAmount(date);
        if (orderStatistics == null){
            System.out.println(true);
        }
        // 2.将OrderStatistics对象添加到order_statistics
        if (orderStatistics!=null){
            orderStatisticsMapper.add(orderStatistics);
        }
        System.out.println(new Date());
    }
}
