package com.liujianan.collab.file;

import java.time.LocalDateTime;

public record FileRecord(
        Long id,
        Long projectId,
        String filename,
        String contentType,
        long size,
        Long uploaderId,
        LocalDateTime uploadedAt
) {
}
