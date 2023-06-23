package com.arguvos.pricereminder.controller;


import com.arguvos.pricereminder.repository.TaskRepository;
import com.arguvos.pricereminder.model.Task;
import com.arguvos.pricereminder.service.TaskService;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.*;
import io.micronaut.scheduling.TaskExecutors;
import io.micronaut.scheduling.annotation.ExecuteOn;

import javax.validation.Valid;

@ExecuteOn(TaskExecutors.IO)
@Controller("tasks")
public class TaskController {

    private final TaskService taskService;
    private final TaskRepository taskRepository;

    public TaskController(TaskService taskService,
                          TaskRepository taskRepository) {
        this.taskService = taskService;
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

    @Post("/update")        //check all task, and notify user
    public void update() {
        taskService.update();
    }

    @Post("/execute")       //check all task, and notify user if value changed
    public void execute() {
        taskService.execute();
    }
}