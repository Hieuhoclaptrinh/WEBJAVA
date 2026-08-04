package com.studentrent.dao;

import com.studentrent.model.Invoice;

import java.math.BigDecimal;
import java.sql.Connection;
import java.util.List;
import java.util.Optional;

/** Truy van bang {@code invoices}. Skeleton - chua implement. */
public class InvoiceDAO implements GenericDAO<Invoice, Long> {

    @Override
    public Optional<Invoice> findById(Long id) {
        throw new UnsupportedOperationException("TODO: InvoiceDAO.findById");
    }

    @Override
    public List<Invoice> findAll() {
        throw new UnsupportedOperationException("TODO: InvoiceDAO.findAll");
    }

    @Override
    public List<Invoice> findPage(int page, int size) {
        throw new UnsupportedOperationException("TODO: InvoiceDAO.findPage");
    }

    @Override
    public Long insert(Invoice entity) {
        throw new UnsupportedOperationException("TODO: InvoiceDAO.insert");
    }

    @Override
    public boolean update(Invoice entity) {
        throw new UnsupportedOperationException("TODO: InvoiceDAO.update");
    }

    @Override
    public boolean delete(Long id) {
        throw new UnsupportedOperationException("TODO: InvoiceDAO.delete");
    }

    @Override
    public long count() {
        throw new UnsupportedOperationException("TODO: InvoiceDAO.count");
    }

    // ---------- Truy van rieng ----------

    /**
     * Insert dung Connection do ben ngoai truyen vao, de InvoiceService co the
     * bao hoa don + invoice_details trong cung mot transaction.
     */
    public Long insert(Connection conn, Invoice entity) {
        throw new UnsupportedOperationException("TODO: InvoiceDAO.insert(Connection, Invoice)");
    }

    public Optional<Invoice> findByCode(String invoiceCode) {
        throw new UnsupportedOperationException("TODO: InvoiceDAO.findByCode");
    }

    /** Lay hoa don kem toan bo invoice_details va thong tin join. */
    public Optional<Invoice> findFullById(Long id) {
        throw new UnsupportedOperationException("TODO: InvoiceDAO.findFullById");
    }

    public List<Invoice> search(String keyword, String status,
                               Integer month, Integer year, int page, int size) {
        throw new UnsupportedOperationException("TODO: InvoiceDAO.search");
    }

    /** Kiem tra hop dong da co hoa don cua ky nay chua (rang buoc uk_invoices_period). */
    public boolean existsForPeriod(Long contractId, int month, int year) {
        throw new UnsupportedOperationException("TODO: InvoiceDAO.existsForPeriod");
    }

    /** Chi so cuoi cua ky truoc - lam chi so dau cho ky moi. */
    public BigDecimal findPreviousIndex(Long contractId, Long serviceId, int month, int year) {
        throw new UnsupportedOperationException("TODO: InvoiceDAO.findPreviousIndex");
    }

    /** Ghi nhan thanh toan, cap nhat paid_amount + status. */
    public boolean recordPayment(Long invoiceId, BigDecimal amount, String method) {
        throw new UnsupportedOperationException("TODO: InvoiceDAO.recordPayment");
    }

    /** Danh dau OVERDUE cho cac hoa don qua han - chay theo lich hoac khi mo dashboard. */
    public int markOverdue() {
        throw new UnsupportedOperationException("TODO: InvoiceDAO.markOverdue");
    }

    public String nextInvoiceCode(int year, int month) {
        throw new UnsupportedOperationException("TODO: InvoiceDAO.nextInvoiceCode");
    }
}
