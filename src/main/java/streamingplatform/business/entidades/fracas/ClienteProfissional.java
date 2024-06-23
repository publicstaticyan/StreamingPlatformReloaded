package streamingplatform.business.entidades.fracas;

import streamingplatform.business.entidades.Cliente;
import streamingplatform.business.entidades.Midia;
import streamingplatform.business.exceptions.MidiaNaoEncontradaException;
import streamingplatform.business.interfaces.IClienteComentador;
import streamingplatform.business.interfaces.IClienteProfissional;

public class ClienteProfissional implements IClienteComentador, IClienteProfissional {

	@Override
	public void assistirMidia(Midia midia, Cliente watcher) throws MidiaNaoEncontradaException {
		watcher.assistirMidia(midia);
	}

    /**
     * Cria uma nova avaliação de uma mídia ao chamar o método criarAvaliacao(Cliente, int, String) da classe Midia.
     *
     * @param midia Mídia a ser avaliada
     * @param avaliador Cliente que está avaliando
     * @param nota  Nota a ser atribuída à mídia avaliada
     * @param comentario Comentário a ser atribuído na mídia avaliada
     * @throws IllegalStateException Caso este cliente já tenha avaliado a mesma mídia anteriormente
     */
	@Override
	public void avaliarMidia(Midia midia, Cliente avaliador, int nota, String comentario) throws IllegalStateException {
		midia.criarAvaliacao(avaliador, nota, comentario);
	}
}
