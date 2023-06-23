package com.arguvos.pricereminder.service.statemachine.state;

import com.arguvos.pricereminder.service.statemachine.BotContext;

import java.util.Optional;

public interface PackageState {
    Optional<String> updateState(BotContext ctx);
}
