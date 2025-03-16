package com.quanlyquancafe.service;

import com.quanlyquancafe.model.HoaDon;
import com.quanlyquancafe.model.MonAn;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ThongKe {
    private List<HoaDon> danhSachHoaDon;

    public ThongKe(List<HoaDon> danhSachHoaDon) {
        this.danhSachHoaDon = danhSachHoaDon;
    }

    /**
     * Thống kê món bán chạy nhất.
     */
    public Map.Entry<String, Integer> thongKeMonBanChayNhat() {
        Map<String, Integer> thongKe = new HashMap<>();

        // Duyệt qua danh sách hóa đơn để thống kê số lượng bán của từng món
        for (HoaDon hoaDon : danhSachHoaDon) {
            String tenMon = hoaDon.getTenMon();
            int soLuong = hoaDon.getSoLuong();

            // Cập nhật số lượng bán vào map
            thongKe.put(tenMon, thongKe.getOrDefault(tenMon, 0) + soLuong);
        }

        // Tìm món bán chạy nhất
        Map.Entry<String, Integer> monBanChayNhat = null;
        for (Map.Entry<String, Integer> entry : thongKe.entrySet()) {
            if (monBanChayNhat == null || entry.getValue() > monBanChayNhat.getValue()) {
                monBanChayNhat = entry;
            }
        }

        return monBanChayNhat;
    }

    /**
     * Báo cáo tình hình hoạt động.
     */
    public void baoCaoTinhHinhHoatDong() {
        int soLuongHoaDon = danhSachHoaDon.size();
        double doanhThu = 0;

        // Tính tổng doanh thu từ danh sách hóa đơn
        for (HoaDon hoaDon : danhSachHoaDon) {
            doanhThu += hoaDon.getTongTien();
        }

        System.out.println("Báo cáo tình hình hoạt động:");
        System.out.println("Số lượng hóa đơn: " + soLuongHoaDon);
        System.out.println("Doanh thu: " + doanhThu);
    }
}