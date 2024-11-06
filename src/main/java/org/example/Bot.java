package org.example;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

public class Bot extends TelegramLongPollingBot {
    private DialogueManager dialogueManager;
    private QuestionManager questionManager;

    public Bot() {
        // Инициализируем все необходимые компоненты
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
        if (msg != null && msg.hasText()) {
            String userMessage = msg.getText();

            // Логика для обработки /start и других команд
            SendMessage response = new SendMessage();
            response.setChatId(msg.getChatId().toString());

            // Команды /start и /help
            if (userMessage.equalsIgnoreCase("/start")) {
                response.setText("Привет, я бот! Я буду задавать тебе вопросы. Введи /help для справки.");
            } else if (userMessage.equalsIgnoreCase("/help")) {
                response.setText("Команды:\n/start - начать общение с ботом\n/help - получить справку");
            } else {
                // Обработка диалога
                response = dialogueManager.processMessage(msg);
            }

            try {
                // Отправка ответа пользователю
                execute(response);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
