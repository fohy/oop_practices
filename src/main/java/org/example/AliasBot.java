package org.example;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

public class AliasBot extends TelegramLongPollingBot {

    private AliasGame aliasGame;

    @Override
    public String getBotUsername() {
        return "StudyProject_Bot";  // Убедитесь, что здесь указано имя вашего бота
    }

    @Override
    public String getBotToken() {
        return "7756968278:AAFYiGjm-zTzluV2v_uHRLnzvWDI6EG_dh8";  // Ваш токен бота
    }

    @Override
    public void onUpdateReceived(Update update) {
        Message msg = update.getMessage();
        String userMessage = msg.getText();
        var usr = msg.getFrom();
        System.out.println(usr.getFirstName() + " написал(а): " + msg.getText());
        SendMessage response = new SendMessage();
        response.setChatId(msg.getChatId().toString());

        if (msg.hasText()) {
            switch (userMessage.toLowerCase()) {
                case "/help":
                    response.setText("Привет! Я бот для игры в Аллиас. Вот команды, которые ты можешь использовать:\n\n" +
                            "/start - Начать игру\n" +
                            "/help - Показать это сообщение");
                    break;
                case "/start":
                    aliasGame = new AliasGame(msg.getChatId());  // Инициализация игры с ID чата
                    response.setText("Привет! Давай играть в Аллиас! Нажми 'Начать игру' для старта.");
                    response.setReplyMarkup(KeyboardHelper.createStartKeyboard()); // Клавиатура для начала игры
                    break;
                case "/rules" :
                    response.setText("Правила игры в Аллиас:\n" +
                            "1. Игра состоит из 3 раундов.\n" +
                            "2. В каждом раунде команда по очереди объясняет слова своей сопернической команде.\n" +
                            "3. За правильный ответ команда получает 1 балл.\n" +
                            "4. Каждый раунд длится 30 секунд.\n" +
                            "5. В конце игры выводится общий результат каждой команды.\n");
                    break;

                case "начать игру":
                    if (aliasGame != null) {
                        String word = aliasGame.startGame();  // Получаем первое слово для объяснения
                        response.setText(word);
                        response.setReplyMarkup(KeyboardHelper.createGameKeyboard()); // Клавиатура с кнопками
                    }
                    break;
                case "следующее":
                    if (aliasGame != null && aliasGame.isAwaitingConfirmation()) {
                        response.setText("Раунд завершен! Готовы ли вы продолжить?");
                        response.setReplyMarkup(KeyboardHelper.createContinueKeyboard()); // Клавиатура для подтверждения
                    } else if (aliasGame != null && !aliasGame.isGameOver()) {
                        String word = aliasGame.nextWord(true);  // Передаем true, если ответ был правильный
                        response.setText(word);
                    } else if (aliasGame != null) {
                        // Игра завершена, выводим итоговый результат
                        response.setText("Игра завершена! Ваш итоговый результат: " + aliasGame.getScore() + " баллов.");
                        response.setReplyMarkup(KeyboardHelper.createNewGameKeyboard()); // Клавиатура с кнопкой для новой игры
                    }
                    break;
                case "пропустить":
                    if (aliasGame != null && aliasGame.isAwaitingConfirmation()) {
                        response.setText("Раунд завершен! Готовы ли вы продолжить?");
                        response.setReplyMarkup(KeyboardHelper.createContinueKeyboard()); // Клавиатура для подтверждения
                    } else if (aliasGame != null && !aliasGame.isGameOver()) {
                        String word = aliasGame.skipWord();  // Пропускаем слово
                        response.setText(word);
                    } else if (aliasGame != null) {
                        // Игра завершена, выводим итоговый результат
                        response.setText("Игра завершена! Ваш итоговый результат: " + aliasGame.getScore() + " баллов.");
                        response.setReplyMarkup(KeyboardHelper.createNewGameKeyboard()); // Клавиатура с кнопкой для новой игры
                    }
                    break;
                case "начать новую игру":
                    aliasGame = new AliasGame(msg.getChatId());  // Инициализация новой игры
                    response.setText("Новая игра началась! Нажмите 'Начать игру' для старта.");
                    response.setReplyMarkup(KeyboardHelper.createStartKeyboard());
                    break;
                case "готово":
                    if (aliasGame != null && aliasGame.isAwaitingConfirmation()) {
                        aliasGame.confirmContinue();  // Подтверждение продолжения игры
                        String word = aliasGame.nextWord(true);  // Получаем следующее слово
                        response.setText(word);
                        response.setReplyMarkup(KeyboardHelper.createGameKeyboard()); // Клавиатура с кнопками
                    }
                    break;
                default:
                    response.setText("Неизвестная команда. Используйте /help для получения справки.");
                    break;
            }
        }

        try {
            execute(response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
