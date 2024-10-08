package vendaingressos;

public class Cartao extends Pagamento {
    private Usuario usuario;
    private String numeroCartao;
    private String nome;

    public Cartao(Usuario usuario, String numeroCartao, String nome) {
        super("Cartão de crédito");
        this.usuario = usuario;
        this.numeroCartao = numeroCartao;
        this.nome = nome;
    }

    public Usuario getUsuario() {
        return usuario;
    }
    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
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
