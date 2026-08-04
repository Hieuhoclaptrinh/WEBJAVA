package com.studentrent.controller;

import com.studentrent.service.ReportService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.time.LocalDate;

/**
 * Bao cao thong ke.
 *
 * <pre>
 * GET /reports          -> bao cao doanh thu theo thang
 * GET /reports/debt     -> bao cao cong no (hoa don qua han)
 * GET /reports/chart    -> AJAX: JSON du lieu ve bieu do
 * </pre>
 */
@WebServlet(name = "ReportServlet", urlPatterns = {"/reports", "/reports/*"})
public class ReportServlet extends BaseServlet {

    private final ReportService reportService = new ReportService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        int year = paramInt(req, "year", LocalDate.now().getYear());

        switch (action(req)) {
            case "debt" -> {
                // TODO: req.setAttribute("debts", reportService.debtReport());
                forward(req, resp, "report/debt");
            }
            case "chart" -> {
                // TODO: JsonUtil.write(resp, reportService.revenueByMonth(year));
                resp.sendError(HttpServletResponse.SC_NOT_IMPLEMENTED, "Chua implement");
            }
            default -> {
                req.setAttribute("year", year);
                // TODO: req.setAttribute("revenueByMonth", reportService.revenueByMonth(year));
                // TODO: req.setAttribute("occupancyRate", reportService.occupancyRate());
                forward(req, resp, "report/revenue");
            }
        }
    }
}
