package com.lesson.spring.dto;

public class FileInfo {

    public FileInfo(String path){
        this.path=path;
    }


    private String path;

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        path = path;
    }
}
