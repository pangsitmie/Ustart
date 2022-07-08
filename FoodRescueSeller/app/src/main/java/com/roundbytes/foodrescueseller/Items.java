package com.roundbytes.foodrescueseller;

import java.time.LocalDate;

public class Items {
    String title,desc, imgURL;
    Double originalPrice, currentPrice;
    LocalDate expDate;

    public Items(String title, String desc, String imgURL, Double originalPrice, Double currentPrice, LocalDate expDate) {
        this.title = title;
        this.desc = desc;
        this.imgURL = imgURL;
        this.originalPrice = originalPrice;
        this.currentPrice = currentPrice;
        this.expDate = expDate;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getImgURL() {
        return imgURL;
    }

    public void setImgURL(String imgURL) {
        this.imgURL = imgURL;
    }

    public Double getOriginalPrice() {
        return originalPrice;
    }

    public void setOriginalPrice(Double originalPrice) {
        this.originalPrice = originalPrice;
    }

    public Double getCurrentPrice() {
        return currentPrice;
    }

    public void setCurrentPrice(Double currentPrice) {
        this.currentPrice = currentPrice;
    }

    public LocalDate getExpDate() {
        return expDate;
    }

    public void setExpDate(LocalDate expDate) {
        this.expDate = expDate;
    }
}
