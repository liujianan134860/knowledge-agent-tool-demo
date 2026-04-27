package com.liujianan.collab.project;

import java.time.LocalDateTime;

public record Project(
        Long id,
        String name,
        String description,
        ProjectStatus status,
        Long ownerId,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
}
