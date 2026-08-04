package com.studentrent.model;

import java.io.Serializable;
import java.math.BigDecimal;

/** Anh xa bang {@code invoice_details} - mot dong dich vu tren hoa don. */
public class InvoiceDetail implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;
    private Long invoiceId;
    private Long serviceId;
    /** Chi so dau - chi dung cho dich vu METERED (dien, nuoc). */
    private BigDecimal oldIndex;
    /** Chi so cuoi - chi dung cho dich vu METERED. */
    private BigDecimal newIndex;
    private BigDecimal quantity;
    /** Snapshot don gia luc lap hoa don (khong doi khi services.unit_price thay doi). */
    private BigDecimal unitPrice;
    private BigDecimal amount;
    private String note;

    /** Truong join - chi de hien thi tren view. */
    private String serviceName;
    private String serviceUnit;
    private String serviceType;

    public InvoiceDetail() {
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getInvoiceId() { return invoiceId; }
    public void setInvoiceId(Long invoiceId) { this.invoiceId = invoiceId; }

    public Long getServiceId() { return serviceId; }
    public void setServiceId(Long serviceId) { this.serviceId = serviceId; }

    public BigDecimal getOldIndex() { return oldIndex; }
    public void setOldIndex(BigDecimal oldIndex) { this.oldIndex = oldIndex; }

    public BigDecimal getNewIndex() { return newIndex; }
    public void setNewIndex(BigDecimal newIndex) { this.newIndex = newIndex; }

    public BigDecimal getQuantity() { return quantity; }
    public void setQuantity(BigDecimal quantity) { this.quantity = quantity; }

    public BigDecimal getUnitPrice() { return unitPrice; }
    public void setUnitPrice(BigDecimal unitPrice) { this.unitPrice = unitPrice; }

    public BigDecimal getAmount() { return amount; }
    public void setAmount(BigDecimal amount) { this.amount = amount; }

    public String getNote() { return note; }
    public void setNote(String note) { this.note = note; }

    public String getServiceName() { return serviceName; }
    public void setServiceName(String serviceName) { this.serviceName = serviceName; }

    public String getServiceUnit() { return serviceUnit; }
    public void setServiceUnit(String serviceUnit) { this.serviceUnit = serviceUnit; }

    public String getServiceType() { return serviceType; }
    public void setServiceType(String serviceType) { this.serviceType = serviceType; }

    @Override
    public String toString() {
        return "InvoiceDetail{id=" + id + ", serviceId=" + serviceId + ", amount=" + amount + "}";
    }
}
