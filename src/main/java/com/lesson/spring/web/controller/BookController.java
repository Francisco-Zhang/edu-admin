package com.lesson.spring.web.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.lesson.spring.dto.BookCondition;
import com.lesson.spring.dto.BookInfo;
import com.lesson.spring.dto.BookInfo.*;
import io.swagger.annotations.ApiOperation;
import org.junit.Test;
import org.springframework.boot.test.autoconfigure.web.client.RestClientTest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpMethod;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.async.DeferredResult;


import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

@RestController
@RequestMapping("/book")
public class BookController {


    private ConcurrentMap<Long,DeferredResult<BookInfo>> map= new ConcurrentHashMap<>();

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
    @ApiOperation("查询图书信息")
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
    @ApiOperation("获取图书详细信息")
    public Callable<BookInfo> getInfo(@PathVariable Long id//, @CookieValue String token, @RequestHeader String auth
                                       ) throws Exception { //用于接收cookie,Header值

        Long start=new Date().getTime();
        System.out.println(Thread.currentThread().getName()+"  开始");
        //就一个方法的接口可以使用Lamda表达式匿名实现
        Callable<BookInfo> result=()->{
            System.out.println(Thread.currentThread().getName()+" 线程开始");

            Thread.sleep(1000);  //模拟调用远程服务耗时1s
            BookInfo bookInfo=new BookInfo();
            bookInfo.setName("战争与和平");
            bookInfo.setPublishDate(new Date());

            System.out.println(Thread.currentThread().getName()+" 线程结束耗时："+(new Date().getTime()-start));
            return  bookInfo;
        };

        System.out.println(Thread.currentThread().getName()+"  返回耗时："+(new Date().getTime()-start));

        //throw  new  Exception("interceptor   test");
        System.out.println(id);
//        System.out.println(token);
//        System.out.println("auth is "+auth);

        return  result;   //会在此处等待子线程返回

    }



//    @GetMapping(value = "/{id:\\d}")  //使用正则表达式设定id长度为1位
//    @JsonView(BookDetailView.class)   //设定同一个bookInfo返回不同字段的json视图，此视图包含 content
//    public DeferredResult<BookInfo> getInfo(@PathVariable Long id) throws Exception { //用于接收cookie,Header值
//
//        Long start=new Date().getTime();
//        System.out.println(Thread.currentThread().getName()+"  开始");
//        //就一个方法的接口可以使用Lamda表达式匿名实现
//        DeferredResult<BookInfo> result= new DeferredResult<BookInfo>();
//        map.put(id,result);
//
//        System.out.println(Thread.currentThread().getName()+"  返回耗时："+(new Date().getTime()-start));
//
//        return  result;   //此处并不会响应前端，方法结束，主线程可以处理其他业务
//
//    }

    //此处是另一个线程监听
    private void listenMessge(BookInfo bookInfo){
        map.get(bookInfo.getId()).setResult(bookInfo);  //返回结果，响应前台，不用在主线程产生响应

    }


    @GetMapping(value = "/exception")  //使用正则表达式设定id长度为1位
    public BookInfo getException() throws Exception {

        //throw new RuntimeException("test");
        throw  new Exception("interceptor test");
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


    @PutMapping("/{id}")
    public BookInfo update(@Valid @RequestBody BookInfo book, BindingResult result){
        // bingdingResult存放的是参数校验的结果，此处是为了给出优雅的提示，而不是服务器返回错误码
        if(result.hasErrors()){
            result.getAllErrors().stream().forEach(e->System.out.println(e.getDefaultMessage()));
        }
        System.out.println("id is "+book.getId());
        System.out.println("name is "+book.getName());
        System.out.println("content is "+book.getContent());
        System.out.println("publishDate is "+book.getPublishDate());//如果不在配置文件中设置时差，会当作标准时间赋值，打印出来的时间会＋8小时
        return  book;
    }


    @DeleteMapping(value = "/{id}")  //使用正则表达式设定id长度为1位
    public void delete(@PathVariable Long id){
        System.out.println(id);

    }



}
