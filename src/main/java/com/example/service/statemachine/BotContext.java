package com.example.service.statemachine;

import com.example.model.Task;
import com.example.service.statemachine.state.*;
import lombok.Getter;
import org.telegram.telegrambots.meta.api.objects.Message;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Getter
public class BotContext {
    private final Map<String, PackageState> allStates = new HashMap<>();
    private final Task task;
    private PackageState currentState;
    private Message message;


    public BotContext(PackageState currentState, List<PackageState> allStates)
    {
        this.currentState = currentState;
        this.task = new Task();
        for(PackageState state : allStates) {
            this.allStates.put(state.getClass().getName(), state);
        }
    }

    public Optional<String> update(Message message) {
        this.message = message;

        if (message.isCommand()) {
            if (message.getText().equals(BotCommand.INFO))
                currentState = allStates.get(BaseState.class.getName());
            else if (message.getText().equals(BotCommand.ADD))
                currentState = allStates.get(AddState.class.getName());
            else if (message.getText().equals(BotCommand.SHOW))
                currentState = allStates.get(ShowState.class.getName());
            else if (message.getText().equals(BotCommand.DELETE))
                currentState = allStates.get(DeleteState.class.getName());
        }

        return currentState.updateState(this);
    }

    public void setCurrentState(String className) {
        currentState = allStates.get(className);
    }
}
