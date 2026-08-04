package com.studentrent.util;

/** Hang so dung chung toan ung dung. */
public final class Constants {

    private Constants() {
    }

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
