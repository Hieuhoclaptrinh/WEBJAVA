package com.studentrent.controller;

import com.studentrent.service.RoomService;
import com.studentrent.util.Constants;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

/**
 * Quan ly phong tro.
 *
 * <pre>
 * GET  /rooms          -> danh sach
 * GET  /rooms/new      -> form them
 * GET  /rooms/edit?id= -> form sua
 * POST /rooms/save     -> luu (them / sua) + upload anh
 * POST /rooms/delete   -> xoa
 * </pre>
 */
@WebServlet(name = "RoomServlet", urlPatterns = {"/rooms", "/rooms/*"})
@MultipartConfig(
        fileSizeThreshold = 1024 * 1024,
        maxFileSize = Constants.MAX_UPLOAD_SIZE,
        maxRequestSize = Constants.MAX_UPLOAD_SIZE * 2
)
public class RoomServlet extends BaseServlet {

    private final RoomService roomService = new RoomService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        switch (action(req)) {
            case "new" -> {
                // TODO: req.setAttribute("roomTypes", roomService.allRoomTypes());
                forward(req, resp, "room/form");
            }
            case "edit" -> {
                Long id = paramLong(req, "id");
                // TODO: nap room theo id, neu khong thay -> resp.sendError(404)
                req.setAttribute("roomId", id);
                forward(req, resp, "room/form");
            }
            default -> {
                // TODO: nap danh sach phong co phan trang:
                //   roomService.search(keyword, status, roomTypeId, floorNo, page, size)
                forward(req, resp, "room/list");
            }
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        switch (action(req)) {
            case "save" -> {
                // TODO: binding tham so -> Room, validate,
                //       UploadUtil.saveImage(req, "image"), roomService.create/update
                redirect(resp, req, "/rooms");
            }
            case "delete" -> {
                // TODO: roomService.delete(paramLong(req, "id"))
                redirect(resp, req, "/rooms");
            }
            default -> resp.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }
}
