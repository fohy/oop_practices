package org.example.bot;

import org.example.service.AliasGameService;
import org.example.bot.TelegramCommandHandler;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

public class AliasBot extends TelegramLongPollingBot {

    private final AliasGameService gameService;
    private final TelegramCommandHandler commandHandler;

    public AliasBot() {
        this.gameService = new AliasGameService();
        this.commandHandler = new TelegramCommandHandler(gameService);
    }

    @Override
    public String getBotUsername() {
        return "StudyProject_Bot";  // Имя вашего бота
    }

    @Override
    public String getBotToken() {
        return "7756968278:AAFYiGjm-zTzluV2v_uHRLnzvWDI6EG_dh8";  // Ваш токен
    }

    @Override
    public void onUpdateReceived(Update update) {

        String userMessage = update.getMessage().getText();
        Long chatId = update.getMessage().getChatId();
        System.out.print(chatId.toString() + " написал: " + userMessage + "\n");
        SendMessage response = commandHandler.handleCommand(userMessage, chatId);


        try {
            execute(response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
