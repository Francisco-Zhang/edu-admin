package com.lesson;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.session.jdbc.config.annotation.web.http.EnableJdbcHttpSession;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableSwagger2    // 路径：http://localhost:8060/admin/swagger-ui.html
@EnableJdbcHttpSession
public class BookShopAdminApplication {
    public static void main(String[] args) {

        SpringApplication.run(BookShopAdminApplication.class,args);
    }
}
