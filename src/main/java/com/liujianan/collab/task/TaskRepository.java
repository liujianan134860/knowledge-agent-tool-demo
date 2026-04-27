package com.liujianan.collab.task;

import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class TaskRepository {
    private final AtomicLong idGenerator = new AtomicLong(3);
    private final ConcurrentHashMap<Long, TaskItem> tasks = new ConcurrentHashMap<>();

    public TaskRepository() {
        LocalDateTime now = LocalDateTime.now();
        save(new TaskItem(1L, 1L, "完成登录认证接口", "实现登录、Token 签发和鉴权拦截", 2L, TaskStatus.DONE, TaskPriority.HIGH, LocalDate.now().plusDays(1), now, now));
        save(new TaskItem(2L, 1L, "整理 Swagger 接口文档", "补充任务、项目、文件和日志接口说明", 3L, TaskStatus.REVIEW, TaskPriority.MEDIUM, LocalDate.now().plusDays(3), now, now));
        save(new TaskItem(3L, 2L, "编写 Docker Compose", "编排后端服务、MySQL、Redis 与 Nginx", 1L, TaskStatus.DOING, TaskPriority.HIGH, LocalDate.now().plusDays(5), now, now));
    }

    public Long nextId() {
        return idGenerator.incrementAndGet();
    }

    public TaskItem save(TaskItem task) {
        tasks.put(task.id(), task);
        return task;
    }

    public Optional<TaskItem> findById(Long id) {
        return Optional.ofNullable(tasks.get(id));
    }

    public List<TaskItem> search(TaskQuery query) {
        return tasks.values().stream()
                .filter(task -> query.projectId() == null || task.projectId().equals(query.projectId()))
                .filter(task -> query.assigneeId() == null || task.assigneeId().equals(query.assigneeId()))
                .filter(task -> query.status() == null || task.status() == query.status())
                .filter(task -> query.keyword() == null || task.title().contains(query.keyword()) || task.description().contains(query.keyword()))
                .sorted((a, b) -> b.updatedAt().compareTo(a.updatedAt()))
                .toList();
    }
}
