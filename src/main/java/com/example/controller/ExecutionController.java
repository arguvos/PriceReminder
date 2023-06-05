package com.example.controller;

import com.example.service.TaskService;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Post;
import io.micronaut.scheduling.TaskExecutors;
import io.micronaut.scheduling.annotation.ExecuteOn;

@ExecuteOn(TaskExecutors.IO)
@Controller("execution")
public class ExecutionController {
    private final TaskService taskService;

    public ExecutionController(TaskService taskService) {
        this.taskService = taskService;
    }

    @Post("/update")
    public void update() {
        taskService.update();
    }

    @Post("/execute")
    public void execute() {
        taskService.execute();
    }
}
