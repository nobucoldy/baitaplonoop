package com.quanlyquancafe.ui;

import com.quanlyquancafe.manager.HoaDonManager;
import com.quanlyquancafe.model.MonAn;

import javax.swing.*;
import java.awt.*;
import java.util.Map;

public class ThongKeDialog extends JDialog {
    private HoaDonManager hoaDonManager;

    public ThongKeDialog(JFrame parent, HoaDonManager hoaDonManager) {
        super(parent, "Thống kê món bán chạy nhất", true);
        this.hoaDonManager = hoaDonManager;
        initUI();
    }

    private void initUI() {
        setSize(400, 200);
        setLocationRelativeTo(getParent());

        JPanel panel = new JPanel(new BorderLayout());

        // Lấy món bán chạy nhất và số lượng bán
        Map.Entry<MonAn, Integer> monBanChayNhat = hoaDonManager.getMonBanChayNhat();

        // Hiển thị thông tin món bán chạy nhất
        JLabel label;
        if (monBanChayNhat != null) {
            MonAn monAn = monBanChayNhat.getKey();
            int soLuongBan = monBanChayNhat.getValue();
            label = new JLabel("Món bán chạy nhất: " + monAn.getTen() + " - Số lượng bán: " + soLuongBan);
        } else {
            label = new JLabel("Không có dữ liệu thống kê.");
        }

        label.setHorizontalAlignment(SwingConstants.CENTER);
        panel.add(label, BorderLayout.CENTER);

        add(panel);
    }
}