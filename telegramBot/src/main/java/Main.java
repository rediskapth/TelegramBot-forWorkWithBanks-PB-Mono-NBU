import api.Facade;
import api.bank.MonoAPI;
import api.bank.NbuAPI;
import api.bank.BankResponce;
import api.bank.PrivatAPI;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.io.IOException;
import java.util.List;

public class Main {
    public static void main(String[] args) {
//        try {
//            TelegramBotsApi telegramBotsApi = new TelegramBotsApi(DefaultBotSession.class);
//            telegramBotsApi.registerBot(new TelegramImplementations());
//        } catch (TelegramApiException e) {
//            e.getStackTrace();
//        }
        MonoAPI monoAPI = new MonoAPI();
        NbuAPI nbuAPI = new NbuAPI();
        PrivatAPI privatAPI = new PrivatAPI();
        Facade facade = new Facade();

//            System.out.println(monoAPI.getCurrencyfromBank());
//            System.out.println(nbuAPI.getCurrencyfromBank());
//            System.out.println(privatAPI.getCurrencyfromBank());

    }


}
