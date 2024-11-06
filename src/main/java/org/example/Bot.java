package org.example;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

public class Bot extends TelegramLongPollingBot {
    private DialogueManager dialogueManager;
    private QuestionManager questionManager;

    public Bot() {
        this.questionManager = new QuestionManager();
        this.dialogueManager = new DialogueManager(questionManager);
    }

    @Override
    public String getBotUsername() {
        return "StudyProject_Bot";  // Имя бота
    }

    @Override
    public String getBotToken() {
        return "7756968278:AAFYiGjm-zTzluV2v_uHRLnzvWDI6EG_dh8";  // Токен бота
    }

    @Override
    public void onUpdateReceived(Update update) {
        var msg = update.getMessage();
        var user = msg.getFrom();

        System.out.println(user.getFirstName() + " wrote " + msg.getText());
        if (msg != null && msg.hasText()) {
            String userMessage = msg.getText();

            // Обработка команд /start, /help и ответов на вопросы
            SendMessage response = dialogueManager.processMessage(msg);

            try {
                // Отправка ответа пользователю
                execute(response);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
