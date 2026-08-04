package com.studentrent.filter;

import com.studentrent.util.Constants;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.Set;

/**
 * Chan moi request khi chua dang nhap, tru cac duong dan cong khai.
 * Khai bao trong web.xml de chay sau EncodingFilter.
 *
 * <p>Skeleton: hien tai CHO QUA tat ca de con deploy va xem duoc giao dien.
 * Bo comment doan kiem tra ben duoi khi da implement dang nhap.</p>
 */
public class AuthFilter implements Filter {

    /** Duong dan khong yeu cau dang nhap. */
    private static final Set<String> PUBLIC_PATHS = Set.of("/login", "/logout");

    /** Tien to tai nguyen tinh - luon cho qua. */
    private static final Set<String> PUBLIC_PREFIXES = Set.of("/assets/", "/uploads/");

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;

        String path = req.getRequestURI().substring(req.getContextPath().length());

        if (isPublic(path)) {
            chain.doFilter(request, response);
            return;
        }

        // TODO: bo comment khi AuthService.login da hoat dong
        // HttpSession session = req.getSession(false);
        // if (session == null || session.getAttribute(Constants.SESSION_USER) == null) {
        //     resp.sendRedirect(req.getContextPath() + "/login");
        //     return;
        // }

        chain.doFilter(request, response);
    }

    private boolean isPublic(String path) {
        if (path.isEmpty() || "/".equals(path) || PUBLIC_PATHS.contains(path)) {
            return true;
        }
        return PUBLIC_PREFIXES.stream().anyMatch(path::startsWith);
    }
}
