package api.controller;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardRemove;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import utils.keyboards.InlineKeyboardMarkupMy;
import utils.keyboards.ReplyKeyboardMarkupMy;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Objects;
import java.util.Properties;


public class TelegramImplementations extends TelegramLongPollingBot {
     static    ReplyKeyboardMarkupMy replyKeyboardMarkupMy=new ReplyKeyboardMarkupMy();
      static   InlineKeyboardMarkupMy inlineKeyboardMarkupMy = new InlineKeyboardMarkupMy();

        String rootPath = Objects.requireNonNull(Thread.currentThread().getContextClassLoader().getResource("application.properties")).getPath();
        Properties appProps=new Properties();
    public String getName(String a)  {
        try {
            appProps.load(new FileInputStream(rootPath));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return appProps.getProperty(a);
    }

        @Override
    public String getBotUsername() {
                return getName("bot.username");
    }

    @Override
    public String getBotToken() {
                 return getName("bot.token");
    }

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasCallbackQuery()) {
            pressingTheButton(update);

        } else if (update.hasMessage()) {
            messageHandler(update);
        }
    }


    private void messageHandler(Update update) {
        InlineKeyboardMarkupMy inlineKeyboardMarkupMy = new InlineKeyboardMarkupMy();
        if (update.getMessage().hasText()) {

            String text = update.getMessage().getText();
            String chatUserId = update.getMessage().getChatId().toString();

            if (text.equals("/start")) {
                inlineKeyboardMarkupMy.mainMenu(chatUserId);
            }else if (update.getMessage().getText().matches(".+:00") || update.getMessage().getText().equals("Выключить уведомления")) {
                inlineKeyboardMarkupMy.menuSettings(chatUserId);
                ReplyKeyboardRemove keyboardMarkup = ReplyKeyboardRemove.builder().removeKeyboard(true).build();
                try {
                    executeAsync(
                            SendMessage.builder()
                                    .text(update.getMessage().getText())
                                    .chatId(chatUserId)
                                    .replyMarkup(keyboardMarkup)
                                    .build());
                } catch (TelegramApiException e) {
                    e.printStackTrace();

                }
            }
        }
    }

    private void pressingTheButton(Update update) {
        String data = update.getCallbackQuery().getData();
        String chatUserId = update.getCallbackQuery().getMessage().getChatId().toString();
        switch (data) {
            case "Settings" -> inlineKeyboardMarkupMy.menuSettings(chatUserId);
            case "Back" -> inlineKeyboardMarkupMy.mainMenu(chatUserId);
            case "Number" -> inlineKeyboardMarkupMy.menuNumber(chatUserId);
            case "BackNum" -> inlineKeyboardMarkupMy.menuSettings(chatUserId);
            case "Bank" -> inlineKeyboardMarkupMy.menuBanks(chatUserId);
            case "BackB" -> inlineKeyboardMarkupMy.menuSettings(chatUserId);
            case "currencies" -> inlineKeyboardMarkupMy.menuCurrency(chatUserId);
            case "BackVal" -> inlineKeyboardMarkupMy.menuSettings(chatUserId);
            case"Time_of_notification"->replyKeyboardMarkupMy.getKeyboardMarkup(chatUserId);
        }
    }


}
