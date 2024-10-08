package vendaingressos;

public abstract class Pagamento {
    public String forma;

    public Pagamento(String forma) {
        this.forma = forma;
    }

    public String getForma() {
        return forma;
    }
    public void setForma(String forma) {
        this.forma = forma;
    }
}
