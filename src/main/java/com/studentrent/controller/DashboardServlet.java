package com.studentrent.controller;

import com.studentrent.service.ReportService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

/** Trang tong quan sau khi dang nhap. */
@WebServlet(name = "DashboardServlet", urlPatterns = {"/dashboard"})
public class DashboardServlet extends BaseServlet {

    private final ReportService reportService = new ReportService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        // TODO: req.setAttribute("stats", reportService.dashboardStats());
        // TODO: req.setAttribute("expiringContracts", contractService.findExpiringSoon(30));
        forward(req, resp, "dashboard/index");
    }
}
