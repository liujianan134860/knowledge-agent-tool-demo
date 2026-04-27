package com.liujianan.collab.project;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CreateProjectRequest(
        @NotBlank @Size(max = 80) String name,
        @Size(max = 300) String description
) {
}
