package com.studentrent.controller;

import com.studentrent.service.AuthService;
import com.studentrent.util.Constants;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

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

        // TODO: goi authService.login(username, password)
        //   - neu thanh cong: req.getSession(true).setAttribute(Constants.SESSION_USER, user)
        //                     roi redirect ve /dashboard
        //   - neu that bai:   set ATTR_ERROR va forward lai auth/login
        //   Nho goi session.invalidate() truoc khi tao session moi de tranh
        //   session fixation.
        req.setAttribute(Constants.ATTR_ERROR, "Chuc nang dang nhap chua duoc implement.");
        req.setAttribute("username", username);
        forward(req, resp, "auth/login");
    }
}
