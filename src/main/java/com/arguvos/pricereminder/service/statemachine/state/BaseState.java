package com.arguvos.pricereminder.service.statemachine.state;

import com.arguvos.pricereminder.service.statemachine.BotMessage;
import com.arguvos.pricereminder.service.statemachine.BotContext;
import jakarta.inject.Singleton;

import java.util.Optional;

@Singleton
public class BaseState implements PackageState {
    @Override
    public Optional<String> updateState(BotContext ctx) {
        return Optional.of(BotMessage.INFO_MSG);
    }
}
