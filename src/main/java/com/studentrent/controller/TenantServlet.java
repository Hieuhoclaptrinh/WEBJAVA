package com.studentrent.controller;

import com.studentrent.model.Tenant;
import com.studentrent.service.TenantService;
import com.studentrent.util.Constants;
import com.studentrent.util.JsonUtil;
import com.studentrent.util.UploadUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Quan ly khach thue (sinh vien).
 *
 * <pre>
 * GET  /tenants          -> danh sach (?keyword=, ?page=; JSON neu Accept co application/json)
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
            case "new" -> {
                req.setAttribute("tenant", new Tenant());
                forward(req, resp, "tenant/form");
            }
            case "edit" -> {
                Optional<Tenant> found = tenantService.findById(paramLong(req, "id"));
                if (found.isEmpty()) {
                    req.getSession().setAttribute(Constants.ATTR_ERROR, "Không tìm thấy khách thuê.");
                    redirect(resp, req, "/tenants");
                    return;
                }
                req.setAttribute("tenant", found.get());
                forward(req, resp, "tenant/form");
            }
            default -> listOrSearch(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        switch (action(req)) {
            case "save" -> save(req, resp);
            case "delete" -> delete(req, resp);
            default -> resp.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }

    private void listOrSearch(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String keyword = paramText(req, "keyword");
        int page = Math.max(paramInt(req, "page", 1), 1);

        List<Tenant> tenants = tenantService.search(keyword, page, Constants.DEFAULT_PAGE_SIZE);

        String accept = req.getHeader("Accept");
        if (accept != null && accept.contains("application/json")) {
            JsonUtil.write(resp, Map.of("success", true, "tenants", tenants));
            return;
        }

        long total = tenantService.countSearch(keyword);
        int totalPages = (int) Math.max(1, Math.ceil(total / (double) Constants.DEFAULT_PAGE_SIZE));

        req.setAttribute("tenants", tenants);
        req.setAttribute("page", page);
        req.setAttribute("totalPages", totalPages);
        forward(req, resp, "tenant/list");
    }

    private void save(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        Long id = paramLong(req, "id");
        Tenant tenant = bind(req, id);

        try {
            String avatarPath = UploadUtil.saveImage(req, "avatar");
            if (avatarPath != null) {
                tenant.setAvatarPath(avatarPath);
            } else if (id != null) {
                // Khong chon anh moi khi sua -> giu lai anh cu
                tenantService.findById(id)
                        .ifPresent(existing -> tenant.setAvatarPath(existing.getAvatarPath()));
            }

            if (id == null) {
                tenantService.create(tenant);
            } else {
                tenantService.update(tenant);
            }
            req.getSession().setAttribute(Constants.ATTR_SUCCESS, "Lưu khách thuê thành công.");
        } catch (IllegalArgumentException e) {
            req.getSession().setAttribute(Constants.ATTR_ERROR, e.getMessage());
        }
        redirect(resp, req, "/tenants");
    }

    private void delete(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        Long id = paramLong(req, "id");
        try {
            boolean deleted = tenantService.delete(id);
            req.getSession().setAttribute(
                    deleted ? Constants.ATTR_SUCCESS : Constants.ATTR_ERROR,
                    deleted ? "Đã xóa khách thuê." : "Không tìm thấy khách thuê cần xóa.");
        } catch (IllegalStateException e) {
            req.getSession().setAttribute(Constants.ATTR_ERROR, e.getMessage());
        }
        redirect(resp, req, "/tenants");
    }

    private Tenant bind(HttpServletRequest req, Long id) {
        Tenant tenant = new Tenant();
        tenant.setId(id);
        tenant.setFullName(paramText(req, "fullName"));
        tenant.setIdCard(paramText(req, "idCard"));
        tenant.setDateOfBirth(parseDate(paramText(req, "dateOfBirth")));
        tenant.setGender(paramText(req, "gender"));
        tenant.setPhone(paramText(req, "phone"));
        tenant.setEmail(paramText(req, "email"));
        tenant.setHometown(paramText(req, "hometown"));
        tenant.setSchool(paramText(req, "school"));
        tenant.setStudentCode(paramText(req, "studentCode"));
        tenant.setNote(paramText(req, "note"));
        return tenant;
    }

    /** Input type="date" luon gui yyyy-MM-dd (ISO), khong phu thuoc locale. */
    private LocalDate parseDate(String raw) {
        if (raw == null) {
            return null;
        }
        try {
            return LocalDate.parse(raw, DateTimeFormatter.ISO_LOCAL_DATE);
        } catch (DateTimeParseException e) {
            return null;
        }
    }
}
