package com.liujianan.taskdemo.task;

public class TaskNotFoundException extends RuntimeException {
    public TaskNotFoundException(Long id) {
        super("task not found: " + id);
    }
}
