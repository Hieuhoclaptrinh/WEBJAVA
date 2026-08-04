package com.studentrent.service;

import com.studentrent.dao.InvoiceDAO;
import com.studentrent.dao.RoomDAO;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/** Bao cao doanh thu, cong no, ty le lap phong. Skeleton - chua implement. */
public class ReportService {

    private final InvoiceDAO invoiceDAO = new InvoiceDAO();
    private final RoomDAO roomDAO = new RoomDAO();

    /** Doanh thu 12 thang cua mot nam: key = thang (1-12), value = tong da thu. */
    public Map<Integer, BigDecimal> revenueByMonth(int year) {
        throw new UnsupportedOperationException("TODO: ReportService.revenueByMonth");
    }

    /** Tong da thu / con phai thu trong mot ky. */
    public Map<String, BigDecimal> revenueSummary(int month, int year) {
        throw new UnsupportedOperationException("TODO: ReportService.revenueSummary");
    }

    /** Danh sach hoa don qua han kem so ngay tre - dung cho bao cao cong no. */
    public List<Map<String, Object>> debtReport() {
        throw new UnsupportedOperationException("TODO: ReportService.debtReport");
    }

    /** Ty le lap phong = OCCUPIED / tong so phong. */
    public double occupancyRate() {
        throw new UnsupportedOperationException("TODO: ReportService.occupancyRate");
    }

    /** So lieu tong hop cho dashboard (so phong trong, khach, hoa don chua thu...). */
    public Map<String, Object> dashboardStats() {
        throw new UnsupportedOperationException("TODO: ReportService.dashboardStats");
    }
}
