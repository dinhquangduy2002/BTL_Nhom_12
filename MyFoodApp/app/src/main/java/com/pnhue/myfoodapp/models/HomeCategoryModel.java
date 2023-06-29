package com.pnhue.myfoodapp.models;

public class HomeCategoryModel {
    String name;
    String img;
    String type;

    public HomeCategoryModel() {

    }

    public HomeCategoryModel(String name, String img, String type) {
        this.name = name;
        this.img = img;
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
