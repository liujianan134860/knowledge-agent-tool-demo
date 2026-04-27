package com.liujianan.collab.audit;

import com.liujianan.collab.common.ApiResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/audit-logs")
public class AuditController {
    private final AuditService auditService;

    public AuditController(AuditService auditService) {
        this.auditService = auditService;
    }

    @GetMapping
    public ApiResponse<List<AuditLog>> list(@RequestParam(required = false) String action,
                                            @RequestParam(required = false) Long operatorId) {
        return ApiResponse.ok(auditService.list(action, operatorId));
    }
}
