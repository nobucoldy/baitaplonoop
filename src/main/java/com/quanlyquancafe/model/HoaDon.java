package com.quanlyquancafe.model;

import java.util.ArrayList;
import java.util.List;

public class HoaDon {
    private int id;
    private List<MonAn> danhSachMonAn;
    private List<Integer> soLuongMonAn; // Danh sách số lượng tương ứng với mỗi món
    private String trangThai;

    public HoaDon(int id) {
        this.id = id;
        this.danhSachMonAn = new ArrayList<>();
        this.soLuongMonAn = new ArrayList<>(); // Khởi tạo danh sách số lượng
        this.trangThai = "Chưa thanh toán";
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    // Thêm món với số lượng
    public void themMonAn(MonAn monAn, int soLuong) {
        // Kiểm tra nếu món đã tồn tại trong hóa đơn
        int index = danhSachMonAn.indexOf(monAn);
        if (index >= 0) {
            // Nếu món đã tồn tại, cập nhật số lượng
            soLuongMonAn.set(index, soLuongMonAn.get(index) + soLuong);
        } else {
            // Nếu món chưa tồn tại, thêm mới
            danhSachMonAn.add(monAn);
            soLuongMonAn.add(soLuong);
        }
    }

    // Xóa món khỏi hóa đơn
    public void xoaMonAn(MonAn monAn) {
        int index = danhSachMonAn.indexOf(monAn);
        if (index >= 0) {
            danhSachMonAn.remove(index);
            soLuongMonAn.remove(index);
        }
    }

    // Cập nhật số lượng của một món
    public void capNhatSoLuongMonAn(MonAn monAn, int soLuongMoi) {
        int index = danhSachMonAn.indexOf(monAn);
        if (index >= 0 && soLuongMoi > 0) {
            soLuongMonAn.set(index, soLuongMoi);
        }
    }

    // Tính tổng tiền của hóa đơn dựa trên số lượng
    public double tinhTien() {
        double tongTien = 0;
        for (int i = 0; i < danhSachMonAn.size(); i++) {
            MonAn monAn = danhSachMonAn.get(i);
            int soLuong = soLuongMonAn.get(i);
            tongTien += monAn.getGia() * soLuong;
        }
        return tongTien;
    }

    public void capNhatTrangThai(String trangThai) {
        this.trangThai = trangThai;
    }

    public List<MonAn> getDanhSachMonAn() {
        return danhSachMonAn;
    }

    public List<Integer> getSoLuongMonAn() {
        return soLuongMonAn;
    }

    public String getTrangThai() {
        return trangThai;
    }

    @Override
    public String toString() {
        return "Hóa đơn #" + id + " - " + trangThai;
    }
}