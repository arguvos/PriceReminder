package com.arguvos.pricereminder.service.statemachine.state;

import com.arguvos.pricereminder.service.statemachine.BotMessage;
import com.arguvos.pricereminder.service.statemachine.BotContext;
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
