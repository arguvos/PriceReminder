package com.arguvos.pricereminder.service.statemachine.state;

import com.arguvos.pricereminder.service.statemachine.BotMessage;
import com.arguvos.pricereminder.service.statemachine.BotContext;
import jakarta.inject.Singleton;

import java.util.Optional;

@Singleton
public class DeleteState implements PackageState {
    @Override
    public Optional<String> updateState(BotContext ctx) {
        ctx.setCurrentState(DeleteIdState.class.getName());
        return Optional.of(BotMessage.DELETE_MSG);
    }
}
