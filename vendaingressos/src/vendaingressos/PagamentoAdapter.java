package vendaingressos;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;
import java.io.IOException;

public class PagamentoAdapter extends TypeAdapter<Pagamento> {
    @Override
    public void write(JsonWriter out, Pagamento pagamento) throws IOException {
        out.beginObject();
        out.name("tipo").value(pagamento instanceof Boleto ? "Boleto" : "Cartao");

        if (pagamento instanceof Boleto) {
            out.name("codigoBoleto").value(((Boleto) pagamento).getCodigoBoleto());
        } else if (pagamento instanceof Cartao) {
            out.name("numeroCartao").value(((Cartao) pagamento).getNumero());
            out.name("nome").value(((Cartao) pagamento).getNome());
        }
        out.name("forma").value(pagamento.getForma());
        out.endObject();
    }

    @Override
    public Pagamento read(JsonReader in) throws IOException {
        in.beginObject();
        String tipo = null;
        String forma = null;
        String codigoBoleto = null;
        String numeroCartao = null;
        String nome = null;

        while (in.hasNext()) {
            String fieldName = in.nextName();
            if (fieldName.equals("tipo") && in.peek() == JsonToken.STRING) {
                tipo = in.nextString();
            } else if (fieldName.equals("forma") && in.peek() == JsonToken.STRING) {
                forma = in.nextString();
            } else if (fieldName.equals("codigoBoleto") && in.peek() == JsonToken.STRING) {
                codigoBoleto = in.nextString();
            } else if (fieldName.equals("numeroCartao") && in.peek() == JsonToken.STRING) {
                numeroCartao = in.nextString();
            } else if (fieldName.equals("nome") && in.peek() == JsonToken.STRING) {
                nome = in.nextString();
            } else {
                in.skipValue();
            }
        }
        in.endObject();

        if ("Boleto".equals(tipo)) {
            return new Boleto(codigoBoleto);
        } else if ("Cartao".equals(tipo)) {
            return new Cartao(numeroCartao, nome);
        } else {
            throw new IOException("Tipo de pagamento não identificado: " + tipo);
        }
    }
}
