package api;

import api.bank.*;
import utils.user.UserSettings;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class CashingService {
    private MonoAPI monoApi = new MonoAPI();
    private NbuAPI nbuAPI = new NbuAPI();
    private PrivatAPI privatAPI = new PrivatAPI();
    public List<BankResponce> monoCurrency = new ArrayList<>();;
    public List<BankResponce> privateCurrency = new ArrayList<>();
    public List<BankResponce> nbuCurrency = new ArrayList<>();
    private static CashingService cashingService;
    Timer timer = new Timer(true);

    public List<BankResponce> getCashedMonoCurrency() {
        if (monoCurrency.isEmpty()) {
            try {
                monoCurrency = monoApi.getCurrencyfromBank();
                timer.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        monoCurrency.clear();
                    }
                }, 6 * 60 * 1000);
            } catch (IOException | InterruptedException e) {
                e.printStackTrace();
            }
        }
        return monoCurrency;
    }

    public List<BankResponce> getCashedPrivatCurrency() {
        if (privateCurrency.isEmpty()) {
            try {
                privateCurrency = privatAPI.getCurrencyfromBank();
                timer.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        privateCurrency.clear();
                    }
                }, 6 * 60 * 1000);
            } catch (IOException | InterruptedException e) {
                e.printStackTrace();
            }

        }
        return privateCurrency;
    }

    public List<BankResponce> getCashedNBUCurrency() {
        if (nbuCurrency.isEmpty()) {
            try {
                nbuCurrency = nbuAPI.getCurrencyfromBank();
                timer.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        nbuCurrency.clear();
                    }
                },6 * 60 * 1000);
            } catch (IOException | InterruptedException e) {
                e.printStackTrace();
            }
        }
        return nbuCurrency;
    }

    public static CashingService getInstance() {
        if (cashingService == null) {
            cashingService = new CashingService();
        }
        return cashingService;
    }

    private CashingService() {}
}
