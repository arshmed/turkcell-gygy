package com.turkcell.spring_cqrs.core.security.authorization;

import java.util.UUID;

public class UserContext {
    private static final ThreadLocal<UserContext> holder = new ThreadLocal<>();

    private final UUID userId;
    private final String email;
    private final String role;

    public UserContext(UUID userId, String email, String role) {
        this.userId = userId;
        this.email = email;
        this.role = role;
    }

    public UUID getUserId() { return userId; }
    public String getEmail() { return email; }
    public String getRole() { return role; }

    public static void set(UserContext context) { holder.set(context); }
    public static UserContext get() { return holder.get(); }
    public static void clear() { holder.remove(); }
}
