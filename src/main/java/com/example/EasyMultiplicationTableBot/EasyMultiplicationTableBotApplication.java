package com.example.EasyMultiplicationTableBot;


import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.pinnedmessages.PinChatMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@SpringBootApplication
public class EasyMultiplicationTableBotApplication extends TelegramLongPollingBot {

    final private String[] phrasesRight = {"Отлично!", "Правильно!", "Молодец!", "Точно!", "Правильный ответ!", "Верно!", "Угадал(а)!", "Класс!", "Супер!", "Великолепно!", "Идеально!", "Браво!", "Гениально!", "Прекрасно!", "Ты прав(а)!", "Верный выбор!", "Совершенно верно!", "Так держать!", "Превосходно!", "Ты мастер!", "Отличная работа!", "Прямо в точку!", "Как ты их все знаешь?", "Ты знаешь ответы на всё!", "Профессионал!", "Ты гений в этом!", "Умница!", "Идеальный ответ!", "Ты на верном пути!", "Ты супер-знаток!", "Безупречно!", "Очень хорошо!", "Потрясающе!", "Ответ богов!", "Какой умница!", "Славно!", "Безошибочно!", "У тебя все получается!", "Ты умеешь это!", "Мне нравится твой ответ!", "Здорово!", "Ты умеешь это как никто другой!", "Ты гений!", "Очаровательно!", "Это ты умеешь!", "Ты просто супер!",};

    final private String[] phrasesWrong = {"Не переживай, это всего лишь шаг к успеху!", "Ошибки - это нормально, они помогают учиться.", "Ты продвигаешься вперед, даже если отвечаешь неправильно.", "Учиться - это процесс, а не моментальный результат.", "Продолжай стараться, и ты достигнешь цели!", "Каждая ошибка - шанс научиться чему-то новому.", "Ты делаешь больше, чем думаешь, просто продолжай!", "Не сдавайся, у тебя все получится!", "Важно не падать, а подниматься после неудач.", "С каждой попыткой ты приближаешься к своей цели.", "Учиться - это путь к успеху, а не соревнование.", "Ты на верном пути, даже если кажется иначе.", "Не унывай, ты сможешь преодолеть любые трудности.", "Ошибки - это возможность расти и развиваться.", "Твоя настойчивость приведет к результатам.", "Ты можешь сделать это! Просто не переставай верить в себя.", "Следующая попытка будет лучше!", "Умение учиться - это важнее, чем знание сразу всего.", "Ты уже ближе к успеху, чем думаешь.", "Не сдавайся, и ты преодолеешь все трудности!", "Ты на правильном пути, продолжай двигаться вперед.",};

    final private String[] emojiRight = {"\uD83D\uDE0E", "\uD83E\uDD73", "\uD83D\uDE09", "\uD83D\uDE07", "\uD83D\uDE42", "\uD83E\uDD29"};

    final private String[] emojiWrong = {"\uD83D\uDE4F", "\uD83D\uDC40", "\uD83E\uDD17", "\uD83D\uDCAA", "\uFE0F", "\uD83D\uDE44"};

    private int tableNumb = 10;

    private boolean isChoose = false;

