package com.arguvos.pricereminder.service;

import com.arguvos.pricereminder.model.Task;
import com.arguvos.pricereminder.repository.ExecutionRepository;
import com.arguvos.pricereminder.repository.TaskRepository;
import com.arguvos.pricereminder.model.Execution;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import jakarta.inject.Singleton;

import java.util.List;
import java.util.Optional;

@Singleton
public class TaskService {

    private final BotService botService;
    private final TaskRepository taskRepository;
    private final ExecutionRepository executionRepository;

    public TaskService(BotService botService, TaskRepository taskRepository, ExecutionRepository executionRepository) {
        this.botService = botService;
        this.taskRepository = taskRepository;
        this.executionRepository = executionRepository;
    }

    public void update() {
        Iterable<Task> tasks = taskRepository.findAll();
        tasks.forEach(e -> {
            Optional<Execution> execution = getExecution(e);
            if (execution.isPresent()) {
                executionRepository.save(execution.get());
                botService.sendText(e.getUserId(), execution.get().getValue());
            };
        });
    }

    public void execute() {
        Iterable<Task> tasks = taskRepository.findAll();
        tasks.forEach(task -> {
            Optional<Execution> execution = getExecution(task);
            if (execution.isPresent() && isValueChanges(task.getExecutions(), execution.get().getValue())) {
                executionRepository.save(execution.get());
                botService.sendText(task.getUserId(), execution.get().getValue());
            }
        });
    }

    private Optional<Execution> getExecution(Task task) {
        WebClient client = new WebClient();
        client.getOptions().setCssEnabled(false);
        client.getOptions().setJavaScriptEnabled(false);
        try {
            HtmlPage page = client.getPage(task.getUrl());
            String value = ((HtmlElement) page.getFirstByXPath(task.getXPath())).getTextContent();
            return Optional.of(new Execution(value, task));
        }catch(Exception e){
            e.printStackTrace();
        }
        return Optional.empty();
    }

    private boolean isValueChanges(List<Execution> executions, String value) {
        return executions.isEmpty() || !executions.get(executions.size() - 1).getValue().equals(value);
    }
}
