package com.atguigu.spzx.common.log;

import com.alibaba.fastjson.JSON;
import com.atguigu.spzx.manager.service.SysOperLogService;
import com.atguigu.spzx.model.entity.system.SysOperLog;
import jakarta.servlet.http.HttpServletRequest;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import com.atguigu.spzx.ThreadLocalUtil;
import java.lang.reflect.Method;

@Aspect
@Component
public class LogAspect {
    @Autowired
    private SysOperLogService sysOperLogService;
    // 用于指定哪些方法引入该通知，也就是对哪个方法的调用需要执行该通知
    // 被Log注解（自定义的注解）标注的方法会执行该通知
    // 参数sysLog其实就是目标方法上标注的Log注解
    @Around(value = "@annotation(sysLog)")
    public Object doAroundAdvice(ProceedingJoinPoint joinPoint,Log sysLog){
        // 目标接口的返回值
        Object result = null;
        // 初始化一个log对象
        SysOperLog sysOperLog = new SysOperLog();
        try{
            this.beforeTargetMethod(sysOperLog,sysLog,joinPoint);
            // 对目标方法的调用,result表示目标方法的返回值
            // 目标方法执行出现异常，返回null
            result = joinPoint.proceed();  // 目标方法的返回值（Result）
            this.afterTargetMethod(sysLog,sysOperLog,result,0,null);
        }catch (Throwable e){
            this.afterTargetMethod(sysLog,sysOperLog,result,1,e.getMessage());
            throw new RuntimeException(e);
        }finally {
            // 确保无论目标接口是否存在异常，最终日志都会创建成功
            sysOperLogService.addLog(sysOperLog);
        }
        return result;  // 将目标方法的返回值结果进行响应
    }
    private void beforeTargetMethod(SysOperLog sysOperLog,Log sysLog,ProceedingJoinPoint joinPoint){
        sysOperLog.setTitle(sysLog.title());  // 获取目标方法的@Log注解中的title指定的值
        // 获取目标方法对应的反射类型的对象（Method类型）
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();  // 目标方法对应的返回对象
        String className = method.getDeclaringClass().getName();  // 包名+类名  获取目标方法所在的类（全限定类名）
        String methodName = method.getName(); // 方法名
        sysOperLog.setMethod(className + "." + methodName + "()");  // 目标方法全限定名，包名+类名+方法名+()
        // 从RequestContextHolder spring的上下文对象中获取当前请求对应的HttpServletRequest
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = requestAttributes.getRequest();
        String requestMethod = request.getMethod();  // 当前请求的类型
        sysOperLog.setRequestMethod(requestMethod);  // PUT（当前请求的类型）
        sysOperLog.setOperatorType(sysLog.operatorType().name());  // 操作人类型  "MANAGE"（从目标方法上的@Log注解中获取operatorType）
        sysOperLog.setOperName(ThreadLocalUtil.get().getUserName());  // 操作人名称
        sysOperLog.setOperUrl(request.getRequestURI());  // /admin/system/sysRole/updateById
        sysOperLog.setOperIp(request.getRemoteHost());  // 远程主机ip地址（客户端的ip）
        // 目标方法@Log注解中的isSaveRequestData，true:需要保存请求的参数
        boolean saveRequestData = sysLog.isSaveRequestData();  // 是否保存请求参数
        if (saveRequestData){
            // 获取当前请求的参数
            Object[] args = joinPoint.getArgs();
            // System.out.println(args);
            String jsonString = JSON.toJSONString(args);
            // System.out.println(jsonString);
            sysOperLog.setOperParam(jsonString);
        }
    }
    private void afterTargetMethod(Log sysLog,SysOperLog sysOperLog,Object result,Integer status,String errorMsg){
        boolean saveResponseData = sysLog.isSaveResponseData();  // 是否保存响应数据，true:需要保存目标接口的返回值
        if (saveResponseData){
            sysOperLog.setJsonResult(JSON.toJSONString(result));  // 目标接口的返回值转换成一个json字符串
            sysOperLog.setStatus(status);
            sysOperLog.setErrorMsg(errorMsg);
        }
    }
}
