package com.studentrent.controller;

import com.studentrent.service.InvoiceService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

/**
 * Quan ly hoa don thang.
 *
 * <pre>
 * GET  /invoices            -> danh sach (loc theo thang / trang thai)
 * GET  /invoices/new        -> form lap hoa don
 * GET  /invoices/detail?id= -> chi tiet
 * GET  /invoices/pdf?id=    -> xuat PDF
 * GET  /invoices/prepare    -> AJAX: tra ve JSON chi so dau + danh sach dich vu
 * POST /invoices/save       -> lap hoa don
 * POST /invoices/pay        -> ghi nhan thanh toan
 * </pre>
 */
@WebServlet(name = "InvoiceServlet", urlPatterns = {"/invoices", "/invoices/*"})
public class InvoiceServlet extends BaseServlet {

    private final InvoiceService invoiceService = new InvoiceService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        switch (action(req)) {
            case "new" -> {
                // TODO: nap danh sach hop dong ACTIVE + dich vu ACTIVE
                forward(req, resp, "invoice/form");
            }
            case "detail" -> {
                req.setAttribute("invoiceId", paramLong(req, "id"));
                // TODO: invoiceService.findFull(id)
                forward(req, resp, "invoice/detail");
            }
            case "prepare" -> {
                // Endpoint AJAX cho form lap hoa don.
                // TODO: JsonUtil.write(resp, invoiceService.prepareDetails(contractId, month, year));
                resp.sendError(HttpServletResponse.SC_NOT_IMPLEMENTED, "Chua implement");
            }
            case "pdf" -> {
                // TODO: resp.setContentType("application/pdf");
                //       PdfExporter.exportInvoice(invoice, resp.getOutputStream());
                resp.sendError(HttpServletResponse.SC_NOT_IMPLEMENTED, "Xuat PDF chua implement");
            }
            default -> {
                // TODO: invoiceService.search(keyword, status, month, year, page, size)
                forward(req, resp, "invoice/list");
            }
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        switch (action(req)) {
            case "save" -> {
                // TODO: binding cac dong chi tiet (mang tham so serviceId[], newIndex[]...)
                //       roi invoiceService.createInvoice(invoice)
                redirect(resp, req, "/invoices");
            }
            case "pay" -> {
                // TODO: invoiceService.pay(id, soTien, phuongThuc)
                redirect(resp, req, "/invoices");
            }
            default -> resp.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }
}
