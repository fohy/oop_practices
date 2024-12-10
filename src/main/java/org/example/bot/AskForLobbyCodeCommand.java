package org.example.bot;

import org.example.bot.IMessageSender;

public class AskForLobbyCodeCommand implements Command {
    private final IMessageSender messageSender;

    public AskForLobbyCodeCommand(IMessageSender messageSender) {
        this.messageSender = messageSender;
    }

    @Override
    public void execute(String chatId, String command) {
        messageSender.sendMessage(chatId, "Введите код лобби для подключения:", null);
    }
}
