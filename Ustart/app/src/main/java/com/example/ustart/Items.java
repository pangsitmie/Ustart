package com.example.ustart;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;

public class Items {
    int ipd, qQuantity;
    String iVender, nName;
    ArrayList<Integer> iType = new ArrayList<>();
    ArrayList<Integer> iUnit = new ArrayList<>();
    double qPrice, dFinalPrice;
    LocalDate dInDate, dLineDate;

    public Items(int ipd, String iVender, String nName, ArrayList<Integer> iType, ArrayList<Integer> iUnit, double qPrice, int qQuantity, double dFinalPrice, LocalDate dInDate, LocalDate dLineDate) {
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
    }

//    public Items(int ipd, int qQuantity, String iVender, String nName) {
//        this.ipd = ipd;
//        this.qQuantity = qQuantity;
//        this.iVender = iVender;
//        this.nName = nName;
//    }

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

    public ArrayList<Integer> getiType() {
        return iType;
    }

    public void setiType(ArrayList<Integer> iType) {
        this.iType = iType;
    }

    public ArrayList<Integer> getiUnit() {
        return iUnit;
    }

    public void setiUnit(ArrayList<Integer> iUnit) {
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
}
