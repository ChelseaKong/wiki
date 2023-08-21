package com.chelsea.wiki.aspect;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.support.spring.PropertyPreFilters;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

@Aspect
@Component // 表示要把这个类交给 Spring管理
public class LogAspect {

    private final static Logger LOG = LoggerFactory.getLogger(LogAspect.class);

    // 定义一个切点
    @Pointcut("execution(public * com.chelsea.*.controller..*Controller.*(..))") // 表示controller包下及其子包下的都生效
    public void controllerPointcut() {}


    @Before("controllerPointcut()") // 前置通知
    public void doBefore(JoinPoint joinPoint) throws Throwable {

        // 开始打印请求日志
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        Signature signature = joinPoint.getSignature();
        String name = signature.getName();

        // 打印请求信息
        LOG.info("------------- Starting: -------------");
        LOG.info("Request Address: {} {}", request.getRequestURL().toString(), request.getMethod());
        LOG.info("Class name: {}.{}", signature.getDeclaringTypeName(), name);
        LOG.info("Remote Address: {}", request.getRemoteAddr());

        // 打印请求参数
        Object[] args = joinPoint.getArgs();
        // LOG.info("请求参数: {}", JSONObject.toJSONString(args));
        Object[] arguments  = new Object[args.length];
        for (int i = 0; i < args.length; i++) {
            if (args[i] instanceof ServletRequest
                    || args[i] instanceof ServletResponse
                    || args[i] instanceof MultipartFile) {
                continue;
            }
            arguments[i] = args[i];
        }

        // 排除字段，敏感字段或太长的字段不显示
        String[] excludeProperties = {"password", "file"};
        PropertyPreFilters filters = new PropertyPreFilters();
        PropertyPreFilters.MySimplePropertyPreFilter excludefilter = filters.addFilter();
        excludefilter.addExcludes(excludeProperties);
        LOG.info("Request Argu: {}", JSONObject.toJSONString(arguments, excludefilter));
    }

    @Around("controllerPointcut()") // 环绕通知
    public Object doAround(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        long startTime = System.currentTimeMillis();
        Object result = proceedingJoinPoint.proceed(); // 执行业务
        // 排除字段，敏感字段或太长的字段不显示
        String[] excludeProperties = {"password", "file"};
        PropertyPreFilters filters = new PropertyPreFilters();
        PropertyPreFilters.MySimplePropertyPreFilter excludefilter = filters.addFilter();
        excludefilter.addExcludes(excludeProperties);
        LOG.info("Return Argu: {}", JSONObject.toJSONString(result, excludefilter));
        LOG.info("------------- End. Time: {} ms -------------", System.currentTimeMillis() - startTime);
        return result;
    }

}
