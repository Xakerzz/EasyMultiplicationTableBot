package com.example.EasyMultiplicationTableBot;


import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.List;
import java.util.Random;

@SpringBootApplication
public class EasyMultiplicationTableBotApplication extends TelegramLongPollingBot {


    final private String BOT_TOKEN = "6322941008:AAGv0H3XZ85-zdRWq2rZFIOUf2OSsPc0CnQ";
    final private String BOT_NAME = "EasyMultiplicationTableBot";


    @Override
    public String getBotUsername() {
        return BOT_NAME;
    }

    @Override
    public String getBotToken() {
        return BOT_TOKEN;
    }

    @Override
    public void onUpdateReceived(Update update) {
        try {
            if (update.hasMessage() && update.getMessage().hasText()) {
                Message inMess = update.getMessage();
                long chatId = inMess.getChatId();
                String messageText = update.getMessage().getText();
                SendMessage outMess = new SendMessage();

               if (messageText.equals("/start")) {
                   outMess.setChatId(chatId);
                   outMess.setText("Приветствую тебя юный ученик, ты здесь, чтобы изучить таблицу умножения.\uD83D\uDE09 Давай приступим к изучению. Желаю тебе успехов в учебе.\uD83D\uDE4F");
                   execute(outMess);
                   sendMultiplicationExample(chatId);
               } else {
                   checkUserAnswer(chatId, messageText);
               }


            }
        } catch (TelegramApiException e) {
            e.printStackTrace();
            throw new RuntimeException(e);

        }

    }

    private void sendMultiplicationExample(long chatId) {
        int numb1 = (int) (Math.random() * 10) + 1;
        int numb2 = (int) (Math.random() * 10) + 1;
        int correctAnswer = numb1 * numb2;

        SendMessage message = new SendMessage();
        message.setChatId(chatId);
        message.setText(String.format("Сколько будет %d * %d?", numb1, numb2));

        DataStorage.getInstance().setCorrectAnswer(chatId, correctAnswer);

        try {
            execute(message);
        } catch (TelegramApiException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    private void checkUserAnswer(long chatId, String userAnswer) {
        Integer correctAnswer = DataStorage.getInstance().getCorrectAnswer(chatId);

        if (correctAnswer != null) {
            String responseText;
            if (userAnswer.equals(String.valueOf(correctAnswer))) {

                responseText = "Правильно! Так держать! \uD83D\uDC4D";

            } else if ("спонтанность".equalsIgnoreCase(userAnswer)) {

               responseText = "Мариночка привет\uD83D\uDE18";

            } else {
                responseText = "Неправильно. Правильный ответ: " + correctAnswer;
            }

            SendMessage message = new SendMessage();
            message.setChatId(chatId);
            message.setText(responseText);

            try {
                execute(message);
            } catch (TelegramApiException e) {
                e.printStackTrace();
                throw new RuntimeException(e);
            }

            if (userAnswer.equals(String.valueOf(correctAnswer))) {
                DataStorage.getInstance().removeCorrectAnswer(chatId);
            }

            sendMultiplicationExample(chatId);
        }
    }



    public void abc () {
//    Random random = new Random();
//    int firstNumb = random.nextInt(10) + 1;
//    int secondNumb = random.nextInt(10) + 1;
//    int result = firstNumb * secondNumb;
//    outMess.setChatId(chatId);
//    response = "Сколько будет если " + firstNumb + " * " + secondNumb + " =";
//    outMess.setText(response);
//    execute(outMess);
}



}
