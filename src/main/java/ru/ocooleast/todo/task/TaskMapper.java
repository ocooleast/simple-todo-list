package ru.ocooleast.todo.task;

import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface TaskMapper {
    TaskDto toDto(Task task);

    List<TaskDto> toDtos(List<Task> tasks);
}
