package com.studentrent.dao;

import com.studentrent.model.Room;

import java.util.List;
import java.util.Optional;

/** Truy van bang {@code rooms}. Skeleton - chua implement. */
public class RoomDAO implements GenericDAO<Room, Long> {

    @Override
    public Optional<Room> findById(Long id) {
        throw new UnsupportedOperationException("TODO: RoomDAO.findById");
    }

    @Override
    public List<Room> findAll() {
        throw new UnsupportedOperationException("TODO: RoomDAO.findAll");
    }

    @Override
    public List<Room> findPage(int page, int size) {
        throw new UnsupportedOperationException("TODO: RoomDAO.findPage");
    }

    @Override
    public Long insert(Room entity) {
        throw new UnsupportedOperationException("TODO: RoomDAO.insert");
    }

    @Override
    public boolean update(Room entity) {
        throw new UnsupportedOperationException("TODO: RoomDAO.update");
    }

    @Override
    public boolean delete(Long id) {
        throw new UnsupportedOperationException("TODO: RoomDAO.delete");
    }

    @Override
    public long count() {
        throw new UnsupportedOperationException("TODO: RoomDAO.count");
    }

    // ---------- Truy van rieng ----------

    public Optional<Room> findByCode(String roomCode) {
        throw new UnsupportedOperationException("TODO: RoomDAO.findByCode");
    }

    /** Loc theo trang thai, loai phong, tang, tu khoa - dung cho trang danh sach. */
    public List<Room> search(String keyword, String status, Long roomTypeId,
                             Integer floorNo, int page, int size) {
        throw new UnsupportedOperationException("TODO: RoomDAO.search");
    }

    /** Dem so phong theo tung trang thai - dung cho dashboard. */
    public java.util.Map<String, Long> countByStatus() {
        throw new UnsupportedOperationException("TODO: RoomDAO.countByStatus");
    }

    /** Chi lay phong AVAILABLE - dung khi tao hop dong moi. */
    public List<Room> findAvailable() {
        throw new UnsupportedOperationException("TODO: RoomDAO.findAvailable");
    }

    /** Cap nhat trang thai phong khi hop dong bat dau / ket thuc. */
    public boolean updateStatus(Long roomId, String status) {
        throw new UnsupportedOperationException("TODO: RoomDAO.updateStatus");
    }
}
