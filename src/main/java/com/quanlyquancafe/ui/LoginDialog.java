package com.quanlyquancafe.ui;

import com.quanlyquancafe.manager.QuanLyTaiKhoan;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginDialog extends JDialog {
    private JTextField tenDangNhapField;
    private JPasswordField matKhauField;
    private JButton dangNhapButton;
    private JButton huyButton;
    private boolean dangNhapThanhCong;
    private QuanLyTaiKhoan quanLyTaiKhoan;

    public LoginDialog(JFrame parent, QuanLyTaiKhoan quanLyTaiKhoan) {
        super(parent, "Đăng nhập", true);
        this.quanLyTaiKhoan = quanLyTaiKhoan;
        dangNhapThanhCong = false;
        initUI();
    }

    private void initUI() {
        setSize(300, 200);
        setLocationRelativeTo(getParent());

        JPanel panel = new JPanel(new GridLayout(3, 2));

        panel.add(new JLabel("Tên đăng nhập:"));
        tenDangNhapField = new JTextField();
        panel.add(tenDangNhapField);

        panel.add(new JLabel("Mật khẩu:"));
        matKhauField = new JPasswordField();
        panel.add(matKhauField);

        dangNhapButton = new JButton("Đăng nhập");
        huyButton = new JButton("Hủy");

        // Xử lý sự kiện đăng nhập
        dangNhapButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String tenDangNhap = tenDangNhapField.getText();
                String matKhau = new String(matKhauField.getPassword());

                if (quanLyTaiKhoan.xacThuc(tenDangNhap, matKhau)) {
                    dangNhapThanhCong = true;
                    dispose(); // Đóng hộp thoại đăng nhập
                } else {
                    JOptionPane.showMessageDialog(LoginDialog.this, "Tên đăng nhập hoặc mật khẩu không đúng.");
                }
            }
        });

        // Xử lý sự kiện hủy
        huyButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose(); // Đóng hộp thoại đăng nhập
            }
        });

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(dangNhapButton);
        buttonPanel.add(huyButton);

        add(panel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    public boolean isDangNhapThanhCong() {
        return dangNhapThanhCong;
    }
}