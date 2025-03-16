package com.quanlyquancafe.manager;

import com.quanlyquancafe.model.HoaDon;
import com.quanlyquancafe.model.MonAn;
import java.util.*;

public class HoaDonManager {
    private List<HoaDon> danhSachHoaDon;
    private int nextId;

    public HoaDonManager() {
        danhSachHoaDon = new ArrayList<>();
        nextId = 1;
    }

    /**
     * Tạo một hóa đơn mới và thêm vào danh sách.
     *
     * @return Hóa đơn vừa được tạo.
     */
    public HoaDon taoHoaDonMoi() {
        HoaDon hoaDon = new HoaDon(nextId++);
        danhSachHoaDon.add(hoaDon);
        return hoaDon;
    }

    /**
     * Thêm món vào hóa đơn với số lượng.
     *
     * @param idHoaDon ID của hóa đơn.
     * @param monAn    Món ăn cần thêm.
     * @param soLuong  Số lượng món ăn.
     */
    public void themMonVaoHoaDon(int idHoaDon, MonAn monAn, int soLuong) {
        for (HoaDon hoaDon : danhSachHoaDon) {
            if (hoaDon.getId() == idHoaDon) {
                hoaDon.themMonAn(monAn, soLuong);
                break;
            }
        }
    }

    /**
     * Xóa món khỏi hóa đơn.
     *
     * @param idHoaDon ID của hóa đơn.
     * @param monAn    Món ăn cần xóa.
     */
    public void xoaMonKhoiHoaDon(int idHoaDon, MonAn monAn) {
        for (HoaDon hoaDon : danhSachHoaDon) {
            if (hoaDon.getId() == idHoaDon) {
                hoaDon.xoaMonAn(monAn);
                break;
            }
        }
    }

    /**
     * Tính tổng tiền của hóa đơn.
     *
     * @param idHoaDon ID của hóa đơn.
     * @return Tổng tiền của hóa đơn.
     */
    public double tinhTienHoaDon(int idHoaDon) {
        for (HoaDon hoaDon : danhSachHoaDon) {
            if (hoaDon.getId() == idHoaDon) {
                return hoaDon.tinhTien();
            }
        }
        return 0;
    }

    /**
     * Xóa hóa đơn khỏi danh sách.
     *
     * @param idHoaDon ID của hóa đơn cần xóa.
     */
    public void xoaHoaDon(int idHoaDon) {
        HoaDon hoaDonCanXoa = null;
        for (HoaDon hoaDon : danhSachHoaDon) {
            if (hoaDon.getId() == idHoaDon) {
                hoaDonCanXoa = hoaDon;
                break;
            }
        }

        if (hoaDonCanXoa != null) {
            hoaDonCanXoa.getDanhSachMonAn().clear();
            danhSachHoaDon.remove(hoaDonCanXoa);
        }
    }

    /**
     * Lấy danh sách hóa đơn.
     *
     * @return Danh sách hóa đơn.
     */
    public List<HoaDon> getDanhSachHoaDon() {
        return danhSachHoaDon;
    }

    /**
     * Lấy món bán chạy nhất và số lượng bán.
     *
     * @return Một Map.Entry chứa MonAn và số lượng bán, hoặc null nếu không có dữ liệu.
     */
    public Map.Entry<MonAn, Integer> getMonBanChayNhat() {
        if (danhSachHoaDon.isEmpty()) {
            return null; // Trả về null nếu không có hóa đơn nào
        }

        // Sử dụng một Map để đếm số lượng bán của từng món
        Map<MonAn, Integer> soLuongBan = new HashMap<>();

        // Duyệt qua tất cả hóa đơn và món ăn để tính tổng số lượng bán
        for (HoaDon hoaDon : danhSachHoaDon) {
            List<MonAn> danhSachMonAn = hoaDon.getDanhSachMonAn();
            List<Integer> soLuongMonAn = hoaDon.getSoLuongMonAn();

            for (int i = 0; i < danhSachMonAn.size(); i++) {
                MonAn monAn = danhSachMonAn.get(i);
                int soLuong = soLuongMonAn.get(i);

                // Cập nhật số lượng bán vào Map
                soLuongBan.put(monAn, soLuongBan.getOrDefault(monAn, 0) + soLuong);
            }
        }

        // Tìm món bán chạy nhất
        Map.Entry<MonAn, Integer> monBanChayNhat = null;
        int maxSoLuong = 0;

        for (Map.Entry<MonAn, Integer> entry : soLuongBan.entrySet()) {
            if (entry.getValue() > maxSoLuong) {
                monBanChayNhat = entry;
                maxSoLuong = entry.getValue();
            }
        }

        return monBanChayNhat;
    }
}