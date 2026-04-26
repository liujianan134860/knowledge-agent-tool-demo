package com.liujianan.taskdemo.task;

import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class TaskService {
    private final AtomicLong idGenerator = new AtomicLong(2);
    private final Map<Long, Task> tasks = new ConcurrentHashMap<>();

    public TaskService() {
        LocalDateTime now = LocalDateTime.now();
        tasks.put(1L, new Task(1L, "Write project README", "Explain run steps and API examples", TaskStatus.TODO, now, now));
        tasks.put(2L, new Task(2L, "Prepare internship resume", "Keep project description accurate and verifiable", TaskStatus.DOING, now, now));
    }

    public List<Task> listTasks() {
        return new ArrayList<>(tasks.values())
                .stream()
                .sorted(Comparator.comparing(Task::id))
                .toList();
    }

    public Task createTask(CreateTaskRequest request) {
        Long id = idGenerator.incrementAndGet();
        LocalDateTime now = LocalDateTime.now();
        Task task = new Task(id, request.title(), request.description(), TaskStatus.TODO, now, now);
        tasks.put(id, task);
        return task;
    }

    public Task updateStatus(Long id, TaskStatus status) {
        Task current = getTask(id);
        Task updated = new Task(
                current.id(),
                current.title(),
                current.description(),
                status,
                current.createdAt(),
                LocalDateTime.now()
        );
        tasks.put(id, updated);
        return updated;
    }

    public void deleteTask(Long id) {
        if (tasks.remove(id) == null) {
            throw new TaskNotFoundException(id);
        }
    }

    private Task getTask(Long id) {
        Task task = tasks.get(id);
        if (task == null) {
            throw new TaskNotFoundException(id);
        }
        return task;
    }
}
