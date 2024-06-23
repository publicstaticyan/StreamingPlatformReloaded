package streamingplatform.business;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

import streamingplatform.business.entidades.Avaliacao;
import streamingplatform.business.entidades.Cliente;
import streamingplatform.business.entidades.Midia;
import streamingplatform.business.entidades.fracas.ClienteEspecialista;
import streamingplatform.business.entidades.fracas.Filme;
import streamingplatform.business.entidades.fracas.Serie;
import streamingplatform.business.exceptions.ClienteJaExisteException;
import streamingplatform.business.exceptions.LoginInvalidoException;
import streamingplatform.business.exceptions.MidiaJaExisteException;
import streamingplatform.business.exceptions.MidiaNaoEncontradaException;
import streamingplatform.utils.BooleanParser;
import streamingplatform.utils.Lista;

public class PlataformaStreaming {

    // ATRIBUTOS
    private final String nome;
    private Cliente clienteAtual;
    private final Map<Integer, Midia> midias;
    private final HashMap<String, Cliente> clientes;

    public enum MidiaType {
        FILMES, SERIES
    }

    // CONSTRUTORES
    public PlataformaStreaming(String nome) {
        this.nome = nome;
        this.midias = new HashMap<>();
        this.clientes = new HashMap<>();

        /* Carregar dados */
        try {
            this.carregarClientes();
            this.carregarMidias();
            this.carregarAudiencia();
            this.carregarAvaliacoes();
        } catch (FileNotFoundException | MidiaNaoEncontradaException e) {
            System.err.print("Erro ao carregar dados da plataforma! " + e.getMessage());
        }
    }

    // GETTERS E SETTERS
    public HashMap<String, Cliente> getClientes() {
        return clientes;
    }

    public Midia findMidiaById(int id) {
        return this.midias.get(id);
    }

    public Map<Integer, Midia> getMidiasMap() {
        return this.midias;
    }

    public List<Midia> getSeries() {
        return midias.values().stream().filter(midia -> midia instanceof Serie).collect(Collectors.toList());
    }

    public List<Midia> getFilmes() {
        return midias.values().stream().filter(midia -> midia instanceof Filme).collect(Collectors.toList());
    }

    public Cliente getClienteAtual() {
        return clienteAtual;
    }

    // MÉTODOS

    /**
     * Realiza operação de login a partir do nome de usuário e senha informados. Se um usuário correspondente for
     * encontrado, o cliente atual será definido como o cliente conectado. Caso contrário, lança
     * LoginInvalidoException.
     *
     * @param nomeUsuario Nome de usuário do cliente que tenta logar.
     * @param senha       Senha do cliente que tenta logar.
     * @throws LoginInvalidoException Se o nome de usuário e a senha fornecidos não corresponderem a nenhum cliente na
     *                                lista.
     */
    public void login(String nomeUsuario, String senha) throws LoginInvalidoException {
        for (Cliente cliente : this.clientes.values()) {
            if (cliente.getId().equals(nomeUsuario) && cliente.getSenha().equals(senha)) {
                this.clienteAtual = cliente;
                System.out.println("Login realizado com sucesso!");
                return;
            }
        }

        throw new LoginInvalidoException(nomeUsuario, senha);
    }

    /**
     * Adiciona um novo cliente à lista de clientes. Caso o cliente a ser adicionado já esteja previamente presente na
     * lista, a operação não é executada
     *
     * @param userId       Id do novo cliente
     * @param userName     Nome do novo cliente
     * @param userPassword Senha do novo cliente
     */
    public void adicionarCliente(String userName, String userId, String userPassword) throws ClienteJaExisteException {
        if (clientes.containsKey(userId)) throw new ClienteJaExisteException(userId);
        else {
            Cliente novoCliente = new Cliente(userName, userId, userPassword);
            clientes.put(userId, novoCliente);
        }
    }

