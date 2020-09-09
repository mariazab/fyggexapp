package com.example.fyggexapp;

public class NewsItem {

    private String image;
    private String title;
    private String date;
    private String url;

    public NewsItem(String image, String title, String date, String url) {
        this.image = image;
        this.title = title;
        this.date = date;
        this.url = url;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}