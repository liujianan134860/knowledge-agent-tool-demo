package com.liujianan.collab.file;

import com.liujianan.collab.audit.AuditService;
import com.liujianan.collab.common.BusinessException;
import com.liujianan.collab.project.ProjectService;
import com.liujianan.collab.security.CurrentUser;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class FileService {
    private final AtomicLong idGenerator = new AtomicLong(1);
    private final ConcurrentHashMap<Long, FileRecord> files = new ConcurrentHashMap<>();
    private final ProjectService projectService;
    private final AuditService auditService;

    public FileService(ProjectService projectService, AuditService auditService) {
        this.projectService = projectService;
        this.auditService = auditService;
    }

    public FileRecord upload(FileUploadRequest request) {
        if (request.size() < 0 || request.size() > 20 * 1024 * 1024) {
            throw new BusinessException("file size must be between 0 and 20MB");
        }
        projectService.getProject(request.projectId());
        Long id = idGenerator.incrementAndGet();
        FileRecord record = new FileRecord(id, request.projectId(), request.filename(), request.contentType(), request.size(), CurrentUser.get().id(), LocalDateTime.now());
        files.put(id, record);
        auditService.record("FILE_UPLOAD", "file", id, "uploaded file " + request.filename());
        return record;
    }

    public List<FileRecord> listByProject(Long projectId) {
        return files.values().stream()
                .filter(file -> file.projectId().equals(projectId))
                .sorted((a, b) -> b.uploadedAt().compareTo(a.uploadedAt()))
                .toList();
    }
}
