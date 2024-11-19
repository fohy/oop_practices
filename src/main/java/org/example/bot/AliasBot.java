package org.example.bot;

import org.example.service.AliasGameService;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

public class AliasBot extends TelegramLongPollingBot {

    private final TelegramCommandHandler commandHandler;

    public AliasBot() {
        this.commandHandler = new TelegramCommandHandler(new AliasGameService());
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
        String userMessage = update.getMessage().getText();
        Long chatId = update.getMessage().getChatId();
        SendMessage response = commandHandler.handleCommand(userMessage, chatId);
        System.out.println(chatId + " написал(а) : " + userMessage);
        try {
            execute(response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
