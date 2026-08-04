package com.studentrent.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/** Anh xa bang {@code services} - dien, nuoc, rac, wifi, gui xe... */
public class Service implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;
    private String code;
    private String name;
    private String unit;
    private BigDecimal unitPrice;
    /** FIXED | METERED | PER_PERSON */
    private String type;
    /** ACTIVE | INACTIVE */
    private String status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public Service() {
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getCode() { return code; }
    public void setCode(String code) { this.code = code; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getUnit() { return unit; }
    public void setUnit(String unit) { this.unit = unit; }

    public BigDecimal getUnitPrice() { return unitPrice; }
    public void setUnitPrice(BigDecimal unitPrice) { this.unitPrice = unitPrice; }

    public String getType() { return type; }
    public void setType(String type) { this.type = type; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }

    @Override
    public String toString() {
        return "Service{id=" + id + ", code='" + code + "', type='" + type + "'}";
    }
}
