import api.controller.MyTimer;
import api.controller.TelegramImplementations;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException, InterruptedException {
        try {
            TelegramImplementations telegramImplementations = new TelegramImplementations();
            TelegramBotsApi telegramBotsApi = new TelegramBotsApi(DefaultBotSession.class);
            telegramBotsApi.registerBot(telegramImplementations);

            MyTimer myTimer = new MyTimer(telegramImplementations);
            myTimer.sendNotify();

        } catch (TelegramApiException e) {
            e.getStackTrace();
        }

    }}