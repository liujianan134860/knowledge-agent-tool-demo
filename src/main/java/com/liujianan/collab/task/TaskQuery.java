package com.liujianan.collab.task;

public record TaskQuery(Long projectId, Long assigneeId, TaskStatus status, String keyword) {
}
