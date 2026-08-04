package com.studentrent.service;

import com.studentrent.dao.ContractDAO;
import com.studentrent.dao.InvoiceDAO;
import com.studentrent.dao.InvoiceDetailDAO;
import com.studentrent.dao.ServiceDAO;
import com.studentrent.model.Invoice;
import com.studentrent.model.InvoiceDetail;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

/** Nghiep vu lap va thanh toan hoa don. Skeleton - chua implement. */
public class InvoiceService {

    private final InvoiceDAO invoiceDAO = new InvoiceDAO();
    private final InvoiceDetailDAO invoiceDetailDAO = new InvoiceDetailDAO();
    private final ContractDAO contractDAO = new ContractDAO();
    private final ServiceDAO serviceDAO = new ServiceDAO();

    public List<Invoice> search(String keyword, String status,
                               Integer month, Integer year, int page, int size) {
        throw new UnsupportedOperationException("TODO: InvoiceService.search");
    }

    /** Hoa don kem chi tiet dich vu - dung cho trang detail va xuat PDF. */
    public Optional<Invoice> findFull(Long id) {
        throw new UnsupportedOperationException("TODO: InvoiceService.findFull");
    }

    /**
     * Chuan bi form lap hoa don: lay danh sach dich vu ACTIVE, dien san
     * old_index tu chi so cuoi cua ky truoc cho cac dich vu METERED.
     */
    public List<InvoiceDetail> prepareDetails(Long contractId, int month, int year) {
        throw new UnsupportedOperationException("TODO: InvoiceService.prepareDetails");
    }

    /**
     * Lap hoa don moi trong MOT transaction (conn.setAutoCommit(false)):
     * <ol>
     *   <li>kiem tra chua co hoa don cho (contract, month, year)</li>
     *   <li>tinh quantity: METERED = new_index - old_index;
     *       PER_PERSON = contracts.num_occupants; FIXED = 1</li>
     *   <li>amount = quantity * unit_price (snapshot don gia)</li>
     *   <li>service_total = SUM(amount); total_amount = room_charge + service_total - discount</li>
     *   <li>insert invoices roi insertBatch invoice_details</li>
     *   <li>commit, rollback neu co loi</li>
     * </ol>
     */
    public Long createInvoice(Invoice invoice) {
        throw new UnsupportedOperationException("TODO: InvoiceService.createInvoice");
    }

    /**
     * Ghi nhan thanh toan. Cap nhat paid_amount va suy ra status:
     * paid = 0 -> UNPAID, 0 &lt; paid &lt; total -> PARTIAL, paid &gt;= total -> PAID.
     */
    public boolean pay(Long invoiceId, BigDecimal amount, String method) {
        throw new UnsupportedOperationException("TODO: InvoiceService.pay");
    }

    public boolean cancel(Long invoiceId, String reason) {
        throw new UnsupportedOperationException("TODO: InvoiceService.cancel");
    }

    /** Lap hoa don hang loat cho moi hop dong ACTIVE trong ky. */
    public int generateMonthlyInvoices(int month, int year, Long createdBy) {
        throw new UnsupportedOperationException("TODO: InvoiceService.generateMonthlyInvoices");
    }
}
