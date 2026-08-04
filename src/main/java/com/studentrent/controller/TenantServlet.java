package com.studentrent.controller;

import com.studentrent.service.TenantService;
import com.studentrent.util.Constants;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

/**
 * Quan ly khach thue (sinh vien).
 *
 * <pre>
 * GET  /tenants          -> danh sach
 * GET  /tenants/new      -> form them
 * GET  /tenants/edit?id= -> form sua
 * POST /tenants/save     -> luu + upload avatar
 * POST /tenants/delete   -> xoa
 * </pre>
 */
@WebServlet(name = "TenantServlet", urlPatterns = {"/tenants", "/tenants/*"})
@MultipartConfig(
        fileSizeThreshold = 1024 * 1024,
        maxFileSize = Constants.MAX_UPLOAD_SIZE,
        maxRequestSize = Constants.MAX_UPLOAD_SIZE * 2
)
public class TenantServlet extends BaseServlet {

    private final TenantService tenantService = new TenantService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        switch (action(req)) {
            case "new" -> forward(req, resp, "tenant/form");
            case "edit" -> {
                Long id = paramLong(req, "id");
                // TODO: nap tenant theo id
                req.setAttribute("tenantId", id);
                forward(req, resp, "tenant/form");
            }
            default -> {
                // TODO: tenantService.search(keyword, page, size)
                forward(req, resp, "tenant/list");
            }
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        switch (action(req)) {
            case "save" -> {
                // TODO: binding + validate CCCD trung + upload avatar + luu
                redirect(resp, req, "/tenants");
            }
            case "delete" -> {
                // TODO: tenantService.delete(paramLong(req, "id"))
                redirect(resp, req, "/tenants");
            }
            default -> resp.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }
}
