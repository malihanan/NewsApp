package com.example.newsapp;

import java.util.Date;

public class News {
    private String title;
    private String sectionName;
    private String authorName = "";
    private Date datePublished = null;
    private String url;

    public News(String title, String sectionName, String url) {
        this.title = title;
        this.sectionName = sectionName;
        this.url = url;
    }

    public News(String title, String sectionName, String authorName, String url) {
        this.title = title;
        this.sectionName = sectionName;
        this.authorName = authorName;
        this.url = url;
    }

    public News(String title, String sectionName, Date datePublished, String url) {
        this.title = title;
        this.sectionName = sectionName;
        this.datePublished = datePublished;
        this.url = url;
    }

    public News(String title, String sectionName, String authorName, Date datePublished, String url) {
        this.title = title;
        this.sectionName = sectionName;
        this.authorName = authorName;
        this.datePublished = datePublished;
        this.url = url;
    }

    public String getTitle() {
        return title;
    }

    public String getSectionName() {
        return sectionName;
    }

    public String getAuthorName() {
        return authorName;
    }

    public Date getDatePublished() {
        return datePublished;
    }

    public String getUrl() {
        return url;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setSectionName(String sectionName) {
        this.sectionName = sectionName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public void setDatePublished(Date datePublished) {
        this.datePublished = datePublished;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public boolean hasAuthorName() {
        return authorName.length() > 0;
    }

    public boolean hasDatePublished() {
        return datePublished != null;
    }


}
