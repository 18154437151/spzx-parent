package com.atguigu.spzx.manager.service.impl;

import com.atguigu.spzx.manager.mapper.SysOperLogMapper;
import com.atguigu.spzx.manager.service.SysOperLogService;
import com.atguigu.spzx.model.entity.system.SysOperLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SysOperLogServiceImpl implements SysOperLogService {
    @Autowired
    private SysOperLogMapper sysOperLogMapper;
    @Override
    public void addLog(SysOperLog sysOperLog) {
        sysOperLogMapper.insert(sysOperLog);
    }
}
