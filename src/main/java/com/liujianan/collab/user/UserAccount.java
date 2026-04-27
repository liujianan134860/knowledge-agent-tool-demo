package com.liujianan.collab.user;

import java.time.LocalDateTime;

public record UserAccount(
        Long id,
        String username,
        String password,
        String displayName,
        Role role,
        boolean enabled,
        LocalDateTime createdAt
) {
}
