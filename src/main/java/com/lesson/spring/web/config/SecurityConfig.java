package com.lesson.spring.web.config;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.oauth2.client.EnableOAuth2Sso;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;

import javax.sql.DataSource;

@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
@EnableOAuth2Sso
public class SecurityConfig  extends WebSecurityConfigurerAdapter{



    @Autowired
    private AuthenticationSuccessHandler bookShopAuthenticationSuccessHandler;

    @Autowired
    private AuthenticationFailureHandler  bookShopAuthenticationFailureHandler;

    @Autowired
    private DataSource dataSource;

    @Bean
    public PersistentTokenRepository persistentTokenRepository(){
        JdbcTokenRepositoryImpl tokenRepository=new JdbcTokenRepositoryImpl();
       // tokenRepository.setCreateTableOnStartup(true);  //创建表之后，再次创建会报错，所以注释掉
        tokenRepository.setDataSource(dataSource);
        return  tokenRepository;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(new MyUserDetailsService())
            .passwordEncoder(new BCryptPasswordEncoder());
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
            .formLogin()
                .loginPage("/login.html")
                .loginProcessingUrl("/auth")
                .usernameParameter("user")   //不进行设置的话，默认的键名为   username,password
                .passwordParameter("pass")
                .successHandler(bookShopAuthenticationSuccessHandler)
                .failureHandler(bookShopAuthenticationFailureHandler)
                .and()
                .rememberMe().tokenRepository(persistentTokenRepository())
                .tokenValiditySeconds(60)    //用于实现记住我的有效期
                .and()
                .sessionManagement()
                .invalidSessionUrl("/session.html")
                .maximumSessions(1).maxSessionsPreventsLogin(true)
                .and()
                .and()
                .csrf().disable()
                .authorizeRequests()
                .antMatchers("/book","/file/*","/login.html","/auth","/session.html").permitAll()    // "/book"  任何人都可以访问
                //.anyRequest().authenticated();   //除了以上所有的其他请求，都需要身份认证才可以访问
                //.anyRequest().access("hasAnyAuthority('admin')");//通用表达式，也可以配置 authenticated权限
                .anyRequest().access("@bookSecurity.check(authentication,request)");


    }
}
