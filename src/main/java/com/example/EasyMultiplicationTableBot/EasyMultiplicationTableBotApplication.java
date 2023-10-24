package com.example.EasyMultiplicationTableBot;


import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.pinnedmessages.PinChatMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendAudio;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.methods.send.SendVideo;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.ArrayList;
import java.util.List;


@SpringBootApplication
public class EasyMultiplicationTableBotApplication extends TelegramLongPollingBot {

    final private String[] phrasesRight = {"Отлично!", "Правильно!", "Молодец!", "Точно!", "Правильный ответ!", "Верно!", "Угадал(а)!", "Класс!", "Супер!", "Великолепно!", "Идеально!", "Браво!", "Гениально!", "Прекрасно!", "Ты прав(а)!", "Верный выбор!", "Совершенно верно!", "Так держать!", "Превосходно!", "Ты мастер!", "Отличная работа!", "Прямо в точку!", "Как ты их все знаешь?", "Ты знаешь ответы на всё!", "Профессионал!", "Ты гений в этом!", "Умница!", "Идеальный ответ!", "Ты на верном пути!", "Ты супер-знаток!", "Безупречно!", "Очень хорошо!", "Потрясающе!", "Ответ богов!", "Какой умница!", "Славно!", "Безошибочно!", "У тебя все получается!", "Ты умеешь это!", "Мне нравится твой ответ!", "Здорово!", "Ты умеешь это как никто другой!", "Ты гений!", "Очаровательно!", "Это ты умеешь!", "Ты просто супер!",};

    final private String[] phrasesWrong = {"Не переживай, это всего лишь шаг к успеху!", "Ошибки - это нормально, они помогают учиться.", "Ты продвигаешься вперед, даже если отвечаешь неправильно.", "Учиться - это процесс, а не моментальный результат.", "Продолжай стараться, и ты достигнешь цели!", "Каждая ошибка - шанс научиться чему-то новому.", "Ты делаешь больше, чем думаешь, просто продолжай!", "Не сдавайся, у тебя все получится!", "Важно не падать, а подниматься после неудач.", "С каждой попыткой ты приближаешься к своей цели.", "Учиться - это путь к успеху, а не соревнование.", "Ты на верном пути, даже если кажется иначе.", "Не унывай, ты сможешь преодолеть любые трудности.", "Ошибки - это возможность расти и развиваться.", "Твоя настойчивость приведет к результатам.", "Ты можешь сделать это! Просто не переставай верить в себя.", "Следующая попытка будет лучше!", "Умение учиться - это важнее, чем знание сразу всего.", "Ты уже ближе к успеху, чем думаешь.", "Не сдавайся, и ты преодолеешь все трудности!", "Ты на правильном пути, продолжай двигаться вперед.",};

    final private String[] emojiRight = {"\uD83D\uDE0E", "\uD83E\uDD73", "\uD83D\uDE09", "\uD83D\uDE07", "\uD83D\uDE42", "\uD83E\uDD29"};

    final private String[] emojiWrong = {"\uD83D\uDE4F", "\uD83D\uDC40", "\uD83E\uDD17", "\uD83D\uDCAA", "\uFE0F", "\uD83D\uDE44"};

    private int tableNumb = 10;

    private boolean isChoose = false;


    final private String BOT_TOKEN = "6322941008:AAGv0H3XZ85-zdRWq2rZFIOUf2OSsPc0CnQ";

    final private String BOT_NAME = "EasyMultiplicationTableBot";

    public int getTableNumb() {
        return tableNumb;
    }

    public void setTableNumb(int tableNumb) {
        this.tableNumb = tableNumb;
    }

    public boolean isChoose() {
        return isChoose;
    }

    public void setChoose(boolean choose) {
        isChoose = choose;
    }

    public String getPhrasesRight(String[] phraseArray, int phraseId) {
        return phraseArray[phraseId];
    }

    public String getPhrasesWrong(String[] phraseArray, int phraseId) {
        return phraseArray[phraseId];
    }

    public String getEmojiRight(String[] emojiArray, int emojiId) {
        return emojiArray[emojiId];
    }

