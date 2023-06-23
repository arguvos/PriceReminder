package com.arguvos.pricereminder.service.statemachine.state;

import com.arguvos.pricereminder.service.statemachine.BotMessage;
import com.arguvos.pricereminder.service.statemachine.BotContext;
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
