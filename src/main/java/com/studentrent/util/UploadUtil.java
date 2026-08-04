package com.studentrent.util;

import jakarta.servlet.http.HttpServletRequest;

/**
 * Xu ly upload anh phong / avatar khach thue.
 *
 * <p>Hai lua chon, du an dung mot trong hai:</p>
 * <ol>
 *   <li>Servlet 6 co san: danh dau servlet bang {@code @MultipartConfig} roi doc
 *       {@code request.getPart("image")} - khong can thu vien ngoai.</li>
 *   <li>Apache Commons FileUpload 2 (ban jakarta-servlet6) qua
 *       {@code JakartaServletFileUpload} - huu ich khi can streaming hoac
 *       kiem soat parse chi tiet hon.</li>
 * </ol>
 *
 * <p><b>Luu y cross-platform khi implement:</b> ghep duong dan bang
 * {@link java.nio.file.Path#resolve(String)} hoac {@code File.separator},
 * TUYET DOI khong noi chuoi bang "\\" hay "/" cung - code phai chay giong nhau
 * tren Windows va macOS. Lay thu muc goc webapp qua
 * {@code request.getServletContext().getRealPath("")} thay vi duong dan tuyet doi.</p>
 *
 * Skeleton - chua implement.
 */
public final class UploadUtil {

    private UploadUtil() {
    }

    /**
     * Luu file anh tu request vao {@link Constants#UPLOAD_DIR}.
     *
     * @param fieldName ten input file tren form
     * @return duong dan tuong doi de luu vao DB (vd "uploads/room-a101-1.jpg"),
     *         null neu nguoi dung khong chon file
     */
    public static String saveImage(HttpServletRequest request, String fieldName) {
        throw new UnsupportedOperationException("TODO: UploadUtil.saveImage");
    }

    /** Kiem tra duoi file nam trong {@link Constants#ALLOWED_IMAGE_EXTENSIONS}. */
    public static boolean isAllowedImage(String fileName) {
        throw new UnsupportedOperationException("TODO: UploadUtil.isAllowedImage");
    }

    /** Xoa anh cu khi nguoi dung thay anh moi. */
    public static boolean delete(String relativePath) {
        throw new UnsupportedOperationException("TODO: UploadUtil.delete");
    }
}
