package org.example;

import java.util.ArrayList;
import java.util.List;

public class QuestionManager {
    private List<Question> questions;
    private int index;

    public QuestionManager() {
        this.questions = new ArrayList<>();
        this.index = 0;

        // Инициализируем вопросы
        this.questions.add(new Question("Сколько будет 2 + 2?", "4"));
        this.questions.add(new Question("Какого цвета небо?", "синий"));
        this.questions.add(new Question("Сколько дней в неделе?", "7"));
    }

    public Question getNextQuestion() {
        if (index < questions.size()) {
            return questions.get(index++);
        } else {
            return null;  // Все вопросы заданы
        }
    }

    public void reset() {
        this.index = 0;  // Сброс индекса для повторного использования
    }
}
