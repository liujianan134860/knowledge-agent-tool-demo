package com.liujianan.collab.task;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

public record CreateTaskRequest(
        @NotNull Long projectId,
        @NotBlank @Size(max = 100) String title,
        @Size(max = 500) String description,
        @NotNull Long assigneeId,
        TaskPriority priority,
        LocalDate dueDate
) {
}
