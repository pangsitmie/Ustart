package com.example.ustart;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;

public class Items {
    int ipd, qQuantity;
    String iVender, nName, imgURL, desc;
    ArrayList<String> iType = new ArrayList<>();
    ArrayList<String> iUnit = new ArrayList<>();
    double qPrice, dFinalPrice;
    LocalDate dInDate, dLineDate;

    public  Items(int ipd, String iVender, String nName, ArrayList<String> iType, ArrayList<String> iUnit, double qPrice, int qQuantity, double dFinalPrice, LocalDate dInDate, LocalDate dLineDate, String imgURL, String desc) {
        this.ipd = ipd;
        this.iVender = iVender;
        this.nName = nName;
        this.iType = iType;
        this.iUnit = iUnit;
        this.qPrice = qPrice;
        this.qQuantity = qQuantity;
        this.dFinalPrice = dFinalPrice;
        this.dInDate = dInDate;
        this.dLineDate = dLineDate;
        this.imgURL = imgURL;
        this.desc = desc;
    }


    public int getIpd() {
        return ipd;
    }

    public void setIpd(int ipd) {
        this.ipd = ipd;
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

    public ArrayList<String> getiType() {
        return iType;
    }

    public void setiType(ArrayList<String> iType) {
        this.iType = iType;
    }

    public ArrayList<String> getiUnit() {
        return iUnit;
    }

    public void setiUnit(ArrayList<String> iUnit) {
        this.iUnit = iUnit;
    }

    public double getqPrice() {
        return qPrice;
    }

    public void setqPrice(double qPrice) {
        this.qPrice = qPrice;
    }

    public int getqQuantity() {
        return qQuantity;
    }

    public void setqQuantity(int qQuantity) {
        this.qQuantity = qQuantity;
    }

    public void addQuantity(){
        this.qQuantity++;
    }

    public void minQuantity(){
        this.qQuantity--;
    }

    public double getdFinalPrice() {
        return dFinalPrice;
    }

    public void setdFinalPrice(double dFinalPrice) {
        this.dFinalPrice = dFinalPrice;
    }

    public LocalDate getdInDate() {
        return dInDate;
    }

    public void setdInDate(LocalDate dInDate) {
        this.dInDate = dInDate;
    }

    public LocalDate getdLineDate() {
        return dLineDate;
    }

    public void setdLineDate(LocalDate dLineDate) {
        this.dLineDate = dLineDate;
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
}
