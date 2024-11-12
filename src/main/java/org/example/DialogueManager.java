package org.example;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardRemove;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;

import java.util.HashMap;
import java.util.Map;

public class DialogueManager {
    private KeyboardManager keyboardManager;
    private Map<Long, GameSession> userSessions = new HashMap<>();

    public DialogueManager(KeyboardManager keyboardManager) {
        this.keyboardManager = keyboardManager;
    }

    public SendMessage processMessage(Message msg) {
        long chatId = msg.getChatId();
        GameSession session = userSessions.computeIfAbsent(chatId, k -> {
            System.out.println("Создаём новую сессию для chatId: " + chatId);
            return new GameSession(new QuestionManager());
        });

        String userMessage = msg.getText();
        SendMessage response = new SendMessage();
        response.setChatId(String.valueOf(chatId));

        System.out.println("Получено сообщение от пользователя " + chatId + ": " + userMessage);

        switch (userMessage.toLowerCase()) {
            case "/stop":
                response.setText("Чё сдался да, ну ниче, яйца отрастут - попробуешь ещё раз!");
                session.reset();
                session.endGame();
                response.setReplyMarkup(keyboardManager.createStartGameKeyboard());
                break;

            case "/descript":
                response.setText("Бот будет отправлять тебе вопросы, твоя задача — правильно на них ответить.");
                break;

            case "/start":
                if (!session.isGameStarted()) {
                    response.setText("Привет! Я бот, давай поиграем в игру. Когда будешь готов, нажми 'Начать игру'.");
                    response.setReplyMarkup(keyboardManager.createStartGameKeyboard());
                    System.out.println("Отправляем кнопку 'Начать игру' для chatId: " + chatId);
                }
                break;

            case "/help":
                response.setText("Команды:\n/start - начать игру\n/help - получить справку");
                break;

            default:
                if (userMessage.equals("Начать игру") && !session.isGameStarted()) {
                    session.startGame();
                    response.setText("Отлично! Начинаем игру!");
                    ReplyKeyboardRemove removeKeyboard = new ReplyKeyboardRemove();
                    removeKeyboard.setRemoveKeyboard(true);
                    response.setReplyMarkup(removeKeyboard);
                    System.out.println("Игра начинается для chatId: " + chatId);

                    Question question = session.getNextQuestion();
                    if (question != null) {
                        response.setText(question.getQuestionText());
                        System.out.println("Отправляем первый вопрос для chatId: " + chatId);
                    } else {
                        response.setText("Ошибка: нет вопросов.");
                    }
                } else {
                    Question currentQuestion = session.getCurrentQuestion();
                    if (currentQuestion != null) {
                        if (evaluateAnswer(userMessage, currentQuestion.getCorrectAnswer())) {
                            session.incrementScore();
                            response.setText("Правильный ответ!");
                        } else {
                            response.setText("Неправильный ответ! Правильный ответ: " + currentQuestion.getCorrectAnswer());
                        }

                        currentQuestion = session.getNextQuestion();
                        if (currentQuestion != null) {
                            response.setText(response.getText() + "\nСледующий вопрос: " + currentQuestion.getQuestionText());
                        } else {
                            response.setText(response.getText() + "\nВы ответили на все вопросы! Ваш результат: " + session.getScore() + " из " + session.getTotalQuestions());
                            session.reset();
                            response.setReplyMarkup(keyboardManager.createStartGameKeyboard());
                            System.out.println("Игра завершена для chatId: " + chatId);
                        }
                    }
                }
                break;
        }

        return response;
    }

    private boolean evaluateAnswer(String userAnswer, String correctAnswer) {
        return userAnswer.trim().equalsIgnoreCase(correctAnswer.trim());
    }
}
