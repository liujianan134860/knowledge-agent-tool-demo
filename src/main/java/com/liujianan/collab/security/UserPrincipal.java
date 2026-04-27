package com.liujianan.collab.security;

import com.liujianan.collab.user.Role;

public record UserPrincipal(Long id, String username, String displayName, Role role) {
    public boolean isAdmin() {
        return role == Role.ADMIN;
    }
}
