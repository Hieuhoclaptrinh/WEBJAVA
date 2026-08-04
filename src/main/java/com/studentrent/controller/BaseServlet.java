package com.studentrent.controller;

import com.studentrent.model.User;
import com.studentrent.util.Constants;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

/** Cac helper dung chung cho moi servlet: forward view, redirect, doc tham so. */
public abstract class BaseServlet extends HttpServlet {

    /** Forward tuong doi trong /WEB-INF/views, vd forward(req, resp, "room/list"). */
    protected void forward(HttpServletRequest req, HttpServletResponse resp, String viewName)
            throws ServletException, IOException {
        req.getRequestDispatcher(Constants.view(viewName)).forward(req, resp);
    }

    /** Redirect kem context path, vd redirect(resp, req, "/rooms"). */
    protected void redirect(HttpServletResponse resp, HttpServletRequest req, String path)
            throws IOException {
        resp.sendRedirect(req.getContextPath() + path);
    }

    /** Lay user dang dang nhap, null neu chua dang nhap. */
    protected User currentUser(HttpServletRequest req) {
        HttpSession session = req.getSession(false);
        return session == null ? null : (User) session.getAttribute(Constants.SESSION_USER);
    }

    /**
     * Lay phan duong dan sau servlet path, vd /rooms/edit -> "edit".
     * Tra ve "list" khi khong co path info.
     */
    protected String action(HttpServletRequest req) {
        String pathInfo = req.getPathInfo();
        if (pathInfo == null || pathInfo.length() <= 1) {
            return "list";
        }
        return pathInfo.substring(1);
    }

    /** Doc tham so kieu Long, tra ve null neu thieu / sai dinh dang. */
    protected Long paramLong(HttpServletRequest req, String name) {
        String raw = req.getParameter(name);
        if (raw == null || raw.isBlank()) {
            return null;
        }
        try {
            return Long.valueOf(raw.trim());
        } catch (NumberFormatException e) {
            return null;
        }
    }

    /** Doc tham so kieu int voi gia tri mac dinh. */
    protected int paramInt(HttpServletRequest req, String name, int defaultValue) {
        String raw = req.getParameter(name);
        if (raw == null || raw.isBlank()) {
            return defaultValue;
        }
        try {
            return Integer.parseInt(raw.trim());
        } catch (NumberFormatException e) {
            return defaultValue;
        }
    }

    /** Doc tham so chuoi, trim, tra ve null neu rong. */
    protected String paramText(HttpServletRequest req, String name) {
        String raw = req.getParameter(name);
        if (raw == null) {
            return null;
        }
        String trimmed = raw.trim();
        return trimmed.isEmpty() ? null : trimmed;
    }
}
