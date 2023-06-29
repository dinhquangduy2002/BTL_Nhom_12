package com.pnhue.myfoodapp.models;

import java.io.Serializable;

public class HomeProductModel implements Serializable {
    String id;
    String productImage;
    String name;
    int price;
    String type;
    String des;
    String unit;
    String productImages;

    public HomeProductModel() {
    }

    public HomeProductModel(String productImage, String name, int price, String type, String des, String unit, String productImages) {
        this.productImage = productImage;
        this.name = name;
        this.price = price;
        this.type = type;
        this.des = des;
        this.unit = unit;
        this.productImages = productImages;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public String getProductImage() {
        return productImage;
    }

    public void setProductImage(String productImage) {
        this.productImage = productImage;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDes() {
        return des;
    }

    public void setDes(String des) {
        this.des = des;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getProductImages() {
        return productImages;
    }

    public void setProductImages(String productImages) {
        this.productImages = productImages;
    }
}
