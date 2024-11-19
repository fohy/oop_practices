package org.example.bot;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.ArrayList;
import java.util.List;

public class KeyboardHelper {

    public static ReplyKeyboardMarkup createThemeSelectionKeyboard() {
        ReplyKeyboardMarkup keyboardMarkup = new ReplyKeyboardMarkup();
        List<KeyboardRow> keyboard = new ArrayList<>();

        // Добавляем строки с кнопками для выбора темы
        KeyboardRow row1 = new KeyboardRow();
        row1.add(new KeyboardButton("Технологии"));
        row1.add(new KeyboardButton("Животные"));
        row1.add(new KeyboardButton("Еда"));
        keyboard.add(row1);

        keyboardMarkup.setKeyboard(keyboard);
        return keyboardMarkup;
    }

    public static ReplyKeyboardMarkup createStartKeyboard() {
        ReplyKeyboardMarkup keyboardMarkup = new ReplyKeyboardMarkup();
        List<KeyboardRow> keyboard = new ArrayList<>();

        // Добавляем строку с кнопкой для старта игры
        KeyboardRow row1 = new KeyboardRow();
        row1.add(new KeyboardButton("Начать игру"));
        keyboard.add(row1);

        keyboardMarkup.setKeyboard(keyboard);
        return keyboardMarkup;
    }

    public static ReplyKeyboardMarkup createGameKeyboard() {
        ReplyKeyboardMarkup keyboardMarkup = new ReplyKeyboardMarkup();
        List<KeyboardRow> keyboard = new ArrayList<>();

        // Добавляем строки с кнопками для управления игрой
        KeyboardRow row1 = new KeyboardRow();
        row1.add(new KeyboardButton("Следующее"));
        row1.add(new KeyboardButton("Пропустить"));
        keyboard.add(row1);

        keyboardMarkup.setKeyboard(keyboard);
        return keyboardMarkup;
    }

    public static ReplyKeyboardMarkup createNewGameKeyboard() {
        ReplyKeyboardMarkup keyboardMarkup = new ReplyKeyboardMarkup();
        List<KeyboardRow> keyboard = new ArrayList<>();

        // Кнопка для начала новой игры
        KeyboardRow row1 = new KeyboardRow();
        row1.add(new KeyboardButton("Начать новую игру"));
        keyboard.add(row1);

        keyboardMarkup.setKeyboard(keyboard);
        return keyboardMarkup;
    }
}
