package com.studentrent.dao;

import com.studentrent.model.User;

import java.util.List;
import java.util.Optional;

/** Truy van bang {@code users}. Skeleton - chua implement. */
public class UserDAO implements GenericDAO<User, Long> {

    @Override
    public Optional<User> findById(Long id) {
        throw new UnsupportedOperationException("TODO: UserDAO.findById");
    }

    @Override
    public List<User> findAll() {
        throw new UnsupportedOperationException("TODO: UserDAO.findAll");
    }

    @Override
    public List<User> findPage(int page, int size) {
        throw new UnsupportedOperationException("TODO: UserDAO.findPage");
    }

    @Override
    public Long insert(User entity) {
        throw new UnsupportedOperationException("TODO: UserDAO.insert");
    }

    @Override
    public boolean update(User entity) {
        throw new UnsupportedOperationException("TODO: UserDAO.update");
    }

    @Override
    public boolean delete(Long id) {
        throw new UnsupportedOperationException("TODO: UserDAO.delete");
    }

    @Override
    public long count() {
        throw new UnsupportedOperationException("TODO: UserDAO.count");
    }

    // ---------- Truy van rieng ----------

    /** Tim theo username - dung khi dang nhap. */
    public Optional<User> findByUsername(String username) {
        throw new UnsupportedOperationException("TODO: UserDAO.findByUsername");
    }

    /** Doi mat khau (luu hash moi). */
    public boolean updatePassword(Long userId, String newPasswordHash) {
        throw new UnsupportedOperationException("TODO: UserDAO.updatePassword");
    }

    /** Ghi nhan thoi diem dang nhap thanh cong. */
    public boolean touchLastLogin(Long userId) {
        throw new UnsupportedOperationException("TODO: UserDAO.touchLastLogin");
    }
}
