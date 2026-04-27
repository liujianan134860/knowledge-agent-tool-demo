package com.liujianan.collab.file;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record FileUploadRequest(
        @NotNull Long projectId,
        @NotBlank String filename,
        String contentType,
        long size
) {
}
