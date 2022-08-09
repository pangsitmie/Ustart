package com.example.ustart.data.entity;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.time.LocalDate;
import java.util.ArrayList;

@Entity(tableName = "itementities")
public class ItemEntity {
    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "ipd")
    private int ipd;

    @ColumnInfo(name = "iVender")
    private String iVender;
    @ColumnInfo(name = "nName")
    private String nName;
//    @ColumnInfo(name = "typeList")
//    private ArrayList<String> typeList;
//    @ColumnInfo(name = "unitList")
//    private ArrayList<String> unitList;
    @ColumnInfo(name = "qPrice")
    private double qPrice;
    @ColumnInfo(name = "qQuantity")
    private int qQuantity;
    @ColumnInfo(name = "dFinalPrice")
    private double dFinalPrice;
    @ColumnInfo(name = "dSellPrice")
    private String dSellPrice;
    @ColumnInfo(name = "dInDate")
    private String dInDate;
    @ColumnInfo(name = "dLineDate")
    private String dLineDate;
    @ColumnInfo(name = "imgURL")
    private String imgURL;
    @ColumnInfo(name = "desc")
    private String desc;


    public ItemEntity(int ipd, String iVender, String nName, double qPrice, int qQuantity, double dFinalPrice, String dInDate, String dLineDate, String imgURL, String desc) {
        this.ipd = ipd;
        this.iVender = iVender;
        this.nName = nName;
//        this.typeList = typeList;
//        this.unitList = unitList;
        this.qPrice = qPrice;
        this.qQuantity = qQuantity;
        this.dFinalPrice = dFinalPrice;
        this.dInDate = dInDate;
        this.dLineDate = dLineDate;
        this.imgURL = imgURL;
        this.desc = desc;
    }
//
//    public int getIpd() {
//        return ipd;
//    }
//
//    public void setIpd(int ipd) {
//        this.ipd = ipd;
//    }
//
//    public String getiVender() {
//        return iVender;
//    }
//
//    public void setiVender(String iVender) {
//        this.iVender = iVender;
//    }
//
//    public String getnName() {
//        return nName;
//    }
//
//    public void setnName(String nName) {
//        this.nName = nName;
//    }
//
////    public ArrayList<String> getTypeList() {
////        return typeList;
////    }
////
////    public void setTypeList(ArrayList<String> typeList) {
////        this.typeList = typeList;
////    }
////
////    public ArrayList<String> getUnitList() {
////        return unitList;
////    }
////
////    public void setUnitList(ArrayList<String> unitList) {
////        this.unitList = unitList;
////    }
//
//    public double getqPrice() {
//        return qPrice;
//    }
//
//    public void setqPrice(double qPrice) {
//        this.qPrice = qPrice;
//    }
//
//    public int getqQuantity() {
//        return qQuantity;
//    }
//
//    public void setqQuantity(int qQuantity) {
//        this.qQuantity = qQuantity;
//    }
//
//    public double getdFinalPrice() {
//        return dFinalPrice;
//    }
//
//    public void setdFinalPrice(double dFinalPrice) {
//        this.dFinalPrice = dFinalPrice;
//    }
//
//    public String getdSellPrice() {
//        return dSellPrice;
//    }
//
//    public void setdSellPrice(String dSellPrice) {
//        this.dSellPrice = dSellPrice;
//    }
//
//    public String getdInDate() {
//        return dInDate;
//    }
//
//    public void setdInDate(String dInDate) {
//        this.dInDate = dInDate;
//    }
//
//    public String getdLineDate() {
//        return dLineDate;
//    }
//
//    public void setdLineDate(String dLineDate) {
//        this.dLineDate = dLineDate;
//    }
//
//    public String getImgURL() {
//        return imgURL;
//    }
//
//    public void setImgURL(String imgURL) {
//        this.imgURL = imgURL;
//    }
//
//    public String getDesc() {
//        return desc;
//    }
//
//    public void setDesc(String desc) {
//        this.desc = desc;
//    }
}
