package com.quanlyquancafe.ui;

import com.quanlyquancafe.manager.HoaDonManager;
import com.quanlyquancafe.manager.MenuManager;
import com.quanlyquancafe.manager.QuanLyTaiKhoan;
import com.quanlyquancafe.model.MonAn;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class MainFrame extends JFrame {
    private MenuManager menuManager;
    private HoaDonManager hoaDonManager;
    private QuanLyTaiKhoan quanLyTaiKhoan;
    private DefaultTableModel tableModel;
    private JTable table;
    private JTextField searchField; // Ô nhập liệu tìm kiếm

    public MainFrame(MenuManager menuManager, HoaDonManager hoaDonManager, QuanLyTaiKhoan quanLyTaiKhoan) {
        this.menuManager = menuManager;
        this.hoaDonManager = hoaDonManager;
        this.quanLyTaiKhoan = quanLyTaiKhoan;
        initUI();
    }

    private void initUI() {
        setTitle("Quản lý quán cafe");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Tạo menu bar
        JMenuBar menuBar = new JMenuBar();

      

        setJMenuBar(menuBar);

        // Tạo panel chứa ô tìm kiếm và nút tìm kiếm
        JPanel searchPanel = new JPanel();
        searchField = new JTextField(20); // Ô nhập liệu tìm kiếm
        JButton searchButton = new JButton("Tìm kiếm");

        // Xử lý sự kiện tìm kiếm
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String keyword = searchField.getText();
                List<MonAn> ketQuaTimKiem = menuManager.timKiemTheoTen(keyword);
                hienThiKetQuaTimKiem(ketQuaTimKiem);
            }
        });

        searchPanel.add(new JLabel("Tìm kiếm món ăn:"));
        searchPanel.add(searchField);
        searchPanel.add(searchButton);

        // Tạo bảng hiển thị danh sách món ăn
        tableModel = new DefaultTableModel();
        tableModel.addColumn("Tên món");
        tableModel.addColumn("Giá (VND)");

        table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);

        // Thêm các thành phần vào giao diện
        add(searchPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);

        // Tạo panel chứa các nút chức năng
        JPanel buttonPanel = new JPanel();
        JButton addButton = new JButton("Thêm món");
        JButton editButton = new JButton("Sửa món");
        JButton deleteButton = new JButton("Xóa món");
        JButton quanLyHoaDonButton = new JButton("Quản lý hóa đơn");

        buttonPanel.add(addButton);
        buttonPanel.add(editButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(quanLyHoaDonButton);
        add(buttonPanel, BorderLayout.SOUTH);

        // Xử lý sự kiện thêm món
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                AddEditMonAnDialog dialog = new AddEditMonAnDialog(MainFrame.this, null);
                dialog.setVisible(true);
                if (dialog.isSaved()) {
                    MonAn monAn = dialog.getMonAn();
                    menuManager.themMonAn(monAn);
                    refreshTable();
                }
            }
        });

        // Xử lý sự kiện sửa món
        editButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = table.getSelectedRow();
                if (selectedRow >= 0) {
                    MonAn monAn = menuManager.getDanhSachMonAn().get(selectedRow);
                    AddEditMonAnDialog dialog = new AddEditMonAnDialog(MainFrame.this, monAn);
                    dialog.setVisible(true);
                    if (dialog.isSaved()) {
                        MonAn updatedMonAn = dialog.getMonAn();
                        menuManager.suaMonAn(selectedRow, updatedMonAn);
                        refreshTable();
                    }
                } else {
                    JOptionPane.showMessageDialog(MainFrame.this, "Vui lòng chọn một món để sửa.");
                }
            }
        });

        // Xử lý sự kiện xóa món
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = table.getSelectedRow();
                if (selectedRow >= 0) {
                    menuManager.xoaMonAn(selectedRow);
                    refreshTable();
                } else {
                    JOptionPane.showMessageDialog(MainFrame.this, "Vui lòng chọn một món để xóa.");
                }
            }
        });

        // Xử lý sự kiện quản lý hóa đơn
        quanLyHoaDonButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                QuanLyHoaDonDialog dialog = new QuanLyHoaDonDialog(MainFrame.this, hoaDonManager, menuManager);
                dialog.setVisible(true);
            }
        });

        // Hiển thị danh sách món ăn khi khởi động
        refreshTable();
    }

    // Cập nhật bảng hiển thị danh sách món ăn
    private void refreshTable() {
        tableModel.setRowCount(0); // Xóa dữ liệu cũ
        for (MonAn monAn : menuManager.getDanhSachMonAn()) {
            tableModel.addRow(new Object[]{monAn.getTen(), monAn.getGia()});
        }
    }

    // Hiển thị kết quả tìm kiếm
    private void hienThiKetQuaTimKiem(List<MonAn> ketQuaTimKiem) {
        tableModel.setRowCount(0); // Xóa dữ liệu cũ
        for (MonAn monAn : ketQuaTimKiem) {
            tableModel.addRow(new Object[]{monAn.getTen(), monAn.getGia()});
        }
    }
}