package com.quanlyquancafe.ui;

import com.quanlyquancafe.manager.MenuManager;
import com.quanlyquancafe.model.HoaDon;
import com.quanlyquancafe.model.MonAn;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ThemMonAnDialog extends JDialog {
    private MenuManager menuManager;
    private HoaDon hoaDon;
    private JList<String> monAnList;
    private JButton themButton;
    private JTextField soLuongField; // Thêm JTextField để nhập số lượng

    public ThemMonAnDialog(JDialog parent, MenuManager menuManager, HoaDon hoaDon) {
        super(parent, "Thêm món vào hóa đơn", true);
        this.menuManager = menuManager;
        this.hoaDon = hoaDon;
        initUI();
    }

    private void initUI() {
        setSize(400, 300);
        setLocationRelativeTo(getParent());

        JPanel panel = new JPanel(new BorderLayout());

        // Danh sách món ăn
        DefaultListModel<String> listModel = new DefaultListModel<>();
        for (MonAn monAn : menuManager.getDanhSachMonAn()) {
            listModel.addElement(monAn.getTen() + " - " + monAn.getGia() + " VND");
        }
        monAnList = new JList<>(listModel);
        JScrollPane scrollPane = new JScrollPane(monAnList);
        panel.add(scrollPane, BorderLayout.CENTER);

        // Panel chứa ô nhập số lượng và nút thêm
        JPanel bottomPanel = new JPanel(new BorderLayout());

        // Ô nhập số lượng
        JPanel soLuongPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        soLuongPanel.add(new JLabel("Số lượng:"));
        soLuongField = new JTextField(5); // Ô nhập số lượng
        soLuongPanel.add(soLuongField);
        bottomPanel.add(soLuongPanel, BorderLayout.CENTER);

        // Nút thêm món
        themButton = new JButton("Thêm");
        bottomPanel.add(themButton, BorderLayout.SOUTH);

        panel.add(bottomPanel, BorderLayout.SOUTH);

        // Xử lý sự kiện thêm món
        themButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedIndex = monAnList.getSelectedIndex();
                if (selectedIndex >= 0) {
                    try {
                        // Lấy số lượng từ ô nhập
                        int soLuong = Integer.parseInt(soLuongField.getText());
                        if (soLuong <= 0) {
                            JOptionPane.showMessageDialog(ThemMonAnDialog.this, "Số lượng phải lớn hơn 0.");
                            return;
                        }

                        // Lấy món ăn được chọn
                        MonAn monAn = menuManager.getDanhSachMonAn().get(selectedIndex);

                        // Thêm món vào hóa đơn với số lượng
                        hoaDon.themMonAn(monAn, soLuong);

                        JOptionPane.showMessageDialog(ThemMonAnDialog.this, 
                            "Đã thêm " + soLuong + " phần " + monAn.getTen());
                        dispose();
                    } catch (NumberFormatException ex) {
                        JOptionPane.showMessageDialog(ThemMonAnDialog.this, 
                            "Số lượng không hợp lệ. Vui lòng nhập số nguyên dương.");
                    }
                } else {
                    JOptionPane.showMessageDialog(ThemMonAnDialog.this, "Vui lòng chọn một món.");
                }
            }
        });

        add(panel);
    }
}