package com.lesson.spring.web.interceptor;

import com.sun.org.apache.bcel.internal.generic.NEW;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;

@Component
public class TimeInterceptor implements HandlerInterceptor{

    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) throws Exception {

        System.out.println("这里是 preHandle");
        System.out.println(((HandlerMethod)o).getBean().getClass().getName());
        System.out.println(((HandlerMethod)o).getMethod().getName());

        httpServletRequest.setAttribute("startTime", new Date().getTime());
        return true;  //返回 false是不会执行 处理方法的
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {


        System.out.println("这里是 postHandle");
        System.out.println("服务处理耗时："+(new Date().getTime()- (Long)httpServletRequest.getAttribute("startTime"))+"毫秒" );

    }

    @Override
    //与postHandle不同的是，不管control方法是否正常执行结束，即便发生异常也会执行该方法。而postHandler只有在contol正常执行结束后才会被调用
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

        System.out.println("这里是 afterCompletion");
        System.out.println("服务处理耗时："+(new Date().getTime()- (Long)httpServletRequest.getAttribute("startTime"))+"毫秒" );
        System.out.println("ex is "+e);   // RuntimeException已经被ExceptionHandlerControler处理掉了，所以此处打印掉仍然是null

    }
}
