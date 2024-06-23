package streamingplatform.business.exceptions;

public class ClienteJaExisteException extends Exception {
	
	public ClienteJaExisteException(String id) {
		super("O cliente " + id + " já existe na plataforma de streaming");
	}

}
