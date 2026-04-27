package com.liujianan.collab.dashboard;

import com.liujianan.collab.audit.AuditService;
import com.liujianan.collab.common.ApiResponse;
import com.liujianan.collab.project.ProjectService;
import com.liujianan.collab.task.TaskQuery;
import com.liujianan.collab.task.TaskService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/dashboard")
public class DashboardController {
    private final ProjectService projectService;
    private final TaskService taskService;
    private final AuditService auditService;

    public DashboardController(ProjectService projectService, TaskService taskService, AuditService auditService) {
        this.projectService = projectService;
        this.taskService = taskService;
        this.auditService = auditService;
    }

    @GetMapping("/summary")
    public ApiResponse<DashboardSummary> summary() {
        var tasks = taskService.search(new TaskQuery(null, null, null, null));
        Map<String, Long> statusCount = tasks.stream().collect(Collectors.groupingBy(task -> task.status().name(), Collectors.counting()));
        return ApiResponse.ok(new DashboardSummary(projectService.listProjects().size(), tasks.size(), statusCount, auditService.list(null, null).size()));
    }
}
