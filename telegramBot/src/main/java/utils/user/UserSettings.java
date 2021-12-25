package utils.user;

import api.bank.Banks;
import api.bank.CurrencyNames;

import java.util.List;
import java.util.Set;

public class UserSettings {
    private String name;
    private Set<Banks> bankList;
    private Set<CurrencyNames> currencies;
    private int roundAccuracy;
    private int notifyHour;

    public UserSettings(){

    };

    public UserSettings(String name, Set<Banks> bankList, Set<CurrencyNames> currencies, int roundAccuracy, int notifyHour) {
        this.name = name;
        this.bankList = bankList;
        this.currencies = currencies;
        this.roundAccuracy = roundAccuracy;
        this.notifyHour = notifyHour;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Banks> getBankList() {
        return bankList;
    }

    public void setBankList(Set<Banks> bankList) {
        this.bankList = bankList;
    }

    public Set<CurrencyNames> getCurrencies() {
        return currencies;
    }

    public void setCurrencies(Set<CurrencyNames> currencies) {
        this.currencies = currencies;
    }

    public int getRoundAccuracy() {
        return roundAccuracy;
    }

    public void setRoundAccuracy(int roundAccuracy) {
        this.roundAccuracy = roundAccuracy;
    }

    public int getNotifyHour() {
        return notifyHour;
    }

    public void setNotifyHour(int notifyHour) {
        this.notifyHour = notifyHour;
    }
}