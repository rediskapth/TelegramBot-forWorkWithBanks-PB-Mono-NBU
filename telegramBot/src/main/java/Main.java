import api.bank.MonoAPI;
import api.bank.NbuAPI;
import api.bank.PrivatAPI;

import java.io.IOException;

public class Main {
    public static void main(String[] args) {
//        try {
//            TelegramBotsApi telegramBotsApi = new TelegramBotsApi(DefaultBotSession.class);
//            telegramBotsApi.registerBot(new TelegramImplementations());
//        } catch (TelegramApiException e) {
//            e.getStackTrace();
//        }
        MonoAPI monoAPI=new MonoAPI();
        NbuAPI nbuAPI=new NbuAPI();
        PrivatAPI privatAPI=new PrivatAPI();
        try {
            System.out.println(monoAPI.getCurrencyfromBank());
            System.out.println(nbuAPI.getCurrencyfromBank());
            System.out.println(privatAPI.getCurrencyfromBank());


        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
