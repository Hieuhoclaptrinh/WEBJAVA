package com.studentrent.dao;

import com.studentrent.model.Tenant;

import java.util.List;
import java.util.Optional;

/** Truy van bang {@code tenants}. Skeleton - chua implement. */
public class TenantDAO implements GenericDAO<Tenant, Long> {

    @Override
    public Optional<Tenant> findById(Long id) {
        throw new UnsupportedOperationException("TODO: TenantDAO.findById");
    }

    @Override
    public List<Tenant> findAll() {
        throw new UnsupportedOperationException("TODO: TenantDAO.findAll");
    }

    @Override
    public List<Tenant> findPage(int page, int size) {
        throw new UnsupportedOperationException("TODO: TenantDAO.findPage");
    }

    @Override
    public Long insert(Tenant entity) {
        throw new UnsupportedOperationException("TODO: TenantDAO.insert");
    }

    @Override
    public boolean update(Tenant entity) {
        throw new UnsupportedOperationException("TODO: TenantDAO.update");
    }

    @Override
    public boolean delete(Long id) {
        throw new UnsupportedOperationException("TODO: TenantDAO.delete");
    }

    @Override
    public long count() {
        throw new UnsupportedOperationException("TODO: TenantDAO.count");
    }

    // ---------- Truy van rieng ----------

    public Optional<Tenant> findByIdCard(String idCard) {
        throw new UnsupportedOperationException("TODO: TenantDAO.findByIdCard");
    }

    /** Tim theo ho ten / CCCD / SDT / ma sinh vien. */
    public List<Tenant> search(String keyword, int page, int size) {
        throw new UnsupportedOperationException("TODO: TenantDAO.search");
    }

    /** Danh sach khach dang thue mot phong (qua hop dong ACTIVE). */
    public List<Tenant> findByRoomId(Long roomId) {
        throw new UnsupportedOperationException("TODO: TenantDAO.findByRoomId");
    }
}
