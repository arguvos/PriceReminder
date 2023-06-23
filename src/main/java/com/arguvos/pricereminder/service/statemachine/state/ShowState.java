package com.arguvos.pricereminder.service.statemachine.state;

import com.arguvos.pricereminder.repository.TaskRepository;
import com.arguvos.pricereminder.model.Task;
import com.arguvos.pricereminder.service.statemachine.BotMessage;
import com.arguvos.pricereminder.service.statemachine.BotContext;
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
