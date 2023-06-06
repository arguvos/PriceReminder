package com.example.service.statemachine.state;

import com.example.service.statemachine.BotMessage;
import com.example.service.statemachine.BotContext;
import jakarta.inject.Singleton;

import java.util.Optional;

@Singleton
public class BaseState implements PackageState {
    @Override
    public Optional<String> updateState(BotContext ctx) {
        return Optional.of(BotMessage.INFO_MSG);
    }
}
