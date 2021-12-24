package utils.keyboards;


import api.bank.Banks;
import api.bank.CurrencyNames;
import api.controller.TelegramImplementations;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class InlineKeyboardMarkupMy extends TelegramImplementations {
    public void mainMenu(String userId) {
        try {
            List<List<InlineKeyboardButton>> buttons = new ArrayList<>();
            buttons.add(Collections.singletonList((InlineKeyboardButton.builder()
                    .text("Получить инфо")
                    .callbackData("Get")
                    .build())));
            buttons.add(Collections.singletonList((InlineKeyboardButton.builder()
                    .text("Настройки")
                    .callbackData("Settings")
                    .build())));
            executeAsync(
                    SendMessage.builder()
                            .text("Привет. Этот бот поможет отслеживать актуальные курсы валют.")
                            .chatId(userId)
                            .replyMarkup(InlineKeyboardMarkup.builder().keyboard(buttons).build())
                            .build());
        } catch (TelegramApiException e) {
            e.printStackTrace();

        }
    }

    public void menuSettings(String chatUserId) {

        try {
            List<List<InlineKeyboardButton>> buttons = new ArrayList<>();
            buttons.add(Collections.singletonList((InlineKeyboardButton.builder()
                    .text("Кол-во знаков после запятой")
                    .callbackData("Number")
                    .build())));
            buttons.add(Collections.singletonList((InlineKeyboardButton.builder()
                    .text("Банк")
                    .callbackData("Bank")
                    .build())));
            buttons.add(Collections.singletonList((InlineKeyboardButton.builder()
                    .text("Валюты")
                    .callbackData("currencies")
                    .build())));
            buttons.add(Collections.singletonList((InlineKeyboardButton.builder()
                    .text("Время оповещений")
                    .callbackData("Time_of_notification")
                    .build())));
            buttons.add(Collections.singletonList((InlineKeyboardButton.builder()
                    .text("Назад.")
                    .callbackData("Back")
                    .build())));

            executeAsync(
                    SendMessage.builder()
                            .chatId(chatUserId)
                            .text("Настройки.")
                            .replyMarkup(InlineKeyboardMarkup.builder().keyboard(buttons).build())
                            .build());
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    public void menuNumber(String chatUserId) {
        try {
            List<List<InlineKeyboardButton>> buttons = new ArrayList<>();
            buttons.add(Collections.singletonList((InlineKeyboardButton.builder()
                    .text("0.00")
                    .callbackData("2")
                    .build())));
            buttons.add(Collections.singletonList((InlineKeyboardButton.builder()
                    .text("0.000")
                    .callbackData("3")
                    .build())));
            buttons.add(Collections.singletonList((InlineKeyboardButton.builder()
                    .text("0.0000")
                    .callbackData("4")
                    .build())));
            buttons.add(Collections.singletonList((InlineKeyboardButton.builder()
                    .text("Назад")
                    .callbackData("BackNum")
                    .build())));

            executeAsync(
                    SendMessage.builder()
                            .chatId(chatUserId)
                            .text("Кол-во знаков после запятой")
                            .replyMarkup(InlineKeyboardMarkup.builder().keyboard(buttons).build())
                            .build());
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    public void menuBanks(String chatUserId) {
        try {
            List<List<InlineKeyboardButton>> buttons = new ArrayList<>();
            for (Banks bankName : Banks.values()) {
                buttons.add(Collections.singletonList((InlineKeyboardButton.builder()
                        .text(bankName.getValue())
                        .callbackData(bankName.getValue())
                        .build())));
            }
            buttons.add(Collections.singletonList((InlineKeyboardButton.builder()
                    .text("Назад")
                    .callbackData("BackB")
                    .build())));

            executeAsync(
                    SendMessage.builder()
                            .chatId(chatUserId)
                            .text("Банк")
                            .replyMarkup(InlineKeyboardMarkup.builder().keyboard(buttons).build())
                            .build());
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    public void menuCurrency(String chatUserId) {
        try {
            List<List<InlineKeyboardButton>> buttons = new ArrayList<>();
            for (CurrencyNames currencyNames : CurrencyNames.values()) {
                buttons.add(Collections.singletonList((InlineKeyboardButton.builder()
                        .text(currencyNames.getValue())
                        .callbackData(currencyNames.getValue())
                        .build())));
            }
            buttons.add(Collections.singletonList((InlineKeyboardButton.builder()
                    .text("Назад")
                    .callbackData("BackVal")
                    .build())));

            executeAsync(
                    SendMessage.builder()
                            .chatId(chatUserId)
                            .text("Валюта")
                            .replyMarkup(InlineKeyboardMarkup.builder().keyboard(buttons).build())
                            .build());
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

}
