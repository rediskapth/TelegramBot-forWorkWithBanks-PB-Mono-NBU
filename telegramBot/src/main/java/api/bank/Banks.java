package api.bank;

public enum Banks {
    PRIVATBANK("Приватбанк"),
    MONOBANK("Монобанк"),
    NBU("НБУ");

    private final String value;

    Banks(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

}
