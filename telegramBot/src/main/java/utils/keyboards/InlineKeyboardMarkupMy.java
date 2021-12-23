package utils.keyboards;


import api.controller.TelegramImplementations;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class InlineKeyboardMarkupMy extends TelegramImplementations {
    public void mainMenu(String chatUserId) {
        try {
            List<List<InlineKeyboardButton>> buttons = new ArrayList<>();
            buttons.add(Collections.singletonList((InlineKeyboardButton.builder()
                    .text("Получить информацию")
                    .callbackData("Get")
                    .build())));
            buttons.add(Collections.singletonList((InlineKeyboardButton.builder()
                    .text("Настройки")
                    .callbackData("Settings")
                    .build())));
            executeAsync(
                    SendMessage.builder()
                            .text("Привет. Этот бот поможет отслеживать актуальные курсы валют.")
                            .chatId(chatUserId)
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
                    .text("Время оповещений.")
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
                    .text("Два знака")
                    .callbackData("2")
                    .build())));
            buttons.add(Collections.singletonList((InlineKeyboardButton.builder()
                    .text("Три знака")
                    .callbackData("3")
                    .build())));
            buttons.add(Collections.singletonList((InlineKeyboardButton.builder()
                    .text("Четыре знака")
                    .callbackData("4")
                    .build())));
            buttons.add(Collections.singletonList((InlineKeyboardButton.builder()
                    .text("Назад")
                    .callbackData("BackNum")
                    .build())));

            executeAsync(
                    SendMessage.builder()
                            .chatId(chatUserId)
                            .text("Кол-во знаков после запятой.")
                            .replyMarkup(InlineKeyboardMarkup.builder().keyboard(buttons).build())
                            .build());
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    public void menuBanks(String chatUserId) {
        try {
            List<List<InlineKeyboardButton>> buttons = new ArrayList<>();
            buttons.add(Collections.singletonList((InlineKeyboardButton.builder()
                    .text("Приват банк")
                    .callbackData("Privat")
                    .build())));
            buttons.add(Collections.singletonList((InlineKeyboardButton.builder()
                    .text("НБУ")
                    .callbackData("NBU")
                    .build())));
            buttons.add(Collections.singletonList((InlineKeyboardButton.builder()
                    .text("Моно банк")
                    .callbackData("Mono")
                    .build())));
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
            buttons.add(Collections.singletonList((InlineKeyboardButton.builder()
                    .text("Доллар")
                    .callbackData("USA")
                    .build())));
            buttons.add(Collections.singletonList((InlineKeyboardButton.builder()
                    .text("Евро")
                    .callbackData("EUR")
                    .build())));
            buttons.add(Collections.singletonList((InlineKeyboardButton.builder()
                    .text("Рубль")
                    .callbackData("RUB")
                    .build())));
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
