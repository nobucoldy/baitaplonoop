package com.quanlyquancafe.ui;

import com.quanlyquancafe.model.MonAn;
import javax.swing.*;
import java.awt.*; // Thêm dòng này
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AddEditMonAnDialog extends JDialog {
    private JTextField tenField;
    private JTextField giaField;
    private JButton saveButton;
    private JButton cancelButton;
    private boolean saved;
    private MonAn monAn;

    public AddEditMonAnDialog(JFrame parent, MonAn monAn) {
        super(parent, monAn == null ? "Thêm món" : "Sửa món", true);
        this.monAn = monAn;
        saved = false;
        initUI();
    }

    private void initUI() {
        setSize(300, 200);
        setLocationRelativeTo(getParent());

        JPanel panel = new JPanel(new GridLayout(3, 2)); // Sử dụng GridLayout
        panel.add(new JLabel("Tên món:"));
        tenField = new JTextField();
        panel.add(tenField);

        panel.add(new JLabel("Giá (VND):"));
        giaField = new JTextField();
        panel.add(giaField);

        saveButton = new JButton("Lưu");
        cancelButton = new JButton("Hủy");

        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                saved = true;
                dispose();
            }
        });

        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });

        panel.add(saveButton);
        panel.add(cancelButton);

        add(panel);

        // Nếu là sửa món, điền dữ liệu hiện tại vào các trường
        if (monAn != null) {
            tenField.setText(monAn.getTen());
            giaField.setText(String.valueOf(monAn.getGia()));
        }
    }

    public boolean isSaved() {
        return saved;
    }

    public MonAn getMonAn() {
        String ten = tenField.getText();
        double gia = Double.parseDouble(giaField.getText());
        return new MonAn(ten, gia);
    }
}