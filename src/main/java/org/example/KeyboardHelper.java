package org.example;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;

import java.util.ArrayList;
import java.util.List;

public class KeyboardHelper {

    // Клавиатура для начала игры
    public static ReplyKeyboardMarkup createStartKeyboard() {
        ReplyKeyboardMarkup markup = new ReplyKeyboardMarkup();
        markup.setResizeKeyboard(true);

        List<KeyboardRow> keyboard = new ArrayList<>();
        KeyboardRow row = new KeyboardRow();
        row.add("Начать игру");
        keyboard.add(row);

        markup.setKeyboard(keyboard);
        return markup;
    }

    // Клавиатура для игры (Следующее, пропустить)
    public static ReplyKeyboardMarkup createGameKeyboard() {
        ReplyKeyboardMarkup markup = new ReplyKeyboardMarkup();
        markup.setResizeKeyboard(true);

        List<KeyboardRow> keyboard = new ArrayList<>();

        KeyboardRow row1 = new KeyboardRow();
        row1.add("Следующее");
        keyboard.add(row1);

        KeyboardRow row2 = new KeyboardRow();
        row2.add("Пропустить");
        keyboard.add(row2);

        markup.setKeyboard(keyboard);
        return markup;
    }

    // Клавиатура для подтверждения продолжения игры
    public static ReplyKeyboardMarkup createContinueKeyboard() {
        ReplyKeyboardMarkup markup = new ReplyKeyboardMarkup();
        markup.setResizeKeyboard(true);

        List<KeyboardRow> keyboard = new ArrayList<>();
        KeyboardRow row = new KeyboardRow();
        row.add("Готово");
        keyboard.add(row);

        markup.setKeyboard(keyboard);
        return markup;
    }

    // Клавиатура для новой игры
    public static ReplyKeyboardMarkup createNewGameKeyboard() {
        ReplyKeyboardMarkup markup = new ReplyKeyboardMarkup();
        markup.setResizeKeyboard(true);

        List<KeyboardRow> keyboard = new ArrayList<>();
        KeyboardRow row = new KeyboardRow();
        row.add("Начать новую игру");
        keyboard.add(row);

        markup.setKeyboard(keyboard);
        return markup;
    }

}
