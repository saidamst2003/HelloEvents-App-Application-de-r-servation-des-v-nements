package com.AppH.HelloEvents.filter;

import com.AppH.HelloEvents.config.jwtUtils;
import com.AppH.HelloEvents.service.CustemUserService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
@Component
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {

    private final CustemUserService custemUserService;
    private final jwtUtils JwtUtils;
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
final String aurhHeader = request.getHeader("Authorization");

String username = null;
String email = null;
String jwt = null;
    if (aurhHeader != null && aurhHeader.startsWith("Bearer ")) {
        jwt = aurhHeader.substring(7);
        username = JwtUtils.extractUsername(jwt);
        email = JwtUtils.extractEmail(jwt);
    }
    if (username != null && email != null && SecurityContextHolder.getContext().getAuthentication() == null) {
        UserDetails userDetails = custemUserService.loadUserByUsername(username);
        UserDetails userDetails1 = custemUserService.loadUserByUsername(email);
    }

    }
}
