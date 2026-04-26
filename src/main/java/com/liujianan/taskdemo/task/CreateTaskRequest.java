package com.liujianan.taskdemo.task;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CreateTaskRequest(
        @NotBlank
        @Size(max = 80)
        String title,

        @Size(max = 300)
        String description
) {
}
