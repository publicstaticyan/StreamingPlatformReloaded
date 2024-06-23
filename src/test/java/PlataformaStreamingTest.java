import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Date;
import streamingplatform.business.PlataformaStreaming;
import streamingplatform.business.entidades.Cliente;
import streamingplatform.business.entidades.fracas.Filme;
import streamingplatform.business.entidades.fracas.Serie;
import streamingplatform.business.exceptions.ClienteJaExisteException;
import streamingplatform.business.exceptions.MidiaJaExisteException;
import streamingplatform.business.exceptions.LoginInvalidoException;

class PlataformaStreamingTest {

	public static PlataformaStreaming plataforma;
	public static Cliente cliente;
	public static Serie serie;
	public static Serie serie1;
	public static Serie serie2;
	public static Serie serie3;
	public static Filme filme;

	@BeforeEach
	void iniciaTeste() {
		plataforma = new PlataformaStreaming("Xam OBH");
		try {
			plataforma.adicionarCliente("Joao", "Joao123", "senha");
		} catch (NullPointerException e) {
			e.printStackTrace();
		} catch (ClienteJaExisteException e) {
			System.out.println(e.getMessage());
		}

		serie = new Serie("11111", "Infinity Train", "Animação", "Inglês", new Date(), false, 10);
		serie1 = new Serie("22222", "One Piece", "Animação", "Português", new Date(), false, 1060);
		serie2 = new Serie("33333", "The Last of Us", "Suspense", "Português", new Date(), false, 9);
		serie3 = new Serie("44444", "Brooklyn Nine-Nine", "Comédia", "Português", new Date(), false, 10);
		filme = new Filme("55555", "Gato de Botas 2", "Animação", "Portugûes", new Date(), false, 6000);
	}

	@Test
	void testLoginInvalido() throws LoginInvalidoException {
		assertThrows(LoginInvalidoException.class, () -> {
			plataforma.login("Maria", "123");
		});
	}

	@Test
	void testAdicionarClienteJaExistente() throws NullPointerException, ClienteJaExisteException {
		assertThrows(ClienteJaExisteException.class, () -> {
			plataforma.adicionarCliente("Joao", "Joao123", "senha");
		});
	}

	@Test
	void testAdicionarSerie() throws NullPointerException, MidiaJaExisteException {
		plataforma.adicionarMidia(1, serie);
		assertEquals(true, plataforma.getSeries().toString().contains("[Nome: Infinity Train\n" +
				"Id: 11111\n" +
				"Gênero: Animação\n" +
				"Idioma: Inglês\n" +
				"É lançamento: false\n" +
				"Nota média: 0.0\n" +
				"Avaliações: []"));
	}

	@Test
	void testAdicionarFilme() throws NullPointerException, MidiaJaExisteException {
		plataforma.adicionarMidia(1, filme);
		assertEquals(true, plataforma.getFilmes().toString().contains("[Nome: Gato de Botas 2\n" +
				"Id: 55555\n" +
				"Gênero: Animação\n" +
				"Idioma: Portugûes\n" +
				"É lançamento: false\n" +
				"Nota média: 0.0\n" +
				"Avaliações: []"));
	}

	@Test
	void testAdicionarSerieJaExistente() throws NullPointerException, MidiaJaExisteException {
		assertThrows(MidiaJaExisteException.class, () -> {
			plataforma.adicionarMidia(1, serie);
			plataforma.adicionarMidia(1, serie);
		});
	}

	@Test
	void testAdicionarFilmeJaExistente() throws NullPointerException, MidiaJaExisteException {
		assertThrows(MidiaJaExisteException.class, () -> {
			plataforma.adicionarMidia(1, filme);
			plataforma.adicionarMidia(1, filme);
		});
	}



}
