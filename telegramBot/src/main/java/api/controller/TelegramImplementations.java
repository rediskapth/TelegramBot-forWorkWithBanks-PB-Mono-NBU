package api.controller;

import api.Facade;
import api.bank.BankResponce;
import api.bank.Banks;
import api.bank.CurrencyNames;
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
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;
import java.util.stream.Collectors;


public class TelegramImplementations extends TelegramLongPollingBot {
    static ReplyKeyboardMarkupMy replyKeyboardMarkupMy = new ReplyKeyboardMarkupMy();
    static InlineKeyboardMarkupMy inlineKeyboardMarkupMy = new InlineKeyboardMarkupMy();
    //   private UserService userList;// = new UserService();
    private final UserService userService;

    String rootPath = Objects.requireNonNull(Thread.currentThread().getContextClassLoader().getResource("r.properties")).getPath();
    Properties appProps = new Properties();

    public TelegramImplementations() {
        userService = UserService.getInstance();
        //Facade bankResponce = new Facade;
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
            if (!userService.isUserExists(userId)) {
                // user = userList.getUserSettings(userId);
                // userList.getUserSettings(userId).setName(update.getCallbackQuery().getMessage().getChat().getUserName());
                UserSettings userSettings = userService.getUserSettings(userId);
                userService.setUserSettings(userId, userSettings);
                userService.getUserSettings(userId).setName(update.getCallbackQuery().getMessage().getChat().getUserName());
            }
            try {
                pressingTheButton(update);
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
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

    private void pressingTheButton(Update update) throws TelegramApiException {
        String data = update.getCallbackQuery().getData();
        Long userId = update.getCallbackQuery().getMessage().getChatId();
        Integer messageId = update.getCallbackQuery().getMessage().getMessageId();

        switch (data) {
            case "Settings" -> {
                inlineKeyboardMarkupMy.menuSettings(userId.toString());
                break;
            }

            case "Back" -> {
                inlineKeyboardMarkupMy.mainMenu(userId.toString());
                break;
            }

            case "GetInfo" -> {
                try {
                    execute(SendMessage.builder()
                            .text(sendInfo(userService.getUserSettings(userId)))
                            .chatId(userId.toString())
                            .build());
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                break;
            }

            case "Number" -> {
                inlineKeyboardMarkupMy.menuNumber(userId, false, messageId, userService.getUserSettings(userId).getRoundAccuracy());
                break;
            }

            case "accuracy:2" -> {
                userService.setAccuracy(userId, 2);
                inlineKeyboardMarkupMy.menuNumber(userId, true, messageId, userService.getUserSettings(userId).getRoundAccuracy());
                break;
            }

            case "accuracy:3" -> {
                userService.setAccuracy(userId, 3);
                inlineKeyboardMarkupMy.menuNumber(userId, true, messageId, userService.getUserSettings(userId).getRoundAccuracy());
                break;
            }

            case "accuracy:4" -> {
                userService.setAccuracy(userId, 4);
                inlineKeyboardMarkupMy.menuNumber(userId, true, messageId, userService.getUserSettings(userId).getRoundAccuracy());
                break;
            }

            case "BackNum" -> {
                inlineKeyboardMarkupMy.menuSettings(userId.toString());
                break;
            }
            case "Bank" -> {
                inlineKeyboardMarkupMy.menuBanks(userId, false, messageId, userService.getUserSettings(userId).getBankList());
                break;
            }
            case "PRIVATBANK" -> {
                if (userService.getUserSettings(userId).getBankList().stream().filter(e -> e.getCommand() == "PRIVATBANK").count() > 0
                        && userService.getUserSettings(userId).getBankList().size() > 1) {
                    userService.unSetBank(userId, "PRIVATBANK");
                } else {
                    userService.setBank(userId, "PRIVATBANK");
                }
                inlineKeyboardMarkupMy.menuBanks(userId, true, messageId, userService.getUserSettings(userId).getBankList());
                break;
            }
            case "MONOBANK" -> {
                if (userService.getUserSettings(userId).getBankList().stream().filter(e -> e.getCommand().equals("MONOBANK")).count() > 0
                        && userService.getUserSettings(userId).getBankList().size() > 1) {
                    userService.unSetBank(userId, "MONOBANK");
                } else {
                    userService.setBank(userId, "MONOBANK");
                }
                inlineKeyboardMarkupMy.menuBanks(userId, true, messageId, userService.getUserSettings(userId).getBankList());
                break;
            }
            case "NBU" -> {
                if (userService.getUserSettings(userId).getBankList().stream().filter(e -> e.getCommand() == "NBU").count() > 0
                        && userService.getUserSettings(userId).getBankList().size() > 1) {
                    userService.unSetBank(userId, "NBU");
                } else {
                    userService.setBank(userId, "NBU");
                }
                inlineKeyboardMarkupMy.menuBanks(userId, true, messageId, userService.getUserSettings(userId).getBankList());
                break;
            }
            case "BackB" -> {
                inlineKeyboardMarkupMy.menuSettings(userId.toString());
                break;
            }
            case "currencies" -> {
                inlineKeyboardMarkupMy.menuCurrency(userId, true, messageId, userService.getUserSettings(userId).getCurrencies());
                break;
            }
            case "EUR" -> {
                if (userService.getUserSettings(userId).getCurrencies().stream().filter(e -> e.getCommand() == "EUR").count() > 0
                        && userService.getUserSettings(userId).getCurrencies().size() > 1) {
                    userService.unSetCurrency(userId, "EUR");
                } else {
                    userService.setCurrency(userId, "EUR");
                }
                inlineKeyboardMarkupMy.menuCurrency(userId, true, messageId, userService.getUserSettings(userId).getCurrencies());
                break;
            }
            case "USD" -> {
                if (userService.getUserSettings(userId).getCurrencies().stream().filter(e -> e.getCommand() == "USD").count() > 0
                        && userService.getUserSettings(userId).getCurrencies().size() > 1) {
                    userService.unSetCurrency(userId, "USD");
                } else {
                    userService.setCurrency(userId, "USD");
                }
                inlineKeyboardMarkupMy.menuCurrency(userId, true, messageId, userService.getUserSettings(userId).getCurrencies());
                break;
            }
            case "RUR" -> {
                if (userService.getUserSettings(userId).getCurrencies().stream().filter(e -> e.getCommand() == "RUR").count() > 0
                        && userService.getUserSettings(userId).getCurrencies().size() > 1) {
                    userService.unSetCurrency(userId, "RUR");
                } else {
                    userService.setCurrency(userId, "RUR");
                }
                inlineKeyboardMarkupMy.menuCurrency(userId, true, messageId, userService.getUserSettings(userId).getCurrencies());
                break;
            }
            case "BackVal" -> {
                inlineKeyboardMarkupMy.menuSettings(userId.toString());
                break;
            }
            case "Time_of_notification" -> {replyKeyboardMarkupMy.getKeyboardMarkup(userId.toString());
                break;}
        }
    }
    public String sendInfo(UserSettings userSettings) throws IOException, InterruptedException {
        Facade facade = new Facade();

        final HashSet<Banks> bankList = userSettings.getBankList();
        System.out.println(bankList.toString());

        final HashSet<CurrencyNames> currencies = userSettings.getCurrencies();
        System.out.println(currencies.toString());
        final int roundAccuracy=userSettings.getRoundAccuracy();
        System.out.println(roundAccuracy);
        int notifyHour = userSettings.getNotifyHour();
        System.out.println(notifyHour);
        StringBuilder result = new StringBuilder();

        for (Banks banks : bankList) {
            List<BankResponce> bankInfo = facade.getBankInfo(banks.getName());
result.append("Курс в: "+banks.getCommand()+"\n");

            for (CurrencyNames currency : currencies) {
                List<BankResponce> collect = bankInfo.stream()
                        .filter(cur -> currency.getCommand().equals(cur.getCurrency()))
                        .collect(Collectors.toList());


                for (BankResponce responce : collect) {
                    result.append( responce.getCurrency() + " Покупка: " + getPosle(responce.getBuy(),roundAccuracy)+" Продажа:"  + getPosle(responce.getSale(),roundAccuracy) + "\n");
                }
                System.out.println(collect);
            }

        }

        return result.toString();
    }
    private BigDecimal getPosle(float t,int roundAccuracy){

        BigDecimal value = new BigDecimal(Float.toString(t));
        BigDecimal bigDecimal = value.setScale(roundAccuracy, RoundingMode.DOWN);
        return bigDecimal;
    }
}