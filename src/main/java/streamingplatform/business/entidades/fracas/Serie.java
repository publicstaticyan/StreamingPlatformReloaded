package streamingplatform.business.entidades.fracas;

import streamingplatform.business.entidades.Midia;

import java.util.Date;

public class Serie extends Midia {

    // ATRIBUTOS
    private int quantidadeEpisodios;

    // CONSTRUTORES
    public Serie(String id, String nome, String genero, String idioma, Date lancamento, boolean isLancamento, int quantidadeEpisodios){
        super(id, nome, genero, idioma, lancamento, isLancamento);
        setQuantidadeEpisodios(quantidadeEpisodios);
    }

    // GETTERS E SETTERS
    public int getQuantidadeEpisodios() {
        return this.quantidadeEpisodios;
    }

    public void setQuantidadeEpisodios(int quantidadeEpisodios){
        if (quantidadeEpisodios > 0)
            this.quantidadeEpisodios = quantidadeEpisodios;
        else
            throw new IllegalArgumentException("A quantidade de episódios da série deve ser superior a 0");
    }

    // MÉTODOS

    /**
     * Extende o método toString() da classe Midia a fim de modificar o resultado da impressão tela ao se passar
     * um objeto da classe Serie como parâmetro do método print().
     *
     * @return string atualizada com o conteúdo de toString() de mídia e quantidade de episódios da série
     */
    @Override
    public String toString(){
        return (super.toString() + "\nQtd de eps.: " + this.quantidadeEpisodios);
    }
}

