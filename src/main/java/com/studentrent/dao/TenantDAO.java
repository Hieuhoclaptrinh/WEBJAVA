package com.studentrent.dao;

import com.studentrent.model.Tenant;
import com.studentrent.util.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Truy van bang {@code tenants}.
 *
 * <p>Moi cau lenh di qua PreparedStatement, ke ca dieu kien LIKE trong
 * {@link #search}, khong noi chuoi gia tri vao SQL.</p>
 */
public class TenantDAO implements GenericDAO<Tenant, Long> {

    private static final String COLUMNS =
            "id, full_name, id_card, date_of_birth, gender, phone, email, "
            + "hometown, school, student_code, avatar_path, note, created_at, updated_at";

    private static final String SELECT_BASE = "select " + COLUMNS + " from tenants";

    @Override
    public Optional<Tenant> findById(Long id) {
        if (id == null) {
            return Optional.empty();
        }
        return queryOne(SELECT_BASE + " where id = ?", ps -> ps.setLong(1, id));
    }

    @Override
    public List<Tenant> findAll() {
        return queryList(SELECT_BASE + " order by full_name", ps -> { });
    }

    @Override
    public List<Tenant> findPage(int page, int size) {
        int safeSize = Math.max(size, 1);
        int offset = Math.max(page - 1, 0) * safeSize;
        return queryList(SELECT_BASE + " order by full_name limit ? offset ?", ps -> {
            ps.setInt(1, safeSize);
            ps.setInt(2, offset);
        });
    }

    /** Them khach thue moi. {@code created_at}/{@code updated_at} de DB tu dien. */
    @Override
    public Long insert(Tenant entity) {
        String sql = "insert into tenants "
                + "(full_name, id_card, date_of_birth, gender, phone, email, hometown, "
                + "school, student_code, avatar_path, note) "
                + "values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            bindWritableFields(ps, entity);
            ps.executeUpdate();

            try (ResultSet keys = ps.getGeneratedKeys()) {
                return keys.next() ? keys.getLong(1) : null;
            }
        } catch (SQLException e) {
            throw new RuntimeException("Loi them tenant: " + entity.getFullName(), e);
        }
    }

    @Override
    public boolean update(Tenant entity) {
        String sql = "update tenants set full_name = ?, id_card = ?, date_of_birth = ?, "
                + "gender = ?, phone = ?, email = ?, hometown = ?, school = ?, "
                + "student_code = ?, avatar_path = ?, note = ? where id = ?";
        return execute(sql, ps -> {
            int nextIndex = bindWritableFields(ps, entity);
            ps.setLong(nextIndex, entity.getId());
        });
    }

    @Override
    public boolean delete(Long id) {
        if (id == null) {
            return false;
        }
        return execute("delete from tenants where id = ?", ps -> ps.setLong(1, id));
    }

    @Override
    public long count() {
        return countWhere("select count(*) from tenants", ps -> { });
    }

    // ---------- Truy van rieng ----------

    public Optional<Tenant> findByIdCard(String idCard) {
        if (idCard == null || idCard.isBlank()) {
            return Optional.empty();
        }
        return queryOne(SELECT_BASE + " where id_card = ?", ps -> ps.setString(1, idCard));
    }

    /** True neu da co khach khac (id != excludeId) dung CCCD nay. */
    public boolean existsByIdCard(String idCard, Long excludeId) {
        String sql = "select 1 from tenants where id_card = ?"
                + (excludeId != null ? " and id <> ?" : "") + " limit 1";
        return exists(sql, ps -> {
            ps.setString(1, idCard);
            if (excludeId != null) {
                ps.setLong(2, excludeId);
            }
        });
    }

    /** True neu da co khach khac (id != excludeId) dung ma sinh vien nay. */
    public boolean existsByStudentCode(String studentCode, Long excludeId) {
        String sql = "select 1 from tenants where student_code = ?"
                + (excludeId != null ? " and id <> ?" : "") + " limit 1";
        return exists(sql, ps -> {
            ps.setString(1, studentCode);
            if (excludeId != null) {
                ps.setLong(2, excludeId);
            }
        });
    }

    /**
     * Tim theo ho ten / CCCD / SDT / ma sinh vien, khong phan biet hoa thuong
     * (mac dinh collation cua schema). Rong hoac null thi tra ve nhu {@link #findPage}.
     */
    public List<Tenant> search(String keyword, int page, int size) {
        if (keyword == null || keyword.isBlank()) {
            return findPage(page, size);
        }
        String like = "%" + escapeLike(keyword.trim()) + "%";
        int safeSize = Math.max(size, 1);
        int offset = Math.max(page - 1, 0) * safeSize;
        String sql = SELECT_BASE
                + " where full_name like ? or student_code like ? or id_card like ? or phone like ? "
                + "order by full_name limit ? offset ?";
        return queryList(sql, ps -> {
            ps.setString(1, like);
            ps.setString(2, like);
            ps.setString(3, like);
            ps.setString(4, like);
            ps.setInt(5, safeSize);
            ps.setInt(6, offset);
        });
    }

    /** Tong so ket qua khop {@link #search} - dung tinh so trang. */
    public long countSearch(String keyword) {
        if (keyword == null || keyword.isBlank()) {
            return count();
        }
        String like = "%" + escapeLike(keyword.trim()) + "%";
        String sql = "select count(*) from tenants "
                + "where full_name like ? or student_code like ? or id_card like ? or phone like ?";
        return countWhere(sql, ps -> {
            ps.setString(1, like);
            ps.setString(2, like);
            ps.setString(3, like);
            ps.setString(4, like);
        });
    }

    /** Danh sach khach dang thue mot phong, qua hop dong dang ACTIVE. */
    public List<Tenant> findByRoomId(Long roomId) {
        if (roomId == null) {
            return List.of();
        }
        String sql = "select t.id, t.full_name, t.id_card, t.date_of_birth, t.gender, t.phone, "
                + "t.email, t.hometown, t.school, t.student_code, t.avatar_path, t.note, "
                + "t.created_at, t.updated_at "
                + "from tenants t join contracts c on c.tenant_id = t.id "
                + "where c.room_id = ? and c.status = 'ACTIVE'";
        return queryList(sql, ps -> ps.setLong(1, roomId));
    }

    /**
     * True neu khach dang co hop dong ACTIVE hoac PENDING - dung chan xoa nham
     * (contracts la nghiep vu Phase 4, nhung rang buoc xoa can biet ngay tu day).
     */
    public boolean hasActiveOrPendingContract(Long tenantId) {
        if (tenantId == null) {
            return false;
        }
        String sql = "select 1 from contracts where tenant_id = ? "
                + "and status in ('ACTIVE', 'PENDING') limit 1";
        return exists(sql, ps -> ps.setLong(1, tenantId));
    }

    // ---------- Ho tro ----------

    /** Gan tham so cho PreparedStatement - lambda duoc phep nem SQLException. */
    @FunctionalInterface
    private interface Binder {
        void bind(PreparedStatement ps) throws SQLException;
    }

    /** Gan 11 truong ghi duoc cua Tenant tu tham so 1; tra ve chi so tham so ke tiep. */
    private int bindWritableFields(PreparedStatement ps, Tenant t) throws SQLException {
        ps.setString(1, t.getFullName());
        ps.setString(2, t.getIdCard());
        ps.setObject(3, t.getDateOfBirth());
        ps.setString(4, t.getGender());
        ps.setString(5, t.getPhone());
        ps.setString(6, t.getEmail());
        ps.setString(7, t.getHometown());
        ps.setString(8, t.getSchool());
        ps.setString(9, t.getStudentCode());
        ps.setString(10, t.getAvatarPath());
        ps.setString(11, t.getNote());
        return 12;
    }

    /**
     * Escape ky tu wildcard cua LIKE (% va _) va escape char (\) trong tu khoa
     * nguoi dung go - neu khong thi go "%" se tra ve toan bo bang.
     */
    private String escapeLike(String keyword) {
        return keyword.replace("\\", "\\\\").replace("%", "\\%").replace("_", "\\_");
    }

    private Optional<Tenant> queryOne(String sql, Binder binder) {
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            binder.bind(ps);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next() ? Optional.of(map(rs)) : Optional.empty();
            }
        } catch (SQLException e) {
            throw new RuntimeException("Loi truy van tenants: " + sql, e);
        }
    }

    private List<Tenant> queryList(String sql, Binder binder) {
        List<Tenant> result = new ArrayList<>();
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            binder.bind(ps);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    result.add(map(rs));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Loi truy van tenants: " + sql, e);
        }
        return result;
    }

    private boolean execute(String sql, Binder binder) {
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            binder.bind(ps);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException("Loi cap nhat tenants: " + sql, e);
        }
    }

    private boolean exists(String sql, Binder binder) {
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            binder.bind(ps);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }
        } catch (SQLException e) {
            throw new RuntimeException("Loi truy van tenants: " + sql, e);
        }
    }

    private long countWhere(String sql, Binder binder) {
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            binder.bind(ps);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next() ? rs.getLong(1) : 0L;
            }
        } catch (SQLException e) {
            throw new RuntimeException("Loi dem tenants: " + sql, e);
        }
    }

    /**
     * Doc mot dong ResultSet thanh Tenant. Cot DATE/DATETIME doc bang
     * {@code getObject(col, Type.class)} chu khong qua {@code java.sql.Date}/
     * {@code Timestamp} - hai lop do ap mui gio mac dinh cua JVM vao gia tri.
     */
    private Tenant map(ResultSet rs) throws SQLException {
        Tenant t = new Tenant();
        t.setId(rs.getLong("id"));
        t.setFullName(rs.getString("full_name"));
        t.setIdCard(rs.getString("id_card"));
        t.setDateOfBirth(rs.getObject("date_of_birth", LocalDate.class));
        t.setGender(rs.getString("gender"));
        t.setPhone(rs.getString("phone"));
        t.setEmail(rs.getString("email"));
        t.setHometown(rs.getString("hometown"));
        t.setSchool(rs.getString("school"));
        t.setStudentCode(rs.getString("student_code"));
        t.setAvatarPath(rs.getString("avatar_path"));
        t.setNote(rs.getString("note"));
        t.setCreatedAt(rs.getObject("created_at", LocalDateTime.class));
        t.setUpdatedAt(rs.getObject("updated_at", LocalDateTime.class));
        return t;
    }
}
