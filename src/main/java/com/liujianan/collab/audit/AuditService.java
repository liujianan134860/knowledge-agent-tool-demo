package com.liujianan.collab.audit;

import com.liujianan.collab.security.CurrentUser;
import com.liujianan.collab.security.UserPrincipal;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class AuditService {
    private final AtomicLong idGenerator = new AtomicLong(0);
    private final ConcurrentHashMap<Long, AuditLog> logs = new ConcurrentHashMap<>();

    public AuditLog record(String action, String targetType, Long targetId, String detail) {
        UserPrincipal user = CurrentUser.get();
        AuditLog log = new AuditLog(idGenerator.incrementAndGet(), user.id(), user.displayName(), action, targetType, targetId, detail, LocalDateTime.now());
        logs.put(log.id(), log);
        return log;
    }

    public List<AuditLog> list(String action, Long operatorId) {
        return logs.values().stream()
                .filter(log -> action == null || log.action().equals(action))
                .filter(log -> operatorId == null || log.operatorId().equals(operatorId))
                .sorted((a, b) -> b.createdAt().compareTo(a.createdAt()))
                .toList();
    }
}
