package com.studentrent.service;

import com.studentrent.dao.UserDAO;
import com.studentrent.model.User;
import com.studentrent.util.Constants;
import com.studentrent.util.PasswordUtil;
import jakarta.servlet.http.HttpSession;

import java.util.Optional;

/** Dang nhap, dang xuat, doi mat khau, phan quyen. */
public class AuthService {

    /** Chi tai khoan ACTIVE moi dang nhap duoc; LOCKED bi tu choi. */
    private static final String STATUS_ACTIVE = "ACTIVE";

    private final UserDAO userDAO = new UserDAO();

    /**
     * Xac thuc dang nhap.
     *
     * <p>Mat khau duoc kiem tra qua {@link PasswordUtil#verify} - khong goi thang
     * {@code BCrypt.checkpw} de moi cho trong du an dung chung mot cach bam.</p>
     *
     * <p>Hash mat khau bi xoa khoi doi tuong tra ve: User nay se nam trong session
     * (Tomcat co the serialize session ra dia khi restart) va duoc doc lai o JSP,
     * khong co ly do gi de hash di theo.</p>
     *
     * @return User neu username ton tai, status = ACTIVE va mat khau khop hash;
     *         Optional.empty() trong moi truong hop con lai (khong phan biet
     *         "sai user" vs "sai pass" de tranh do thong tin)
     */
    public Optional<User> login(String username, String rawPassword) {
        if (username == null || username.isBlank() || rawPassword == null || rawPassword.isEmpty()) {
            return Optional.empty();
        }

        Optional<User> found = userDAO.findByUsername(username.trim());
        if (found.isEmpty()) {
            return Optional.empty();
        }

        User user = found.get();
        if (!STATUS_ACTIVE.equals(user.getStatus())) {
            return Optional.empty();
        }
        if (!PasswordUtil.verify(rawPassword, user.getPasswordHash())) {
            return Optional.empty();
        }

        userDAO.updateLastLogin(user.getId());
        user.setPasswordHash(null);
        return Optional.of(user);
    }

    /** Dang xuat: huy session hien tai. An toan khi session da null. */
    public void logout(HttpSession session) {
        if (session != null) {
            session.invalidate();
        }
    }

    /** True khi session dang giu mot User da dang nhap. */
    public boolean isAuthenticated(HttpSession session) {
        return getCurrentUser(session) != null;
    }

    /** User dang dang nhap, null neu chua. */
    public User getCurrentUser(HttpSession session) {
        return session == null ? null : (User) session.getAttribute(Constants.SESSION_USER);
    }

    /** Doi mat khau sau khi kiem tra mat khau cu. */
    public boolean changePassword(Long userId, String oldPassword, String newPassword) {
        if (userId == null || newPassword == null || newPassword.isBlank()) {
            return false;
        }
        Optional<User> found = userDAO.findById(userId);
        if (found.isEmpty() || !PasswordUtil.verify(oldPassword, found.get().getPasswordHash())) {
            return false;
        }
        return userDAO.updatePassword(userId, PasswordUtil.hash(newPassword));
    }

    /** Tao tai khoan moi (hash mat khau bang PasswordUtil truoc khi luu). */
    public Long register(User user, String rawPassword) {
        if (user == null || rawPassword == null || rawPassword.isBlank()) {
            throw new IllegalArgumentException("Thieu thong tin tai khoan hoac mat khau");
        }
        user.setPasswordHash(PasswordUtil.hash(rawPassword));
        if (user.getRole() == null) {
            user.setRole(Constants.ROLE_STAFF);
        }
        if (user.getStatus() == null) {
            user.setStatus(STATUS_ACTIVE);
        }
        return userDAO.insert(user);
    }

    /** Kiem tra user co thuoc mot trong cac role duoc phep (ADMIN | MANAGER | STAFF). */
    public boolean hasAnyRole(User user, String... roles) {
        if (user == null || user.getRole() == null || roles == null) {
            return false;
        }
        for (String role : roles) {
            if (user.getRole().equals(role)) {
                return true;
            }
        }
        return false;
    }
}
