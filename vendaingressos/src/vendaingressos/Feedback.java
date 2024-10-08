package vendaingressos;

public class Feedback {
    private Evento evento;
    private Usuario usuario;
    private String avaliacao;
    private String comentario;

    public Feedback(Evento evento, Usuario usuario, String avaliacao, String comentario) {
        this.evento = evento;
        this.usuario = usuario;
        this.avaliacao = avaliacao;
        this.comentario = comentario;
    }

    public Evento getEvento() {
        return evento;
    }
    public void setEvento(Evento evento) {
        this.evento = evento;
    }

    public Usuario getUsuario() {
        return usuario;
    }
    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public String getAvaliacao() {
        return avaliacao;
    }
    public void setAvaliacao(String avaliacao) {
        this.avaliacao = avaliacao;
    }

    public String getComentario() {
        return comentario;
    }
    public void setComentario(String comentario) {
        this.comentario = comentario;
    }
}
