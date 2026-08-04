package com.studentrent.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/** Anh xa bang {@code rooms}. */
public class Room implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;
    private String roomCode;
    private Long roomTypeId;
    private Integer floorNo;
    private BigDecimal area;
    private BigDecimal price;
    private Integer maxOccupants;
    /** AVAILABLE | OCCUPIED | RESERVED | MAINTENANCE */
    private String status;
    private String imagePath;
    private String description;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    /** Ten loai phong - join tu room_types, chi de hien thi tren view. */
    private String roomTypeName;

    public Room() {
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getRoomCode() { return roomCode; }
    public void setRoomCode(String roomCode) { this.roomCode = roomCode; }

    public Long getRoomTypeId() { return roomTypeId; }
    public void setRoomTypeId(Long roomTypeId) { this.roomTypeId = roomTypeId; }

    public Integer getFloorNo() { return floorNo; }
    public void setFloorNo(Integer floorNo) { this.floorNo = floorNo; }

    public BigDecimal getArea() { return area; }
    public void setArea(BigDecimal area) { this.area = area; }

    public BigDecimal getPrice() { return price; }
    public void setPrice(BigDecimal price) { this.price = price; }

    public Integer getMaxOccupants() { return maxOccupants; }
    public void setMaxOccupants(Integer maxOccupants) { this.maxOccupants = maxOccupants; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public String getImagePath() { return imagePath; }
    public void setImagePath(String imagePath) { this.imagePath = imagePath; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }

    public String getRoomTypeName() { return roomTypeName; }
    public void setRoomTypeName(String roomTypeName) { this.roomTypeName = roomTypeName; }

    @Override
    public String toString() {
        return "Room{id=" + id + ", roomCode='" + roomCode + "', status='" + status + "'}";
    }
}
