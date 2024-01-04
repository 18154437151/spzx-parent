package com.atguigu.spzx.manager.service.impl;

import cn.hutool.captcha.CaptchaUtil;
import cn.hutool.captcha.CircleCaptcha;
import com.atguigu.spzx.manager.service.ValidateCodeService;
import com.atguigu.spzx.model.vo.system.ValidateCodeVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Service
public class ValidateCodeServiceImpl implements ValidateCodeService {
    @Autowired
    private RedisTemplate<String,String> redisTemplate;
    @Override
    public ValidateCodeVo generateValidateCode() {
        // 1.创建一个CircleCaptcha对象
        CircleCaptcha circleCaptcha = CaptchaUtil.createCircleCaptcha(150,50,4,20);  // 宽度，高度，验证码位数，干扰线个数
        // 2.从circleCaptcha中获取
        String code = circleCaptcha.getCode(); // 4个字符（验证码）
        String imageBase64 = circleCaptcha.getImageBase64(); // 图形字符串
        // 3.生成redis的key
        String key = UUID.randomUUID().toString().replaceAll("-", "");
        // 4.暂存到redis中，5分钟
        redisTemplate.opsForValue().set(key,code,5, TimeUnit.MINUTES);
        // 5.返回数据（key+imageBase64）
        ValidateCodeVo validateCodeVo = new ValidateCodeVo();
        validateCodeVo.setCodeKey(key);  // redis中的key
        validateCodeVo.setCodeValue("data:image/png;base64," + imageBase64);  // base64字符串

        return validateCodeVo;
    }
}
