package com.jeequan.jeepay.core.filter;

// SSOSecurityFilter.java

import com.jeequan.jeepay.core.ssoservice.SSOTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import java.io.IOException;

@Component
public class SSOSecurityFilter extends OncePerRequestFilter {

    @Autowired
    private SSOTokenService tokenService;

    @Override
    protected void doFilterInternal(jakarta.servlet.http.HttpServletRequest request, jakarta.servlet.http.HttpServletResponse response, jakarta.servlet.FilterChain filterChain) throws jakarta.servlet.ServletException, IOException {
        String token = request.getHeader("Authorization");
        if (token == null ||!tokenService.validateToken(token)) {
            response.sendRedirect("/sso/login");
            return;
        }
        filterChain.doFilter(request, response);
    }
}