package utils.keyboards;


import api.bank.Banks;
import api.bank.CurrencyNames;
import api.controller.TelegramImplementations;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageReplyMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.*;

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
                    .text("2")
                    .callbackData("accuracy:2")
                    .build())));
            buttons.add(Collections.singletonList((InlineKeyboardButton.builder()
                    .text("3")
                    .callbackData("accuracy:3")
                    .build())));
            buttons.add(Collections.singletonList((InlineKeyboardButton.builder()
                    .text("4")
                    .callbackData("accuracy:4")
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

    public void menuCurrency(Long ChatId, boolean isUpdate, int messageId, HashSet<CurrencyNames> checkedCurrencies) {
        try {
            List<List<InlineKeyboardButton>> buttons = new ArrayList<>();
            for (CurrencyNames currencyNames : CurrencyNames.values()) {
                String checked = "";
                for (CurrencyNames cur : checkedCurrencies) {
                    if (currencyNames.getName() == cur.getName()) {
                        checked = "✅  ";
                        break;
                    } else {
                        checked = "";
                    }
                }
                buttons.add(Collections.singletonList((InlineKeyboardButton.builder()
                        .text(checked + currencyNames.getName())
                        .callbackData(currencyNames.getCommand())
                        .build())));
            }
            buttons.add(Collections.singletonList((InlineKeyboardButton.builder()
                    .text("Назад")
                    .callbackData("BackVal")
                    .build())));
            if (isUpdate) {
                executeAsync(
                        EditMessageReplyMarkup.builder()
                                .chatId(ChatId.toString())
                                .messageId(messageId)
                                .replyMarkup(InlineKeyboardMarkup.builder().keyboard(buttons).build())
                                .build());
            } else {
                executeAsync(
                        SendMessage.builder()
                                .chatId(ChatId.toString())
                                .text("Валюта")
                                .replyMarkup(InlineKeyboardMarkup.builder().keyboard(buttons).build())
                                .build());
            }
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }


    public void menuBanks(Long ChatId, boolean isUpdate, int messageId, HashSet<Banks> checkedBanks) {
        try {
            List<List<InlineKeyboardButton>> buttons = new ArrayList<>();
            for (Banks banks : Banks.values()) {
                String checked = "";
                for (Banks cur : checkedBanks) {
                    if (banks.getName() == cur.getName()) {
                        checked = "✅  ";
                        break;
                    } else {
                        checked = "";
                    }
                }
                buttons.add(Collections.singletonList((InlineKeyboardButton.builder()
                        .text(checked + banks.getName())
                        .callbackData(banks.getCommand())
                        .build())));
            }
            buttons.add(Collections.singletonList((InlineKeyboardButton.builder()
                    .text("Назад")
                    .callbackData("BackB")
                    .build())));
            if (isUpdate) {
                executeAsync(
                        EditMessageReplyMarkup.builder()
                                .chatId(ChatId.toString())
                                .messageId(messageId)
                                .replyMarkup(InlineKeyboardMarkup.builder().keyboard(buttons).build())
                                .build());
            } else {
                executeAsync(
                        SendMessage.builder()
                                .chatId(ChatId.toString())
                                .text("Банки")
                                .replyMarkup(InlineKeyboardMarkup.builder().keyboard(buttons).build())
                                .build());
            }
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
}