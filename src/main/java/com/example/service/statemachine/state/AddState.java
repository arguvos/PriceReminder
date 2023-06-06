package com.example.service.statemachine.state;

import com.example.service.statemachine.BotMessage;
import com.example.service.statemachine.BotContext;
import jakarta.inject.Singleton;

import java.util.Optional;

@Singleton
public class AddState implements PackageState {
    @Override
    public Optional<String> updateState(BotContext ctx) {
        ctx.getTask().setUserId(ctx.getMessage().getFrom().getId());
        ctx.setCurrentState(AddUrlState.class.getName());
        return Optional.of(BotMessage.ADD_URL_MSG);
    }
}
