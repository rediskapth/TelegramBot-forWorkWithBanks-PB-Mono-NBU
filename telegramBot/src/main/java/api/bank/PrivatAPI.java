package api.bank;

import api.bank.objects.PrivatObject;
import com.google.gson.Gson;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class PrivatAPI {
    private final HttpClient client = HttpClient.newHttpClient();
    private final Gson GSON = new Gson();

    public List<PrivatObject> get() throws IOException, InterruptedException {
        HttpRequest build = HttpRequest.newBuilder()
                .uri(URI.create("https://api.privatbank.ua/p24api/pubinfo?json&exchange&coursid=5"))
                .GET()
                .header("Content-type", "application/json")
                .build();
        HttpResponse<String> send = client.send(build, HttpResponse.BodyHandlers.ofString());
        List<PrivatObject> privat = Arrays.asList(GSON.fromJson(send.body(), PrivatObject[].class));
        return privat.stream().filter(r -> (r.getCcy().equals("USD") && r.getBase_ccy().equals("UAH")) || (r.getCcy().equals("EUR") && r.getBase_ccy().equals("UAH")) || (r.getCcy().equals("RUR") && r.getBase_ccy().equals("UAH")))
                .collect(Collectors.toList());
    }
}
