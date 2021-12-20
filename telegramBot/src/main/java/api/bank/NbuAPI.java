package api.bank;

import api.bank.objects.NbuObject;
import com.google.gson.Gson;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class NbuAPI {
    private final HttpClient client = HttpClient.newHttpClient();
    private final Gson GSON = new Gson();

    public List<NbuObject> get() throws IOException, InterruptedException {
        HttpRequest build = HttpRequest.newBuilder()
                .uri(URI.create("https://bank.gov.ua/NBUStatService/v1/statdirectory/exchange?json"))
                .GET()
                .header("Content-type", "application/json")
                .build();
        HttpResponse<String> send = client.send(build, HttpResponse.BodyHandlers.ofString());
        List<NbuObject> nbu = Arrays.asList(GSON.fromJson(send.body(), NbuObject[].class));
        return nbu.stream().filter(r -> r.getCc().equals("USD") || r.getCc().equals("EUR") || r.getCc().equals("RUB"))
                .collect(Collectors.toList());
    }
}
