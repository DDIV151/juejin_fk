package com.ddiv.juejin_fk.filter;


import com.ddiv.juejin_fk.utils.TokenUtils;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class TokenFilter implements Filter {

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        String url = request.getRequestURI();
        // 不管登录和注册（因为没有token）
        if (url.contains("/login") || url.contains("/register")) {
            filterChain.doFilter(request, response);
            return;
        }
        try {
            String token = request.getHeader("token");
            TokenUtils.parseToken(token);
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("{\n" +
                    "    \"code\": 401,\n" +
                    "    \"message\": \"NEED_TOKEN\",\n" +
                    "    \"data\": null\n" +
                    "}");
            return;
        }
        filterChain.doFilter(request, response);
    }
}
