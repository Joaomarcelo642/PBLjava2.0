package vendaingressos;

public class Boleto extends Pagamento {
    private String codigoBoleto;

    public Boleto(String codigoBoleto) {
        super("Boleto Bancário");
        this.codigoBoleto = codigoBoleto;
    }

    public String getCodigoBoleto() {
        return codigoBoleto;
    }
    public void setCodigoBoleto(String codigoBoleto) {
        this.codigoBoleto = codigoBoleto;
    }
}
