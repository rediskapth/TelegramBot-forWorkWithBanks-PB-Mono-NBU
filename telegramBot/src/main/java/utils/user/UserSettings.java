package utils.user;

import java.util.List;

public class UserSettings {
    private String name;
    private List<Banks> bankList;
    private List<Currencies> currencies;
    private int roundAccuracy;
    private int notifyHour;

    public UserSettings(String name, List<Banks> bankList, List<Currencies> currencies, int roundAccuracy, int notifyHour) {
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

    public List<Banks> getBankList() {
        return bankList;
    }

    public void setBankList(List<Banks> bankList) {
        this.bankList = bankList;
    }

    public List<Currencies> getCurrencies() {
        return currencies;
    }

    public void setCurrencies(List<Currencies> currencies) {
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
