package ru.ocooleast.todo.task.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.ocooleast.todo.task.Task;
import ru.ocooleast.todo.task.TaskNotFoundException;
import ru.ocooleast.todo.task.TaskRepository;
import ru.ocooleast.todo.task.TaskService;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TaskServiceImpl implements TaskService {
    private final TaskRepository taskRepository;

    @Override
    public Task createTask(String taskDescription) {
        var newTask = new Task();
        newTask.setDescription(taskDescription);
        return taskRepository.save(newTask);
    }

    @Override
    public Task finishTask(Long taskId) {
        var task = getTask(taskId);
        task.setFinished(true);
        return taskRepository.save(task);
    }

    @Override
    public List<Task> getAllTasks() {
        return taskRepository.findAll();
    }

    @Override
    public Task getTask(Long taskId) {
        return findTask(taskId).orElseThrow(() -> new TaskNotFoundException(taskId));
    }

    @Override
    public Optional<Task> findTask(Long taskId) {
        return taskRepository.findById(taskId);
    }

    @Override
    public void deleteTask(Long taskId) {
        if (!taskRepository.existsById(taskId)) {
            throw new TaskNotFoundException(taskId);
        }
        taskRepository.deleteById(taskId);
    }
}
