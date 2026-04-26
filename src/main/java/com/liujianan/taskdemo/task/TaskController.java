package com.liujianan.taskdemo.task;

import com.liujianan.taskdemo.common.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.DeleteMapping;
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
    public ApiResponse<List<Task>> listTasks() {
        return ApiResponse.ok(taskService.listTasks());
    }

    @PostMapping
    public ApiResponse<Task> createTask(@Valid @RequestBody CreateTaskRequest request) {
        return ApiResponse.ok(taskService.createTask(request));
    }

    @PatchMapping("/{id}/status")
    public ApiResponse<Task> updateStatus(@PathVariable Long id, @RequestParam TaskStatus status) {
        return ApiResponse.ok(taskService.updateStatus(id, status));
    }

    @DeleteMapping("/{id}")
    public ApiResponse<Void> deleteTask(@PathVariable Long id) {
        taskService.deleteTask(id);
        return ApiResponse.ok(null);
    }
}
