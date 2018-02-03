package com.lesson.spring.web.config;


import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@EnableWebSecurity
public class SecurityConfig  extends WebSecurityConfigurerAdapter{


    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(new MyUserDetailsService());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

//        http.formLogin()
//                .and()
//                .csrf().disable()
//                .authorizeRequests()
//                .antMatchers("/book").permitAll()    // "/book"  任何人都可以访问
//                .anyRequest().authenticated();   //除了以上所有的其他请求，都需要身份认证才可以访问





        http.httpBasic()
                .and()
                .csrf().disable()
                .authorizeRequests()
                .antMatchers("/book").permitAll()    // "/book"  任何人都可以访问
                .anyRequest().authenticated();   //除了以上所有的其他请求，都需要身份认证才可以访问




    }
}
