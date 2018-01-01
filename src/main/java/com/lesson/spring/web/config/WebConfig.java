package com.lesson.spring.web.config;

import com.lesson.spring.web.interceptor.TimeInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class WebConfig extends WebMvcConfigurerAdapter{

    @Autowired
    private TimeInterceptor timeInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {

        registry.addInterceptor(timeInterceptor);
    }

    @Bean
    public FilterRegistrationBean chracterEncodingFilterRegister(){
        FilterRegistrationBean filterRegistrationBean =new FilterRegistrationBean();
        CharacterEncodingFilter filter=new CharacterEncodingFilter("UTF-8");
        filter.setForceEncoding(true);
        List<String>  urls =new ArrayList<>();
        urls.add("/*");

        filterRegistrationBean.setFilter(filter);
        filterRegistrationBean.setUrlPatterns(urls);

        return  filterRegistrationBean;
    };


}
