package com.example.service.statemachine.state;

import com.example.service.statemachine.BotMessage;
import com.example.service.statemachine.BotContext;
import jakarta.inject.Singleton;

import java.util.Optional;

@Singleton
public class AddUrlState implements PackageState {
    @Override
    public Optional<String> updateState(BotContext ctx) {
        ctx.getTask().setUrl(ctx.getMessage().getText());
        ctx.setCurrentState(AddXpathState.class.getName());
        return Optional.of(BotMessage.ADD_XPATH_MSG);
    }
}
