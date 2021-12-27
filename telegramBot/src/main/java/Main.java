import api.controller.TelegramBotController;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException, InterruptedException {
        try {
            TelegramBotsApi telegramBotsApi = new TelegramBotsApi(DefaultBotSession.class);
            telegramBotsApi.registerBot(new TelegramBotController());
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
