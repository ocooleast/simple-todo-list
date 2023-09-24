package ru.ocooleast.todo.task;

import java.util.List;
import java.util.Optional;

public interface TaskService {
    Task createTask(String taskDescription);

    Task finishTask(Long taskId);

    List<Task> getAllTasks();

    Task getTask(Long taskId);

    Optional<Task> findTask(Long taskId);

    void deleteTask(Long taskId);
}
