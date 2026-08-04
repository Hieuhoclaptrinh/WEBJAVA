package com.studentrent.util;

import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;

/**
 * Dong connection pool khi ung dung bi undeploy / Tomcat shutdown.
 * Neu khong dong, Tomcat se bao leak thread cua HikariCP.
 */
@WebListener
public class AppContextListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        sce.getServletContext().log("StudentRent: ung dung khoi dong.");
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        DBConnection.shutdown();
        sce.getServletContext().log("StudentRent: da dong connection pool.");
    }
}
