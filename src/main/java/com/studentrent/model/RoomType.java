package com.studentrent.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/** Anh xa bang {@code room_types}. */
public class RoomType implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;
    private String name;
    private BigDecimal basePrice;
    private Integer maxOccupants;
    private BigDecimal defaultArea;
    private String description;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public RoomType() {
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public BigDecimal getBasePrice() { return basePrice; }
    public void setBasePrice(BigDecimal basePrice) { this.basePrice = basePrice; }

    public Integer getMaxOccupants() { return maxOccupants; }
    public void setMaxOccupants(Integer maxOccupants) { this.maxOccupants = maxOccupants; }

    public BigDecimal getDefaultArea() { return defaultArea; }
    public void setDefaultArea(BigDecimal defaultArea) { this.defaultArea = defaultArea; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }

    @Override
    public String toString() {
        return "RoomType{id=" + id + ", name='" + name + "'}";
    }
}
