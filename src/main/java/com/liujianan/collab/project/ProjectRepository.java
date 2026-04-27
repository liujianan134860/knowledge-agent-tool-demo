package com.liujianan.collab.project;

import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class ProjectRepository {
    private final AtomicLong idGenerator = new AtomicLong(2);
    private final ConcurrentHashMap<Long, Project> projects = new ConcurrentHashMap<>();

    public ProjectRepository() {
        LocalDateTime now = LocalDateTime.now();
        save(new Project(1L, "实习项目协同平台", "用于展示后台项目管理、任务流转和权限控制能力", ProjectStatus.ACTIVE, 1L, now, now));
        save(new Project(2L, "作品集部署优化", "整理 GitHub README、Docker Compose 和接口文档", ProjectStatus.PLANNING, 2L, now, now));
    }

    public Long nextId() {
        return idGenerator.incrementAndGet();
    }

    public Project save(Project project) {
        projects.put(project.id(), project);
        return project;
    }

    public Optional<Project> findById(Long id) {
        return Optional.ofNullable(projects.get(id));
    }

    public List<Project> findAll() {
        return projects.values().stream().sorted((a, b) -> a.id().compareTo(b.id())).toList();
    }
}
