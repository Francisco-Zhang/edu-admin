package com.lesson.spring.service;

import org.springframework.security.access.prepost.PreAuthorize;

public interface BookService {



    @PreAuthorize("hasAnyAuthority('xxxx')")
    void  getInfo(Long id);
}
