package com.example.controller;


import com.example.model.Task;
import com.example.repository.TaskRepository;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.*;
import io.micronaut.scheduling.TaskExecutors;
import io.micronaut.scheduling.annotation.ExecuteOn;

import javax.validation.Valid;

@ExecuteOn(TaskExecutors.IO)
@Controller("tasks")
public class TaskController {
    private final TaskRepository taskRepository;

    public TaskController(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    @Get("/{id}")
    public Task show(Long id) {
        return taskRepository.findById(id).orElse(null);
    }

    @Get()
    public Iterable<Task> findAll() {
        return taskRepository.findAll();
    }

    @Post()
    public HttpResponse<Task> save(@Body @Valid Task task) {
        return HttpResponse.created(taskRepository.save(task));
    }

    @Delete("/{id}")
    public void delete(Long id) {
        taskRepository.deleteById(id);
    }
}