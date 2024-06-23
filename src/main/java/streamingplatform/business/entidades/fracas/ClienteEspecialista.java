package streamingplatform.business.entidades.fracas;

import streamingplatform.business.entidades.Cliente;
import streamingplatform.business.entidades.Midia;
import streamingplatform.business.interfaces.IClienteComentador;

public class ClienteEspecialista implements IClienteComentador {

    /**
     * Chama o método criarAvaliacao(Cliente, int, String) de mídia.
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
