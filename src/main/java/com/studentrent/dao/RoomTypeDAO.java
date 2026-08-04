package com.studentrent.dao;

import com.studentrent.model.RoomType;

import java.util.List;
import java.util.Optional;

/** Truy van bang {@code room_types}. Skeleton - chua implement. */
public class RoomTypeDAO implements GenericDAO<RoomType, Long> {

    @Override
    public Optional<RoomType> findById(Long id) {
        throw new UnsupportedOperationException("TODO: RoomTypeDAO.findById");
    }

    @Override
    public List<RoomType> findAll() {
        throw new UnsupportedOperationException("TODO: RoomTypeDAO.findAll");
    }

    @Override
    public List<RoomType> findPage(int page, int size) {
        throw new UnsupportedOperationException("TODO: RoomTypeDAO.findPage");
    }

    @Override
    public Long insert(RoomType entity) {
        throw new UnsupportedOperationException("TODO: RoomTypeDAO.insert");
    }

    @Override
    public boolean update(RoomType entity) {
        throw new UnsupportedOperationException("TODO: RoomTypeDAO.update");
    }

    @Override
    public boolean delete(Long id) {
        throw new UnsupportedOperationException("TODO: RoomTypeDAO.delete");
    }

    @Override
    public long count() {
        throw new UnsupportedOperationException("TODO: RoomTypeDAO.count");
    }

    /** Kiem tra ten loai phong da ton tai chua (tru chinh ban ghi dang sua). */
    public boolean existsByName(String name, Long excludeId) {
        throw new UnsupportedOperationException("TODO: RoomTypeDAO.existsByName");
    }
}
