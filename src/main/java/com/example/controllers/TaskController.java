package com.example.controllers;


import com.example.models.Task;
import com.example.repositorys.TaskRepository;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.*;
import io.micronaut.scheduling.TaskExecutors;
import io.micronaut.scheduling.annotation.ExecuteOn;

import javax.validation.Valid;

@ExecuteOn(TaskExecutors.IO)
@Controller("tasks")
public class TaskController {

    private final TaskRepository repository;

    public TaskController(TaskRepository repository) {
        this.repository = repository;
    }

    @Get("/{id}")
    public Task show(Long id) {
        return repository.findById(id).orElse(null);
    }

    @Get()
    public Iterable<Task> findAll() {
        return repository.findAll();
    }

    @Post()
    public HttpResponse<Task> save(@Body @Valid Task task) {
        return HttpResponse.created(repository.save(task));
    }

    @Delete("/{id}")
    public HttpResponse delete(Long id) {
        repository.deleteById(id);
        return HttpResponse.noContent();
    }

}