    /**
     * Adiciona uma nova mídia ao map mídias, usando o id fornecido como chave.
     * Lança uma exceção se a mídia já existe.
     *
     * @param id        o ID da mídia a ser adicionada
     * @param novaMidia a mídia a ser adicionada ao mapa
     * @throws MidiaJaExisteException se a mídia já existe no mapa
     * @throws NullPointerException  se a novaMidia for nula
     */
    public void adicionarMidia(Integer id, Midia novaMidia) throws MidiaJaExisteException {
        if (novaMidia == null) {
            throw new NullPointerException();
        }

        if (this.midias.containsKey(id)) {
            throw new MidiaJaExisteException(novaMidia.getId());
        }

        this.midias.put(id, novaMidia);
    }

    /**
     * Lê o conteúdo do HashMap clientes e os escreve em um arquvivo .csv, sobrepondo o arquivo já existente de nome
     * Espectadores.csv. O salvamento deve ser realizado após execução do programa a fim de registrar em arquivo todas
     * as mudanças realizadas nos dados em memória
     *
     * @throws MidiaNaoEncontradaException
     */
    public void registrarAudiencia(Midia midia) throws NullPointerException, MidiaNaoEncontradaException {
        if (clienteAtual == null) {
            throw new NullPointerException();
        }

        clienteAtual.assistirMidia(midia);
    }

    /**
     * Invoca o método "filtrarPorGenero" do cliente atual, obtendo, assim, as séries das listas que correspondem ao
     * gênero selecionado.
     *
     * @param genero Gênero selecionado
     * @return Lista filtrada por gênero das séries encontradas
     * @throws NullPointerException Se não houver cliente atual na plataforma
     */
    public Lista<Midia> filtrarPorGenero(String genero) throws NullPointerException {
        if (clienteAtual == null) {
            throw new NullPointerException();
        }

        return clienteAtual.filtrarPorGenero(genero);
    }

    /**
     * Invoca o método "filtrarPorIdioma" do cliente atual, obtendo, assim, as séries das listas que correspondem ao
     * idioma selecionado.
     *
     * @param idioma Idioma selecionado
     * @return Lista filtrada por idioma das séries encontradas
     * @throws NullPointerException Se não houver cliente atual na plataforma
     */
    public Lista<Midia> filtrarPorIdioma(String idioma) throws NullPointerException {
        if (clienteAtual == null) {
            throw new NullPointerException();
        }

        return clienteAtual.filtrarPorIdioma(idioma);
    }

    /**
     * Invoca o método "filtrarPorQtdEpisodios" do cliente atual, obtendo, assim, as séries das listas que possuem a
     * quantidade de episódios selecionada.
     *
     * @param quantEpisodios Quantidade de episódios selecionada
     * @return Lista filtrada por idioma das séries encontradas
     * @throws NullPointerException Se não houver cliente atual na plataforma
     */
    public Lista<Midia> filtrarPorQtdEpisodios(int quantEpisodios) throws NullPointerException {
        if (clienteAtual == null) {
            throw new NullPointerException();
        }

        return clienteAtual.filtrarPorQtdEpisodios(quantEpisodios);
    }

    /**
     * Lê o arquivo "Espectadores.csv", ignorando a primeira linha do arquivo, instancia clientes a partir das
     * informações lidas e os adiciona à lista de clientes.
     *
     * @throws FileNotFoundException se o arquivo não for encontrado.
     */
    public void carregarClientes() throws FileNotFoundException {
        File file = new File("src/main/resources/db/Espectadores.csv");
        Scanner filereader = new Scanner(file);

        filereader.nextLine(); // Artifício para ignorar primeira linha do csv
        int linha = 0;

        while (filereader.hasNextLine()) {
            String[] split = filereader.nextLine().split(";");

            try {
                adicionarCliente(split[0], split[1], split[2]);
            } catch (NullPointerException e) {
                System.out.println(linha + ":" + split);
            } catch (ClienteJaExisteException e) {
                System.out.println(e.getMessage());
            }

            linha++;
        }

        filereader.close();

    }

