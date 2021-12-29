package utils.user;

import api.bank.Banks;
import api.bank.CurrencyNames;
import api.controller.SendNotif;

import java.util.HashSet;

public class UserSettings {
    private String name;
    private HashSet<Banks> banksHashSet;
    private HashSet<CurrencyNames> currenciesHashSet;
    private int roundAccuracy;
    private int notifyHour;


    public UserSettings(){};

    public UserSettings(String name, HashSet<Banks> bankList, HashSet<CurrencyNames> currencies, int roundAccuracy, int notifyHour) {
        this.name = name;
        this.banksHashSet = bankList;
        this.currenciesHashSet = currencies;
        this.roundAccuracy = roundAccuracy;
        this.notifyHour = notifyHour;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public HashSet<Banks> getBanksHashSet() {
        return banksHashSet;
    }

    public HashSet<CurrencyNames> getCurrenciesHashSet() {
        return currenciesHashSet;
    }

    public void setCurrenciesHashSet(HashSet<CurrencyNames> currenciesHashSet) {
        this.currenciesHashSet = currenciesHashSet;
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

    public void setBanksHashSet(HashSet<Banks> banksHashSet) {
        this.banksHashSet = banksHashSet;
    }
}
