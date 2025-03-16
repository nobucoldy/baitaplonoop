package com.quanlyquancafe;

import com.quanlyquancafe.manager.HoaDonManager;
import com.quanlyquancafe.manager.MenuManager;
import com.quanlyquancafe.manager.QuanLyTaiKhoan;
import com.quanlyquancafe.ui.LoginDialog;
import com.quanlyquancafe.ui.MainFrame;
import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        // Khởi tạo các đối tượng quản lý
        QuanLyTaiKhoan quanLyTaiKhoan = new QuanLyTaiKhoan();
        MenuManager menuManager = new MenuManager();
        HoaDonManager hoaDonManager = new HoaDonManager();

        // Hiển thị giao diện đăng nhập
        SwingUtilities.invokeLater(() -> {
            LoginDialog loginDialog = new LoginDialog(null, quanLyTaiKhoan);
            loginDialog.setVisible(true);

            // Kiểm tra đăng nhập thành công
            if (loginDialog.isDangNhapThanhCong()) {
                // Mở giao diện chính
                MainFrame frame = new MainFrame(menuManager, hoaDonManager, quanLyTaiKhoan);
                frame.setVisible(true);
            } else {
                // Thoát ứng dụng nếu đăng nhập thất bại
                System.exit(0);
            }
        });
    }
}