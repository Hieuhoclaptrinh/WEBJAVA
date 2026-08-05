package com.studentrent.dao;

import com.studentrent.model.User;
import com.studentrent.util.Constants;
import com.studentrent.util.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Truy van bang {@code users}.
 *
 * <p>Moi cau lenh di qua PreparedStatement, khong noi chuoi gia tri vao SQL.
 * Ten bang va ten cot viet CHU THUONG dung nhu schema.sql: MySQL tren Windows
 * khong phan biet hoa thuong nhung tren Linux/CI thi co, nen "FROM Users" chay
 * duoc o may nay va bao "table doesn't exist" o may kia.</p>
 */
public class UserDAO implements GenericDAO<User, Long> {

    private static final String COLUMNS =
            "id, username, password_hash, full_name, email, phone, "
            + "role, status, last_login_at, created_at, updated_at";

    private static final String SELECT_BASE = "select " + COLUMNS + " from users";

    /** Gia tri mac dinh cua ENUM {@code users.status}. */
    private static final String STATUS_ACTIVE = "ACTIVE";

    @Override
    public Optional<User> findById(Long id) {
        if (id == null) {
            return Optional.empty();
        }
        return queryOne(SELECT_BASE + " where id = ?", ps -> ps.setLong(1, id));
    }

    @Override
    public List<User> findAll() {
        return queryList(SELECT_BASE + " order by id", ps -> { });
    }

    @Override
    public List<User> findPage(int page, int size) {
        int safeSize = Math.max(size, 1);
        int offset = Math.max(page - 1, 0) * safeSize;
        return queryList(SELECT_BASE + " order by id limit ? offset ?", ps -> {
            ps.setInt(1, safeSize);
            ps.setInt(2, offset);
        });
    }

    /**
     * Them tai khoan moi. {@code created_at} / {@code updated_at} de DB tu dien
     * theo DEFAULT CURRENT_TIMESTAMP trong schema.
     *
     * @return khoa chinh vua sinh
     */
    @Override
    public Long insert(User entity) {
        String sql = "insert into users "
                + "(username, password_hash, full_name, email, phone, role, status) "
                + "values (?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, entity.getUsername());
            ps.setString(2, entity.getPasswordHash());
            ps.setString(3, entity.getFullName());
            ps.setString(4, entity.getEmail());
            ps.setString(5, entity.getPhone());
            ps.setString(6, entity.getRole() == null ? Constants.ROLE_STAFF : entity.getRole());
            ps.setString(7, entity.getStatus() == null ? STATUS_ACTIVE : entity.getStatus());
            ps.executeUpdate();

            try (ResultSet keys = ps.getGeneratedKeys()) {
                return keys.next() ? keys.getLong(1) : null;
            }
        } catch (SQLException e) {
            throw new RuntimeException("Loi them user: " + entity.getUsername(), e);
        }
    }

    /** Cap nhat thong tin ho so. Mat khau doi rieng qua {@link #updatePassword}. */
    @Override
    public boolean update(User entity) {
        String sql = "update users set username = ?, full_name = ?, email = ?, "
                + "phone = ?, role = ?, status = ? where id = ?";
        return execute(sql, ps -> {
            ps.setString(1, entity.getUsername());
            ps.setString(2, entity.getFullName());
            ps.setString(3, entity.getEmail());
            ps.setString(4, entity.getPhone());
            ps.setString(5, entity.getRole());
            ps.setString(6, entity.getStatus());
            ps.setLong(7, entity.getId());
        });
    }

    @Override
    public boolean delete(Long id) {
        if (id == null) {
            return false;
        }
        return execute("delete from users where id = ?", ps -> ps.setLong(1, id));
    }

    @Override
    public long count() {
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement("select count(*) from users");
             ResultSet rs = ps.executeQuery()) {
            return rs.next() ? rs.getLong(1) : 0L;
        } catch (SQLException e) {
            throw new RuntimeException("Loi dem users", e);
        }
    }

    // ---------- Truy van rieng ----------

    /** Tim theo username - dung khi dang nhap. */
    public Optional<User> findByUsername(String username) {
        if (username == null || username.isBlank()) {
            return Optional.empty();
        }
        return queryOne(SELECT_BASE + " where username = ?", ps -> ps.setString(1, username));
    }

    /** Doi mat khau (luu hash moi, KHONG bao gio luu plaintext). */
    public boolean updatePassword(Long userId, String newPasswordHash) {
        if (userId == null || newPasswordHash == null || newPasswordHash.isBlank()) {
            return false;
        }
        return execute("update users set password_hash = ? where id = ?", ps -> {
            ps.setString(1, newPasswordHash);
            ps.setLong(2, userId);
        });
    }

    /**
     * Ghi nhan thoi diem dang nhap thanh cong.
     *
     * <p>Thoi diem duoc tinh o phia Java bang {@code LocalDateTime.now(Constants.ZONE)}
     * roi truyen vao PreparedStatement, KHONG dung {@code NOW()} cua MySQL: server
     * MySQL va JVM co the nam o hai mui gio khac nhau nen gia tri ghi xuong se lech.</p>
     */
    public boolean updateLastLogin(Long userId) {
        if (userId == null) {
            return false;
        }
        LocalDateTime now = LocalDateTime.now(Constants.ZONE);
        return execute("update users set last_login_at = ? where id = ?", ps -> {
            ps.setObject(1, now);
            ps.setLong(2, userId);
        });
    }

    // ---------- Ho tro ----------

    /** Gan tham so cho PreparedStatement - lambda duoc phep nem SQLException. */
    @FunctionalInterface
    private interface Binder {
        void bind(PreparedStatement ps) throws SQLException;
    }

    private Optional<User> queryOne(String sql, Binder binder) {
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            binder.bind(ps);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next() ? Optional.of(map(rs)) : Optional.empty();
            }
        } catch (SQLException e) {
            throw new RuntimeException("Loi truy van users: " + sql, e);
        }
    }

    private List<User> queryList(String sql, Binder binder) {
        List<User> result = new ArrayList<>();
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            binder.bind(ps);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    result.add(map(rs));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Loi truy van users: " + sql, e);
        }
        return result;
    }

    private boolean execute(String sql, Binder binder) {
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            binder.bind(ps);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException("Loi cap nhat users: " + sql, e);
        }
    }

    /**
     * Doc mot dong ResultSet thanh User.
     *
     * <p>Cot DATETIME doc bang {@code getObject(col, LocalDateTime.class)} chu khong
     * qua {@code java.sql.Timestamp} - Timestamp ap mui gio mac dinh cua JVM vao gia
     * tri nen cung mot ban ghi se doc ra hai gio khac nhau tren hai may.</p>
     */
    private User map(ResultSet rs) throws SQLException {
        User user = new User();
        user.setId(rs.getLong("id"));
        user.setUsername(rs.getString("username"));
        user.setPasswordHash(rs.getString("password_hash"));
        user.setFullName(rs.getString("full_name"));
        user.setEmail(rs.getString("email"));
        user.setPhone(rs.getString("phone"));
        user.setRole(rs.getString("role"));
        user.setStatus(rs.getString("status"));
        user.setLastLoginAt(rs.getObject("last_login_at", LocalDateTime.class));
        user.setCreatedAt(rs.getObject("created_at", LocalDateTime.class));
        user.setUpdatedAt(rs.getObject("updated_at", LocalDateTime.class));
        return user;
    }
}
