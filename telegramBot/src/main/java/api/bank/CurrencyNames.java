package api.bank;

public enum CurrencyNames {
    USD("Долар США","USD"),
    RUR("Рубль","RUR"),
    EUR("Евро","EUR");

    private final String name;
    private final String command;


    CurrencyNames(String name, String command) {
        this.name = name;
        this.command = command;
    }

    public String getName() {
        return name;
    }
    public String getCommand() {
        return command;
    }

}