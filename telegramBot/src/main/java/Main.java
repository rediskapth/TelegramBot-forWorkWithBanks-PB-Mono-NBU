import api.bank.MonoAPI;
import api.bank.NbuAPI;
import api.bank.PrivatAPI;
import api.controller.TelegramImplementations;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Objects;
import java.util.Properties;

public class Main {
    public static void main(String[] args) {
        try {
            TelegramBotsApi telegramBotsApi = new TelegramBotsApi(DefaultBotSession.class);
            telegramBotsApi.registerBot(new TelegramImplementations());
        } catch (TelegramApiException e) {
            e.getStackTrace();
        }



    }

}
