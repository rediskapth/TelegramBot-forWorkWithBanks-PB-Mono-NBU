package api.bank;

import api.bank.objects.MonoObject;
import com.google.gson.Gson;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class MonoAPI {
    private final HttpClient client = HttpClient.newHttpClient();
    private final Gson GSON = new Gson();

    public List<MonoObject> get() throws IOException, InterruptedException {
        HttpRequest build = HttpRequest.newBuilder()
                .uri(URI.create("https://api.monobank.ua/bank/currency"))
                .GET()
                .header("Content-type", "application/json")
                .build();
        HttpResponse<String> send = client.send(build, HttpResponse.BodyHandlers.ofString());
        List<MonoObject> mono = Arrays.asList(GSON.fromJson(send.body(), MonoObject[].class));

        return mono.stream().filter(r -> (r.getCurrencyCodeA() == 840 && r.getCurrencyCodeB() == 980)
                        || (r.getCurrencyCodeA() == 978 && r.getCurrencyCodeB() == 980) || (r.getCurrencyCodeA() == 643 && r.getCurrencyCodeB() == 980))
                .collect(Collectors.toList());
    }
}
