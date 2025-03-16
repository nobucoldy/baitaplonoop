package com.quanlyquancafe.model;

public class MonAn {
    private String ten;
    private double gia;

    public MonAn(String ten, double gia) {
        this.ten = ten;
        this.gia = gia;
    }

    public String getTen() {
        return ten;
    }

    public void setTen(String ten) {
        this.ten = ten;
    }

    public double getGia() {
        return gia;
    }

    public void setGia(double gia) {
        this.gia = gia;
    }

    @Override
    public String toString() {
        return ten + " - " + gia + " VND";
    }
}