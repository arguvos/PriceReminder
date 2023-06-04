package com.example.services;

import com.example.Bot;
import com.example.repositorys.TaskRepository;
import io.micronaut.context.annotation.Context;
import jakarta.annotation.PostConstruct;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

@Context
public class BotService {
    TaskRepository taskRepository;

    BotService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    @PostConstruct
    void init() throws TelegramApiException {
        TelegramBotsApi botsApi = new TelegramBotsApi(DefaultBotSession.class);
        Bot bot = new Bot(System.getenv("BOT_TOKEN"), taskRepository);
        botsApi.registerBot(bot);
    }
}
