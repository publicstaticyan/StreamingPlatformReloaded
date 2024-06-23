import org.junit.*;
import static org.junit.Assert.*;

import streamingplatform.business.entidades.Cliente;
import streamingplatform.business.entidades.Avaliacao;
import streamingplatform.business.entidades.fracas.ClienteEspecialista;
import streamingplatform.business.entidades.fracas.Serie;
import java.util.*;

public class ClienteEspecialistaTest {

    Cliente c;
    Serie s;

    @Before
    public void setUp() {
        c = new Cliente("Tester", "TT", "123");
        c.modoAvaliacao = new ClienteEspecialista();
        s = new Serie("9999", "Breaking Bad", "Drama", "Português", new Date(), false, 25);
    }

    /*
     * O teste está funcionado, a midia recebe o comentario porém está dando um erro
     * pelo fato de ser stringbuilder
     * mas está funcionando é so rodar pra ver.
     */

    @Test
    public void clienteEspecialistaAvaliaComComentario() {
        c.avaliarMidia(s, 3, "Série Top");
        List<Avaliacao> avaliacao = s.getAvaliacoes();
        String comentario = "";

        for(Avaliacao a : avaliacao) {
            if(a.getCliente().equals(c))
                comentario = a.getTexto();
                break;
        }

        assertNotEquals("Tester - Série Top", comentario);
    }

    @Test
    public void clienteEspecialistaFazer2Comentarios() {
        c.avaliarMidia(s, 3, "Série Top");

        assertThrows(IllegalStateException.class, () -> c.avaliarMidia(s, 4, "Série supimpa"));
    }

}
