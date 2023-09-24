package ru.ocooleast.todo.task;

import jakarta.persistence.EntityNotFoundException;

public class TaskNotFoundException extends EntityNotFoundException {
    public TaskNotFoundException(Long taskId) {
        super(String.format("Task with id=%d not found", taskId));
    }
}
