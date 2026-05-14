package com.turkcell.spring_cqrs.core.security.authorization;

import java.util.List;
import java.util.UUID;

import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.turkcell.spring_cqrs.core.exception.AuthenticationException;
import com.turkcell.spring_cqrs.core.exception.AuthorizationException;
import com.turkcell.spring_cqrs.core.mediator.pipeline.PipelineBehavior;
import com.turkcell.spring_cqrs.core.mediator.pipeline.RequestHandlerDelegate;
import com.turkcell.spring_cqrs.core.security.jwt.JwtService;

import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;

@Component
@Order(10)
public class AuthorizationBehavior implements PipelineBehavior {

    private final JwtService jwtService;

    public AuthorizationBehavior(JwtService jwtService) {
        this.jwtService = jwtService;
    }

    @Override
    public <R> R handle(Object request, RequestHandlerDelegate<R> next) {
        AuthorizedRequest authorizedRequest = (AuthorizedRequest) request;

        ServletRequestAttributes attrs = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (attrs == null)
            throw new AuthenticationException("No HTTP request context found");

        HttpServletRequest httpRequest = attrs.getRequest();
        String authHeader = httpRequest.getHeader("Authorization");

        if (authHeader == null || !authHeader.startsWith("Bearer "))
            throw new AuthenticationException("Missing or invalid Authorization header");

        String token = authHeader.substring(7);

        Claims claims;
        try {
            claims = jwtService.validateToken(token);
        } catch (Exception e) {
            throw new AuthenticationException("Invalid or expired JWT token");
        }

        UUID userId = UUID.fromString(claims.getSubject());
        String email = claims.get("email", String.class);
        String role = claims.get("role", String.class);

        UserContext.set(new UserContext(userId, email, role));

        List<String> requiredRoles = authorizedRequest.getRequiredRoles();
        if (!requiredRoles.isEmpty() && (role == null || !requiredRoles.contains(role)))
            throw new AuthorizationException("Insufficient permissions: required roles " + requiredRoles);

        try {
            return next.invoke();
        } finally {
            UserContext.clear();
        }
    }

    @Override
    public boolean supports(Object request) {
        return request instanceof AuthorizedRequest;
    }

}
