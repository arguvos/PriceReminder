package com.example;

import com.example.models.Task;
import com.example.repositorys.TaskRepository;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;


public class Bot extends TelegramLongPollingBot {
    TaskRepository taskRepository;

    public Bot(String botToken, TaskRepository taskRepository) {
        super(botToken);
        this.taskRepository = taskRepository;
    }

    private String state = null;

    @Override
    public String getBotUsername() {
        return "price_reminder";
    }

    @Override
    public void onUpdateReceived(Update update) {
        var msg = update.getMessage();
        var user = msg.getFrom();
        var id = user.getId();

        if (msg.isCommand()) {
            if (msg.getText().equals("/add")) {
                state = "add";
                return;
            } else if (msg.getText().equals("/show"))
                state = "show";
        }

        if (state.equals("add")) {
            Task task = new Task(id, msg.getText());
            taskRepository.save(task);
        } else if (state.equals("show")) {
            Iterable<Task> allTask = taskRepository.findAll();
            StringBuilder answer = new StringBuilder();
            for (Task task : allTask) {
                answer.append(" ").append(task.getSite());
            }
            sendText(id, answer.toString());
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
