package vendaingressos;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;

import java.io.*;
import java.lang.reflect.Modifier;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class DataStore {
    private static final String USUARIOS_JSON = "usuarios.json";
    private static final String EVENTOS_JSON = "eventos.json";
    private static final String COMPRAS_JSON = "compras.json";
    private static final Gson gson = new GsonBuilder()
            .setPrettyPrinting()
            .excludeFieldsWithModifiers(Modifier.STATIC, Modifier.FINAL, Modifier.TRANSIENT)
            .create();

    //Métodos para salvar dados
    public void salvarUsuarios(List<Usuario> usuarios) {
        salvarDados(USUARIOS_JSON, usuarios);
    }
    public void salvarEventos(List<Evento> eventos) {
        salvarDados(EVENTOS_JSON, eventos);
    }

    public void salvarCompras(List<Compra> compras) {
        salvarDados(COMPRAS_JSON, compras);
    }


    // Métodos para carregar dados
    public List<Usuario> carregarUsuarios() {
        return carregarDados(USUARIOS_JSON, Usuario[].class);
    }
    public List<Evento> carregarEventos() {
        return carregarDados(EVENTOS_JSON, Evento[].class);
    }
    public List<Compra> carregarCompras() {
        return carregarDados(COMPRAS_JSON, Compra[].class);
    }


    //Métodos para Salvar e Carregar dados
    public <T> void salvarDados(String filePath, List<T> dados) {
        try (FileWriter writer = new FileWriter(filePath)) {
            gson.toJson(dados, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public <T> List<T> carregarDados(String filePath, Class<T[]> clazz) {
        try (FileReader reader = new FileReader(filePath)) {
            JsonElement jsonElement = JsonParser.parseReader(reader);

            if (jsonElement.isJsonArray()) {
                T[] dataArray = gson.fromJson(jsonElement, clazz);
                return new ArrayList<>(Arrays.asList(dataArray));
            } else if (jsonElement.isJsonObject()) {
                T dataObject = (T) gson.fromJson(jsonElement, clazz.getComponentType());
                return new ArrayList<>(Collections.singletonList(dataObject));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }
}

