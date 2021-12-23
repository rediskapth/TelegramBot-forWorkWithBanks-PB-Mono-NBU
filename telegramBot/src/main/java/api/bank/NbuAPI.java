package api.bank;

import api.bank.ObjectAllBank;
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
    ArrayList<ObjectAllBank> responses = new ArrayList<>();

    public ArrayList<ObjectAllBank> getCurrencyfromBank() throws IOException, InterruptedException {
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
                ObjectAllBank objectAllBank = new ObjectAllBank();
                objectAllBank.setBank("NBU");
                objectAllBank.setBuy(NBU.getRate());
                objectAllBank.setSale(NBU.getRate());
                objectAllBank.setCurrency(NBU.getCc());
                responses.add(objectAllBank);
            }
        }
        return responses;
    }
}

