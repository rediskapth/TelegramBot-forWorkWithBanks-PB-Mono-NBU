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
    public static void main(String[] args) throws IOException, InterruptedException {
        try {
            TelegramBotsApi telegramBotsApi = new TelegramBotsApi(DefaultBotSession.class);
            telegramBotsApi.registerBot(new TelegramImplementations());
        } catch (TelegramApiException e) {
            e.getStackTrace();
        }
//        MonoAPI monoAPI=new MonoAPI();
//        NbuAPI nbuAPI=new NbuAPI();
//        PrivatAPI privatAPI=new PrivatAPI();
        //try {
//            System.out.println(monoAPI.getCurrencyfromBank());
//            System.out.println(nbuAPI.getCurrencyfromBank());
//            System.out.println(privatAPI.getCurrencyfromBank());
//
//
//        }

    }

}
