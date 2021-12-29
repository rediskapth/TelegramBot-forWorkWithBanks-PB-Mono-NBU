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
                    .callbackData("GetInfo")
                    .build())));
            buttons.add(Collections.singletonList((InlineKeyboardButton.builder()
                    .text("Настройки")
                    .callbackData("Settings")
                    .build())));
            executeAsync(
                    SendMessage.builder()
                            .chatId(userId)
                            .text("Привет! \n Этот бот поможет отслеживать актуальные курсы валют.")
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

    public void menuNumber(Long userId, boolean isUpdate, int messageId, int accuracy) {
        try {
            List<List<InlineKeyboardButton>> buttons = new ArrayList<>();
            buttons.add(Collections.singletonList((InlineKeyboardButton.builder()
                    .text(accuracy==2?"✅  2":"2")
                    .callbackData("accuracy:2")
                    .build())));
            buttons.add(Collections.singletonList((InlineKeyboardButton.builder()
                    .text(accuracy==3?"✅  3":"3")
                    .callbackData("accuracy:3")
                    .build())));
            buttons.add(Collections.singletonList((InlineKeyboardButton.builder()
                    .text(accuracy==4?"✅  4":"4")
                    .callbackData("accuracy:4")
                    .build())));
            buttons.add(Collections.singletonList((InlineKeyboardButton.builder()
                    .text("Назад")
                    .callbackData("BackNum")
                    .build())));
            if (isUpdate) {
                executeAsync(
                        EditMessageReplyMarkup.builder()
                                .chatId(userId.toString())
                                .messageId(messageId)
                                .replyMarkup(InlineKeyboardMarkup.builder().keyboard(buttons).build())
                                .build());
            } else {
                executeAsync(
                        SendMessage.builder()
                                .chatId(userId.toString())
                                .text("Кол-во знаков после запятой")
                                .replyMarkup(InlineKeyboardMarkup.builder().keyboard(buttons).build())
                                .build());
            }
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    public void menuCurrency(Long userId, boolean isUpdate, int messageId, HashSet<CurrencyNames> checkedCurrencies) {
        try {
            List<List<InlineKeyboardButton>> buttons = new ArrayList<>();
            for (CurrencyNames currencyNames : CurrencyNames.values()) {
                String checked = "";
                for (CurrencyNames cur : checkedCurrencies) {
                    if (currencyNames.getName().equals(cur.getName())) {
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
                                .chatId(userId.toString())
                                .messageId(messageId)
                                .replyMarkup(InlineKeyboardMarkup.builder().keyboard(buttons).build())
                                .build());
            } else {
                executeAsync(
                        SendMessage.builder()
                                .chatId(userId.toString())
                                .text("Валюта")
                                .replyMarkup(InlineKeyboardMarkup.builder().keyboard(buttons).build())
                                .build());
            }
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }


    public void menuBanks(Long userId, boolean isUpdate, int messageId, HashSet<Banks> checkedBanks) {
        try {
            List<List<InlineKeyboardButton>> buttons = new ArrayList<>();
            for (Banks banks : Banks.values()) {
                String checked = "";
                for (Banks cur : checkedBanks) {
                    if (banks.getName().equals(cur.getName())) {
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
                                .chatId(userId.toString())
                                .messageId(messageId)
                                .replyMarkup(InlineKeyboardMarkup.builder().keyboard(buttons).build())
                                .build());
            } else {
                executeAsync(
                        SendMessage.builder()
                                .chatId(userId.toString())
                                .text("Банки")
                                .replyMarkup(InlineKeyboardMarkup.builder().keyboard(buttons).build())
                                .build());
            }
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
    public void infoMenu(String userId) {
        try {
            List<List<InlineKeyboardButton>> buttons = new ArrayList<>();
            buttons.add(Collections.singletonList((InlineKeyboardButton.builder()
                    .text("Получить инфо")
                    .callbackData("GetInfo")
                    .build())));
            buttons.add(Collections.singletonList((InlineKeyboardButton.builder()
                    .text("Настройки")
                    .callbackData("Settings")
                    .build())));
            executeAsync(
                    SendMessage.builder()
                            .chatId(userId)
                            .text("Выберите меню")
                            .replyMarkup(InlineKeyboardMarkup.builder().keyboard(buttons).build())
                            .build());
        } catch (TelegramApiException e) {
            e.printStackTrace();

        }
    }

}
