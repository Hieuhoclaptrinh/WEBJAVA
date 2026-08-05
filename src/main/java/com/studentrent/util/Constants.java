package com.studentrent.util;

import java.time.ZoneId;

/** Hang so dung chung toan ung dung. */
public final class Constants {

    private Constants() {
    }

    // ---------- Mui gio ----------
    /**
     * Mui gio nghiep vu cua ung dung.
     *
     * <p>Moi cho lay thoi diem hien tai PHAI truyen hang nay:
     * {@code LocalDate.now(Constants.ZONE)} / {@code LocalDateTime.now(Constants.ZONE)}.
     * Goi {@code now()} khong tham so se lay mui gio cua may dang chay, nen hai may
     * dat timezone khac nhau se cho ra "hom nay" khac nhau - hoa don qua han va hop
     * dong het han se tinh sai. Cung ly do do: khong dung NOW() / CURDATE() cua MySQL,
     * vi server MySQL co the o mui gio khac JVM.</p>
     */
    public static final ZoneId ZONE = ZoneId.of("Asia/Ho_Chi_Minh");

    // ---------- Session / request attribute keys ----------
    public static final String SESSION_USER = "currentUser";
    public static final String ATTR_ERROR = "errorMessage";
    public static final String ATTR_SUCCESS = "successMessage";

    // ---------- Duong dan view ----------
    public static final String VIEW_PREFIX = "/WEB-INF/views/";
    public static final String VIEW_SUFFIX = ".jsp";

    // ---------- Phan trang ----------
    public static final int DEFAULT_PAGE_SIZE = 10;

    // ---------- Upload ----------
    /** Thu muc con trong webapp de luu anh upload. */
    public static final String UPLOAD_DIR = "uploads";
    /** Gioi han 5 MB moi file. */
    public static final long MAX_UPLOAD_SIZE = 5L * 1024 * 1024;
    public static final String[] ALLOWED_IMAGE_EXTENSIONS = {"jpg", "jpeg", "png", "webp"};

    // ---------- Role ----------
    public static final String ROLE_ADMIN = "ADMIN";
    public static final String ROLE_MANAGER = "MANAGER";
    public static final String ROLE_STAFF = "STAFF";

    /** Ghep duong dan forward tuong doi cho servlet, vd view("room/list"). */
    public static String view(String name) {
        return VIEW_PREFIX + name + VIEW_SUFFIX;
    }
}
