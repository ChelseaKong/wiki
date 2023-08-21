//package com.chelsea.wiki.interceptor;
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.stereotype.Component;
//import org.springframework.web.servlet.HandlerInterceptor;
//import org.springframework.web.servlet.ModelAndView;
//
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//
//// 拦截器：Spring框架特有的，常用于'登录校验'，'权限校验'，'请求日志打印'
//// /login 这个接口不能做登录校验拦截。因为这个接口肯定是未登录的，用会陷入死循环
//// 不是所有的接口都会去校验登录，或者校验权限
//@Component
//public class LogInterceptor implements HandlerInterceptor {
//
//    private static final Logger LOG = LoggerFactory.getLogger(LogInterceptor.class);
//
//    @Override
//    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
//        // 打印请求信息
//        LOG.info("------------- LogInterceptor Starting: -------------");
//        LOG.info("Request Address: {} {}", request.getRequestURL().toString(), request.getMethod());
//        LOG.info("Remote Address: {}", request.getRemoteAddr());
//
//        long startTime = System.currentTimeMillis();
//        request.setAttribute("requestStartTime", startTime);
//        return true;
//    }
//
//    @Override
//    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
//        long startTime = (Long) request.getAttribute("requestStartTime");
//        LOG.info("------------- LogInterceptor End. Time: {} ms -------------", System.currentTimeMillis() - startTime);
//    }
//}
