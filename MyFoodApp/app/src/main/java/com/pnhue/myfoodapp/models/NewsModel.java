package com.pnhue.myfoodapp.models;

public class NewsModel {
    String Time;
    String name_news;
    String newImage;
    String description;

    public NewsModel() {

    }


    public NewsModel(String Time, String name_news, String newImage, String description) {
        this.Time = Time;
        this.name_news = name_news;
        this.newImage = newImage;
        this.description = description;
    }

    public String getTime() {
        return Time;
    }

    public void setTime(String time) {
        Time = time;
    }

    public String getName_news() {
        return name_news;
    }

    public void setName_news(String name_news) {
        this.name_news = name_news;
    }

    public String getNewImage() {
        return newImage;
    }

    public void setNewImage(String newImage) {
        this.newImage = newImage;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}

