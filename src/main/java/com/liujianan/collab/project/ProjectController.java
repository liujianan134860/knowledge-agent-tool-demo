package com.liujianan.collab.project;

import com.liujianan.collab.common.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/projects")
public class ProjectController {
    private final ProjectService projectService;

    public ProjectController(ProjectService projectService) {
        this.projectService = projectService;
    }

    @GetMapping
    public ApiResponse<List<Project>> listProjects() {
        return ApiResponse.ok(projectService.listProjects());
    }

    @PostMapping
    public ApiResponse<Project> createProject(@Valid @RequestBody CreateProjectRequest request) {
        return ApiResponse.ok(projectService.createProject(request));
    }

    @PatchMapping("/{id}/status")
    public ApiResponse<Project> updateStatus(@PathVariable Long id, @RequestParam ProjectStatus status) {
        return ApiResponse.ok(projectService.updateStatus(id, status));
    }
}
