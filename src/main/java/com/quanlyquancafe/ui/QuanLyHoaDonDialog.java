package com.quanlyquancafe.ui;

import com.quanlyquancafe.manager.HoaDonManager;
import com.quanlyquancafe.manager.MenuManager;
import com.quanlyquancafe.model.HoaDon;
import com.quanlyquancafe.model.MonAn;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class QuanLyHoaDonDialog extends JDialog {
    private HoaDonManager hoaDonManager;
    private MenuManager menuManager;
    private DefaultListModel<String> listModel;
    private JList<String> hoaDonList;
    private JList<String> monAnList;
    private JLabel tongTienLabel;
    private JButton taoHoaDonButton;
    private JButton themMonButton;
    private JButton xoaMonButton;
    private JButton thanhToanButton;
    private JButton xoaHoaDonButton;
    private JButton thongKeButton; // Nút thống kê

    public QuanLyHoaDonDialog(JFrame parent, HoaDonManager hoaDonManager, MenuManager menuManager) {
        super(parent, "Quản lý hóa đơn", true);
        this.hoaDonManager = hoaDonManager;
        this.menuManager = menuManager;
        initUI();
    }

    private void initUI() {
        setSize(800, 400);
        setLocationRelativeTo(getParent());

        JPanel panel = new JPanel(new BorderLayout());

        // Danh sách hóa đơn
        listModel = new DefaultListModel<>();
        hoaDonList = new JList<>(listModel);
        JScrollPane hoaDonScrollPane = new JScrollPane(hoaDonList);
        panel.add(hoaDonScrollPane, BorderLayout.WEST);

        // Panel chứa danh sách món và tổng tiền
        JPanel centerPanel = new JPanel(new BorderLayout());

        // Danh sách món ăn trong hóa đơn
        DefaultListModel<String> monAnListModel = new DefaultListModel<>();
        monAnList = new JList<>(monAnListModel);
        JScrollPane monAnScrollPane = new JScrollPane(monAnList);
        centerPanel.add(monAnScrollPane, BorderLayout.CENTER);

        // Label hiển thị tổng tiền
        tongTienLabel = new JLabel("Tổng tiền: 0 VND");
        tongTienLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        centerPanel.add(tongTienLabel, BorderLayout.SOUTH);

        panel.add(centerPanel, BorderLayout.CENTER);

        // Panel chứa các nút chức năng
        JPanel buttonPanel = new JPanel(new GridLayout(6, 1)); // Tăng số hàng lên 6 để thêm nút thống kê
        taoHoaDonButton = new JButton("Tạo hóa đơn");
        themMonButton = new JButton("Thêm món");
        xoaMonButton = new JButton("Xóa món");
        thanhToanButton = new JButton("Thanh toán");
        xoaHoaDonButton = new JButton("Xóa hóa đơn");
        thongKeButton = new JButton("Thống kê"); // Nút thống kê

        buttonPanel.add(taoHoaDonButton);
        buttonPanel.add(themMonButton);
        buttonPanel.add(xoaMonButton);
        buttonPanel.add(thanhToanButton);
        buttonPanel.add(xoaHoaDonButton);
        buttonPanel.add(thongKeButton); // Thêm nút thống kê vào panel
        panel.add(buttonPanel, BorderLayout.EAST);

        // Xử lý sự kiện tạo hóa đơn
        taoHoaDonButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                HoaDon hoaDon = hoaDonManager.taoHoaDonMoi();
                JOptionPane.showMessageDialog(QuanLyHoaDonDialog.this, "Đã tạo hóa đơn #" + hoaDon.getId());
                refreshHoaDonList();
            }
        });

        // Xử lý sự kiện thêm món
        themMonButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedHoaDonIndex = hoaDonList.getSelectedIndex();
                if (selectedHoaDonIndex >= 0) {
                    HoaDon hoaDon = hoaDonManager.getDanhSachHoaDon().get(selectedHoaDonIndex);
                    ThemMonAnDialog dialog = new ThemMonAnDialog(QuanLyHoaDonDialog.this, menuManager, hoaDon);
                    dialog.setVisible(true);
                    refreshMonAnList(hoaDon);
                    capNhatTongTien(hoaDon); // Cập nhật tổng tiền sau khi thêm món
                } else {
                    JOptionPane.showMessageDialog(QuanLyHoaDonDialog.this, "Vui lòng chọn một hóa đơn.");
                }
            }
        });

        // Xử lý sự kiện xóa món
        xoaMonButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedHoaDonIndex = hoaDonList.getSelectedIndex();
                int selectedMonAnIndex = monAnList.getSelectedIndex();
                if (selectedHoaDonIndex >= 0 && selectedMonAnIndex >= 0) {
                    HoaDon hoaDon = hoaDonManager.getDanhSachHoaDon().get(selectedHoaDonIndex);
                    MonAn monAn = hoaDon.getDanhSachMonAn().get(selectedMonAnIndex);
                    hoaDon.xoaMonAn(monAn); // Xóa món khỏi hóa đơn
                    refreshMonAnList(hoaDon);
                    capNhatTongTien(hoaDon); // Cập nhật tổng tiền sau khi xóa món
                } else {
                    JOptionPane.showMessageDialog(QuanLyHoaDonDialog.this, "Vui lòng chọn một hóa đơn và một món ăn.");
                }
            }
        });

        // Xử lý sự kiện thanh toán
        thanhToanButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedHoaDonIndex = hoaDonList.getSelectedIndex();
                if (selectedHoaDonIndex >= 0) {
                    HoaDon hoaDon = hoaDonManager.getDanhSachHoaDon().get(selectedHoaDonIndex);
                    hoaDon.capNhatTrangThai("Đã thanh toán");
                    JOptionPane.showMessageDialog(QuanLyHoaDonDialog.this, "Hóa đơn #" + hoaDon.getId() + " đã được thanh toán.");
                    refreshHoaDonList();
                } else {
                    JOptionPane.showMessageDialog(QuanLyHoaDonDialog.this, "Vui lòng chọn một hóa đơn.");
                }
            }
        });

        // Xử lý sự kiện xóa hóa đơn
        xoaHoaDonButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedHoaDonIndex = hoaDonList.getSelectedIndex();
                if (selectedHoaDonIndex >= 0) {
                    HoaDon hoaDon = hoaDonManager.getDanhSachHoaDon().get(selectedHoaDonIndex);
                    hoaDonManager.xoaHoaDon(hoaDon.getId());
                    refreshHoaDonList(); // Cập nhật danh sách hóa đơn
                    refreshMonAnList(null); // Xóa danh sách món trong hóa đơn
                    capNhatTongTien(null); // Xóa tổng tiền
                } else {
                    JOptionPane.showMessageDialog(QuanLyHoaDonDialog.this, "Vui lòng chọn một hóa đơn.");
                }
            }
        });

        // Xử lý sự kiện thống kê
        thongKeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ThongKeDialog dialog = new ThongKeDialog((JFrame) getParent(), hoaDonManager);
                dialog.setVisible(true);
            }
        });

        // Hiển thị danh sách hóa đơn khi khởi động
        refreshHoaDonList();

        add(panel);
    }

    // Cập nhật danh sách hóa đơn
    private void refreshHoaDonList() {
        listModel.clear();
        for (HoaDon hoaDon : hoaDonManager.getDanhSachHoaDon()) {
            listModel.addElement(hoaDon.toString());
        }
    }

    // Cập nhật danh sách món trong hóa đơn
    private void refreshMonAnList(HoaDon hoaDon) {
        DefaultListModel<String> model = (DefaultListModel<String>) monAnList.getModel();
        model.clear(); // Xóa tất cả các món hiện tại

        if (hoaDon != null) {
            // Hiển thị các món trong hóa đơn được chọn
            List<MonAn> danhSachMonAn = hoaDon.getDanhSachMonAn();
            List<Integer> soLuongMonAn = hoaDon.getSoLuongMonAn();
            for (int i = 0; i < danhSachMonAn.size(); i++) {
                MonAn monAn = danhSachMonAn.get(i);
                int soLuong = soLuongMonAn.get(i);
                model.addElement(monAn.getTen() + " - Số lượng: " + soLuong + " - Giá: " + monAn.getGia() + " VND");
            }
        }
    }

    // Cập nhật tổng tiền
    private void capNhatTongTien(HoaDon hoaDon) {
        if (hoaDon != null) {
            double tongTien = hoaDon.tinhTien();
            tongTienLabel.setText("Tổng tiền: " + tongTien + " VND");
        } else {
            tongTienLabel.setText("Tổng tiền: 0 VND");
        }
    }
}