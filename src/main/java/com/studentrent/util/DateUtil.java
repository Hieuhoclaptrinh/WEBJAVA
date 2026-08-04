package com.studentrent.util;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * Chuyen doi ngay thang giua chuoi form (dd/MM/yyyy hoac yyyy-MM-dd) va LocalDate.
 * Skeleton - chua implement.
 */
public final class DateUtil {

    public static final DateTimeFormatter VN_DATE = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    public static final DateTimeFormatter ISO_DATE = DateTimeFormatter.ISO_LOCAL_DATE;

    private DateUtil() {
    }

    /** Parse chuoi ngay tu form, tra ve null neu rong / sai dinh dang. */
    public static LocalDate parse(String text) {
        throw new UnsupportedOperationException("TODO: DateUtil.parse");
    }

    /** Format LocalDate ra dd/MM/yyyy de hien thi. */
    public static String format(LocalDate date) {
        throw new UnsupportedOperationException("TODO: DateUtil.format");
    }

    /** Ngay den han thanh toan = ngay lap + soNgay. */
    public static LocalDate plusDays(LocalDate from, int days) {
        throw new UnsupportedOperationException("TODO: DateUtil.plusDays");
    }
}
