package vendaingressos;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.*;
import java.lang.reflect.Modifier;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class DataStore {
    private static final String USUARIOS_JSON = "usuarios.json";
    private static final String EVENTOS_JSON = "eventos.json";
    private static final String INGRESSOS_JSON = "ingressos.json";
    private static final String COMPRAS_JSON = "compras.json";
    private static final String FEEDBACKS_JSON = "feedbacks.json";
    private static final String FORMASDEPAGAMENTO_JSON = "formasdepagamentos.json";
    private static final Gson gson = new GsonBuilder()
            .excludeFieldsWithModifiers(Modifier.STATIC, Modifier.FINAL, Modifier.TRANSIENT)
            .create();

    //Métodos para salvar dados
    public void salvarUsuarios(List<Usuario> usuarios) {
        salvarDados(USUARIOS_JSON, usuarios);
    }
    public void salvarEventos(List<Evento> eventos) {
        salvarDados(EVENTOS_JSON, eventos);
    }
    public void salvarIngressos(List<Ingresso> ingressos) {
        salvarDados(INGRESSOS_JSON, ingressos);
    }
    public void salvarCompras(List<Compra> compras) {
        salvarDados(COMPRAS_JSON, compras);
    }
    public void salvarFeedbacks(List<Feedback> feedbacks) {
        salvarDados(FEEDBACKS_JSON, feedbacks);
    }
    public void salvarFormasDEPagamento(List<Pagamento> formasDePagamento) {
        salvarDados(FORMASDEPAGAMENTO_JSON, formasDePagamento);
    }

    // Métodos para carregar dados
    public List<Usuario> carregarUsuarios() {
        return carregarDados(USUARIOS_JSON, new TypeToken<List<Usuario>>() {}.getType());
    }
    public List<Evento> carregarEventos() {
        return carregarDados(EVENTOS_JSON, new TypeToken<List<Evento>>() {}.getType());
    }
    public List<Ingresso> carregarIngressos() {
        return carregarDados(INGRESSOS_JSON, new TypeToken<List<Ingresso>>() {}.getType());
    }
    public List<Compra> carregarCompras() {
        return carregarDados(COMPRAS_JSON, new TypeToken<List<Compra>>() {}.getType());
    }
    public List<Feedback> carregarFeedbacks() {
        return carregarDados(FEEDBACKS_JSON, new TypeToken<List<Feedback>>() {}.getType());
    }
    public List<Pagamento> carregarPagamentos() {
        return carregarDados(FORMASDEPAGAMENTO_JSON, new TypeToken<List<Pagamento>>() {}.getType());
    }

    //Métodos para Salvar e Carregar dados
    private <T> void salvarDados(String fileName, List<T> dados) {
        try (Writer writer = new FileWriter(fileName)) {
            gson.toJson(dados, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private <T> List<T> carregarDados(String fileName, Type tipo) {
        File file = new File(fileName);
        if (!file.exists() || file.length() == 0) {
            return new ArrayList<>();
        }
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            if (!reader.ready() || reader.lines().allMatch(String::isBlank)) {
                return new ArrayList<>();
            }
            List<T> dados = gson.fromJson(reader, tipo);
            return dados != null ? dados : new ArrayList<T>();
        } catch (FileNotFoundException e) {
            return new ArrayList<>();
        } catch (IOException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }


}
