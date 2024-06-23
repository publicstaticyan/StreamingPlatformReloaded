package streamingplatform.business.exceptions;

public class LoginInvalidoException extends Exception {

	public LoginInvalidoException(String user, String password) {
		super("Não foi encontrado nenhum usuário com as credenciais: " + user + " | " + password + "\n");
	}
	
}
