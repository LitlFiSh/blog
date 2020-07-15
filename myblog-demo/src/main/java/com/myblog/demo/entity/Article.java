package com.myblog.demo.entity;

import javax.persistence.*;

@Entity   //和数据表映射的类
@Table//(name = "article")   //默认值为类名小写(article)
public class Article {
    @Id   //主键标识
    //自增主键
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column//(name = "author")   //和数据表对应的列
    private String author;
    @Column   //省略默认列名为属性名
    private String articleTitle;
    @Column//(name = "articleContent")
    private String articleContent;
    @Column//(name = "publishDate")
    private String publishDate;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getArticleTitle() {
        return articleTitle;
    }

    public void setArticleTitle(String articleTitle) {
        this.articleTitle = articleTitle;
    }

    public String getArticleContent() {
        return articleContent;
    }

    public void setArticleContent(String articleContent) {
        this.articleContent = articleContent;
    }

    public String getPublishDate() {
        return publishDate;
    }

    public void setPublishDate(String publishDate) {
        this.publishDate = publishDate;
    }
}
