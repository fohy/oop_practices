package org.example;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;

public class DialogueManager {
    private QuestionManager questionManager;
    private int score; // Для подсчета баллов

    public DialogueManager(QuestionManager questionManager) {
        this.questionManager = questionManager;
        this.score = 0; // Изначально баллы 0
    }

    // Метод для оценки ответа пользователя
    public boolean evaluateAnswer(String userAnswer, String correctAnswer) {
        // Оценка ответа, игнорируя регистр и лишние пробелы
        return userAnswer.trim().equalsIgnoreCase(correctAnswer.trim());
    }

    // Метод для обработки сообщений
    public SendMessage processMessage(Message msg) {
        String userMessage = msg.getText();
        SendMessage response = new SendMessage();
        response.setChatId(msg.getChatId().toString());

        switch (userMessage.toLowerCase()) {
            case "/stop":
                response.setText("Ну не справился и не справился, с кем не бывает (петушара бездарный)\nтык на /start чтоб начать заново");
                questionManager.reset();
                break;
            case "/descript":
                response.setText("Бот будет отправлять тебе вопросы, твоя задача постараться правильно на них ответить\nА может негр ебаный мне тут рэп не исполнять?");
                break;
            case "/start":
                response.setText("Привет, я бот! Я буду задавать тебе вопросы. Введи /help для справки.");
                // Получаем первый вопрос
                Question question = questionManager.getNextQuestion();
                if (question != null) {
                    response.setText(question.getQuestionText());
                } else {
                    response.setText("Все вопросы заданы. Введите /start для нового раунда.");
                    questionManager.reset();  // Если все вопросы заданы, сбрасываем и начинаем заново
                }
                break;

            case "/help":
                response.setText("Команды:\n/start - начать общение с ботом\n/help - получить справку");
                break;

            default:
                // Обработка ответа на вопрос
                Question currentQuestion = questionManager.getCurrentQuestion();
                if (currentQuestion != null) {
                    if (evaluateAnswer(userMessage, currentQuestion.getCorrectAnswer())) {
                        score++; // Увеличиваем баллы за правильный ответ
                        response.setText("Правильный ответ!");
                    } else {
                        response.setText("Неправильный ответ! Правильный ответ: " + currentQuestion.getCorrectAnswer());
                    }

                    // После ответа на текущий вопрос - следующий
                    currentQuestion = questionManager.getNextQuestion();
                    if (currentQuestion != null) {
                        response.setText(response.getText() + "\nСледующий вопрос: " + currentQuestion.getQuestionText());
                    } else {
                        response.setText(response.getText() + "\nВы ответили на все вопросы! Ваш результат: " + score + " из " + questionManager.getTotalQuestions());
                        questionManager.reset();  // Если все вопросы заданы, сбрасываем
                        score = 0; // Сбрасываем баллы
                    }
                }
                break;
        }

        return response;
    }
}
