package org.example;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Update;

public class Bot extends TelegramLongPollingBot {

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

        System.out.println(user.getFirstName() + " wrote " + msg.getText());
    }
}
