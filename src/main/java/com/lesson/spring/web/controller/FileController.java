package com.lesson.spring.web.controller;

import com.lesson.spring.dto.FileInfo;
import com.lesson.spring.web.support.Md5;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/file")
public class FileController {

    @PostMapping("/upload")
    public FileInfo upload(MultipartFile file) throws IOException {


        System.out.println(file.getContentType());
        System.out.println(file.getName());
        System.out.println(file.getOriginalFilename());
        System.out.println(file.getSize());
        String path ="/Users/Francisco/gitHub/edu-admin/src/main/java/com/lesson/spring/web/controller";
        String extention= StringUtils.substringAfterLast(file.getOriginalFilename(),".");
        File localFile=new File(path,new Date().getTime()+"."+extention);
        file.transferTo(localFile);

        FileInfo fileInfo=new FileInfo(localFile.getAbsolutePath());
        return  fileInfo;
    }


    @GetMapping("/uploadtest")
    public FileInfo uploadtest() throws Exception {

        long timeStamp = System.currentTimeMillis() / 1000;
        Md5 md5 = new Md5();
        String safeKey = md5.get32LowerCase("lTQ3Cp5S" + timeStamp);

        HttpPostUtil post = new HttpPostUtil("http://www.eeo.cn/partner/api/cloud.api.php?action=uploadFile");
        post.addParameter("SID", "2740354");
        post.addParameter("safeKey", safeKey);
        post.addParameter("timeStamp", String.valueOf(timeStamp));
        post.addParameter("folderId", "1674421");
        post.addParameter("Filedata",  "文件内容".getBytes("UTF-8"));//此处参数类似 curl -F media=@test.jpg
        post.send();


        return  null;
    }



    @GetMapping("/download")
    public void download(HttpServletRequest request, HttpServletResponse response) throws Exception {

        String filePath="/Users/Francisco/gitHub/edu-admin/src/main/java/com/lesson/spring/web/controller/1515249279964.txt";
        try (InputStream inputStream=new FileInputStream(filePath);    //会自动在finally里面关闭
        OutputStream outputStream=response.getOutputStream())
        {
            response.setContentType("application/x-download");
            response.setHeader("Content-Disposition","attachment;filename=test.txt");
            IOUtils.copy(inputStream,outputStream);
            outputStream.flush();

        }

    }








}
