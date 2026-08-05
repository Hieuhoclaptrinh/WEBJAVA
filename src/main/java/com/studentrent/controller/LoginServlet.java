package com.studentrent.controller;

import com.studentrent.model.User;
import com.studentrent.service.AuthService;
import com.studentrent.util.Constants;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.Optional;

/** Trang dang nhap. */
@WebServlet(name = "LoginServlet", urlPatterns = {"/login"})
public class LoginServlet extends BaseServlet {

    private final AuthService authService = new AuthService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        if (currentUser(req) != null) {
            redirect(resp, req, "/dashboard");
            return;
        }
        forward(req, resp, "auth/login");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String username = paramText(req, "username");
        String password = req.getParameter("password");

        Optional<User> authenticated = authService.login(username, password);

        if (authenticated.isEmpty()) {
            // FORWARD chu khong redirect: redirect sinh request moi nen attribute
            // bao loi va username vua go se mat sach.
            req.setAttribute(Constants.ATTR_ERROR, "Tên đăng nhập hoặc mật khẩu không đúng.");
            req.setAttribute("username", username);
            forward(req, resp, "auth/login");
            return;
        }

        // Chong session fixation: doi session id TRUOC khi gan user vao session,
        // de id ma ke tan cong co the da biet tu truoc tro nen vo dung.
        // changeSessionId() nem IllegalStateException neu chua co session, nen phai
        // getSession(true) truoc. Day la cach cua Servlet 3.1+ va giu nguyen moi
        // attribute dang co - khong dung invalidate() roi tu tao session moi.
        req.getSession(true);
        req.changeSessionId();
        req.getSession().setAttribute(Constants.SESSION_USER, authenticated.get());

        redirect(resp, req, "/dashboard");
    }
}
