package com.lesson.spring.dto;

import com.fasterxml.jackson.annotation.JsonView;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.NotBlank;

import java.util.Date;

public class BookInfo {

    public interface BookListView{};

    public interface BookDetailView extends BookListView{}

    private  Long id;

    @ApiModelProperty("图书名称")
    private  String name;

    @NotBlank   // hibernate validator 不为空的约束
    private String content;

    private Date publishDate;

    @JsonView(BookListView.class)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @JsonView(BookListView.class)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @JsonView(BookDetailView.class)
    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @JsonView(BookListView.class)
    public Date getPublishDate() {
        return publishDate;
    }

    public void setPublishDate(Date publishDate) {
        this.publishDate = publishDate;
    }
}
