package com.lesson.spring.web.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.lesson.spring.dto.BookCondition;
import com.lesson.spring.dto.BookInfo;
import com.lesson.spring.dto.BookInfo.*;
import org.springframework.boot.test.autoconfigure.web.client.RestClientTest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpMethod;
import org.springframework.web.bind.annotation.*;


import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/book")
public class BookController {


    //@RequestMapping(method = RequestMethod.GET)   //对应  localhost:8060/admin／book
    @GetMapping  //上面一种方式的简写，意思相同
    //或者直接写 String name，与请求中参数名称相同自动赋值,request默认true
    public List<BookInfo> query(@RequestParam(value="name",required = false,defaultValue = "default mvc param") String bookName,
                                Long categoryId){   //会自动将字符串转为long类型
        System.out.println(bookName);
        System.out.println(categoryId);

        List<BookInfo> books=new ArrayList<BookInfo>();
        books.add(new BookInfo());
        books.add(new BookInfo());
        books.add(new BookInfo());

        return  books;

    }

    @RequestMapping(value = "/book2", method = RequestMethod.GET)
    @JsonView(BookListView.class)   //此视图不包含 content 字段
    public List<BookInfo> query2(BookCondition condition,@PageableDefault(size = 10) Pageable pageable){   //会自动将字符串转为long类型
        System.out.println(condition.getName());
        System.out.println(condition.getCategoryId());

        System.out.println(pageable.getPageNumber());   //当前请求的是第几页
        System.out.println(pageable.getPageSize());     //每页第条数
        System.out.println(pageable.getSort());     //排序


        List<BookInfo> books=new ArrayList<BookInfo>();
        books.add(new BookInfo());
        books.add(new BookInfo());
        books.add(new BookInfo());

        return  books;

    }

    //@RequestMapping(value = "/{id}", method = RequestMethod.GET)
    //@GetMapping(value = "/{id}")
    @GetMapping(value = "/{id:\\d}")  //使用正则表达式设定id长度为1位
    @JsonView(BookDetailView.class)   //设定同一个bookInfo返回不同字段的json视图，此视图包含 content
    public BookInfo getInfo(@PathVariable Long id){

        BookInfo bookInfo=new BookInfo();
        bookInfo.setName("战争与和平");
        return  bookInfo;

    }



}
