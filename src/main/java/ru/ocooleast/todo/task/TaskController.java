package ru.ocooleast.todo.task;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/tasks")
@RequiredArgsConstructor
@CacheConfig(cacheNames = "taskCache")
public class TaskController {
    private final TaskService taskService;
    private final TaskMapper taskMapper;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @CacheEvict(allEntries = true)
    public ResponseEntity<TaskDto> createTask(@RequestParam String taskDescription) {
        var createdTask = taskService.createTask(taskDescription);
        var createdTaskDto = taskMapper.toDto(createdTask);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdTaskDto);
    }

    @PostMapping("/finish")
    @CacheEvict(allEntries = true)
    public ResponseEntity<TaskDto> finishTask(@RequestParam Long taskId) {
        var finishedTask = taskService.finishTask(taskId);
        var finishedTaskDto = taskMapper.toDto(finishedTask);
        return ResponseEntity.ofNullable(finishedTaskDto);
    }

    @GetMapping
    @Cacheable
    public ResponseEntity<List<TaskDto>> getAllTasks() {
        var tasks = taskService.getAllTasks();
        var taskDtos = taskMapper.toDtos(tasks);
        return ResponseEntity.ofNullable(taskDtos);
    }

    @GetMapping("/{taskId}")
    @Cacheable(key = "#taskId")
    public ResponseEntity<TaskDto> getTask(@PathVariable Long taskId) {
        var task = taskService.getTask(taskId);
        var taskDto = taskMapper.toDto(task);
        return ResponseEntity.ofNullable(taskDto);
    }

    @DeleteMapping("/{taskId}")
    @CacheEvict(allEntries = true)
    public void deleteTask(@PathVariable Long taskId) {
        taskService.deleteTask(taskId);
    }

    @ExceptionHandler(TaskNotFoundException.class)
    public ResponseEntity<String> handleNotFoundTaskException(TaskNotFoundException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
    }
}
