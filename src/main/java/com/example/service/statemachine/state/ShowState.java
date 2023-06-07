package com.example.service.statemachine.state;

import com.example.model.Task;
import com.example.repository.TaskRepository;
import com.example.service.statemachine.BotMessage;
import com.example.service.statemachine.BotContext;
import jakarta.inject.Singleton;

import java.util.Optional;

@Singleton
public class ShowState implements PackageState {
    private final TaskRepository taskRepository;

    public ShowState(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }
    @Override
    public Optional<String> updateState(BotContext ctx) {
        Iterable<Task> allTask = taskRepository.findAllByUserId(ctx.getMessage().getFrom().getId());
        StringBuilder answer = new StringBuilder();
        for (Task task : allTask) {
            answer.append("ID: ").append(task.getId()).append("\n")
                    .append("URL: ").append(task.getUrl()).append("\n")
                    .append("XPATH: ").append(task.getXPath()).append("\n")
                    .append("\n");
        }
        ctx.setCurrentState(BaseState.class.getName());
        if (answer.isEmpty()) {
            return Optional.of(BotMessage.SHOW_EMPTY_MSG);
        }
        return Optional.of(answer.toString());
    }
}
