package org.example;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

public class Bot extends TelegramLongPollingBot {
    private DialogueManager dialogueManager;
    private GameSession gameSession;
    private KeyboardManager keyboardManager;

    public Bot() {
        this.keyboardManager = new KeyboardManager();
        this.gameSession = new GameSession(new QuestionManager());
        this.dialogueManager = new DialogueManager(gameSession, keyboardManager);
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
            SendMessage response = dialogueManager.processMessage(msg);
            try {
                execute(response);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
