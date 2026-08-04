package com.studentrent.controller;

import com.studentrent.service.ContractService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

/**
 * Quan ly hop dong thue.
 *
 * <pre>
 * GET  /contracts             -> danh sach
 * GET  /contracts/new         -> form tao
 * GET  /contracts/edit?id=    -> form sua
 * GET  /contracts/pdf?id=     -> xuat PDF hop dong
 * POST /contracts/save        -> luu
 * POST /contracts/terminate   -> cham dut truoc han
 * </pre>
 */
@WebServlet(name = "ContractServlet", urlPatterns = {"/contracts", "/contracts/*"})
public class ContractServlet extends BaseServlet {

    private final ContractService contractService = new ContractService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        switch (action(req)) {
            case "new" -> {
                // TODO: req.setAttribute("availableRooms", roomService.findAvailable());
                // TODO: req.setAttribute("tenants", tenantService.search(null, 1, 1000));
                forward(req, resp, "contract/form");
            }
            case "edit" -> {
                req.setAttribute("contractId", paramLong(req, "id"));
                // TODO: nap contract theo id
                forward(req, resp, "contract/form");
            }
            case "detail" -> {
                req.setAttribute("contractId", paramLong(req, "id"));
                forward(req, resp, "contract/detail");
            }
            case "pdf" -> {
                // TODO: resp.setContentType("application/pdf");
                //       resp.setHeader("Content-Disposition", "inline; filename=hopdong.pdf");
                //       PdfExporter.exportContract(contract, resp.getOutputStream());
                resp.sendError(HttpServletResponse.SC_NOT_IMPLEMENTED, "Xuat PDF chua implement");
            }
            default -> {
                // TODO: contractService.search(keyword, status, page, size)
                forward(req, resp, "contract/list");
            }
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        switch (action(req)) {
            case "save" -> {
                // TODO: binding + validate (phong con trong, ngay hop le) + contractService.create/update
                redirect(resp, req, "/contracts");
            }
            case "terminate" -> {
                // TODO: contractService.terminate(id, ngay, lyDo)
                redirect(resp, req, "/contracts");
            }
            default -> resp.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }
}
