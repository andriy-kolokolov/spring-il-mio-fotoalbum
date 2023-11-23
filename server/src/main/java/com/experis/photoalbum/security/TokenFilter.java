package com.experis.photoalbum.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class TokenFilter extends OncePerRequestFilter {
    private final JwtCore jwtCore;
    private final UserDetailsService userDetailsService;

    @Autowired
    public TokenFilter(JwtCore jwtCore, @Lazy UserDetailsService userDetailsService) {
        this.jwtCore = jwtCore;
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        try {
            String headerAuth = request.getHeader("Authorization");
            if (headerAuth != null && headerAuth.startsWith("Bearer ")) {
                String jwt = headerAuth.substring(7);
                String username = jwtCore.getNameFromJwt(jwt); // Assuming this verifies the token as well
                if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                    UserDetails userDetails = userDetailsService.loadUserByUsername(username);
                    UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                            userDetails, null, userDetails.getAuthorities()
                    );
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }
            }
            filterChain.doFilter(request, response);
        } catch (ExpiredJwtException e) {
            logger.error("Expired JWT Token", e);
            handleException(response, "Expired JWT Token", HttpServletResponse.SC_UNAUTHORIZED, e);
        } catch (Exception e) {
            logger.error("Authentication error", e);
            handleException(response, "Authentication error", HttpServletResponse.SC_UNAUTHORIZED, e);
        }
    }

    private void handleException(HttpServletResponse response, String message, int status, Exception e) throws IOException {
        logger.error(message, e);

        Map<String, Object> errorDetails = new HashMap<>();
        errorDetails.put("message", e.getMessage());
        errorDetails.put("timestamp", new Date());
        errorDetails.put("status", status);

        response.setStatus(status);
        response.setContentType("application/json");
        new ObjectMapper().writeValue(response.getWriter(), errorDetails);
    }
}
