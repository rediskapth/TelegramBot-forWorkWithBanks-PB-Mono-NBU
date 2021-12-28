package api;

import api.bank.BankResponce;

import java.io.IOException;
import java.util.List;

public class Facade {
    private CashingService cashingService = CashingService.getInstance();

    public List<BankResponce> getBankInfo(String bankName) {
        return switch (bankName) {
            case "Монобанк" -> cashingService.getCashedMonoCurrency();
            case "НБУ" -> cashingService.getCashedNBUCurrency();
            case "Приватбанк" -> cashingService.getCashedPrivatCurrency();
            default -> null;
        };

    }
}