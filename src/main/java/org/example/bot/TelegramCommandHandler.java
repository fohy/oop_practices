// TelegramCommandHandler.java
package org.example.bot;

import org.example.service.AliasGameService;
import org.example.bot.IMessageSender;

import java.util.HashMap;
import java.util.Map;

public class TelegramCommandHandler {
    private final Map<String, Command> commandMap = new HashMap<>();
    private final AliasGameService gameService;
    private final IMessageSender messageSender;

    public TelegramCommandHandler(AliasGameService gameService, IMessageSender messageSender) {
        this.gameService = gameService;
        this.messageSender = messageSender;
        initCommandMap();
    }

    // Инициализация карты команд
    private void initCommandMap() {
        // Основные команды
        commandMap.put("/start", new StartCommand(messageSender));
        commandMap.put("создать лобби", new CreateLobbyCommand(gameService, messageSender)); // Используется и gameService, и messageSender
        commandMap.put("войти в лобби по коду", new AskForLobbyCodeCommand(messageSender)); // Только messageSender
        commandMap.put("ввести код лобби", new HandleLobbyCodeCommand(gameService, messageSender)); // Используется и gameService, и messageSender

        // Специальные команды
        commandMap.put("начать игру досрочно", new StartGameEarlyCommand(gameService, messageSender)); // Используется и gameService, и messageSender
        commandMap.put("выйти из лобби",  new ExitLobbyCommand(gameService, messageSender)); // Только messageSender

        // Команды для выбора команд
        commandMap.put("лосиный сфинктер", new SelectTeamCommand(gameService, messageSender, "Лосиный сфинктер"));
        commandMap.put("ежиная перхоть", new SelectTeamCommand(gameService, messageSender, "Ежиная перхоть"));

        // Команды для выбора темы
        commandMap.put("технологии", new SelectThemeCommand(gameService, messageSender, "Технологии"));
        commandMap.put("животные", new SelectThemeCommand(gameService, messageSender, "Животные"));
        commandMap.put("еда", new SelectThemeCommand(gameService, messageSender, "Еда"));

        // Игра
        commandMap.put("начать игру", new StartGameCommand(gameService, messageSender)); // Используется и gameService, и messageSender
        commandMap.put("следующее", new NextWordCommand(gameService, messageSender)); // Используется и gameService, и messageSender
        commandMap.put("пропустить", new SkipWordCommand(gameService, messageSender)); // Используется и gameService, и messageSender
        commandMap.put("в главное меню", new DynamicExitCommand(messageSender));
    }

    // Метод для обработки команд
    public void handleCommand(String command, String chatId) {
        Command commandHandler = commandMap.get(command.toLowerCase());
        if (commandHandler != null) {
            commandHandler.execute(chatId, command);
        } else {
            messageSender.sendMessage(chatId, "Неизвестная команда!", null);
        }
    }
}
