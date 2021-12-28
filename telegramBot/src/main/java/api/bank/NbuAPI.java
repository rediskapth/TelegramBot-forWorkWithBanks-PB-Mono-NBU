package api.bank;

import api.bank.objects.NbuObject;
import com.google.gson.Gson;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;

public class NbuAPI {
    private final HttpClient client = HttpClient.newHttpClient();
    private final Gson GSON = new Gson();
    ArrayList<BankResponce> responses = new ArrayList<>();

    public ArrayList<BankResponce> getCurrencyfromBank() throws IOException, InterruptedException {
        HttpRequest build = HttpRequest.newBuilder()
                .uri(URI.create("https://bank.gov.ua/NBUStatService/v1/statdirectory/exchange?json"))
                .GET()
                .header("Content-type", "application/json")
                .build();
        HttpResponse<String> send = client.send(build, HttpResponse.BodyHandlers.ofString());
        NbuObject[] nbu = GSON.fromJson(send.body(), NbuObject[].class);

        responses.clear();
        for (NbuObject NBU : nbu) {
            if ((NBU.getCc().equals("USD")) || (NBU.getCc().equals("EUR")) || (NBU.getCc().equals("RUB"))) {
                BankResponce bankResponce = new BankResponce();
                bankResponce.setBank("NBU");
                bankResponce.setBuy(NBU.getRate());
                bankResponce.setSale(NBU.getRate());
                bankResponce.setCurrency(getCurrencyNameByName(NBU.getCc()));
                responses.add(bankResponce);
            }
        }
        return responses;
    }
    private String  getCurrencyNameByName(String Cc){
        return switch (Cc) {
            case "USD" -> "USD";
            case "EUR" -> "EUR";
            case "RUB" -> "RUR";
            default -> null;
        };
    }

}
