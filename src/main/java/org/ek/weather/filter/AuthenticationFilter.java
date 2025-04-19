package org.ek.weather.filter;

import jakarta.servlet.*;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.ek.weather.model.User;
import org.ek.weather.service.SessionService;
import org.ek.weather.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.util.WebUtils;

import java.io.IOException;
import java.util.Optional;
import java.util.UUID;

//Once Per request Filter
@Component
public class AuthenticationFilter implements Filter {

    private SessionService sessionService;
    private UserService userService;

    @Autowired
    public AuthenticationFilter(SessionService sessionService,
                                UserService userService) {
        this.sessionService = sessionService;
        this.userService = userService;
    }

    @Override
    public void doFilter(ServletRequest servletRequest,
                         ServletResponse servletResponse,
                         FilterChain filterChain)
            throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        String path = request.getRequestURI();

        if (path.equals("/login") || path.equals("/new")) {
            filterChain.doFilter(servletRequest, servletResponse);
            return;
        }

        Cookie sessionCookie = WebUtils.getCookie(request, "_sessionId");

        if (sessionCookie != null) {
            try{
                UUID sessionId = UUID.fromString(sessionCookie.getValue());
                Optional<User> userOptional = sessionService.findById(sessionId).
                        flatMap(session -> userService.findById(session.getUser().getId()));
                if (userOptional.isPresent()) {
                    request.setAttribute("_user_attribute", userOptional.get());
                    filterChain.doFilter(servletRequest, servletResponse);
                    return;
                }
            }
            catch (IllegalArgumentException e){
                //TODO Wrong uuid Format
            }

        }

        response.sendRedirect("/login");

    }

}
