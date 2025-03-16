package com.quanlyquancafe.manager;

import com.quanlyquancafe.model.MonAn;
import java.util.ArrayList;
import java.util.List;

public class MenuManager {
    private List<MonAn> danhSachMonAn;

    public MenuManager() {
        danhSachMonAn = new ArrayList<>();
        themDuLieuMau();
    }

    private void themDuLieuMau() {
        danhSachMonAn.add(new MonAn("Nước cam", 25000));
        danhSachMonAn.add(new MonAn("Trà sữa Việt Quất", 30000));
        danhSachMonAn.add(new MonAn("Kem xôi dừa", 35000));
        danhSachMonAn.add(new MonAn("Nước ép táo", 27000));
        danhSachMonAn.add(new MonAn("Matcha đá xay", 40000));
        danhSachMonAn.add(new MonAn("Trà sữa Kem Cheese", 32000));
    }

    public void themMonAn(MonAn monAn) {
        danhSachMonAn.add(monAn);
    }

    public void suaMonAn(int index, MonAn monAn) {
        if (index >= 0 && index < danhSachMonAn.size()) {
            danhSachMonAn.set(index, monAn);
        }
    }

    public void xoaMonAn(int index) {
        if (index >= 0 && index < danhSachMonAn.size()) {
            danhSachMonAn.remove(index);
        }
    }

    public List<MonAn> getDanhSachMonAn() {
        return danhSachMonAn;
    }

    public List<MonAn> timKiemTheoTen(String ten) {
        List<MonAn> ketQua = new ArrayList<>();
        for (MonAn monAn : danhSachMonAn) {
            if (monAn.getTen().toLowerCase().contains(ten.toLowerCase())) {
                ketQua.add(monAn);
            }
        }
        return ketQua;
    }
}
