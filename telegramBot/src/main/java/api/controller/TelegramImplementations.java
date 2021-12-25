package api.controller;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardRemove;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import utils.keyboards.InlineKeyboardMarkupMy;
import utils.keyboards.ReplyKeyboardMarkupMy;
import utils.user.UserService;
import utils.user.UserSettings;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Objects;
import java.util.Properties;


public class TelegramImplementations extends TelegramLongPollingBot {
    static ReplyKeyboardMarkupMy replyKeyboardMarkupMy = new ReplyKeyboardMarkupMy();
    static InlineKeyboardMarkupMy inlineKeyboardMarkupMy = new InlineKeyboardMarkupMy();
    private UserService userList = new UserService();

    String rootPath = Objects.requireNonNull(Thread.currentThread().getContextClassLoader().getResource("application.properties")).getPath();
    Properties appProps = new Properties();

    public TelegramImplementations() {

    }

    public String getName(String a) {
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
            Long userId = update.getCallbackQuery().getMessage().getChatId();
            if (!userList.isUserExists(userId)) {
                UserSettings user = userList.getUserSettings(userId);
                user.setName(update.getCallbackQuery().getMessage().getFrom().getFirstName());
                userList.setUserSettings(userId, user);
            }
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
            } else if (update.getMessage().getText().matches(".+:00") || update.getMessage().getText().equals("Выключить уведомления")) {
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
        Long userId = update.getCallbackQuery().getMessage().getChatId();
        switch (data) {
            case "Settings" -> inlineKeyboardMarkupMy.menuSettings(userId.toString());
            case "Back" -> inlineKeyboardMarkupMy.mainMenu(userId.toString());
            case "Number" -> inlineKeyboardMarkupMy.menuNumber(userId.toString());
            case "BackNum" -> inlineKeyboardMarkupMy.menuSettings(userId.toString());
            case "Bank" -> inlineKeyboardMarkupMy.menuBanks(userId.toString());
            case "BackB" -> inlineKeyboardMarkupMy.menuSettings(userId.toString());
            case "currencies" -> inlineKeyboardMarkupMy.menuCurrency(userId, userList.getUserSettings(userId).getCurrencies());
            case "EUR" -> userList.setCurrency(userId, "EUR");
            case "USD" -> userList.setCurrency(userId, "USD");
            case "RUR" -> userList.setCurrency(userId, "RUR");
            case "BackVal" -> inlineKeyboardMarkupMy.menuSettings(userId.toString());
            case "Time_of_notification" -> replyKeyboardMarkupMy.getKeyboardMarkup(userId.toString());
        }
    }


}
