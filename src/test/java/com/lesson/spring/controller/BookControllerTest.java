package com.lesson.spring.controller;

import com.lesson.BookShopAdminApplication;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MockMvcBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
// 引入静态方法，不用每次都写MockMvcRequestBuilders
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import static   org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = BookShopAdminApplication.class)
public class BookControllerTest {

    @Autowired
    private WebApplicationContext wac;

    private MockMvc mockMvc;

    @Before
    public void  setup(){
        mockMvc= MockMvcBuilders.webAppContextSetup(wac).build();

    }


    @Test
    public  void  whenQuerySuccess() throws Exception {
        String result =  mockMvc.perform(MockMvcRequestBuilders.get("/book/book2?page=1&sort=name,desc&sort=createdTime,asc")
                .param("categoryId","1")
                .param("name","Tom and Jerry")
                //.param("page","1")   // 1 代表第二页,参数也可以写在url里
                .param("size","15")
                //.param("sort","name,desc","createdTime,asc")
                .accept(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.length()").value(3))
                .andReturn().getResponse().getContentAsString();
        System.out.println(result);
    }

    @Test
    public  void  whenGetInfoSuccess() throws Exception {
        String result =    mockMvc.perform(get("/book/1")
                .accept(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath( "$.name").value("战争与和平"))
                .andReturn().getResponse().getContentAsString();
        System.out.println(result);

    }

    @Test
    public  void  whenGetInfoFail() throws Exception {
        mockMvc.perform(get("/book/10")
                .accept(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(MockMvcResultMatchers.status().is4xxClientError());



    }






}
