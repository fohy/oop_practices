package org.example.bot;

import org.example.bot.IMessageSender;

public class StartCommand implements Command {
    private final IMessageSender messageSender;

    public StartCommand(IMessageSender messageSender) {
        this.messageSender = messageSender;
    }

    @Override
    public void execute(String chatId, String command) {
        messageSender.sendMessage(chatId, "Добро пожаловать в Алиас! Выберите действие:", KeyboardHelper.createLobbyMenuKeyboard());
    }
}
