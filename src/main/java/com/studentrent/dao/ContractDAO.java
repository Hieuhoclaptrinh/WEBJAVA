package com.studentrent.dao;

import com.studentrent.model.Contract;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/** Truy van bang {@code contracts}. Skeleton - chua implement. */
public class ContractDAO implements GenericDAO<Contract, Long> {

    @Override
    public Optional<Contract> findById(Long id) {
        throw new UnsupportedOperationException("TODO: ContractDAO.findById");
    }

    @Override
    public List<Contract> findAll() {
        throw new UnsupportedOperationException("TODO: ContractDAO.findAll");
    }

    @Override
    public List<Contract> findPage(int page, int size) {
        throw new UnsupportedOperationException("TODO: ContractDAO.findPage");
    }

    @Override
    public Long insert(Contract entity) {
        throw new UnsupportedOperationException("TODO: ContractDAO.insert");
    }

    @Override
    public boolean update(Contract entity) {
        throw new UnsupportedOperationException("TODO: ContractDAO.update");
    }

    @Override
    public boolean delete(Long id) {
        throw new UnsupportedOperationException("TODO: ContractDAO.delete");
    }

    @Override
    public long count() {
        throw new UnsupportedOperationException("TODO: ContractDAO.count");
    }

    // ---------- Truy van rieng ----------

    public Optional<Contract> findByCode(String contractCode) {
        throw new UnsupportedOperationException("TODO: ContractDAO.findByCode");
    }

    /** Join rooms + tenants de hien thi ma phong / ten khach tren danh sach. */
    public List<Contract> search(String keyword, String status, int page, int size) {
        throw new UnsupportedOperationException("TODO: ContractDAO.search");
    }

    /** Hop dong ACTIVE hien tai cua mot phong (null neu phong dang trong). */
    public Optional<Contract> findActiveByRoomId(Long roomId) {
        throw new UnsupportedOperationException("TODO: ContractDAO.findActiveByRoomId");
    }

    public List<Contract> findByTenantId(Long tenantId) {
        throw new UnsupportedOperationException("TODO: ContractDAO.findByTenantId");
    }

    /** Hop dong sap het han truoc {@code beforeDate} - dung canh bao tren dashboard. */
    public List<Contract> findExpiringBefore(LocalDate beforeDate) {
        throw new UnsupportedOperationException("TODO: ContractDAO.findExpiringBefore");
    }

    public boolean updateStatus(Long contractId, String status) {
        throw new UnsupportedOperationException("TODO: ContractDAO.updateStatus");
    }

    /** Sinh ma hop dong tiep theo, vd HD2026-0007. */
    public String nextContractCode(int year) {
        throw new UnsupportedOperationException("TODO: ContractDAO.nextContractCode");
    }
}
