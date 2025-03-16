package com.quanlyquancafe.manager;

import com.quanlyquancafe.model.TaiKhoan;

import java.util.ArrayList;
import java.util.List;

public class QuanLyTaiKhoan {
    private List<TaiKhoan> danhSachTaiKhoan;

    public QuanLyTaiKhoan() {
        danhSachTaiKhoan = new ArrayList<>();
        // Thêm tài khoản mẫu
        danhSachTaiKhoan.add(new TaiKhoan("admin", "admin123"));
        danhSachTaiKhoan.add(new TaiKhoan("user", "user123"));
    }

    // Xác thực đăng nhập
    public boolean xacThuc(String tenDangNhap, String matKhau) {
        for (TaiKhoan taiKhoan : danhSachTaiKhoan) {
            if (taiKhoan.getTenDangNhap().equals(tenDangNhap) && taiKhoan.getMatKhau().equals(matKhau)) {
                return true;
            }
        }
        return false;
    }

    // Thêm tài khoản mới
    public void themTaiKhoan(TaiKhoan taiKhoan) {
        danhSachTaiKhoan.add(taiKhoan);
    }
}