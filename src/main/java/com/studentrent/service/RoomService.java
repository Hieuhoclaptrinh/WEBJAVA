package com.studentrent.service;

import com.studentrent.dao.RoomDAO;
import com.studentrent.dao.RoomTypeDAO;
import com.studentrent.model.Room;
import com.studentrent.model.RoomType;

import java.util.List;
import java.util.Map;
import java.util.Optional;

/** Nghiep vu quan ly phong va loai phong. Skeleton - chua implement. */
public class RoomService {

    private final RoomDAO roomDAO = new RoomDAO();
    private final RoomTypeDAO roomTypeDAO = new RoomTypeDAO();

    public List<Room> search(String keyword, String status, Long roomTypeId,
                             Integer floorNo, int page, int size) {
        throw new UnsupportedOperationException("TODO: RoomService.search");
    }

    public Optional<Room> findById(Long id) {
        throw new UnsupportedOperationException("TODO: RoomService.findById");
    }

    /** Tao phong moi - kiem tra trung room_code truoc khi insert. */
    public Long create(Room room) {
        throw new UnsupportedOperationException("TODO: RoomService.create");
    }

    public boolean update(Room room) {
        throw new UnsupportedOperationException("TODO: RoomService.update");
    }

    /** Chi cho xoa khi phong chua tung co hop dong nao. */
    public boolean delete(Long id) {
        throw new UnsupportedOperationException("TODO: RoomService.delete");
    }

    public List<Room> findAvailable() {
        throw new UnsupportedOperationException("TODO: RoomService.findAvailable");
    }

    /** Thong ke so phong theo trang thai cho dashboard. */
    public Map<String, Long> statusSummary() {
        throw new UnsupportedOperationException("TODO: RoomService.statusSummary");
    }

    public List<RoomType> allRoomTypes() {
        throw new UnsupportedOperationException("TODO: RoomService.allRoomTypes");
    }
}
