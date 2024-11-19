package org.example.bot;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.ArrayList;
import java.util.List;

public class KeyboardHelper {

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

        KeyboardRow row3 = new KeyboardRow();
        row3.add("Закончить игру");
        keyboard.add(row3);

        markup.setKeyboard(keyboard);
        return markup;
    }

    public static ReplyKeyboardMarkup createThemeSelectionKeyboard() {
        ReplyKeyboardMarkup markup = new ReplyKeyboardMarkup();
        markup.setResizeKeyboard(true);

        List<KeyboardRow> keyboard = new ArrayList<>();

        KeyboardRow row1 = new KeyboardRow();
        row1.add("Технологии");
        keyboard.add(row1);

        KeyboardRow row2 = new KeyboardRow();
        row2.add("Животные");
        keyboard.add(row2);

        KeyboardRow row3 = new KeyboardRow();
        row3.add("Еда");
        keyboard.add(row3);

        markup.setKeyboard(keyboard);
        return markup;
    }

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
