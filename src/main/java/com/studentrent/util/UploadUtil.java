package com.studentrent.util;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.Part;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.Locale;
import java.util.UUID;

/**
 * Luu / doc / xoa anh upload (avatar khach thue, anh phong...).
 *
 * <p>File that nam trong mot thu muc NGOAI webapp - {@code AppContextListener}
 * doc key {@code app.upload.dir} luc khoi dong, tao thu muc va goi
 * {@link #init(Path)}. Ly do khong luu trong {@code webapp/uploads/}: thu muc
 * do nam trong WAR da giai nen o {@code target/}, nen {@code mvn clean package}
 * xoa sach anh da upload.</p>
 *
 * <p>Tra ve / nhan vao CHI TEN FILE (khong co thu muc con), luu flat duoi thu
 * muc goc. Duong dan luu vao DB vi vay khong bao gio chua "\\" cua Windows.
 * File duoc phuc vu lai qua {@code UploadServlet} ("/uploads/*"), khong qua
 * duong dan tinh trong webapp.</p>
 */
public final class UploadUtil {

    private static volatile Path baseDir;

    private UploadUtil() {
    }

    /** Goi mot lan luc ung dung khoi dong (AppContextListener) sau khi tao thu muc. */
    public static void init(Path resolvedBaseDir) {
        baseDir = resolvedBaseDir;
    }

    /**
     * Luu file anh tu request vao thu muc upload.
     *
     * @param fieldName ten input file tren form
     * @return ten file da luu (UUID + duoi), null neu nguoi dung khong chon file
     * @throws IllegalArgumentException neu duoi file khong hop le hoac vuot qua
     *         {@link Constants#MAX_AVATAR_SIZE}
     */
    public static String saveImage(HttpServletRequest request, String fieldName)
            throws IOException, ServletException {
        Part part = request.getPart(fieldName);
        if (part == null || part.getSize() <= 0) {
            return null;
        }
        String submittedFileName = part.getSubmittedFileName();
        if (submittedFileName == null || submittedFileName.isBlank()) {
            return null;
        }
        if (!isAllowedImage(submittedFileName)) {
            throw new IllegalArgumentException(
                    "Chỉ nhận file ảnh đuôi: " + String.join(", ", Constants.ALLOWED_IMAGE_EXTENSIONS));
        }
        if (part.getSize() > Constants.MAX_AVATAR_SIZE) {
            throw new IllegalArgumentException("Kích thước ảnh vượt quá 2MB.");
        }

        String fileName = UUID.randomUUID() + "." + extension(submittedFileName).toLowerCase(Locale.ROOT);
        Path target = requireBaseDir().resolve(fileName);
        try (InputStream in = part.getInputStream()) {
            Files.copy(in, target, StandardCopyOption.REPLACE_EXISTING);
        }
        return fileName;
    }

    /** Kiem tra duoi file nam trong {@link Constants#ALLOWED_IMAGE_EXTENSIONS}. */
    public static boolean isAllowedImage(String fileName) {
        if (fileName == null) {
            return false;
        }
        String ext = extension(fileName).toLowerCase(Locale.ROOT);
        for (String allowed : Constants.ALLOWED_IMAGE_EXTENSIONS) {
            if (allowed.equals(ext)) {
                return true;
            }
        }
        return false;
    }

    /** Xoa anh cu khi nguoi dung thay anh moi. An toan khi file khong ton tai. */
    public static boolean delete(String relativePath) {
        try {
            Path target = resolve(relativePath);
            return target != null && Files.deleteIfExists(target);
        } catch (IllegalArgumentException | IllegalStateException | IOException e) {
            return false;
        }
    }

    /**
     * Quy doi ten file da luu thanh duong dan tuyet doi tren dia, dung cho
     * {@code UploadServlet} khi phuc vu lai anh.
     *
     * @return null neu {@code relativePath} rong
     * @throws IllegalArgumentException neu duong dan co the leo ra ngoai thu muc goc
     */
    public static Path resolve(String relativePath) {
        if (relativePath == null || relativePath.isBlank()) {
            return null;
        }
        Path dir = requireBaseDir();
        Path target = dir.resolve(relativePath).normalize();
        if (!target.startsWith(dir)) {
            throw new IllegalArgumentException("Đường dẫn không hợp lệ: " + relativePath);
        }
        return target;
    }

    private static Path requireBaseDir() {
        Path dir = baseDir;
        if (dir == null) {
            throw new IllegalStateException(
                    "Thư mục upload chưa được khởi tạo - kiểm tra AppContextListener.");
        }
        return dir;
    }

    private static String extension(String fileName) {
        int dot = fileName.lastIndexOf('.');
        return dot >= 0 && dot < fileName.length() - 1 ? fileName.substring(dot + 1) : "";
    }
}
