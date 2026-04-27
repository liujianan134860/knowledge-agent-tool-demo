package com.liujianan.collab.task;

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
@RequestMapping("/api/tasks")
public class TaskController {
    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping
    public ApiResponse<List<TaskItem>> search(@RequestParam(required = false) Long projectId,
                                              @RequestParam(required = false) Long assigneeId,
                                              @RequestParam(required = false) TaskStatus status,
                                              @RequestParam(required = false) String keyword) {
        return ApiResponse.ok(taskService.search(new TaskQuery(projectId, assigneeId, status, keyword)));
    }

    @PostMapping
    public ApiResponse<TaskItem> createTask(@Valid @RequestBody CreateTaskRequest request) {
        return ApiResponse.ok(taskService.createTask(request));
    }

    @PatchMapping("/{id}/status")
    public ApiResponse<TaskItem> updateStatus(@PathVariable Long id, @RequestParam TaskStatus status) {
        return ApiResponse.ok(taskService.updateStatus(id, status));
    }
}
