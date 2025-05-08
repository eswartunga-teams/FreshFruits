//package com.freshfruits.app.filter;
//
//import java.io.IOException;
//import java.util.Optional;
//import java.util.Set;
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.stereotype.Component;
//
//import com.freshfruits.app.entity.Role;
//import com.freshfruits.app.entity.User;
//import com.freshfruits.app.repository.UserRepository;
//import com.freshfruits.app.service.AuthService;
//
//import jakarta.servlet.Filter;
//import jakarta.servlet.FilterChain;
//import jakarta.servlet.ServletException;
//import jakarta.servlet.ServletRequest;
//import jakarta.servlet.ServletResponse;
//import jakarta.servlet.annotation.WebFilter;
//import jakarta.servlet.http.Cookie;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//
//@WebFilter(urlPatterns = { "/api/*", "/admin/*" })
//@Component
//public class AuthenticationFilter implements Filter {
//
//    private final AuthService authService;
//    private final UserRepository userRepository;
//    private static final Logger logger = LoggerFactory.getLogger(AuthenticationFilter.class);
//    private static final Set<String> UNAUTHENTICATED_PATHS = Set.of("/api/auth/login", "/api/users/register");
//
//    public AuthenticationFilter(AuthService authService, UserRepository userRepository) {
//        System.out.println("Filter Started");
//        this.authService = authService;
//        this.userRepository = userRepository;
//    }
//
//    @Override
//    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
//            throws IOException, ServletException {
//        HttpServletRequest httpRequest = (HttpServletRequest) request;
//        HttpServletResponse httpResponse = (HttpServletResponse) response;
//
//        try {
//            executeFilterLogin(httpRequest, httpResponse, chain);
//        } catch (Exception e) {
//            logger.error("Unexpected Error in Authentication Filter", e);
//            sendErrorResponse(httpResponse, HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Internal Server Error");
//        }
//    }
//
//    private void executeFilterLogin(HttpServletRequest httpRequest, HttpServletResponse httpResponse, FilterChain chain)
//            throws IOException, ServletException {
//        String requestURI = httpRequest.getRequestURI();
//        logger.info("Request URI: {}", requestURI);
//
//        if (UNAUTHENTICATED_PATHS.stream().anyMatch(requestURI::startsWith)) {
//            logger.info("Skipping authentication for: {}", requestURI);
//            chain.doFilter(httpRequest, httpResponse);
//            return;
//        }
//
//        String token = getAuthTokenFromCookie(httpRequest);
//        if (token == null || !authService.validateToken(token)) {
//            sendErrorResponse(httpResponse, HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized: Invalid Token");
//            return;
//        }
//
//        String username = authService.extractUsername(token);
//        Optional<User> userOptional = userRepository.findByUsername(username);
//        if (userOptional.isEmpty()) {
//            sendErrorResponse(httpResponse, HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized: User Not Found");
//            return;
//        }
//
//        User authenticatedUser = userOptional.get();
//        Role role = authenticatedUser.getRole();
//        logger.info("✅ Authenticated User: {}, Role: {}", authenticatedUser.getUsername(), role);
//
//        chain.doFilter(httpRequest, httpResponse);
//    }
//
//    private String getAuthTokenFromCookie(HttpServletRequest request) {
//        Cookie[] cookies = request.getCookies();
//        if (cookies != null) {
//            for (Cookie cookie : cookies) {
//                if ("authToken".equals(cookie.getName())) {
//                    return cookie.getValue();
//                }
//            }
//        }
//        return null;
//    }
//
//    private void sendErrorResponse(HttpServletResponse response, int statusCode, String message) throws IOException {
//        response.setStatus(statusCode);
//        response.getWriter().write(message);
//    }
//}