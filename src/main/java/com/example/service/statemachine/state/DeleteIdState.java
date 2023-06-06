package com.example.service.statemachine.state;

import com.example.repository.TaskRepository;
import com.example.service.statemachine.BotMessage;
import com.example.service.statemachine.BotContext;

import java.util.Optional;

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
