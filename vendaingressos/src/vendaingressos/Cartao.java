package vendaingressos;

import com.google.gson.annotations.Expose;

public class Cartao extends Pagamento {
    private String numeroCartao;
    private String nome;

    public Cartao(String numeroCartao, String nome) {
        super("Cartão de Crédito");
        this.numeroCartao = numeroCartao;
        this.nome = nome;
    }


    public String getNumero() {
        return numeroCartao;
    }
    public void setNumero(String numeroCartao) {
        this.numeroCartao = numeroCartao;
    }

    public String getNome() {
        return nome;
    }
    public void setNome(String nome) {
        this.nome = nome;
    }

}
