package com.example.service.statemachine.state;

import com.example.service.statemachine.BotMessage;
import com.example.service.statemachine.BotContext;

import java.util.Optional;

public class DeleteState implements PackageState {
    @Override
    public Optional<String> updateState(BotContext ctx) {
        ctx.setCurrentState(DeleteIdState.class.getName());
        return Optional.of(BotMessage.DELETE_MSG);
    }
}