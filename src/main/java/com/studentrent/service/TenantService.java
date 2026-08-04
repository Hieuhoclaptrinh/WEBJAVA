package com.studentrent.service;

import com.studentrent.dao.TenantDAO;
import com.studentrent.model.Tenant;

import java.util.List;
import java.util.Optional;

/** Nghiep vu quan ly khach thue. Skeleton - chua implement. */
public class TenantService {

    private final TenantDAO tenantDAO = new TenantDAO();

    public List<Tenant> search(String keyword, int page, int size) {
        throw new UnsupportedOperationException("TODO: TenantService.search");
    }

    public Optional<Tenant> findById(Long id) {
        throw new UnsupportedOperationException("TODO: TenantService.findById");
    }

    /** Tao khach thue - kiem tra trung CCCD. */
    public Long create(Tenant tenant) {
        throw new UnsupportedOperationException("TODO: TenantService.create");
    }

    public boolean update(Tenant tenant) {
        throw new UnsupportedOperationException("TODO: TenantService.update");
    }

    /** Chi cho xoa khi khach khong con hop dong ACTIVE. */
    public boolean delete(Long id) {
        throw new UnsupportedOperationException("TODO: TenantService.delete");
    }

    public long countAll() {
        throw new UnsupportedOperationException("TODO: TenantService.countAll");
    }
}
