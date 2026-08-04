package com.studentrent.service;

import com.studentrent.dao.ContractDAO;
import com.studentrent.dao.RoomDAO;
import com.studentrent.model.Contract;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/** Nghiep vu quan ly hop dong thue. Skeleton - chua implement. */
public class ContractService {

    private final ContractDAO contractDAO = new ContractDAO();
    private final RoomDAO roomDAO = new RoomDAO();

    public List<Contract> search(String keyword, String status, int page, int size) {
        throw new UnsupportedOperationException("TODO: ContractService.search");
    }

    public Optional<Contract> findById(Long id) {
        throw new UnsupportedOperationException("TODO: ContractService.findById");
    }

    /**
     * Tao hop dong moi. Can lam trong 1 transaction:
     * <ol>
     *   <li>kiem tra phong dang AVAILABLE va chua co hop dong ACTIVE</li>
     *   <li>kiem tra num_occupants &lt;= rooms.max_occupants</li>
     *   <li>sinh contract_code</li>
     *   <li>insert contracts</li>
     *   <li>UPDATE rooms SET status = 'OCCUPIED'</li>
     * </ol>
     */
    public Long create(Contract contract) {
        throw new UnsupportedOperationException("TODO: ContractService.create");
    }

    public boolean update(Contract contract) {
        throw new UnsupportedOperationException("TODO: ContractService.update");
    }

    /**
     * Cham dut hop dong truoc han: set status = TERMINATED, terminated_at,
     * va tra phong ve AVAILABLE (trong cung transaction).
     */
    public boolean terminate(Long contractId, LocalDate terminatedAt, String reason) {
        throw new UnsupportedOperationException("TODO: ContractService.terminate");
    }

    /** Quet cac hop dong da qua end_date va chuyen sang EXPIRED. */
    public int expireOverdueContracts() {
        throw new UnsupportedOperationException("TODO: ContractService.expireOverdueContracts");
    }

    /** Hop dong het han trong {@code days} ngay toi - canh bao tren dashboard. */
    public List<Contract> findExpiringSoon(int days) {
        throw new UnsupportedOperationException("TODO: ContractService.findExpiringSoon");
    }
}
