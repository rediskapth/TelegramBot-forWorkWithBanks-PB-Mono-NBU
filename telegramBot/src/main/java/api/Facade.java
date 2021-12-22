package api;

import api.bank.MonoAPI;
import api.bank.NbuAPI;
import api.bank.ObjectAllBank;
import api.bank.PrivatAPI;

import java.io.IOException;
import java.util.List;

public class Facade {
    private MonoAPI monoApi = new MonoAPI();
    private NbuAPI nbuAPI = new NbuAPI();
    private PrivatAPI privatAPI = new PrivatAPI();

    public List<ObjectAllBank> getBankInfo(String bankName) throws IOException, InterruptedException {
        switch (bankName) {
            case "mono" : return monoApi.getCurrencyfromBank();
            case "nbu" : return nbuAPI.getCurrencyfromBank();
            case "private" : return privatAPI.getCurrencyfromBank();
        }

        return null;
    }
}
