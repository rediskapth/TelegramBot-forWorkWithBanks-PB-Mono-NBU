package api.bank;

public class BankResponce {
    private String bank;
    private String currency;
    private Float buy;
    private Float sale;

    public String getBank() {
        return bank;
    }

    public void setBank(String bank) {
        this.bank = bank;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public Float getBuy() {
        return buy;
    }

    public void setBuy(Float buy) {
        this.buy = buy;
    }

    public Float getSale() {
        return sale;
    }

    public void setSale(Float sale) {
        this.sale = sale;
    }

    @Override
    public String toString() {
        return "ObjectAllBank{" +
                "bank='" + bank + '\'' +
                ", currency='" + currency + '\'' +
                ", buy=" + buy +
                ", sale=" + sale +
                '}';
    }
}