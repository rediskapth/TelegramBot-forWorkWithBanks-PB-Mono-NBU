package api.bank;

import api.bank.ObjectAllBank;
import api.bank.objects.PrivatObject;
import com.google.gson.Gson;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;

public class PrivatAPI {
    private final HttpClient client = HttpClient.newHttpClient();
    private final Gson GSON = new Gson();
    ArrayList<ObjectAllBank> responses = new ArrayList<>();

    public ArrayList<ObjectAllBank> getCurrencyfromBank() throws IOException, InterruptedException {
        HttpRequest build = HttpRequest.newBuilder()
                .uri(URI.create("https://api.privatbank.ua/p24api/pubinfo?json&exchange&coursid=5"))
                .GET()
                .header("Content-type", "application/json")
                .build();
        HttpResponse<String> send = client.send(build, HttpResponse.BodyHandlers.ofString());
        PrivatObject[] privat = GSON.fromJson(send.body(), PrivatObject[].class);


        for (PrivatObject pb : privat) {
            if ((pb.getCcy().equals("USD") && pb.getBase_ccy().equals("UAH")) || (pb.getCcy().equals("EUR") && pb.getBase_ccy().equals("UAH")) || (pb.getCcy().equals("RUR") && pb.getBase_ccy().equals("UAH"))) {
                ObjectAllBank objectAllBank = new ObjectAllBank();
                objectAllBank.setBank("PrivatBank");
                objectAllBank.setBuy(pb.getBuy());
                objectAllBank.setSale(pb.getSale());
                objectAllBank.setCurrency(getCurrencyNameByName( pb.getCcy()));
                responses.add(objectAllBank);
            }
        }
        return responses;
    }
  private String  getCurrencyNameByName(String Ccy){
      return switch (Ccy) {
          case "USD" -> "USD";
          case "EUR" -> "EUR";
          case "RUR" -> "RUB";
          default -> null;
      };
    }
}