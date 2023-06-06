package com.example.service.statemachine.state;

import com.example.repository.TaskRepository;
import com.example.service.statemachine.BotMessage;
import com.example.service.statemachine.BotContext;
import jakarta.inject.Singleton;

import java.util.Optional;

@Singleton
public class AddXpathState implements PackageState {
    private final TaskRepository taskRepository;

    public AddXpathState(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    @Override
    public Optional<String> updateState(BotContext ctx) {
        ctx.getTask().setXPath(ctx.getMessage().getText());
        taskRepository.save(ctx.getTask());
        ctx.setCurrentState(BaseState.class.getName());
        return Optional.of(BotMessage.FINISH_ADD_MSG);
    }
}
