package vendaingressos;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class Compra {
    private Ingresso ingresso;
    private Usuario usuario;
    private Boolean confirmacao;

    public Compra(Ingresso ingresso, Usuario usuario) {
        this.ingresso = ingresso;
        this.usuario = usuario;
        this.confirmacao = true;
    }

    public Ingresso getIngresso() {
        return ingresso;
    }
    public void setIngresso(Ingresso ingresso) {
        this.ingresso = ingresso;
    }

    public Usuario getUsuario() {
        return usuario;
    }
    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Boolean getConfirmacao() {
        return confirmacao;
    }
    public void setConfirmacao(Boolean confirmacao) {
        this.confirmacao = confirmacao;
    }

    public void confirmacaoCompra(){
        if(!confirmacao){
            this.confirmacao = true;
            emailConfirmacao();
        }
    }

    public void emailConfirmacao(){
        String nomeArquivo = "confirmacao_compra_" + usuario.getLogin() + ".txt";
        try (PrintWriter writer = new PrintWriter(new FileWriter(nomeArquivo))) {
            writer.println("Olá " + usuario.getNome() + ",");
            writer.println("Sua compra foi confirmada com sucesso!");
            writer.println("Detalhes da compra:");
            writer.println("Evento: " + ingresso.getEvento().getNome());
            writer.println("Assento: " + ingresso.getAssento());
            writer.println("Preço: R$" + ingresso.getPreco());
            writer.println("Data do evento: " + ingresso.getEvento().getData());
            writer.println("Obrigado por sua compra!");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void cancelarCompra(){
        if(confirmacao){
            this.confirmacao = false;
        }
    }
}
