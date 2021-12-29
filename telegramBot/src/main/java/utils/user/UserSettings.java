package utils.user;

import api.bank.Banks;
import api.bank.CurrencyNames;
import api.controller.SendNotif;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class UserSettings {
    private String name;
    private HashSet<Banks> bankList;
    private HashSet<CurrencyNames> currencies;
    private int roundAccuracy;
    private int notifyHour;


    public UserSettings(){};

    public UserSettings(String name, HashSet<Banks> bankList, HashSet<CurrencyNames> currencies, int roundAccuracy, int notifyHour) {
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

    public HashSet<Banks> getBankList() {
        return bankList;
    }

    public void setBankList(HashSet<Banks> bankList) {
        this.bankList = bankList;
    }

    public HashSet<CurrencyNames> getCurrencies() {
        return currencies;
    }

    public void setCurrencies(HashSet<CurrencyNames> currencies) {
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

    @Override
    public String toString() {
        return "UserSettings{" +
                "name='" + name + '\'' +
                ", bankList=" + bankList +
                ", currencies=" + currencies +
                ", roundAccuracy=" + roundAccuracy +
                ", notifyHour=" + notifyHour +
                '}';
    }
}