    final private String BOT_TOKEN ="6376884410:AAGlXB4bjoGzIvGtwXI9Nlv2MfEEs6vKDOI";//"6322941008:AAGv0H3XZ85-zdRWq2rZFIOUf2OSsPc0CnQ";
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
                    outMess.setText("Приветствую тебя юный ученик, ты здесь, чтобы изучить таблицу умножения.\uD83D\uDE09 Давай приступим к изучению. Желаю тебе успехов в учебе.\uD83D\uDE4F\n\n\n" +
                            "Как управлять режимами бота:\n" +
                            "1. Введите команду \"Умножаем на (укажите своё число от 1 до 10, без скобок)\".\n" +
                            "2. Введите команду \"Умножаем всё(е)\".");
                    execute(outMess);
                    isChoose = false;
                    sendMultiplicationExample(chatId);


                } else if (messageText.equals("/table")) {
                    getPinMessage(chatId);
                } else if (messageText.contains("Умножаем на") || messageText.contains("умножаем на")) {

                    isChoose = true;
                    String[] userInput = messageText.trim().split(" ");

                    switch (Integer.parseInt(userInput[2])) {
                        case 1:
                            tableNumb = 1;
                            sendMultiplicationExample(chatId);
                            break;
                        case 2:
                            tableNumb = 2;
                            sendMultiplicationExample(chatId);
                            break;
                        case 3:
                            tableNumb = 3;
                            sendMultiplicationExample(chatId);
                            break;
                        case 4:
                            tableNumb = 4;
                            sendMultiplicationExample(chatId);
                            break;
                        case 5:
                            tableNumb = 5;
                            sendMultiplicationExample(chatId);
                            break;
                        case 6:
                            tableNumb = 6;
                            sendMultiplicationExample(chatId);
                            break;
                        case 7:
                            tableNumb = 7;
                            sendMultiplicationExample(chatId);
                            break;
                        case 8:
                            tableNumb = 8;
                            sendMultiplicationExample(chatId);
                            break;
                        case 9:
                            tableNumb = 9;
                            sendMultiplicationExample(chatId);
                            break;
                        case 10:
                            tableNumb = 10;
                            sendMultiplicationExample(chatId);
                            break;
                        default:
                            isChoose = false;
                            outMess.setChatId(chatId);
                            outMess.setText("Твоё число не входит в рамки Таблицы умножения.\uD83D\uDE1D\n Допустимы числа от 1 до 10.\uD83D\uDE07 Повтори команду с числом от 1 до 10\uD83E\uDD1D");
                            execute(outMess);
                            break;
                    }


                } else if ("Умножаем всё".equalsIgnoreCase(messageText) || "Умножаем все".equalsIgnoreCase(messageText)) {
                    isChoose = false;
                    sendMultiplicationExample(chatId);
                } else if ("/info".equalsIgnoreCase(messageText)) {
                    getInfo(chatId);
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
        int numb1;
        int numb2;
        int correctAnswer;
        if (isChoose) {
            numb1 = tableNumb;
            numb2 = (int) (Math.random() * 10) + 1;
            correctAnswer = numb1 * numb2;
        } else {
            numb1 = (int) (Math.random() * 10) + 1;
            numb2 = (int) (Math.random() * 10) + 1;
            correctAnswer = numb1 * numb2;
        }


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

                responseText = String.format("Правильно!\n %s! %s", phrasesRight[(int) (Math.random() * phrasesRight.length)], emojiRight[(int) (Math.random() * emojiRight.length)]);

            } else if ("спонтанность".equalsIgnoreCase(userAnswer)) {

                responseText = "Мариночка привет\uD83D\uDE18";

            } else {
                responseText = String.format("Неправильно. Правильный ответ: %d \n" + "%s%s", correctAnswer, phrasesWrong[(int) (Math.random() * phrasesWrong.length)], emojiWrong[(int) (Math.random() * emojiWrong.length)]);
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


    private void getPinMessage(long chatId) {
        SendPhoto sendPhoto = new SendPhoto();
        sendPhoto.setChatId(chatId);
        sendPhoto.setPhoto(new InputFile("http://saratov-pifagorka-cdn.kernelix.com/images/saratov/tabliza_umno.png"));
        try {
            Message sentImageMessage = execute(sendPhoto);

            PinChatMessage pinMessage = new PinChatMessage();
            pinMessage.setChatId(chatId);
            pinMessage.setMessageId(sentImageMessage.getMessageId());
            execute(pinMessage);


        } catch (TelegramApiException e) {
            e.printStackTrace();

        }
    }

    private void getInfo(long chatId) throws TelegramApiException {
        SendMessage outMess = new SendMessage();
        outMess.setChatId(chatId);
        outMess.setText("Как управлять режимами бота:\n" +
                "1. Введите команду \"Умножаем на (укажите своё число от 1 до 10, без скобок)\".\n" +
                "2. Введите команду \"Умножаем всё(е)\".");
        execute(outMess);

    }

}
