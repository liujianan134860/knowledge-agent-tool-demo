package com.liujianan.collab.audit;

import java.time.LocalDateTime;

public record AuditLog(
        Long id,
        Long operatorId,
        String operatorName,
        String action,
        String targetType,
        Long targetId,
        String detail,
        LocalDateTime createdAt
) {
}
