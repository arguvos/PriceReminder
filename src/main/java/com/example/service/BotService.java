package com.example.service;

import com.example.model.Task;
import com.example.repository.TaskRepository;
import io.micronaut.context.annotation.Context;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;


@Context
public class BotService extends TelegramLongPollingBot {
    private static final String BOT_TOKEN = "BOT_TOKEN";
    private static final String BOT_USERNAME = "BOT_USERNAME";
    private final TaskRepository taskRepository;
    private Task task = new Task();
    private State state = State.BASE;

    public BotService(TaskRepository taskRepository) throws TelegramApiException {
        super(System.getenv(BOT_TOKEN));
        this.taskRepository = taskRepository;
        TelegramBotsApi botsApi = new TelegramBotsApi(DefaultBotSession.class);
        botsApi.registerBot(this);
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

        if (msg.isCommand()) {
            if (msg.getText().equals(BotCommand.INFO))
                state = State.START;
            else if (msg.getText().equals(BotCommand.ADD))
                state = State.ADD;
            else if (msg.getText().equals(BotCommand.SHOW))
                state = State.SHOW;
            else if (msg.getText().equals(BotCommand.DELETE))
                state = State.DELETE; //TODO
        }

        switch (state) {
            case START -> {
                sendText(id, BotMessage.INFO_MSG);
            }
            case ADD -> {
                sendText(id, BotMessage.ADD_URL_MSG);
                task = new Task();
                task.setUserId(id);
                state = State.ADD_URL;
            }
            case ADD_URL -> {
                sendText(id, BotMessage.ADD_XPATH_MSG);
                task.setUrl(msg.getText());
                state = State.ADD_XPATH;
            }
            case ADD_XPATH -> {
                task.setXPath(msg.getText());
                taskRepository.save(task);
                sendText(id, BotMessage.FINISH_ADD_MSG);
                state = State.BASE;
            }
            case SHOW -> {
                Iterable<Task> allTask = taskRepository.findAll();
                StringBuilder answer = new StringBuilder();
                for (Task task : allTask) {
                    answer.append("ID: ").append(task.getId()).append("\n")
                            .append("URL: ").append(task.getUrl()).append("\n")
                            .append("XPATH: ").append(task.getXPath()).append("\n")
                            .append("\n");
                }
                sendText(id, answer.toString());
                state = State.BASE;
            }
            case DELETE -> {
                sendText(id, BotMessage.DELETE_MSG);
                state = State.DELETE_ID;
            }
            case DELETE_ID -> {
                long delete_id = Long.parseLong(msg.getText());
                taskRepository.deleteById(delete_id);
                sendText(id, BotMessage.FINISH_DELETE_MSG);
                state = State.BASE;
            }
        }
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
