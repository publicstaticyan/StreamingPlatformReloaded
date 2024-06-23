import streamingplatform.business.entidades.Cliente;
import streamingplatform.business.entidades.Midia;
import streamingplatform.business.entidades.fracas.Serie;
import streamingplatform.utils.Lista;

import static org.junit.Assert.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Date;

public class ClienteTest {
    Serie s;
    Cliente c, c2;
    Lista<Midia> l;

    @BeforeEach
    public void setUp() {
        s = new Serie("9999", "Breaking Bad", "Drama", "Português", new Date(), false, 25);
        c = new Cliente("xX_TesterCraft_Xx", "10", "teste123");
        c2 = new Cliente("Testerrr", "12", "teste321");
        l = new Lista<>(); // Cria uma lista secundária de Séries

        c.adicionarNaListaParaVer(s); // Adiciona a série na lista de series do cliente

    }

    @Test
    public void filtrarPorGeneroTest() {
        c.adicionarNaListaParaVer(s);
        l = c.filtrarPorGenero("Drama"); // A lista de séries recebe a serie filtrada pelo genero

        assertEquals(1, l.size());
    }

    @Test
    public void filtrarPorIdiomaTest() {
        c.adicionarNaListaParaVer(s);
        l = c.filtrarPorIdioma("Português"); // A lista de séries recebe a serie filtrada pelo genero

        assertEquals(1, l.size());
    }

    @Test
    public void adicionarNaLista() {
        assertEquals(1, c.tamanhoListaParaVer());
    }

    @Test
    public void removerItemDaLista() {
        c.retirarDaListaParaVer(s);

        assertEquals(0, c.tamanhoListaParaVer());
    }

    @Test
    public void adicionarSerieRepetida() {
        c.adicionarNaListaParaVer(s);
        c.adicionarNaListaParaVer(s);

        assertEquals(1, c.tamanhoListaParaVer());
    }

    @Test
    public void adicionarListaJaVisto() {
        c.adicionarNaListaJaVistos(s);

        assertEquals(1, c.tamanhoListaJaVistos());
    }

    @Test
    public void testPrint() {
        assertEquals("Usuário: xX_TesterCraft_Xx\n" +
                "Senha: teste123\n" +
                "Modo de avaliação: Cliente Regular", c.toString());
    }

    // @SuppressWarnings("deprecation")
    // @Test
    // public void avaliarMidiaTest() {
    // c.avaliarMidia(s, 3);
    // c2.avaliarMidia(s, 5);

    // assertEquals(4.0,s.getMediaNota());

    // }

}