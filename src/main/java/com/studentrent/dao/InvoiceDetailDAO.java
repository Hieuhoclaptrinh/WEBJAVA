package com.studentrent.dao;

import com.studentrent.model.InvoiceDetail;

import java.sql.Connection;
import java.util.List;
import java.util.Optional;

/** Truy van bang {@code invoice_details}. Skeleton - chua implement. */
public class InvoiceDetailDAO implements GenericDAO<InvoiceDetail, Long> {

    @Override
    public Optional<InvoiceDetail> findById(Long id) {
        throw new UnsupportedOperationException("TODO: InvoiceDetailDAO.findById");
    }

    @Override
    public List<InvoiceDetail> findAll() {
        throw new UnsupportedOperationException("TODO: InvoiceDetailDAO.findAll");
    }

    @Override
    public List<InvoiceDetail> findPage(int page, int size) {
        throw new UnsupportedOperationException("TODO: InvoiceDetailDAO.findPage");
    }

    @Override
    public Long insert(InvoiceDetail entity) {
        throw new UnsupportedOperationException("TODO: InvoiceDetailDAO.insert");
    }

    @Override
    public boolean update(InvoiceDetail entity) {
        throw new UnsupportedOperationException("TODO: InvoiceDetailDAO.update");
    }

    @Override
    public boolean delete(Long id) {
        throw new UnsupportedOperationException("TODO: InvoiceDetailDAO.delete");
    }

    @Override
    public long count() {
        throw new UnsupportedOperationException("TODO: InvoiceDetailDAO.count");
    }

    // ---------- Truy van rieng ----------

    /** Chi tiet cua mot hoa don, join services de lay ten / don vi. */
    public List<InvoiceDetail> findByInvoiceId(Long invoiceId) {
        throw new UnsupportedOperationException("TODO: InvoiceDetailDAO.findByInvoiceId");
    }

    /**
     * Insert nhieu dong trong cung transaction voi invoice (dung batch).
     * Connection do InvoiceService quan ly.
     */
    public int[] insertBatch(Connection conn, Long invoiceId, List<InvoiceDetail> details) {
        throw new UnsupportedOperationException("TODO: InvoiceDetailDAO.insertBatch");
    }

    /** Xoa toan bo chi tiet cua hoa don truoc khi ghi lai (khi sua hoa don). */
    public int deleteByInvoiceId(Connection conn, Long invoiceId) {
        throw new UnsupportedOperationException("TODO: InvoiceDetailDAO.deleteByInvoiceId");
    }
}
