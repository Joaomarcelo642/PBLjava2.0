/*******************************************************************************
 Sistema Operacional: Windows 10 - 64 Bits
 Linguagem: JAVA 21.0.4
 Autor: João Marcelo Nascimento Fernandes
 Componente Curricular: EXA 863 - MI Programção
 Concluido em: 16/09/2024
 Declaro que este código foi elaborado por mim de forma individual e não contém nenhum
 trecho de código de outro colega ou de outro autor, tais como provindos de livros e
 apostilas, e páginas ou documentos eletrônicos da Internet. Qualquer trecho de código
 de outra autoria que não a minha está destacado com uma citação para o autor e a fonte
 do código, e estou ciente que estes trechos não serão considerados para fins de avaliação.
 ******************************************************************************************/

package vendaingressos;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.Date;

/**
 * A classe Controller gerencia as operações relacionadas a usuários, eventos e ingressos,
 * permitindo o cadastro de usuários, eventos, compra e cancelamento de ingressos.
 */
public class Controller {
    // Lista de Usuários e Eventos
    private List<Usuario> usuarios;
    private List<Evento> eventos;
    private List<Compra> compras;
    private DataStore dataStore;

    public Controller() {
        dataStore = new DataStore();
        usuarios = dataStore.carregarUsuarios();
        eventos = dataStore.carregarEventos();
        compras = dataStore.carregarCompras();
    }

    // Métodos para os testes

    /**
     * Cadastra um novo usuário no sistema.
     *
     * @param login O login do usuário.
     * @param senha A senha do usuário.
     * @param nome O nome do usuário.
     * @param cpf O CPF do usuário.
     * @param email O email do usuário.
     * @param admin Define se o usuário é administrador.
     * @return O usuário cadastrado.
     */
    public Usuario cadastrarUsuario(String login, String senha, String nome, String cpf, String email, Boolean admin) {
        Usuario usuario = new Usuario(login, senha, nome, cpf, email, admin);
        usuarios.add(usuario);
        dataStore.salvarUsuarios(usuarios);
        return usuario;
    }

    /**
     * Cadastra um novo evento, caso o usuário seja administrador.
     *
     * @param usuario O usuário que está tentando cadastrar o evento.
     * @param nome O nome do evento.
     * @param descricao A descrição do evento.
     * @param data A data do evento.
     * @return O evento cadastrado, se o usuário for administrador.
     * @throws SecurityException Se o usuário não for administrador.
     */
    public Evento cadastrarEvento(Usuario usuario, String nome, String descricao, Date data) {
        if (usuario.getAdmin()) {
            Evento evento = new Evento(nome, descricao, data);
            eventos.add(evento);
            dataStore.salvarEventos(eventos);
            return evento;
        } else {
            throw new SecurityException("Somente administradores podem cadastrar eventos.");
        }
    }

    /**
     * Adiciona um assento a um evento específico.
     *
     * @param nomeEvento O nome do evento.
     * @param assento O assento a ser adicionado ao evento.
     */
    public void adicionarAssentoEvento(String nomeEvento, String assento) {
        for (Evento evento : eventos) {
            if (evento.getNome().equals(nomeEvento)) {
                evento.adicionarAssento(assento);
                dataStore.salvarEventos(eventos);
                return;
            }
        }
        throw new IllegalArgumentException("Evento " + nomeEvento + " não encontrado.");
    }

    /**
     * Realiza a compra de um ingresso para um evento e assento específicos.
     *
     * @param usuario O usuário que está comprando o ingresso.
     * @param nomeEvento O nome do evento.
     * @param assento O assento a ser comprado.
     * @return O ingresso comprado ou null se o evento ou assento não estiverem disponíveis.
     */
    public Ingresso comprarIngresso(Usuario usuario, String nomeEvento, String assento) {
        for (Evento evento : eventos) {
            if (evento.getNome().equals(nomeEvento) && evento.isAtivo()) {
                if (evento.getAssentosDisponiveis().contains(assento)) {
                    Ingresso ingresso = new Ingresso(evento, 1, assento);
                    usuario.getIngressos().add(ingresso);

//                    if (!usuarios.contains(usuario)) {
//                        usuarios.add(usuario);
//                    }

                    dataStore.salvarUsuarios(usuarios);
                    compras.add(new Compra(ingresso, usuario));
                    evento.removerAssento(assento);
                    dataStore.salvarCompras(compras);
                    dataStore.salvarEventos(eventos);
                    return ingresso;
                } else {
                    throw new IllegalArgumentException("Assento " + assento + " não disponível.");
                }
            }
        }
        throw new IllegalArgumentException("Evento " + nomeEvento + " não encontrado ou inativo.");
    }

    /**
     * Cancela a compra de um ingresso e adiciona o assento de volta ao evento.
     *
     * @param usuario O usuário que deseja cancelar o ingresso.
     * @param ingresso O ingresso a ser cancelado.
     * @return true se o ingresso foi cancelado com sucesso, false caso contrário.
     */
    public Boolean cancelarCompra(Usuario usuario, Ingresso ingresso) {
        if (!usuarios.contains(usuario)) {
            throw new IllegalArgumentException("Usuário não encontrado.");
        }

        if (!usuario.getIngressos().contains(ingresso)) {
            throw new IllegalArgumentException("Ingresso não encontrado para este usuário.");
        }

        for (Compra compra : compras) {
            if (compra.getUsuario().equals(usuario) && compra.getIngresso().equals(ingresso)) {
                boolean cancelado = ingresso.cancelar();
                if (cancelado) {
                    usuario.getIngressos().remove(ingresso);
                    dataStore.salvarUsuarios(usuarios);
                    ingresso.getEvento().adicionarAssento(ingresso.getAssento());
                    compra.cancelarCompra();
                    dataStore.salvarCompras(compras);
                    dataStore.salvarEventos(eventos);
                    return true;
                } else {
                    return false;
                }
            } else {
                throw new RuntimeException("Erro no cancelamento da compra");
            }
        }
        return null;
    }

    /**
     * Lista todos os eventos disponíveis no sistema.
     *
     * @return Uma lista de eventos disponíveis.
     */
    public List<Evento> listarEventosDisponiveis() {
        return eventos;
    }

    /**
     * Lista todos os ingressos comprados por um usuário.
     *
     * @param usuario O usuário para o qual listar os ingressos.
     * @return Uma lista de ingressos comprados pelo usuário.
     */
    public List<Ingresso> listarIngressosComprados(Usuario usuario) {
        return usuario.getIngressos();
    }

    public void adicionarFeedback(Evento evento, Usuario usuario, String avaliacao, String comentario){
        Feedback feedback = new Feedback(usuario, avaliacao, comentario);
        evento.getFeedbacks().add(feedback);
        dataStore.salvarEventos(eventos);
    }

    public void adicionarCartao(Usuario usuario, String numeroCartao, String nome) {
        Cartao cartao = new Cartao(numeroCartao, nome);
        usuario.getFormasDePagamento().add(cartao);
        dataStore.salvarUsuarios(usuarios);
    }

    public void adicionarBoleto(Usuario usuario, String codigoBoleto) {
        Boleto boleto = new Boleto(codigoBoleto);
        usuario.getFormasDePagamento().add(boleto);
        dataStore.salvarUsuarios(usuarios);
    }
}
