/*
package com.atguigu.spzx.manager.auth;

import com.alibaba.fastjson.JSON;
import com.atguigu.spzx.ThreadLocalUtil;
import com.atguigu.spzx.model.entity.system.SysUser;
import com.atguigu.spzx.model.vo.common.Result;
import com.atguigu.spzx.model.vo.common.ResultCodeEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.concurrent.TimeUnit;

@Component
public class UserLoginAuthInterceptor implements HandlerInterceptor {
    @Autowired
    private RedisTemplate<String,String> redisTemplate;  // 注意：向redis添加数据时的redisTemplate对象，如果声明了泛型，之后从redis中取数据时，使用的redisTemplate对象也要加泛型
    // preHandle  --> 处理器（controller方法）--> postHandle --> 响应视图 --> afertCompletion
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String method = request.getMethod();  // 请求的类型
        // OPTIONS类型的请求，并不是一个真正的http请求，而是跨域请求的一个预检请求，
        // 跨域请求=预检请求（OPTIONS类型的一个请求）+ 真实请求
        // 如果在拦截器中，拦截到了预检请求，直接放行即可，不要做任何其它的处理
        if (method.equals("OPTIONS")){
            return true;
        }
        // 1. 校验当前用户是否登录，如果没有登录，拦截请求，直接返回{ code:208 }
        // （1）从请求头中获取token
        String token = request.getHeader("token");
        // （2）判断token如果为空，返回208
        if (StringUtils.isEmpty(token)){
            // 当前用户未登录，给前端响应{ code:208 }
            this.response208(response);
            return false;
        }
        // （3）如果token不为空，从redis中查询用户信息
        String key = "user:login:" + token;
        String jsonString = redisTemplate.opsForValue().get(key);
        if (StringUtils.isEmpty(jsonString)){
            // redis中没有这个token对应的用户信息，当前用户未登录
            this.response208(response);
            return false;
        }
        // 附加1：将json字符串转成SysUser对象，放在threadLocal中
        SysUser sysUser = JSON.parseObject(jsonString, SysUser.class);
        ThreadLocalUtil.set(sysUser);


        // 2. 如果已经登录（token不为空，并且redis中存在数据），延长redis中当前用户的会话时长
        redisTemplate.expire(key,30, TimeUnit.MINUTES);
        return true;
    }

    private void response208(HttpServletResponse response) {
        PrintWriter writer = null;

        try {
            Result result = Result.build(null, ResultCodeEnum.LOGIN_AUTH);
            String jsonString = JSON.toJSONString(result);
            response.setCharacterEncoding("utf-8");
            response.setContentType("application/json");  // 设置响应文档类型（如果你给客户端返回的是json数据，设置成application/json）
            response.getWriter();
            writer.write(jsonString);
        } catch (IOException e){
            throw new RuntimeException(e);
        }
        finally {
            if (writer != null){
                writer.close();
            }
        }

    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        HandlerInterceptor.super.postHandle(request, response, handler, modelAndView);
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        ThreadLocalUtil.remove();
    }
}
*/
