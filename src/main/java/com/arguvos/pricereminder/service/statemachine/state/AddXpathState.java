package com.arguvos.pricereminder.service.statemachine.state;

import com.arguvos.pricereminder.repository.TaskRepository;
import com.arguvos.pricereminder.service.statemachine.BotMessage;
import com.arguvos.pricereminder.service.statemachine.BotContext;
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
