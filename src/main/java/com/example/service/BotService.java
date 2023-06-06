package com.example.service;

import com.example.service.statemachine.BotContext;
import com.example.service.statemachine.state.BaseState;
import com.example.service.statemachine.state.PackageState;
import io.micronaut.context.annotation.Context;
import jakarta.annotation.PostConstruct;
import jakarta.inject.Inject;
import lombok.Setter;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

import java.util.List;
import java.util.Optional;


@Context
public class BotService extends TelegramLongPollingBot {
    private static final String BOT_TOKEN = "BOT_TOKEN";
    private static final String BOT_USERNAME = "BOT_USERNAME";
    @Setter
    @Inject
    private List<PackageState> allStates;

    private BotContext botContext;

    public BotService() throws TelegramApiException {
        super(System.getenv(BOT_TOKEN));
        TelegramBotsApi botsApi = new TelegramBotsApi(DefaultBotSession.class);
        botsApi.registerBot(this);
    }

    @PostConstruct
    public void init() {
         botContext = new BotContext(new BaseState(), allStates);
    }

    @Override
    public String getBotUsername() {
        return System.getenv(BOT_USERNAME);
    }

    @Override
    public void onUpdateReceived(Update update) {
        var msg = update.getMessage();
        var user = msg.getFrom();
        var id = user.getId();

        Optional<String> answer = botContext.update(update.getMessage());
        answer.ifPresent(s -> sendText(id, s));
    }

    public void sendText(Long who, String what) {
        SendMessage sm = SendMessage.builder()
                .chatId(who.toString())
                .text(what).build();
        try {
            execute(sm);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }
}
