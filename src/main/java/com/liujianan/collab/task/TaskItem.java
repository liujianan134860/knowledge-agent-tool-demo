package com.liujianan.collab.task;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record TaskItem(
        Long id,
        Long projectId,
        String title,
        String description,
        Long assigneeId,
        TaskStatus status,
        TaskPriority priority,
        LocalDate dueDate,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
}
