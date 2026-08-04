package com.studentrent.service;

import com.studentrent.dao.UserDAO;
import com.studentrent.model.User;

import java.util.Optional;

/** Dang nhap, doi mat khau, phan quyen. Skeleton - chua implement. */
public class AuthService {

    private final UserDAO userDAO = new UserDAO();

    /**
     * Xac thuc dang nhap.
     *
     * @return User neu username ton tai, status = ACTIVE va mat khau khop hash;
     *         Optional.empty() trong moi truong hop con lai (khong phan biet
     *         "sai user" vs "sai pass" de tranh do thong tin)
     */
    public Optional<User> login(String username, String rawPassword) {
        throw new UnsupportedOperationException("TODO: AuthService.login");
    }

    /** Doi mat khau sau khi kiem tra mat khau cu. */
    public boolean changePassword(Long userId, String oldPassword, String newPassword) {
        throw new UnsupportedOperationException("TODO: AuthService.changePassword");
    }

    /** Tao tai khoan moi (hash mat khau bang PasswordUtil truoc khi luu). */
    public Long register(User user, String rawPassword) {
        throw new UnsupportedOperationException("TODO: AuthService.register");
    }

    /** Kiem tra user co thuoc mot trong cac role duoc phep. */
    public boolean hasAnyRole(User user, String... roles) {
        throw new UnsupportedOperationException("TODO: AuthService.hasAnyRole");
    }
}
