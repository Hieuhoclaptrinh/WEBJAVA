package com.studentrent.model;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

/** Anh xa bang {@code tenants}. */
public class Tenant implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;
    private String fullName;
    private String idCard;
    private LocalDate dateOfBirth;
    /** MALE | FEMALE | OTHER */
    private String gender;
    private String phone;
    private String email;
    private String hometown;
    private String school;
    private String studentCode;
    private String avatarPath;
    private String note;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public Tenant() {
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getFullName() { return fullName; }
    public void setFullName(String fullName) { this.fullName = fullName; }

    public String getIdCard() { return idCard; }
    public void setIdCard(String idCard) { this.idCard = idCard; }

    public LocalDate getDateOfBirth() { return dateOfBirth; }
    public void setDateOfBirth(LocalDate dateOfBirth) { this.dateOfBirth = dateOfBirth; }

    public String getGender() { return gender; }
    public void setGender(String gender) { this.gender = gender; }

    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getHometown() { return hometown; }
    public void setHometown(String hometown) { this.hometown = hometown; }

    public String getSchool() { return school; }
    public void setSchool(String school) { this.school = school; }

    public String getStudentCode() { return studentCode; }
    public void setStudentCode(String studentCode) { this.studentCode = studentCode; }

    public String getAvatarPath() { return avatarPath; }
    public void setAvatarPath(String avatarPath) { this.avatarPath = avatarPath; }

    public String getNote() { return note; }
    public void setNote(String note) { this.note = note; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }

    @Override
    public String toString() {
        return "Tenant{id=" + id + ", fullName='" + fullName + "', idCard='" + idCard + "'}";
    }
}
