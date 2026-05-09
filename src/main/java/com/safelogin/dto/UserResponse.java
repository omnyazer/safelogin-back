package com.safelogin.dto;

import com.safelogin.entity.User;
import com.safelogin.entity.UserRole;

public class UserResponse {

    private final Long id;
    private final String username;
    private final String role;

    public UserResponse(Long id, String username, String role) {
        this.id = id;
        this.username = username;
        this.role = role;
    }

    public static UserResponse fromEntity(User user) {
        UserRole userRole = user.getRole() == null ? UserRole.USER : user.getRole();
        return new UserResponse(user.getId(), user.getUsername(), userRole.name());
    }

    public Long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getRole() {
        return role;
    }
}
