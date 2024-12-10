package org.example.bot;

import org.example.bot.IMessageSender;
import org.example.bot.KeyboardHelper;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;

public class ExitLobbyCommand implements Command {
    private final IMessageSender messageSender;

    public ExitLobbyCommand(IMessageSender messageSender) {
        this.messageSender = messageSender;
    }

    @Override
    public void execute(String chatId, String command) {
        messageSender.sendMessage(chatId, "Возвращаемся в меню!", KeyboardHelper.createLobbyMenuKeyboard());
    }
}
