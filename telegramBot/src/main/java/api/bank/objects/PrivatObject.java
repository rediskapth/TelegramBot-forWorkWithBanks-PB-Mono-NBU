package api.bank.objects;

import java.util.Objects;

public class PrivatObject {
    private String ccy;
    private String base_ccy;
    private float buy;
    private float sale;

    public String getCcy() {
        return ccy;
    }

    public void setCcy(String ccy) {
        this.ccy = ccy;
    }

    public String getBase_ccy() {
        return base_ccy;
    }

    public void setBase_ccy(String base_ccy) {
        this.base_ccy = base_ccy;
    }

    public float getBuy() {
        return buy;
    }

    public void setBuy(float buy) {
        this.buy = buy;
    }

    public float getSale() {
        return sale;
    }

    public void setSale(float sale) {
        this.sale = sale;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PrivatObject that = (PrivatObject) o;
        return Float.compare(that.buy, buy) == 0 && Float.compare(that.sale, sale) == 0 && Objects.equals(ccy, that.ccy) && Objects.equals(base_ccy, that.base_ccy);
    }

    @Override
    public int hashCode() {
        return Objects.hash(ccy, base_ccy, buy, sale);
    }

    @Override
    public String toString() {
        return "PrivarBankObject{" +
                "ccy='" + ccy + '\'' +
                ", base_ccy='" + base_ccy + '\'' +
                ", buy=" + buy +
                ", sale=" + sale +
                '}';
    }
}