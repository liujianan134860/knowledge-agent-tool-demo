package com.liujianan.collab.dashboard;

import java.util.Map;

public record DashboardSummary(
        int projectCount,
        int taskCount,
        Map<String, Long> taskStatusCount,
        int recentAuditCount
) {
}
