package streamingplatform.business.entidades;

public class Avaliacao {
    // ATRIBUTOS
    private int nota;
    private Cliente cliente;
    private String comentario;

    // CONSTRUTORES
    public Avaliacao(Cliente cliente, int nota, String comentario) {
        this.setCliente(cliente);
        this.setTexto(comentario);
        this.setNota(nota);
    }

    // GETTERS E SETTERS
    public int getNota() {
        return nota;
    }

    /**
     * Valida se a nota recebida como parâmetro está dentro do intervalo fechado de 1 a 5 e atribui valor ao campo 'nota'.
     * Caso contrário, lança a exceção IllegalArgumentException.
     *
     * @param nota
     */
    public void setNota(int nota) {
        if (nota >= 1 && nota <= 5)
            this.nota = nota;
        else
            throw new IllegalArgumentException("A nota da avalição não pode ser inferior a 1 ou superior a 5");
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        if (cliente != null)
            this.cliente = cliente;
        else
            throw new IllegalArgumentException("O cliente avaliador não pode ser nulo");
    }

    public String getTexto() {
        return comentario;
    }

    public void setTexto(String comentario) {
        this.comentario = comentario;
    }

    // MÉTODOS

    /**
     * Sobrepõe o método equals() da classe Java Object a fim de modificar o resultado da comparação entre dois objetos.
     * Para isso, realiza downcast para Avaliação, possibilitando comparar o cliente avaliador de ambos objetos. Caso
     * possuam o mesmo cliente avaliador, o método assegura que os dois objetos se tratam de uma mesma avaliação.
     *
     * @param o Objeto a ser comparado com this.
     * @return Se os clientes avaliadores são iguais.
     */
    @Override
    public boolean equals(Object o) {
        Avaliacao outra = (Avaliacao) o;
        return this.cliente.equals(outra.cliente);
    }

    /**
     * Sobrepoe o método toString() da classe Java Object a fim de modificar o resultado da impressão tela ao se passar
     * um objeto da classe Avaliacao como parâmetro do método print().
     *
     * @return string contendo nome do usuário avaliador, nota e comentário da avaliação
     */
    @Override
    public String toString() {
        return " Avaliador: " + cliente.getNomeUsuario() +
                "\n Nota: " + nota +
                "\n Comentário: " + comentario;
    }
}
