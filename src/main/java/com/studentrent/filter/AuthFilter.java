package com.studentrent.filter;

import com.studentrent.util.Constants;
import jakarta.servlet.DispatcherType;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.Set;

/**
 * Chan moi request khi chua dang nhap, tru cac duong dan cong khai.
 * Khai bao trong web.xml (map san /*) de chay sau EncodingFilter.
 *
 * <p>Hai chieu dieu huong:</p>
 * <ul>
 *   <li>chua dang nhap ma vao trang can quyen -&gt; {contextPath}/login</li>
 *   <li>da dang nhap ma vao lai /login        -&gt; {contextPath}/dashboard</li>
 * </ul>
 */
public class AuthFilter implements Filter {

    /** Duong dan khong yeu cau dang nhap. */
    private static final Set<String> PUBLIC_PATHS = Set.of("/login", "/logout");

    /** Tien to tai nguyen tinh - luon cho qua. */
    private static final Set<String> PUBLIC_PREFIXES = Set.of("/assets/", "/uploads/");

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;

        // Trang loi do container dispatch toi (403/404/500) luon phai qua. Mapping
        // trong web.xml khong khai bao <dispatcher> nen mac dinh chi la REQUEST va
        // nhanh nay khong chay; giu lai de neu sau nay co ai them ERROR vao mapping
        // thi loi 500 khong bi bien thanh redirect ve /login, giau mat loi that.
        if (req.getDispatcherType() == DispatcherType.ERROR) {
            chain.doFilter(request, response);
            return;
        }

        String path = req.getRequestURI().substring(req.getContextPath().length());

        HttpSession session = req.getSession(false);
        boolean loggedIn = session != null && session.getAttribute(Constants.SESSION_USER) != null;

        // Da dang nhap thi khong quay lai trang dang nhap nua
        if (loggedIn && "/login".equals(path)) {
            resp.sendRedirect(req.getContextPath() + "/dashboard");
            return;
        }

        if (isPublic(path)) {
            chain.doFilter(request, response);
            return;
        }

        if (!loggedIn) {
            resp.sendRedirect(req.getContextPath() + "/login");
            return;
        }

        chain.doFilter(request, response);
    }

    private boolean isPublic(String path) {
        if (path.isEmpty() || "/".equals(path) || PUBLIC_PATHS.contains(path)) {
            return true;
        }
        return PUBLIC_PREFIXES.stream().anyMatch(path::startsWith);
    }
}
