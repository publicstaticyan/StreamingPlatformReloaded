import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Date;
import streamingplatform.business.entidades.Midia;
import streamingplatform.business.entidades.fracas.Serie;
import streamingplatform.business.entidades.fracas.Filme;
import streamingplatform.business.entidades.Cliente;
import streamingplatform.business.exceptions.MidiaNaoEncontradaException;

public class MidiaTest {
    public static Midia midia1, midia2;
    public static Cliente cliente1, cliente2, cliente3;

    @BeforeEach
    public void setUp() {
        midia1 = new Serie("3459", "Light","Comédia","Inglês",new Date(), false, 25);
        midia2 = new Filme("9361", "Heroes And Captains", "Suspense", "Esperanto", new Date(), false, 140);
        cliente1 = new Cliente("Lucas", "123456", "pwd");
        cliente2 = new Cliente("Pedro", "123457", "senhaSenha");
        cliente3 = new Cliente("Jonas", "123458", "insert");
    }

    @Test
    public void testCriarAvaliacaoMidia() {
        midia1.criarAvaliacao(cliente1, 5, "Grande serie");
        midia2.criarAvaliacao(cliente2, 4, "Bom, bom, muito bom!");

        assertEquals(5, midia1.getAvaliacoes().get(0).getNota());
        assertEquals(cliente1, midia1.getAvaliacoes().get(0).getCliente());
        assertEquals("Grande serie", midia1.getAvaliacoes().get(0).getTexto());

        assertEquals(4, midia2.getAvaliacoes().get(0).getNota());
        assertEquals(cliente2, midia2.getAvaliacoes().get(0).getCliente());
        assertEquals("Bom, bom, muito bom!", midia2.getAvaliacoes().get(0).getTexto());
    }

    @Test
    public void testMediaAvaliacoes() {
        midia1.criarAvaliacao(cliente1, 5, "Muito boa série");
        midia1.criarAvaliacao(cliente2, 3, "Série superestimada");
        midia1.criarAvaliacao(cliente3, 1, "Vi três series ruins esse ano: essa aqui conta como duas");

        midia2.criarAvaliacao(cliente1, 1, "DECEPICIONANTE!!!");
        midia2.criarAvaliacao(cliente3, 5, "Quem não gosta disso é louco.");

        assertEquals(3, midia1.mediaAvaliacoes());
        assertEquals(3, midia1.mediaAvaliacoes());
    }

    @Test
    public void testAudienciaMidia() throws IllegalStateException, MidiaNaoEncontradaException {
        cliente1.assistirMidia(midia1);
        cliente1.assistirMidia(midia2);
        cliente2.assistirMidia(midia1);
        cliente3.assistirMidia(midia1);

        assertEquals(3, midia1.getAudiencia());
        assertEquals(1, midia2.getAudiencia());
    }
}
