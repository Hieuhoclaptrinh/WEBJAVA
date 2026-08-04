package com.studentrent.service;

import com.studentrent.dao.ServiceDAO;
import com.studentrent.model.Service;

import java.util.List;
import java.util.Optional;

/**
 * Nghiep vu quan ly danh muc dich vu (dien, nuoc, rac, wifi...).
 * Dat ten "ServiceCatalogService" de khong trung voi {@link Service} entity.
 * Skeleton - chua implement.
 */
public class ServiceCatalogService {

    private final ServiceDAO serviceDAO = new ServiceDAO();

    public List<Service> findAll() {
        throw new UnsupportedOperationException("TODO: ServiceCatalogService.findAll");
    }

    public List<Service> findActive() {
        throw new UnsupportedOperationException("TODO: ServiceCatalogService.findActive");
    }

    public Optional<Service> findById(Long id) {
        throw new UnsupportedOperationException("TODO: ServiceCatalogService.findById");
    }

    public Long create(Service service) {
        throw new UnsupportedOperationException("TODO: ServiceCatalogService.create");
    }

    public boolean update(Service service) {
        throw new UnsupportedOperationException("TODO: ServiceCatalogService.update");
    }

    /** Khong xoa cung neu dich vu da xuat hien tren hoa don - chuyen INACTIVE. */
    public boolean deactivate(Long id) {
        throw new UnsupportedOperationException("TODO: ServiceCatalogService.deactivate");
    }
}
