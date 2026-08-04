package com.studentrent.filter;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;

import java.io.IOException;

/**
 * Dat UTF-8 cho moi request/response - bat buoc de nhap tieng Viet khong bi loi font.
 *
 * <p>Khai bao trong web.xml (khong dung @WebFilter) de kiem soat thu tu chay:
 * EncodingFilter -&gt; AuthFilter -&gt; RoleFilter. Annotation khong bao dam thu tu.</p>
 */
public class EncodingFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        chain.doFilter(request, response);
    }
}