    public String getEmojiWrong(String[] emojiArray, int emojiId) {
        return emojiArray[emojiId];
    }

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
        if (update.hasMessage() && update.getMessage().hasText()) {
            Message inMess = update.getMessage();
            long chatId = inMess.getChatId();
            String messageText = update.getMessage().getText();


            if (messageText.equals("/start")) {
                setChoose(false);
                sendUnderKeyboard(chatId);
                sendMultiplicationExample(chatId);
            } else if (messageText.equals("Показать таблицу умножения")) {
                getPinPhotoMessage(chatId, "http://saratov-pifagorka-cdn.kernelix.com/images/saratov/tabliza_umno.png");
            } else if ("Сменить режим".equalsIgnoreCase(messageText)) {
                setChoose(true);
                sendKeyboard(chatId);
            } else if ("Информация о боте".equalsIgnoreCase(messageText)) {
                getInfo(chatId);
            } else if ("Информация".equalsIgnoreCase(messageText)) {
                sendInfoAboutDeveloper(chatId);
            } else {
                checkUserAnswer(chatId, messageText);
            }
        } else if (update.hasCallbackQuery()) {

            CallbackQuery callbackQuery = update.getCallbackQuery();
            String callBackData = callbackQuery.getData();
            long chatId = callbackQuery.getMessage().getChatId();
            setChoose(true);

            if ("1".equals(callBackData)) {
                chooseTableNumber(chatId, 1);
            } else if (callBackData.equals("2")) {
                chooseTableNumber(chatId, 2);
            } else if (callBackData.equals("3")) {
                chooseTableNumber(chatId, 3);
            } else if (callBackData.equals("4")) {
                chooseTableNumber(chatId, 4);
            } else if (callBackData.equals("5")) {
                chooseTableNumber(chatId, 5);
            } else if (callBackData.equals("6")) {
                chooseTableNumber(chatId, 6);
            } else if (callBackData.equals("7")) {
                chooseTableNumber(chatId, 7);
            } else if (callBackData.equals("8")) {
                chooseTableNumber(chatId, 8);
            } else if (callBackData.equals("9")) {
                chooseTableNumber(chatId, 9);
            } else if (callBackData.equals("10")) {
                chooseTableNumber(chatId, 10);
            } else if (callBackData.equals("11")) {
                setChoose(false);
                sendMultiplicationExample(chatId);
            }


        }

    }


    private void sendMultiplicationExample(long chatId) {
        int firstNumber;
        int secondNumber;
        if (isChoose) {
            firstNumber = getTableNumb();
            secondNumber = getRandomNumber();
            multiplicationOfNumbers(chatId, firstNumber, secondNumber);
        } else {
            firstNumber = getRandomNumber();
            secondNumber = getRandomNumber();
            multiplicationOfNumbers(chatId, firstNumber, secondNumber);
        }

        sendTextMessage(chatId, String.format("Сколько будет %d * %d?", firstNumber, secondNumber));

    }

    private int getRandomNumber() {
        return (int) (Math.random() * 10) + 1;
    }

    private void multiplicationOfNumbers(long chatId, int firstNumber, int secondNumber) {
        int correctAnswer = firstNumber * secondNumber;
        DataStorage.getInstance().setCorrectAnswer(chatId, correctAnswer);
    }

    private void chooseTableNumber(long chatId, int tableNumb) {
        switch (tableNumb) {
            case 1:
                setTableNumb(1);
                sendMultiplicationExample(chatId);
                break;
            case 2:
                setTableNumb(2);
                sendMultiplicationExample(chatId);
                break;
            case 3:
                setTableNumb(3);
                sendMultiplicationExample(chatId);
                break;
            case 4:
                setTableNumb(4);
                sendMultiplicationExample(chatId);
                break;
            case 5:
                setTableNumb(5);
                sendMultiplicationExample(chatId);
                break;
            case 6:
                setTableNumb(6);
                sendMultiplicationExample(chatId);
                break;
            case 7:
                setTableNumb(7);
                sendMultiplicationExample(chatId);
                break;
            case 8:
                setTableNumb(8);
                sendMultiplicationExample(chatId);
                break;
            case 9:
                setTableNumb(9);
                sendMultiplicationExample(chatId);
                break;
            case 10:
                setTableNumb(10);
                sendMultiplicationExample(chatId);
                break;
            default:
                setChoose(false);
                sendTextMessage(chatId, "Твоё число не входит в рамки Таблицы умножения.\uD83D\uDE1D\n Допустимы числа от 1 до 10.\uD83D\uDE07 Повтори команду с числом от 1 до 10\uD83E\uDD1D");
                break;
        }
    }

    private void checkUserAnswer(long chatId, String userAnswer) {
        Integer correctAnswer = DataStorage.getInstance().getCorrectAnswer(chatId);

        if (correctAnswer != null) {
            if (userAnswer.equals(String.valueOf(correctAnswer))) {

                sendTextMessage(chatId, String.format("Правильно!\n%s! %s", getPhrasesRight(phrasesRight, (int) (Math.random() * phrasesRight.length)),
                        getEmojiRight(emojiRight, (int) (Math.random() * emojiRight.length))));

            } else if ("спонтанность".equalsIgnoreCase(userAnswer)) {

                sendTextMessage(chatId, "Мариночка привет\uD83D\uDE18");

            } else if ("мера".equalsIgnoreCase(userAnswer)) {
                sendAudio(chatId, "https://mp3uk.net/mp3/files/basta-kavabanga-mp3.mp3");
                sendTextMessage(chatId, "Мариночка, песня по твоему заказу\uD83D\uDE01");
            } else if ("Море".equalsIgnoreCase(userAnswer)) {
                sendTextMessage(chatId,"Марина приветик \uD83D\uDE18");
                sendTextMessage(chatId, "Поздравляю с нахождением очередной \"Пасхалки\" \uD83E\uDD73");
                sendPhoto(chatId,"https://цветочныйрай.рф/upload/medialibrary/db1/db194e41e04ee990aa3b6efe709f2404.png");
                sendAudio(chatId, "https://mp3uk.net/mp3/files/basta-daria-yanina-zazhigat-live-mp3.mp3");
                sendTextMessage(chatId, "Отличного дня Марин \uD83E\uDD17");
            }
            else {

                sendTextMessage(chatId, String.format("Неправильно. Правильный ответ: %d \n" + "%s%s", correctAnswer, getPhrasesWrong(phrasesWrong, (int) (Math.random() * phrasesWrong.length)),
                        getEmojiWrong(emojiWrong, (int) (Math.random() * emojiWrong.length))));

            }
            if (userAnswer.equals(String.valueOf(correctAnswer))) {

                DataStorage.getInstance().removeCorrectAnswer(chatId);
            }

            sendMultiplicationExample(chatId);
        }
    }

    private void sendTextMessage(long chatId, String textMessage) {
        SendMessage message = new SendMessage();
        message.setChatId(chatId);
        message.setText(textMessage);
        try {
            execute(message);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }

    private void sendAudio(long chatId, String pathToAudioFile) {
        SendAudio sendAudio = new SendAudio();
        sendAudio.setChatId(String.valueOf(chatId));
        InputFile audio = new InputFile(pathToAudioFile);
        sendAudio.setAudio(audio);
        try {
            execute(sendAudio);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }

    private void sendVideo(long chatId, String pathToVideoFile) {
        SendVideo sendVideo = new SendVideo();
        sendVideo.setChatId(chatId);
        InputFile video = new InputFile(pathToVideoFile);
        sendVideo.setVideo(video);
        try {
            execute(sendVideo);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }

    private void sendPhoto(long chatId, String pathToPhotoFile) {
        SendPhoto sendPhoto = new SendPhoto();
        sendPhoto.setChatId(chatId);
        InputFile video = new InputFile(pathToPhotoFile);
        sendPhoto.setPhoto(video);
        try {
            execute(sendPhoto);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }

    private void getPinPhotoMessage(long chatId, String pathToPhotoFile) {
        SendPhoto sendPhoto = new SendPhoto();
        sendPhoto.setChatId(chatId);
        sendPhoto.setPhoto(new InputFile(pathToPhotoFile));
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

    private void getInfo(long chatId) {
        sendTextMessage(chatId, "Как управлять режимами бота:\n\n" +
                "1. Отвечайте на примеры бота, просто вводите свой ответ определенным числом.\n\n" +
                "2. Под областью ввода сообщения есть меню при помощи которого можно получить информацию о боте, попросить прислать таблицу умножения прямо в ваш чат, сменить режим генерации - нажав на кнопку \"Сменить режим\", и информацию и контакты разработчика.");
    }

    private void sendKeyboard(long chatId) {
        SendMessage message = new SendMessage();
        message.setChatId(chatId);
        message.setText("Выбери на какое число будем умножать:");

// Создайте инлайн-кнопку и добавьте ее к сообщению
        InlineKeyboardMarkup markup = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> keyboard = new ArrayList<>();
        List<InlineKeyboardButton> row = new ArrayList<>();
        List<InlineKeyboardButton> row2 = new ArrayList<>();
        List<InlineKeyboardButton> row3 = new ArrayList<>();
        List<InlineKeyboardButton> row4 = new ArrayList<>();
        List<InlineKeyboardButton> row5 = new ArrayList<>();
        List<InlineKeyboardButton> row6 = new ArrayList<>();

        for (int i = 1; i <= 2; i++) {
            InlineKeyboardButton inlineKeyboardButton = new InlineKeyboardButton();
            inlineKeyboardButton.setText("Умножать на " + i);
            inlineKeyboardButton.setCallbackData(String.valueOf(i));
            row.add(inlineKeyboardButton);
        }

        for (int i = 3; i <= 4; i++) {
            InlineKeyboardButton inlineKeyboardButton = new InlineKeyboardButton();
            inlineKeyboardButton.setText("Умножать на " + i);
            inlineKeyboardButton.setCallbackData(String.valueOf(i));
            row2.add(inlineKeyboardButton);
        }

        for (int i = 5; i <= 6; i++) {
            InlineKeyboardButton inlineKeyboardButton = new InlineKeyboardButton();
            inlineKeyboardButton.setText("Умножать на " + i);
            inlineKeyboardButton.setCallbackData(String.valueOf(i));
            row3.add(inlineKeyboardButton);
        }

        for (int i = 7; i <= 8; i++) {
            InlineKeyboardButton inlineKeyboardButton = new InlineKeyboardButton();
            inlineKeyboardButton.setText("Умножать на " + i);
            inlineKeyboardButton.setCallbackData(String.valueOf(i));
            row4.add(inlineKeyboardButton);
        }

        for (int i = 9; i <= 10; i++) {
            InlineKeyboardButton inlineKeyboardButton = new InlineKeyboardButton();
            inlineKeyboardButton.setText("Умножать на " + i);
            inlineKeyboardButton.setCallbackData(String.valueOf(i));
            row5.add(inlineKeyboardButton);
        }

        InlineKeyboardButton inlineKeyboardButton = new InlineKeyboardButton();
        inlineKeyboardButton.setText("Умножать все числа ");
        inlineKeyboardButton.setCallbackData(String.valueOf(11));
        row6.add(inlineKeyboardButton);


// Добавьте кнопки в строку
        keyboard.add(row);
        keyboard.add(row2);
        keyboard.add(row3);
        keyboard.add(row4);
        keyboard.add(row5);
        keyboard.add(row6);

        markup.setKeyboard(keyboard);
        message.setReplyMarkup(markup);

        try {
            execute(message);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }

    }

    private void sendUnderKeyboard(long chatId) {
        SendMessage message = new SendMessage();
        message.setChatId(chatId);
        message.setText("\"Приветствую тебя юный ученик, ты здесь, чтобы изучить таблицу умножения.\uD83D\uDE09 Давай приступим к изучению. Желаю тебе успехов в учебе.\uD83D\uDE4F\n\n\n" +
                "Как управлять режимами бота:\n\n" +
                "1. Отвечайте на примеры бота, просто вводите свой ответ определенным числом.\n\n" +
                "2. Под областью ввода сообщения есть меню при помощи которого можно получить информацию о боте, попросить прислать таблицу умножения прямо в ваш чат, сменить режим генерации - нажав на кнопку \"Сменить режим\", и информацию и контакты разработчика.");

// Создайте клавиатуру и инициализируйте ее
        ReplyKeyboardMarkup keyboardMarkup = new ReplyKeyboardMarkup();
        keyboardMarkup.setOneTimeKeyboard(false); // Можно сделать, чтобы клавиатура скрывалась после нажатия
        keyboardMarkup.setResizeKeyboard(true); // Устанавливаем resizeKeyboard в true

// Создайте строку клавиатуры и добавьте кнопки
        KeyboardRow row = new KeyboardRow();
        KeyboardRow row2 = new KeyboardRow();
        KeyboardButton button = new KeyboardButton("Показать таблицу умножения");
        KeyboardButton button2 = new KeyboardButton("Информация");
        KeyboardButton button3 = new KeyboardButton("Сменить режим");
        KeyboardButton button4 = new KeyboardButton("Информация о боте");

        row.add(button);
        row.add(button3);
        row2.add(button2);
        row2.add(button4);

// Добавьте строки в клавиатуру
        List<KeyboardRow> keyboardRowList = new ArrayList<>();
        keyboardRowList.add(row);
        keyboardRowList.add(row2);
        keyboardMarkup.setKeyboard(keyboardRowList); // Используйте List.of() для создания списка

// Установите клавиатуру в сообщение
        message.setReplyMarkup(keyboardMarkup);

        try {
            execute(message);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    private void sendInfoAboutDeveloper (long chatId) {
        sendTextMessage(chatId, "Если вы обнаружили ошибку или у вас есть предложения ,пишите в телеграм или по адресу электронной почты");
        sendTextMessage(chatId, "Телеграм - @PRV27\n" +
                                           "Email - Roman@Pantiukhov.ru");
    }

    private void newMethod() {

    }

}
