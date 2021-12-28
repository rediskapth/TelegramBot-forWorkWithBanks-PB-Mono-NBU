package api.bank;


import api.bank.objects.MonoObject;
import com.google.gson.Gson;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;

public class MonoAPI {
    private final HttpClient client = HttpClient.newHttpClient();
    private final Gson GSON = new Gson();
    ArrayList<BankResponce> responses = new ArrayList<>();
    public ArrayList<BankResponce> getCurrencyfromBank() throws IOException, InterruptedException {
        HttpRequest build = HttpRequest.newBuilder()
                .uri(URI.create("https://api.monobank.ua/bank/currency"))
                .GET()
                .header("Content-type", "application/json")
                .build();
        HttpResponse<String> send = client.send(build, HttpResponse.BodyHandlers.ofString());
        MonoObject[] mono = GSON.fromJson(send.body(), MonoObject[].class);
        responses.clear();
        for (MonoObject mb : mono) {
            if ((mb.getCurrencyCodeA() == 840 && mb.getCurrencyCodeB() == 980)||(mb.getCurrencyCodeA() == 978 && mb.getCurrencyCodeB() == 980)||(mb.getCurrencyCodeA() == 643 && mb.getCurrencyCodeB() == 980)){
                BankResponce bankResponce =new BankResponce();
                bankResponce.setBank("МоноБанк");
                bankResponce.setBuy(mb.getRateBuy());
                bankResponce.setSale(mb.getRateSell());
                bankResponce.setCurrency(getCurrencyNameByCode(mb.getCurrencyCodeA()));
                responses.add(bankResponce);
            }
        }
        return responses;
    }
    private String getCurrencyNameByCode(int code) {
        return switch (code) {
            case 840 -> "USD";
            case 978 -> "EUR";
            case 643 -> "RUR";
            default -> null;
        };
    }
}