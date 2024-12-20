package org.example.bot;

public class DynamicExitCommand implements Command {
    private final IMessageSender messageSender;

    public DynamicExitCommand(IMessageSender messageSender) {
        this.messageSender = messageSender;
    }

    @Override
    public void execute(String chatId, String command) {
        messageSender.sendMessage(chatId, "Вы вернулись в главное меню!", KeyboardHelper.createLobbyMenuKeyboard());
    }
}
