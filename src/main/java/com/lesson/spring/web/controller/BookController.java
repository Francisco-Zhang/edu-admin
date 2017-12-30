package com.lesson.spring.web.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.lesson.spring.dto.BookCondition;
import com.lesson.spring.dto.BookInfo;
import com.lesson.spring.dto.BookInfo.*;
import org.junit.Test;
import org.springframework.boot.test.autoconfigure.web.client.RestClientTest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpMethod;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Date;
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
        bookInfo.setPublishDate(new Date());
        return  bookInfo;

    }


    @PostMapping
    //默认是从param中取值，不写RequestBody注解，book 为null,@Valid注解会使用BookInfo中的约束注解对参数进行校验
    public BookInfo create(@Valid @RequestBody BookInfo book, BindingResult result){
        // bingdingResult存放的是参数校验的结果，此处是为了给出优雅的提示，而不是服务器返回错误码
        if(result.hasErrors()){
            result.getAllErrors().stream().forEach(e->System.out.println(e.getDefaultMessage()));
        }
        System.out.println("id is "+book.getId());
        System.out.println("name is "+book.getName());
        System.out.println("content is "+book.getContent());
        System.out.println("publishDate is "+book.getPublishDate());//如果不在配置文件中设置时差，会当作标准时间赋值，打印出来的时间会＋8小时
        book.setId(1L);
        return  book;
    }




}