    /**
     * Lê os arquivos "Filmes.csv" e "Series.csv" ignorando a primeira linha do arquivo, instancia midias a partir das
     * informações lidas e os adiciona ao mapa de midias.
     *
     * @throws FileNotFoundException se o arquivo não for encontrado.
     */
    public void carregarMidias() throws FileNotFoundException {

        for (MidiaType mt : MidiaType.values()) {

            File file = new File("src/main/resources/db/" + mt.name().substring(0, 1).toUpperCase() + mt.name().substring(1).toLowerCase() + ".csv");
            Scanner sc = new Scanner(file);

            sc.nextLine();

            int linha = 0;

            while (sc.hasNextLine()) {
                String[] dados = sc.nextLine().split(";");

                // Gera um número aleatório, limitado pelo tamanho do vetor de gêneros/idiomas,
                // como índice a fim de selecionar algum dos gêneros/idiomas disponíveis
                String novoGenero = Midia.GENEROS[(int) (Math.random() * (Midia.GENEROS.length))];
                String novoIdioma = Midia.IDIOMAS[(int) (Math.random() * (Midia.IDIOMAS.length))];

                // Atribui a uma data os valores de dia/mes/ano lidos no arquivo. Caso a string
                // não apresente formato válido, é lançada uma exceção
                Date novaData = null;
                try {
                    novaData = new SimpleDateFormat("dd/MM/yyyy").parse(dados[2]);
                } catch (Exception e) {
                    System.out.println("Erro: Formato inválido na leitura da data de lançamento de série");
                }

                Midia midia = null;

                switch (mt) {
                    case FILMES:
                        // Passa-se como parâmetros o nome conforme lido no arquivo (dados[1]), gênero,
                        // idioma, data de lançamento (dados[2]), se é lancamento (dados[3]) e duracao (dados[4])
                        // em segundos. Em seguida, insere-se o novo filme no hashmap
                        midia = new Filme(dados[0], dados[1], novoGenero, novoIdioma, novaData, BooleanParser.parseBooleanValue(dados[3]), Integer.parseInt(dados[4]) * 60);
                        break;

                    case SERIES:
                        // Passa-se como parâmetros o nome conforme lido no arquivo (dados[1]), gênero e
                        // idioma gerados aleatóriamente, novaData e qtd de episódios. Em seguida, insere-se a nova
                        // série no hashmap
                        midia = new Serie(dados[0], dados[1], novoGenero, novoIdioma, novaData, BooleanParser.parseBooleanValue(dados[3]), Integer.parseInt(dados[4]));
                        break;
                }

                try {
                    adicionarMidia(Integer.valueOf(dados[0]), midia);
                } catch (NumberFormatException | NullPointerException e) {
                    System.out.println(linha + ": " + dados);
                } catch (MidiaJaExisteException e) {
                    System.out.println(e.getMessage());
                }

                linha++;
            }

            sc.close();
        }
    }

