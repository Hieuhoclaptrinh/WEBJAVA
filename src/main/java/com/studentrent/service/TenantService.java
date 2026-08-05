package com.studentrent.service;

import com.studentrent.dao.TenantDAO;
import com.studentrent.model.Tenant;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.regex.Pattern;

/** Nghiep vu quan ly khach thue. */
public class TenantService {

    /** So dien thoai Viet Nam - dung 10 chu so. */
    private static final Pattern PHONE_PATTERN = Pattern.compile("\\d{10}");
    private static final Pattern EMAIL_PATTERN = Pattern.compile("^[^@\\s]+@[^@\\s]+\\.[^@\\s]+$");
    private static final Set<String> GENDERS = Set.of("MALE", "FEMALE", "OTHER");
    private static final String DEFAULT_GENDER = "OTHER";

    private final TenantDAO tenantDAO = new TenantDAO();

    public List<Tenant> search(String keyword, int page, int size) {
        return tenantDAO.search(keyword, page, size);
    }

    /** Tong so ket qua khop {@link #search} - dung tinh so trang o servlet. */
    public long countSearch(String keyword) {
        return tenantDAO.countSearch(keyword);
    }

    public Optional<Tenant> findById(Long id) {
        return tenantDAO.findById(id);
    }

    /** Tao khach thue - kiem tra trung CCCD / ma sinh vien truoc khi ghi. */
    public Long create(Tenant tenant) {
        validate(tenant, null);
        return tenantDAO.insert(tenant);
    }

    public boolean update(Tenant tenant) {
        if (tenant.getId() == null) {
            throw new IllegalArgumentException("Thiếu id khách thuê cần sửa.");
        }
        validate(tenant, tenant.getId());
        return tenantDAO.update(tenant);
    }

    /** Chi cho xoa khi khach khong con hop dong ACTIVE hoac PENDING nao giu phong. */
    public boolean delete(Long id) {
        if (id == null) {
            return false;
        }
        if (tenantDAO.hasActiveOrPendingContract(id)) {
            throw new IllegalStateException(
                    "Khách thuê đang có hợp đồng hiệu lực, không thể xóa.");
        }
        return tenantDAO.delete(id);
    }

    public long countAll() {
        return tenantDAO.count();
    }

    /**
     * Kiem tra du lieu bat buoc, dinh dang SDT/email, va trung CCCD/ma sinh vien.
     *
     * @param excludeId id cua khach dang sua (bo qua khi kiem tra trung voi chinh
     *                   no); null khi tao moi
     */
    private void validate(Tenant tenant, Long excludeId) {
        if (tenant.getFullName() == null || tenant.getFullName().isBlank()) {
            throw new IllegalArgumentException("Họ tên không được để trống.");
        }
        if (tenant.getIdCard() == null || tenant.getIdCard().isBlank()) {
            throw new IllegalArgumentException("CMND/CCCD không được để trống.");
        }
        // Cot gender la NOT NULL va chi co DEFAULT 'OTHER' luc INSERT (khong ap
        // dung cho UPDATE) - tu dien gia tri thieu o day de khong bao gio nem
        // SQLException vi phat lai xuong DB, thay vao do bao ro loi khi gia tri
        // sai enum.
        if (tenant.getGender() == null || tenant.getGender().isBlank()) {
            tenant.setGender(DEFAULT_GENDER);
        } else if (!GENDERS.contains(tenant.getGender())) {
            throw new IllegalArgumentException("Giới tính không hợp lệ.");
        }
        if (tenant.getPhone() == null || !PHONE_PATTERN.matcher(tenant.getPhone()).matches()) {
            throw new IllegalArgumentException("Số điện thoại phải gồm đúng 10 chữ số.");
        }
        if (tenant.getEmail() != null && !tenant.getEmail().isBlank()
                && !EMAIL_PATTERN.matcher(tenant.getEmail()).matches()) {
            throw new IllegalArgumentException("Email không hợp lệ.");
        }
        if (tenantDAO.existsByIdCard(tenant.getIdCard(), excludeId)) {
            throw new IllegalArgumentException("CMND/CCCD đã tồn tại.");
        }
        if (tenant.getStudentCode() != null && !tenant.getStudentCode().isBlank()
                && tenantDAO.existsByStudentCode(tenant.getStudentCode(), excludeId)) {
            throw new IllegalArgumentException("Mã sinh viên đã tồn tại.");
        }
    }
}
