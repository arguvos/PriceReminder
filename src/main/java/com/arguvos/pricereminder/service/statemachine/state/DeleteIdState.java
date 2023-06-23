package com.arguvos.pricereminder.service.statemachine.state;

import com.arguvos.pricereminder.repository.TaskRepository;
import com.arguvos.pricereminder.service.statemachine.BotMessage;
import com.arguvos.pricereminder.service.statemachine.BotContext;
import jakarta.inject.Singleton;

import java.util.Optional;

@Singleton
public class DeleteIdState implements PackageState {
    private final TaskRepository taskRepository;

    public DeleteIdState(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }
    @Override
    public Optional<String> updateState(BotContext ctx) {
        long delete_id = Long.parseLong(ctx.getMessage().getText());
        taskRepository.deleteById(delete_id);
        ctx.setCurrentState(BaseState.class.getName());
        return Optional.of(BotMessage.FINISH_DELETE_MSG);
    }
}
