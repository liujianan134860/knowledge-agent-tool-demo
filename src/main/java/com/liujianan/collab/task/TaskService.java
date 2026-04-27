package com.liujianan.collab.task;

import com.liujianan.collab.audit.AuditService;
import com.liujianan.collab.common.BusinessException;
import com.liujianan.collab.project.ProjectService;
import com.liujianan.collab.user.UserRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class TaskService {
    private final TaskRepository taskRepository;
    private final ProjectService projectService;
    private final UserRepository userRepository;
    private final AuditService auditService;

    public TaskService(TaskRepository taskRepository, ProjectService projectService, UserRepository userRepository, AuditService auditService) {
        this.taskRepository = taskRepository;
        this.projectService = projectService;
        this.userRepository = userRepository;
        this.auditService = auditService;
    }

    public List<TaskItem> search(TaskQuery query) {
        return taskRepository.search(query);
    }

    public TaskItem createTask(CreateTaskRequest request) {
        projectService.getProject(request.projectId());
        userRepository.findById(request.assigneeId()).orElseThrow(() -> new BusinessException("assignee not found: " + request.assigneeId()));
        LocalDateTime now = LocalDateTime.now();
        TaskItem task = new TaskItem(
                taskRepository.nextId(),
                request.projectId(),
                request.title(),
                request.description(),
                request.assigneeId(),
                TaskStatus.TODO,
                request.priority() == null ? TaskPriority.MEDIUM : request.priority(),
                request.dueDate(),
                now,
                now
        );
        taskRepository.save(task);
        auditService.record("TASK_CREATE", "task", task.id(), "created task " + task.title());
        return task;
    }

    public TaskItem updateStatus(Long id, TaskStatus status) {
        TaskItem current = getTask(id);
        TaskItem updated = new TaskItem(current.id(), current.projectId(), current.title(), current.description(), current.assigneeId(), status, current.priority(), current.dueDate(), current.createdAt(), LocalDateTime.now());
        taskRepository.save(updated);
        auditService.record("TASK_STATUS", "task", id, "updated task status to " + status);
        return updated;
    }

    public TaskItem getTask(Long id) {
        return taskRepository.findById(id).orElseThrow(() -> new BusinessException("task not found: " + id));
    }
}
