package com.lesson.spring.web.config;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

@Component("bookSecurity")
public class BookSecurity {



    public Boolean check(Authentication authentication, HttpServletRequest request){

        System.out.println(request.getRequestURL());
        if(authentication.getPrincipal()!=null && authentication.getPrincipal() instanceof UserDetails){
            System.out.println (((UserDetails)authentication.getPrincipal()).getAuthorities()) ;
            return  true;
        }
        return  false;
    }
}
