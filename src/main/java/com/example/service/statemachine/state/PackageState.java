package com.example.service.statemachine.state;

import com.example.service.statemachine.BotContext;

import java.util.Optional;

public interface PackageState {
    Optional<String> updateState(BotContext ctx);
}
