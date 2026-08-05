package com.studentrent.controller;

import com.studentrent.util.UploadUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * Phuc vu lai anh da upload (avatar khach thue, anh phong...).
 *
 * <p>File that nam ngoai webapp (xem {@code UploadUtil} / {@code AppContextListener}),
 * nen khong the tro thang bang duong dan tinh trong webapp - servlet nay doc va
 * ghi lai byte tu thu muc cau hinh. AuthFilter da cho qua tien to "/uploads/"
 * nhu tai nguyen tinh (giong "/assets/"), khong yeu cau dang nhap.</p>
 */
@WebServlet(name = "UploadServlet", urlPatterns = {"/uploads/*"})
public class UploadServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String pathInfo = req.getPathInfo();
        if (pathInfo == null || pathInfo.length() <= 1) {
            resp.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        Path file;
        try {
            file = UploadUtil.resolve(pathInfo.substring(1));
        } catch (IllegalArgumentException | IllegalStateException e) {
            resp.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        if (file == null || !Files.isRegularFile(file)) {
            resp.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        String contentType = req.getServletContext().getMimeType(file.getFileName().toString());
        resp.setContentType(contentType != null ? contentType : "application/octet-stream");
        resp.setContentLengthLong(Files.size(file));
        Files.copy(file, resp.getOutputStream());
    }
}
