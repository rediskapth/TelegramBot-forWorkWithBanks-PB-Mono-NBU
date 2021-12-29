package api.controller;

import api.CashingService;
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
    Facade facade = new Facade();

    String rootPath = Objects.requireNonNull(Thread.currentThread().getContextClassLoader().getResource("application.properties")).getPath();
    Properties appProps = new Properties();

    public TelegramImplementations() {
        userService = UserService.getInstance();
        //Facade bankResponce = new Facade;

    }

//    public String getName(String a) {
//        try {
//            appProps.load(new FileInputStream(rootPath));
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        return appProps.getProperty(a);
//    }

    @Override
    public String getBotUsername() {
        return "Telebot2021bot";
    }

    @Override
    public String getBotToken() {
        return "5015821414:AAHhRmcO5vPUcrjERPkqqPrZ-oyy6CNLxw8";
    }

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasCallbackQuery()) {
            Long userId = update.getCallbackQuery().getMessage().getChatId();
            if (!userService.isUserExists(userId)) {
                UserSettings userSettings = userService.getUserSettings(userId);
                userSettings.setName(update.getCallbackQuery().getMessage().getChat().getUserName());
                userService.setUserSettings(userId, userSettings);
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
                int hour;
                if (update.getMessage().getText().matches(".+:00")) {
                    hour = Integer.parseInt(update.getMessage()
                            .getText()
                            .replaceAll(":00", "")
                            .replaceAll("✅", "")
                            .trim());
                } else {
                    hour = -1;
                }
//                System.out.println(hour);
                userService.setNotify(Long.parseLong(chatUserId), hour);
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
            case "Settings", "BackNum", "BackB", "BackVal" -> inlineKeyboardMarkupMy.menuSettings(userId.toString());
            case "Back" -> inlineKeyboardMarkupMy.infoMenu(userId.toString());
            case "GetInfo" -> {
                try {
                    execute(SendMessage.builder()
                            .text(sendInfo(userService.getUserSettings(userId)))
                            .chatId(userId.toString())
                            .build());
                } catch (IOException | InterruptedException e) {
                    e.printStackTrace();
                }
                inlineKeyboardMarkupMy.infoMenu(userId.toString());
            }

            case "Number" -> inlineKeyboardMarkupMy.menuNumber(userId, false, messageId, userService.getUserSettings(userId).getRoundAccuracy());
            case "accuracy:2" -> {
                userService.setAccuracy(userId, 2);
                inlineKeyboardMarkupMy.menuNumber(userId, true, messageId, userService.getUserSettings(userId).getRoundAccuracy());
            }
            case "accuracy:3" -> {
                userService.setAccuracy(userId, 3);
                inlineKeyboardMarkupMy.menuNumber(userId, true, messageId, userService.getUserSettings(userId).getRoundAccuracy());
            }
            case "accuracy:4" -> {
                userService.setAccuracy(userId, 4);
                inlineKeyboardMarkupMy.menuNumber(userId, true, messageId, userService.getUserSettings(userId).getRoundAccuracy());
            }
            case "Bank" -> inlineKeyboardMarkupMy.menuBanks(userId, false, messageId, userService.getUserSettings(userId).getBanksHashSet());

            case "PRIVATBANK" -> {
                if (userService.getUserSettings(userId).getBanksHashSet().stream().anyMatch(e -> e.getCommand().equals("PRIVATBANK"))
                        && userService.getUserSettings(userId).getBanksHashSet().size() > 1) {
                    userService.unSetBank(userId, "PRIVATBANK");
                } else {
                    userService.setBank(userId, "PRIVATBANK");
                }
                inlineKeyboardMarkupMy.menuBanks(userId, true, messageId, userService.getUserSettings(userId).getBanksHashSet());
            }
            case "MONOBANK" -> {
                if (userService.getUserSettings(userId).getBanksHashSet().stream().anyMatch(e -> e.getCommand().equals("MONOBANK"))
                        && userService.getUserSettings(userId).getBanksHashSet().size() > 1) {
                    userService.unSetBank(userId, "MONOBANK");
                } else {
                    userService.setBank(userId, "MONOBANK");
                }
                inlineKeyboardMarkupMy.menuBanks(userId, true, messageId, userService.getUserSettings(userId).getBanksHashSet());
            }
            case "NBU" -> {
                if (userService.getUserSettings(userId).getBanksHashSet().stream().anyMatch(e -> e.getCommand().equals("NBU"))
                        && userService.getUserSettings(userId).getBanksHashSet().size() > 1) {
                    userService.unSetBank(userId, "NBU");
                } else {
                    userService.setBank(userId, "NBU");
                }
                inlineKeyboardMarkupMy.menuBanks(userId, true, messageId, userService.getUserSettings(userId).getBanksHashSet());
            }
            case "currencies" -> inlineKeyboardMarkupMy.menuCurrency(userId, true, messageId, userService.getUserSettings(userId).getCurrenciesHashSet());

            case "EUR" -> {
                if (userService.getUserSettings(userId).getCurrenciesHashSet().stream().anyMatch(e -> e.getCommand().equals("EUR"))
                        && userService.getUserSettings(userId).getCurrenciesHashSet().size() > 1) {
                    userService.unSetCurrency(userId, "EUR");
                } else {
                    userService.setCurrency(userId, "EUR");
                }
                inlineKeyboardMarkupMy.menuCurrency(userId, true, messageId, userService.getUserSettings(userId).getCurrenciesHashSet());
            }
            case "USD" -> {
                if (userService.getUserSettings(userId).getCurrenciesHashSet().stream().anyMatch(e -> e.getCommand().equals("USD"))
                        && userService.getUserSettings(userId).getCurrenciesHashSet().size() > 1) {
                    userService.unSetCurrency(userId, "USD");
                } else {
                    userService.setCurrency(userId, "USD");
                }
                inlineKeyboardMarkupMy.menuCurrency(userId, true, messageId, userService.getUserSettings(userId).getCurrenciesHashSet());
            }
            case "RUR" -> {
                if (userService.getUserSettings(userId).getCurrenciesHashSet().stream().anyMatch(e -> e.getCommand().equals("RUR"))
                        && userService.getUserSettings(userId).getCurrenciesHashSet().size() > 1) {
                    userService.unSetCurrency(userId, "RUR");
                } else {
                    userService.setCurrency(userId, "RUR");
                }
                inlineKeyboardMarkupMy.menuCurrency(userId, true, messageId, userService.getUserSettings(userId).getCurrenciesHashSet());
            }
            case "Time_of_notification" ->
                replyKeyboardMarkupMy.getKeyboardMarkup(userId.toString(), userService.getUserSettings(userId));
        }
    }

    public String sendInfo(UserSettings userSettings) throws IOException, InterruptedException {
        final HashSet<Banks> bankList = userSettings.getBanksHashSet();
        System.out.println(bankList.toString());

        final HashSet<CurrencyNames> currencies = userSettings.getCurrenciesHashSet();
        System.out.println(currencies.toString());
        final int roundAccuracy = userSettings.getRoundAccuracy();
        System.out.println(roundAccuracy);
        int notifyHour = userSettings.getNotifyHour();
        System.out.println(notifyHour);
        StringBuilder result = new StringBuilder();

        for (Banks banks : bankList) {
            List<BankResponce> bankInfo = facade.getBankInfo(banks.getName());
            result.append("Курс в: " + banks.getCommand() + "\n");

            for (CurrencyNames currency : currencies) {
                List<BankResponce> collect = bankInfo.stream()
                        .filter(cur -> currency.getCommand().equals(cur.getCurrency()))
                        .collect(Collectors.toList());


                for (BankResponce responce : collect) {

                    BigDecimal buy = new BigDecimal(Float.toString(responce.getBuy())).setScale(roundAccuracy, RoundingMode.DOWN);
                    BigDecimal sale = new BigDecimal(Float.toString(responce.getSale())).setScale(roundAccuracy, RoundingMode.DOWN);

                    result.append(responce.getCurrency() + " Покупка: " + buy + " Продажа:" + sale + "\n");
                }
                System.out.println(collect);
            }

        }
        return result.toString();
    }

}