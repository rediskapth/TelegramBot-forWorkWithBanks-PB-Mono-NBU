package api.bank;

public enum CurrencyNames {
    USD("USD"),
    RUB("RUB"),
    EUR("EUR");

    String value;

    CurrencyNames(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
