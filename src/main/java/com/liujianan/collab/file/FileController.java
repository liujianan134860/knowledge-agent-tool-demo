package com.liujianan.collab.file;

import com.liujianan.collab.common.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/files")
public class FileController {
    private final FileService fileService;

    public FileController(FileService fileService) {
        this.fileService = fileService;
    }

    @PostMapping
    public ApiResponse<FileRecord> upload(@Valid @RequestBody FileUploadRequest request) {
        return ApiResponse.ok(fileService.upload(request));
    }

    @GetMapping
    public ApiResponse<List<FileRecord>> listByProject(@RequestParam Long projectId) {
        return ApiResponse.ok(fileService.listByProject(projectId));
    }
}
