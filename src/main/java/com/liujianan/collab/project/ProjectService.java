package com.liujianan.collab.project;

import com.liujianan.collab.audit.AuditService;
import com.liujianan.collab.common.BusinessException;
import com.liujianan.collab.security.CurrentUser;
import com.liujianan.collab.security.UserPrincipal;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ProjectService {
    private final ProjectRepository projectRepository;
    private final AuditService auditService;

    public ProjectService(ProjectRepository projectRepository, AuditService auditService) {
        this.projectRepository = projectRepository;
        this.auditService = auditService;
    }

    public List<Project> listProjects() {
        return projectRepository.findAll();
    }

    public Project createProject(CreateProjectRequest request) {
        UserPrincipal user = CurrentUser.get();
        LocalDateTime now = LocalDateTime.now();
        Project project = new Project(projectRepository.nextId(), request.name(), request.description(), ProjectStatus.ACTIVE, user.id(), now, now);
        projectRepository.save(project);
        auditService.record("PROJECT_CREATE", "project", project.id(), "created project " + project.name());
        return project;
    }

    public Project updateStatus(Long id, ProjectStatus status) {
        Project current = getProject(id);
        Project updated = new Project(current.id(), current.name(), current.description(), status, current.ownerId(), current.createdAt(), LocalDateTime.now());
        projectRepository.save(updated);
        auditService.record("PROJECT_STATUS", "project", id, "updated project status to " + status);
        return updated;
    }

    public Project getProject(Long id) {
        return projectRepository.findById(id).orElseThrow(() -> new BusinessException("project not found: " + id));
    }
}