    /**
     * Lê o arquivo "Audiencia.csv" e, conforme lido no arquivo, adiciona série à lista para assistir (F) ou registra
     * audiência de série já assistida pelo cliente (A). Caso exista registro de audiência de uma mídia lançamento para
     * um cliente não profissional, é capturada uma exceção IllegalStateException.
     *
     * @throws FileNotFoundException       se o arquivo não for encontrado.
     * @throws MidiaNaoEncontradaException se não for possível encontrar no catálogo mídia com id informado.
     */
    public void carregarAudiencia() throws FileNotFoundException, MidiaNaoEncontradaException {
        String arquivo = "src/main/resources/db/Audiencia.csv";

        try (BufferedReader br = new BufferedReader(new FileReader(arquivo))) {
            String line;
            while ((line = br.readLine()) != null) {
                StringTokenizer tokenizer = new StringTokenizer(line, ";");

                String userId = tokenizer.nextToken();
                String status = tokenizer.nextToken();
                int mediaId = Integer.parseInt(tokenizer.nextToken());

                if (clientes.containsKey(userId) && midias.containsKey(mediaId)) {
                    if (status.equals("F")) {
                        clientes.get(userId).adicionarNaListaParaVer(midias.get(mediaId));
                    } else if (status.equals("A")) {
                        try {
                            clientes.get(userId).assistirMidia(midias.get(mediaId));
                        }
                        catch (IllegalStateException e) {
                            System.out.println("Erro: " + e.getMessage());
                        }
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("Erro: não foi possível ler arquivo de dados de audiência");
        }
    }

    /**
     * Lê o arquivo "Avaliacoes.csv" e, conforme lido no arquivo, adiciona às midias as avaliações presentes, com nota e
     * comentário. No entanto, caso o cliente avaliador não seja do tipo cliente especialista, se adiciona à avaliação
     * apenas nota.
     *
     * @throws FileNotFoundException se o arquivo não for encontrado.
     */
    public void carregarAvaliacoes() throws FileNotFoundException, NullPointerException {
        File file = new File("src/main/resources/db/Avaliacoes.csv");
        Scanner filereader = new Scanner(file);

        filereader.nextLine(); // Pular primeira linha do arquivo

        while (filereader.hasNextLine()) {
            String[] dados = filereader.nextLine().split(";");

            Midia midiaAvaliada = null;

            // Verificar se mídia é filme, série ou se não existe na plataforma
            if (this.midias.containsKey(Integer.parseInt(dados[0])))
                midiaAvaliada = this.midias.get(Integer.parseInt(dados[0]));
            else throw new NullPointerException("Erro: A mídia a ser avaliada não existe");

            Cliente avaliador = this.clientes.get(dados[1]);

            if (avaliador.getModoAvaliacao() instanceof ClienteEspecialista) {
                avaliador.avaliarMidia(midiaAvaliada, Integer.parseInt(dados[2]), dados[3]);
            } else {
                avaliador.avaliarMidia(midiaAvaliada, Integer.parseInt(dados[2]));
            }
        }
    }

    /**
     * Lê o hashmap clientes e registra no arquivo Filmes.csv os atributos de cada cliente presente.
     */
    public void salvarClientes() {
        String csvFilename = "src/main/resources/db/Espectadores.csv";

        try (FileWriter writer = new FileWriter(csvFilename)) {
            writer.append("nomeDeUsuario; id; senha\n");

            this.clientes.forEach((key, value) -> {
                try {
                    writer.append(value.getNomeUsuario()).append(";").append(value.getId()).append(";").append(value.getSenha()).append("\n");
                } catch (IOException e) {
                    System.err.println("Erro: não foi escrever no arquivo para salvar dados do cliente.");
                }
            });

            System.out.println("Clientes salvos com sucesso!");

        } catch (IOException e) {
            System.err.println("Erro: não foi possível gerar arquivo para salvar dados do cliente.");
        }
    }

    /**
     * Lê o hashmap midias e, conforme tipo de mídia presente, grava os atributos de filme ou série, respectivamente,
     * nos arquivos Filmes.csv ou Series.csv.
     */
    public void salvarMidia() {
        for (MidiaType mt : MidiaType.values()) {
            try (FileWriter writer = new FileWriter("src/main/resources/db/" + mt.name().substring(0, 1).toUpperCase() + mt.name().substring(1).toLowerCase() + ".csv")) {

                writer.append("id; nome; data_lancamento; is_lancamento; ");

                switch (mt) {
                    case FILMES:
                        writer.append("duracao;\n");

                        for (Midia filme : getFilmes()) {
                            String lancamentoFormatted = new SimpleDateFormat("dd/MM/yyyy").format(filme.getLancamento());

                            try {
                                writer.append(filme.getId()).append(";")
                                        .append(filme.getNome()).append(";")
                                        .append(lancamentoFormatted).append(";")
                                        .append(String.valueOf(BooleanParser.stringify(filme.isLancamento()))).append(";")
                                        .append(String.valueOf(((Filme) filme).getDuracao() / 60));
                            } catch (IOException e) {
                                System.out.println("Erro: não foi possivel escrever no arquivo para salvar dados do filme.");
                            }

                            writer.append("\n");
                        }
                        break;

                    case SERIES:
                        writer.append("qtd_eps;\n");

                        for (Midia serie : getSeries()) {
                            String lancamentoFormatted = new SimpleDateFormat("dd/MM/yyyy").format(serie.getLancamento());

                            try {
                                writer.append(serie.getId()).append(";")
                                        .append(serie.getNome()).append(";")
                                        .append(lancamentoFormatted).append(";")
                                        .append(String.valueOf(BooleanParser.stringify(serie.isLancamento()))).append(";")
                                        .append(String.valueOf(((Serie) serie).getQuantidadeEpisodios()));
                            } catch (IOException e) {
                                System.out.println("Erro: não foi possivel escrever no arquivo para salvar dados do filme.");
                            }

                            writer.append("\n");
                        }
                        break;
                }

            } catch (IOException e) {
                System.out.println("Erro: não foi possível gerar arquivo para salvar dados da serie." + e.getMessage());
            }
        }

        System.out.println("Mídias salvas com sucesso!");
    }

    /**
     * Lê as listas de séries assistidas e para ver e registra no arquivo Audiencia.csv a audiência de cada uma delas no
     * formato idDoUsuario;F/A;idDaSerie, sendo que "A" significa que a série já foi assistida, e "F" significa que a
     * série está na lista para ver.
     */
    public void salvarAudiencia() {
        String arquivo = "src/main/resources/db/Audiencia.csv";

        try (FileWriter writer = new FileWriter(arquivo)) {
            this.getClientes().forEach((key, value) -> {

                // Séries assistidas
                for (Midia serieAssistida : value.getListaJaVistas().toList()) {
                    try {
                        writer.append(value.getId());
                        writer.append(";");
                        writer.append("A");
                        writer.append(";");
                        writer.append(serieAssistida.getId());
                        writer.append("\n");
                    } catch (IOException e) {
                        System.out.println("Erro: não foi possivel escrever no arquivo para salvar dados de audiência.");
                    }
                }

                for (Midia serie : value.getListaParaVer().toList()) {
                    try {
                        writer.append(value.getId());
                        writer.append(";");
                        writer.append("F");
                        writer.append(";");
                        writer.append(serie.getId());
                        writer.append("\n");
                    } catch (IOException e) {
                        System.out.println("Erro: não foi possivel escrever no arquivo para salvar dados de audiência.");
                    }
                }
            });

        } catch (IOException e) {
            System.out.println("Erro: não foi possível gerar arquivo para salvar dados de audiência.");
        }

        System.out.println("Audiência salva com sucesso!");
    }

    /**
     * Lê a lista de mídias e acessa as avaliações de cada uma delas. Em seguida, para cada avaliação, salva no arquivo
     * Avaliacoes.csv o id da mídia, id do cliente, nota e comentário da avaliação. Caso não haja comentário de
     * avaliação, salva-se uma string vazia.
     */
    public void salvarAvaliacoes() {
        try (FileWriter writer = new FileWriter("src/main/resources/db/Avaliacoes.csv")) {
            writer.append("id_midia;id_cliente;nota;comentario\n");

            for (Map.Entry<Integer, Midia> entry : midias.entrySet()) {
                for (Avaliacao avaliacao : entry.getValue().getAvaliacoes()) {
                    try {
                        writer.append(entry.getValue().getId());
                        writer.append(";");
                        writer.append(avaliacao.getCliente().getId());
                        writer.append(";");
                        writer.append(String.valueOf(avaliacao.getNota()));
                        writer.append(";");
                        if (avaliacao.getTexto() != null) writer.append(avaliacao.getTexto());
                        else writer.append(" ");
                        writer.append("\n");
                    } catch (IOException e) {
                        System.out.println("Erro: não foi possivel escrever no arquivo de avaliações.");
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("Erro: não foi possível gerar arquivo para salvar avaliações.");
        }
        System.out.println("Avaliações salvas com sucesso!");
    }

    /**
     * Promove o salvamento de todas as entidades da plataforma em seus respectivos arquivos designados ao chamar os
     * métodos de salvamento de avaliações, audiência, mídia e clientes
     */
    public void salvar() {
        salvarAvaliacoes();
        salvarAudiencia();
        salvarMidia();
        salvarClientes();
    }

    /**
     * Filta pela lista de clientes da plataforma de streaming e checa qual cliente tem a maior lista de séries ja
     * vistas
     *
     * @return Cliente que mais assistiu midia
     */
    public Cliente qualClienteAssistiuMaisMidias() {

        Collection<Cliente> c = this.clientes.values();
        List<Cliente> clientesOrdenados = c.stream()
                        .sorted(Comparator.comparingInt(Cliente::tamanhoListaJaVistos).reversed())
                        .collect(Collectors.toList());


        return clientesOrdenados.get(0);

    }

    public Map<Cliente, Integer> qualClienteTemMaisAvaliacoes() {
        Collection<Midia> midias = this.midias.values();
        Map<Cliente, Integer> clientesAvaliacoes = new HashMap<>();

        for(Midia m : midias) {
            List<Avaliacao> avaliacao = m.getAvaliacoes();

            for(Avaliacao a : avaliacao) {
                if(clientesAvaliacoes.containsKey(a.getCliente())) {
                    int qtdAva = clientesAvaliacoes.get(a.getCliente());
                    clientesAvaliacoes.put(a.getCliente(), qtdAva + 1);
                } else {
                    clientesAvaliacoes.put(a.getCliente(), 1);
                }
            }
        }

        return clientesAvaliacoes;
    }

    public int clientesComQuinzeAvaliacoes () {
        int qtdAvaliacoes;
        int clientesQuinze = 0;

        Collection<Cliente> listaClientes = this.clientes.values();

        for(Cliente cliente : listaClientes) {
            qtdAvaliacoes = 0;
            for(Midia midia : cliente.getListaJaVistas().toList()) {
                List<Avaliacao> avaliacoes = midia.getAvaliacoes();

                for(Avaliacao avaliacao : avaliacoes) {
                    if(avaliacao.getCliente().equals(cliente)) {
                        qtdAvaliacoes++;
                    }
                }
            }

            if(qtdAvaliacoes >= 15) {
                clientesQuinze++;
            }
        }

        return (clientesQuinze / listaClientes.size()) * 100;
    }

    /**
     * Ordena a lista de midias por audiência em ordem decrescente e retorna as 10 primeiras midias.
     */
    public List<Midia> midiasMaisVistas() {
    	List<Midia> midiasOrdenadas = new ArrayList<Midia>(midias.values());
    	Collections.sort(midiasOrdenadas, Comparator.comparingInt(Midia::getAudiencia).reversed());
    	
    	return midiasOrdenadas.subList(0, Math.min(10, midiasOrdenadas.size()));
     }
    
    /**
     * Ordena a lista com as midias mais vistas por genero, sendo 10 a cada genero.
     */
    public List<Midia> midiasMaisVistasPorGenero() {
    	
    	final String[] GENEROS = new String[]{"Comédia", "Ação", "Terror", "Drama", "Romance", "Aventura", "Animação", "Suspense"};
    	Collection<Midia> relatorioMidia = midias.values();

    	List<Midia>appendList;
    	List<Midia>midiasMaisVistas = new LinkedList<>();

    	for(String s : GENEROS) {
    		appendList = relatorioMidia.stream()
    					.filter(m -> m.getGenero().equals(s))
    					.sorted(Comparator.comparingInt(Midia::getAudiencia).reversed())
    					.collect(Collectors.toList());

    		midiasMaisVistas.addAll(appendList.subList(0,10));
    	}

    	return midiasMaisVistas;
    }
    
    /**
     * Ordena a lista com as 10 midias mais bem avaliadas, contando apenas as midias com mais de 100 avaliações.
     */
    public List<Midia> midiasMaisBemAvaliadas() throws IndexOutOfBoundsException{
    	Collection<Midia> relatorioMidia = midias.values();
    	List<Midia> midiasMaisAvaliadas = relatorioMidia.stream()
    					.filter(m -> m.getAvaliacoes().size() >= 100)
    					.sorted(Comparator.comparingDouble(Midia::mediaAvaliacoes).reversed())
    					.collect(Collectors.toList());
    					
    	return midiasMaisAvaliadas.subList(0,10);
    }

}
