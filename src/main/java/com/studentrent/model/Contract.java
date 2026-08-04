package com.studentrent.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

/** Anh xa bang {@code contracts}. */
public class Contract implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;
    private String contractCode;
    private Long roomId;
    private Long tenantId;
    private LocalDate startDate;
    private LocalDate endDate;
    private BigDecimal deposit;
    private BigDecimal monthlyPrice;
    private Integer numOccupants;
    /** Ngay chot hoa don hang thang (1-28). */
    private Integer billingDay;
    /** PENDING | ACTIVE | EXPIRED | TERMINATED */
    private String status;
    private LocalDate terminatedAt;
    private String note;
    private Long createdBy;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    /** Truong join - chi de hien thi tren view. */
    private String roomCode;
    private String tenantName;

    public Contract() {
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getContractCode() { return contractCode; }
    public void setContractCode(String contractCode) { this.contractCode = contractCode; }

    public Long getRoomId() { return roomId; }
    public void setRoomId(Long roomId) { this.roomId = roomId; }

    public Long getTenantId() { return tenantId; }
    public void setTenantId(Long tenantId) { this.tenantId = tenantId; }

    public LocalDate getStartDate() { return startDate; }
    public void setStartDate(LocalDate startDate) { this.startDate = startDate; }

    public LocalDate getEndDate() { return endDate; }
    public void setEndDate(LocalDate endDate) { this.endDate = endDate; }

    public BigDecimal getDeposit() { return deposit; }
    public void setDeposit(BigDecimal deposit) { this.deposit = deposit; }

    public BigDecimal getMonthlyPrice() { return monthlyPrice; }
    public void setMonthlyPrice(BigDecimal monthlyPrice) { this.monthlyPrice = monthlyPrice; }

    public Integer getNumOccupants() { return numOccupants; }
    public void setNumOccupants(Integer numOccupants) { this.numOccupants = numOccupants; }

    public Integer getBillingDay() { return billingDay; }
    public void setBillingDay(Integer billingDay) { this.billingDay = billingDay; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public LocalDate getTerminatedAt() { return terminatedAt; }
    public void setTerminatedAt(LocalDate terminatedAt) { this.terminatedAt = terminatedAt; }

    public String getNote() { return note; }
    public void setNote(String note) { this.note = note; }

    public Long getCreatedBy() { return createdBy; }
    public void setCreatedBy(Long createdBy) { this.createdBy = createdBy; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }

    public String getRoomCode() { return roomCode; }
    public void setRoomCode(String roomCode) { this.roomCode = roomCode; }

    public String getTenantName() { return tenantName; }
    public void setTenantName(String tenantName) { this.tenantName = tenantName; }

    @Override
    public String toString() {
        return "Contract{id=" + id + ", contractCode='" + contractCode + "', status='" + status + "'}";
    }
}
