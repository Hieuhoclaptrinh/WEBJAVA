package com.studentrent.dao;

import java.util.List;
import java.util.Optional;

/**
 * Hop dong CRUD chung cho moi DAO.
 *
 * <p>Moi DAO tu mo connection qua {@code DBConnection.getConnection()} trong
 * try-with-resources. Cac thao tac can nhieu buoc (vd tao hoa don + chi tiet)
 * nen dat o tang service va truyen chung mot Connection de giu transaction.</p>
 *
 * @param <T>  kieu entity
 * @param <ID> kieu khoa chinh
 */
public interface GenericDAO<T, ID> {

    /** Tim theo khoa chinh. */
    Optional<T> findById(ID id);

    /** Lay toan bo ban ghi. */
    List<T> findAll();

    /** Lay 1 trang ban ghi (offset = (page - 1) * size). */
    List<T> findPage(int page, int size);

    /** Them moi, tra ve khoa chinh vua sinh. */
    ID insert(T entity);

    /** Cap nhat theo id, tra ve true neu co dong bi anh huong. */
    boolean update(T entity);

    /** Xoa theo khoa chinh. */
    boolean delete(ID id);

    /** Tong so ban ghi - dung cho phan trang. */
    long count();
}
