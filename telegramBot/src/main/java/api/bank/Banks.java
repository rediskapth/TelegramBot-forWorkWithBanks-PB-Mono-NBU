package api.bank;

public enum Banks {
    PRIVATBANK("Приватбанк","PRIVATBANK"),
    MONOBANK("Монобанк","MONOBANK"),
    NBU("НБУ","NBU");

    private final String name;
    private final String command;


    Banks(String name, String command) {
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