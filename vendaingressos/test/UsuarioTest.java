
import vendaingressos.Boleto;
import vendaingressos.Cartao;
import vendaingressos.Pagamento;
import vendaingressos.Usuario;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;


public class UsuarioTest {

    @Test
    public void testCadastrarUsuario() {
        Usuario usuario = new Usuario("johndoe", "senha123", "John Doe", "12345678901", "john.doe@example.com", false);

        assertNotNull(usuario);
        assertEquals("johndoe", usuario.getLogin());
        assertEquals("John Doe", usuario.getNome());
        assertEquals("12345678901", usuario.getCpf());
        assertEquals("john.doe@example.com", usuario.getEmail());
        assertFalse(usuario.isAdmin());
    }

    @Test
    public void testCadastrarUsuarioAdmin() {
        Usuario admin = new Usuario("admin", "senha123", "Admin User", "00000000000", "admin@example.com", true);

        assertNotNull(admin);
        assertEquals("admin", admin.getLogin());
        assertEquals("Admin User", admin.getNome());
        assertEquals("00000000000", admin.getCpf());
        assertEquals("admin@example.com", admin.getEmail());
        assertTrue(admin.isAdmin());
    }

    @Test
    public void testLogin() {
        Usuario usuario = new Usuario("johndoe", "senha123", "John Doe", "12345678901", "john.doe@example.com", false);

        assertTrue(usuario.login("johndoe", "senha123"));
        assertFalse(usuario.login("johndoe", "senhaErrada"));
    }

    @Test
    public void testAtualizarSenha() {
        Usuario usuario = new Usuario("johndoe", "senha123", "John Doe", "12345678901", "john.doe@example.com", false);

        usuario.setSenha("novaSenha123");
        assertTrue(usuario.login("johndoe", "novaSenha123"));
        assertFalse(usuario.login("johndoe", "senha123"));
    }

    @Test
    public void testDadosUsuario() {
        Usuario usuario = new Usuario("johndoe", "senha123", "John Doe", "12345678901", "john.doe@example.com", false);

        usuario.setNome("Jonathan Doe");
        usuario.setCpf("10987654321");
        usuario.setEmail("jon.doe@example.com");

        assertEquals("Jonathan Doe", usuario.getNome());
        assertEquals("10987654321", usuario.getCpf());
        assertEquals("jon.doe@example.com", usuario.getEmail());
    }

    @Test
    public void testUsuarioDuplicado() {
        Usuario usuario1 = new Usuario("johndoe", "senha123", "John Doe", "12345678901", "john.doe@example.com", false);
        Usuario usuario2 = new Usuario("johndoe", "senha456", "John Doe", "12345678901", "john.doe@example.com", false);

        assertEquals(usuario1, usuario2);
    }

    @Test
    public void testEditarUsuario() {
        Usuario usuario = new Usuario("usuario123", "senha123", "Joao Marcelo", "12345678902", "jm.com", false);

        assertEquals("Joao Marcelo", usuario.getNome());
        assertEquals("jm.com", usuario.getEmail());
        assertEquals("senha123", usuario.getSenha());

        usuario.setNome("Joao Fernandes");
        usuario.setEmail("jmf.com");
        usuario.setSenha("novasenha123");

        assertEquals("Joao Fernandes", usuario.getNome());
        assertEquals("jmf.com", usuario.getEmail());
        assertEquals("novasenha123", usuario.getSenha());
    }

//    @Test
//    public void testAdicionarFormaDePagamento() {
//        Usuario usuario = new Usuario("johndoe", "senha123", "John Doe", "12345678901", "john.doe@example.com", false);
//
//        Pagamento cartao = new Cartao(usuario, "1234 5678 9123 4567", "John Doe");
//        usuario.adicionarFormaDePagamento(cartao);
//        Pagamento boleto = new Boleto("23798.12345.34567.890123.456789");
//        usuario.adicionarFormaDePagamento(boleto);
//
//        List<Pagamento> formasDePagamento = usuario.listarFormasDePagamento();
//
//        assertEquals(2, formasDePagamento.size());
//        assertEquals("Cartão de crédito", formasDePagamento.get(0).getForma());
//        assertEquals("Boleto Bancário", formasDePagamento.get(1).getForma());
//    }
}
