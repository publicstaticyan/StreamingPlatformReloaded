package streamingplatform.business.entidades.fracas;

import streamingplatform.business.entidades.Midia;

import java.util.Date;

public class Filme extends Midia {

    // ATRIBUTOS
    private int duracao;

     // CONSTRUTORES
    public Filme(String id, String nome, String genero, String idioma, Date lancamento, boolean isLancamento, int duracao){
        super(id, nome, genero, idioma, lancamento, isLancamento);
        setDuracao(duracao);
    }

    // GETTERS E SETTERS
    public int getDuracao() {
        return this.duracao;
    }

    public void setDuracao(int duracao) {
        if (duracao > 0)
            this.duracao = duracao;
        else
            throw new IllegalArgumentException("A duração do filme deve ser superior a 0");
    }

    // MÉTODOS

    /**
     * Extende o método toString() da classe Midia a fim de modificar o resultado da impressão tela ao se passar
     * um objeto da classe Filme como parâmetro do método print().
     *
     * @return string atualizada com o conteúdo de toString() de mídia e duração do filme em segundos
     */
    @Override
    public String toString(){
        return (super.toString() + "\nDuração: " + this.duracao + " segundos");
    }
}
