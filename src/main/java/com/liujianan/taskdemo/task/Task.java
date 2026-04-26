package com.liujianan.taskdemo.task;

import java.time.LocalDateTime;

public record Task(
        Long id,
        String title,
        String description,
        TaskStatus status,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
}
