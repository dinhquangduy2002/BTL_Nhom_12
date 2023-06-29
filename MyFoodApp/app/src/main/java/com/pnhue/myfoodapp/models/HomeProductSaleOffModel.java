package com.pnhue.myfoodapp.models;

import java.io.Serializable;

public class HomeProductSaleOffModel implements Serializable {
    String percentSaleOff;
    String productImage;
    String name;
    int price;
    int priceOld;
    String des;
    String save;

    public HomeProductSaleOffModel() {
    }

    public HomeProductSaleOffModel(String percentSaleOff, String productImage, String name, int price, int priceOld, String des, String save) {
        this.percentSaleOff = percentSaleOff;
        this.productImage = productImage;
        this.name = name;
        this.price = price;
        this.priceOld = priceOld;
        this.des = des;
        this.save = save;
    }

    public String getPercentSaleOff() {
        return percentSaleOff;
    }

    public void setPercentSaleOff(String percentSaleOff) {
        this.percentSaleOff = percentSaleOff;
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

    public int getPriceOld() {
        return priceOld;
    }

    public void setPriceOld(int priceOld) {
        this.priceOld = priceOld;
    }

    public String getDes() {
        return des;
    }

    public void setDes(String des) {
        this.des = des;
    }

    public String getSave() {
        return save;
    }

    public void setSave(String save) {
        this.save = save;
    }
}
