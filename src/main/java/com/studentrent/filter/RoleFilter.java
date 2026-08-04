package com.studentrent.filter;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;

import java.io.IOException;

/**
 * Gioi han cac chuc nang quan tri (bao cao, quan ly tai khoan) cho ADMIN/MANAGER.
 * Khai bao trong web.xml, chay sau {@link AuthFilter}.
 *
 * <p>Skeleton - hien tai cho qua tat ca.</p>
 */
public class RoleFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        // TODO: bo comment khi da implement dang nhap (can import HttpServletRequest,
        //       HttpServletResponse, HttpSession, User, Constants)
        // HttpSession session = req.getSession(false);
        // User user = session == null ? null : (User) session.getAttribute(Constants.SESSION_USER);
        // if (user == null
        //         || !(Constants.ROLE_ADMIN.equals(user.getRole())
        //              || Constants.ROLE_MANAGER.equals(user.getRole()))) {
        //     resp.sendError(HttpServletResponse.SC_FORBIDDEN, "Ban khong co quyen truy cap");
        //     return;
        // }

        chain.doFilter(request, response);
    }
}
