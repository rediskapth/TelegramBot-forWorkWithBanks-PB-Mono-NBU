package api.controller;

import org.springframework.beans.factory.annotation.Value;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;
import utils.keyboards.InlineKeyboardMarkupMy;



public class TelegramImplementations extends TelegramLongPollingBot {
    @Value("${bot.username}")
    private String username;

    @Value("${bot.token}")
    private String token;
    @Override
    public String getBotUsername() {
        return username;
    }
    @Override
    public String getBotToken() {
        return token;
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
        InlineKeyboardMarkupMy inlineKeyboardMarkupMy =new InlineKeyboardMarkupMy();
        if (update.getMessage().hasText()) {

            String text = update.getMessage().getText();
            String chatUserId = update.getMessage().getChatId().toString();

            if (text.equals("/start")) {
                inlineKeyboardMarkupMy .mainMenu(chatUserId);
            }
        }
    }

    private void pressingTheButton(Update update) {
        InlineKeyboardMarkupMy inlineKeyboardMarkupMy =new InlineKeyboardMarkupMy();
        String data = update.getCallbackQuery().getData();
        String chatUserId = update.getCallbackQuery().getMessage().getChatId().toString();
        switch (data) {
            case "Settings" ->inlineKeyboardMarkupMy.menuSettings(chatUserId);
            case "Back" -> inlineKeyboardMarkupMy.mainMenu(chatUserId);
            case "Number" -> inlineKeyboardMarkupMy.menuNumber(chatUserId);
            case "BackNum" -> inlineKeyboardMarkupMy.menuSettings(chatUserId);
            case "Bank" -> inlineKeyboardMarkupMy.menuBanks(chatUserId);
            case "BackB" -> inlineKeyboardMarkupMy.menuSettings(chatUserId);
            case "currencies" -> inlineKeyboardMarkupMy.menuCurrency(chatUserId);
            case "BackVal" -> inlineKeyboardMarkupMy.menuSettings(chatUserId);
        }
    }
    public static void main(String[] args) {
        try {
            TelegramBotsApi telegramBotsApi = new TelegramBotsApi(DefaultBotSession.class);
            telegramBotsApi.registerBot(new TelegramImplementations());
        } catch (TelegramApiException e) {
            e.getStackTrace();
        }
    }

}
