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
import java.util.*;


public class TelegramImplementations extends TelegramLongPollingBot {
    static ReplyKeyboardMarkupMy replyKeyboardMarkupMy = new ReplyKeyboardMarkupMy();
    static InlineKeyboardMarkupMy inlineKeyboardMarkupMy = new InlineKeyboardMarkupMy();
    private UserService userList = new UserService();

    String rootPath = Objects.requireNonNull(Thread.currentThread().getContextClassLoader().getResource("application.properties")).getPath();
    Properties appProps = new Properties();

    public TelegramImplementations() {
        UserService.getInstance();
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
                user.setName(update.getCallbackQuery().getMessage().getChat().getUserName());
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
        Integer messageId = update.getCallbackQuery().getMessage().getMessageId();

        switch (data) {
            case "Settings" -> inlineKeyboardMarkupMy.menuSettings(userId.toString());
            case "Back" -> inlineKeyboardMarkupMy.mainMenu(userId.toString());
            case "Number" -> inlineKeyboardMarkupMy.menuNumber(userId, false, messageId, userList.getUserSettings(userId).getRoundAccuracy());
            case "accuracy:2" -> {
                    userList.getUserSettings(userId).setRoundAccuracy(2);
                inlineKeyboardMarkupMy.menuNumber(userId, true, messageId, userList.getUserSettings(userId).getRoundAccuracy());
            }
            case "accuracy:3" -> {
                    userList.getUserSettings(userId).setRoundAccuracy(3);
                inlineKeyboardMarkupMy.menuNumber(userId, true, messageId, userList.getUserSettings(userId).getRoundAccuracy());
            }
            case "accuracy:4" -> {
                    userList.getUserSettings(userId).setRoundAccuracy(4);
                inlineKeyboardMarkupMy.menuNumber(userId, true, messageId, userList.getUserSettings(userId).getRoundAccuracy());
            }
            case "BackNum" -> inlineKeyboardMarkupMy.menuSettings(userId.toString());
            case "Bank" -> inlineKeyboardMarkupMy.menuBanks(userId, false, messageId, userList.getUserSettings(userId).getBankList());
            case "PRIVATBANK" -> {
                if (userList.getUserSettings(userId).getBankList().stream().filter(e -> e.getCommand() == "PRIVATBANK").count() > 0
                        && userList.getUserSettings(userId).getBankList().size() > 1) {
                    userList.unSetBank(userId, "PRIVATBANK");
                } else {
                    userList.setBank(userId, "PRIVATBANK");
                }
                inlineKeyboardMarkupMy.menuBanks(userId, true, messageId, userList.getUserSettings(userId).getBankList());
            }
            case "MONOBANK" -> {
                if (userList.getUserSettings(userId).getBankList().stream().filter(e -> e.getCommand() == "MONOBANK").count() > 0
                        && userList.getUserSettings(userId).getBankList().size() > 1) {
                    userList.unSetBank(userId, "MONOBANK");
                } else {
                    userList.setBank(userId, "MONOBANK");
                }
                inlineKeyboardMarkupMy.menuBanks(userId, true, messageId, userList.getUserSettings(userId).getBankList());
            }
            case "NBU" -> {
                if (userList.getUserSettings(userId).getBankList().stream().filter(e -> e.getCommand() == "NBU").count() > 0
                        && userList.getUserSettings(userId).getBankList().size() > 1) {
                    userList.unSetBank(userId, "NBU");
                } else {
                    userList.setBank(userId, "NBU");
                }
                inlineKeyboardMarkupMy.menuBanks(userId, true, messageId, userList.getUserSettings(userId).getBankList());
            }
            case "BackB" -> inlineKeyboardMarkupMy.menuSettings(userId.toString());
            case "currencies" -> inlineKeyboardMarkupMy.menuCurrency(userId, true, messageId, userList.getUserSettings(userId).getCurrencies());
            case "EUR" -> {
                if (userList.getUserSettings(userId).getCurrencies().stream().filter(e -> e.getCommand() == "EUR").count() > 0
                        && userList.getUserSettings(userId).getCurrencies().size() > 1) {
                    userList.unSetCurrency(userId, "EUR");
                } else {
                    userList.setCurrency(userId, "EUR");
                }
                inlineKeyboardMarkupMy.menuCurrency(userId, true, messageId, userList.getUserSettings(userId).getCurrencies());
            }
            case "USD" -> {
                if (userList.getUserSettings(userId).getCurrencies().stream().filter(e -> e.getCommand() == "USD").count() > 0
                        && userList.getUserSettings(userId).getCurrencies().size() > 1) {
                    userList.unSetCurrency(userId, "USD");
                } else {
                    userList.setCurrency(userId, "USD");
                }
                inlineKeyboardMarkupMy.menuCurrency(userId, true, messageId, userList.getUserSettings(userId).getCurrencies());
            }
            case "RUR" -> {
                if (userList.getUserSettings(userId).getCurrencies().stream().filter(e -> e.getCommand() == "RUR").count() > 0
                        && userList.getUserSettings(userId).getCurrencies().size() > 1) {
                    userList.unSetCurrency(userId, "RUR");
                } else {
                    userList.setCurrency(userId, "RUR");
                }
                inlineKeyboardMarkupMy.menuCurrency(userId, true, messageId, userList.getUserSettings(userId).getCurrencies());
            }
            case "BackVal" -> inlineKeyboardMarkupMy.menuSettings(userId.toString());
            case "Time_of_notification" -> replyKeyboardMarkupMy.getKeyboardMarkup(userId.toString());
        }
    }
}
