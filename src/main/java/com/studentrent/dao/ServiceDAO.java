package com.studentrent.dao;

import com.studentrent.model.Service;

import java.util.List;
import java.util.Optional;

/** Truy van bang {@code services}. Skeleton - chua implement. */
public class ServiceDAO implements GenericDAO<Service, Long> {

    @Override
    public Optional<Service> findById(Long id) {
        throw new UnsupportedOperationException("TODO: ServiceDAO.findById");
    }

    @Override
    public List<Service> findAll() {
        throw new UnsupportedOperationException("TODO: ServiceDAO.findAll");
    }

    @Override
    public List<Service> findPage(int page, int size) {
        throw new UnsupportedOperationException("TODO: ServiceDAO.findPage");
    }

    @Override
    public Long insert(Service entity) {
        throw new UnsupportedOperationException("TODO: ServiceDAO.insert");
    }

    @Override
    public boolean update(Service entity) {
        throw new UnsupportedOperationException("TODO: ServiceDAO.update");
    }

    @Override
    public boolean delete(Long id) {
        throw new UnsupportedOperationException("TODO: ServiceDAO.delete");
    }

    @Override
    public long count() {
        throw new UnsupportedOperationException("TODO: ServiceDAO.count");
    }

    // ---------- Truy van rieng ----------

    public Optional<Service> findByCode(String code) {
        throw new UnsupportedOperationException("TODO: ServiceDAO.findByCode");
    }

    /** Chi lay dich vu ACTIVE - dung khi lap hoa don. */
    public List<Service> findActive() {
        throw new UnsupportedOperationException("TODO: ServiceDAO.findActive");
    }
}
