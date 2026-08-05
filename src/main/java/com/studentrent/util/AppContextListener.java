package com.studentrent.util;

import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * Khoi tao / dong tai nguyen dung chung khi ung dung deploy / undeploy.
 *
 * <p>Moi buoc boc try/catch rieng va chi log khi loi: DB hoac thu muc upload
 * chua san sang thi app van phai khoi dong duoc, neu khong thi may moi clone
 * ve se khong mo noi trang nao de xem loi la gi.</p>
 */
@WebListener
public class AppContextListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        ServletContext ctx = sce.getServletContext();
        try {
            Path uploadBase = resolveUploadBase();
            Files.createDirectories(uploadBase);
            UploadUtil.init(uploadBase);
            ctx.log("StudentRent: thu muc upload = " + uploadBase);
        } catch (IOException | RuntimeException e) {
            ctx.log("StudentRent: khong khoi tao duoc thu muc upload", e);
        }
        ctx.log("StudentRent: ung dung khoi dong.");
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        DBConnection.shutdown();
        sce.getServletContext().log("StudentRent: da dong connection pool.");
    }

    /**
     * Uu tien bien moi truong {@code APP_UPLOAD_DIR}, roi key {@code app.upload.dir}
     * trong db.properties / db.local.properties. Rong hoac khong khai bao thi dung
     * mac dinh {@code ~/studentrent-uploads} - moi may mot cho, khong ai phai sua
     * file dang duoc git theo doi.
     */
    private Path resolveUploadBase() {
        String fromEnv = System.getenv("APP_UPLOAD_DIR");
        String configured = fromEnv != null ? fromEnv : DBConnection.getAppProperty("app.upload.dir", "");
        if (configured != null && !configured.isBlank()) {
            return Path.of(configured.trim());
        }
        return Path.of(System.getProperty("user.home"), "studentrent-uploads");
    }
}
