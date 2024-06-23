package streamingplatform.business.interfaces;
import streamingplatform.business.entidades.Cliente;
import streamingplatform.business.entidades.Midia;

public interface IClienteComentador {

	void avaliarMidia(Midia midia, Cliente avaliador, int nota, String comentario) throws IllegalStateException;

}
