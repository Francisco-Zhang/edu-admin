package com.lesson;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableSwagger2    // 路径：http://localhost:8060/admin/swagger-ui.html
public class BookShopAdminApplication {
    public static void main(String[] args) {

        SpringApplication.run(BookShopAdminApplication.class,args);
    }
}
