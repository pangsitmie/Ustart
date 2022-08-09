package com.example.ustart.data.entity;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.time.LocalDate;
import java.util.ArrayList;

@Entity(tableName = "cartentities")
public class CartEntity {
    @PrimaryKey(autoGenerate = true)
    @NonNull
    @ColumnInfo(name = "id")
    private int id;
    @ColumnInfo(name = "ipd")
    private int ipd;
    @ColumnInfo(name = "qQuantity")
    private int qQuantity;
    @ColumnInfo(name = "iVender")
    private String iVender;
    @ColumnInfo(name = "nName")
    private String nName;
    @ColumnInfo(name = "imgURL")
    private String imgURL;
    @ColumnInfo(name = "desc")
    private String desc;
    @ColumnInfo(name = "qPrice")
    private String qPrice;
    @ColumnInfo(name = "dSellPrice")
    private String dSellPrice;
    @ColumnInfo(name = "dFinalPrice")
    private String dFinalPrice;

    public CartEntity(int ipd, int qQuantity, String iVender, String nName, String imgURL, String desc, String qPrice, String dSellPrice, String dFinalPrice) {
        this.ipd = ipd;
        this.qQuantity = qQuantity;
        this.iVender = iVender;
        this.nName = nName;
        this.imgURL = imgURL;
        this.desc = desc;
        this.qPrice = qPrice;
        this.dSellPrice = dSellPrice;
        this.dFinalPrice = dFinalPrice;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIpd() {
        return ipd;
    }

    public void setIpd(int ipd) {
        this.ipd = ipd;
    }

    public int getqQuantity() {
        return qQuantity;
    }

    public void setqQuantity(int qQuantity) {
        this.qQuantity = qQuantity;
    }

    public String getiVender() {
        return iVender;
    }

    public void setiVender(String iVender) {
        this.iVender = iVender;
    }

    public String getnName() {
        return nName;
    }

    public void setnName(String nName) {
        this.nName = nName;
    }

    public String getImgURL() {
        return imgURL;
    }

    public void setImgURL(String imgURL) {
        this.imgURL = imgURL;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getqPrice() {
        return qPrice;
    }

    public void setqPrice(String qPrice) {
        this.qPrice = qPrice;
    }

    public String getdSellPrice() {
        return dSellPrice;
    }

    public void setdSellPrice(String dSellPrice) {
        this.dSellPrice = dSellPrice;
    }

    public String getdFinalPrice() {
        return dFinalPrice;
    }

    public void setdFinalPrice(String dFinalPrice) {
        this.dFinalPrice = dFinalPrice;
    }
}
