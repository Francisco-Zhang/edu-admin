package com.lesson.spring.web.controller;

import com.lesson.spring.dto.BookInfo;
import org.springframework.boot.test.autoconfigure.web.client.RestClientTest;
import org.springframework.http.HttpMethod;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class BookController {


    @RequestMapping(value = "/book", method = RequestMethod.GET)
    //或者直接写 String name，与请求中参数名称相同自动赋值,request默认true
    public List<BookInfo> query(@RequestParam(value="name",required = false,defaultValue = "default mvc param") String bookName){
        System.out.println(bookName);
        List<BookInfo> books=new ArrayList<BookInfo>();
        books.add(new BookInfo());
        books.add(new BookInfo());
        books.add(new BookInfo());

        return  books;

    }

}
