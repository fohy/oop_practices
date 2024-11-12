package org.example;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

public class Bot extends TelegramLongPollingBot {
    private DialogueManager dialogueManager;
    private KeyboardManager keyboardManager;

    public Bot() {
        this.keyboardManager = new KeyboardManager();
        this.dialogueManager = new DialogueManager(keyboardManager);
    }

    @Override
    public String getBotUsername() {
        return "StudyProject_Bot";
    }

    @Override
    public String getBotToken() {
        return "7756968278:AAFYiGjm-zTzluV2v_uHRLnzvWDI6EG_dh8";
    }

    @Override
    public void onUpdateReceived(Update update) {
        var msg = update.getMessage();
        var user = msg.getFrom();
        System.out.println(user.getFirstName() + " написал: " + msg.getText());
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
