package streamingplatform.app;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicBoolean;

import java.util.*;

import streamingplatform.business.PlataformaStreaming;
import streamingplatform.business.entidades.Cliente;
import streamingplatform.business.entidades.Midia;
import streamingplatform.business.entidades.fracas.Filme;
import streamingplatform.business.entidades.fracas.Serie;
import streamingplatform.business.exceptions.ClienteJaExisteException;
import streamingplatform.business.exceptions.LoginInvalidoException;
import streamingplatform.business.exceptions.MidiaJaExisteException;
import streamingplatform.business.exceptions.MidiaNaoEncontradaException;
import streamingplatform.utils.Lista;

public class App {
    /*
     * System.in implementa a interface Closable. Ver javadoc java.util.scanner ->
     * Scanner.close
     */
    public static Scanner read = new Scanner(System.in);
    public static Cliente clientePadrao = new Cliente("Lei108163", "Leia D V Brum", "LAus15911");
    public static PlataformaStreaming ps = new PlataformaStreaming("Xam OBH");

    public static int readOption() {
    	try {
    		return read.nextInt();
    	} catch (InputMismatchException e) {
    		return -1;
    	}
    }
    
    public static void main(String[] args) {
    	
        try {
            ps.login(clientePadrao.getNomeUsuario(), clientePadrao.getSenha());
        } catch (LoginInvalidoException e) {
            e.printStackTrace();
        }

        int option = 0;

        while (option != 99) {
            
        	printMenu();
        	
            System.out.print("Opção: ");
            option = readOption();
            read.nextLine(); // Lê o \n que o nextInt não lê

            // Escolher opção do menu
            switch (option) {
                case 1:
                    fazerLogin(ps);
                    break;
                case 2:
                    cadastrarCliente(ps);
                    break;
                case 3:
                    tornarProfissional(ps);
                    break;
                case 4:
                    cadastrarMidia(ps);
                    break;
                case 5:
                    assistirMidia(ps);
                    break;
                case 6:
                    verAudiencia(ps);
                    break;
                case 7:
                    midiasParaAssistir(ps);
                    break;
                case 8:
                    midiasAssistidas(ps);
                    break;
                case 9:
                    filtrarMidias(ps);
                    break;
                case 10:
                    buscarMidia(ps);
                    break;
                case 11:
                    imprimirMidias(ps);
                    break;
                case 12:
                    addSerieListaParaAssistir(ps);
                    break;
                case 13:
                    addSerieListaAssistidos(ps);
                    break;
                case 14:
                    avaliarMidia(ps);
                    break;
                case 15:
                    midiasAvaliadas(ps);
                    break;
                case 16:
                    imprimirClienteComMaisMidias(ps);
                    break;
                case 17:
                    imprimirClienteComMaisAvaliacoes(ps);
                    break;
                case 18:
                    imprimirPorcentagemClientes(ps);
                    break;
                case 19:
                    imprimirMidiasMaisVistas(ps);
                    break;
                case 20:
                	imprimirMidiasMaisVistasPorGenero(ps);
                	break;
                case 21:
                	imprimirMidiasMaisBemAvaliadas(ps);
                	break;
                case 22:
                	imprimirMidiasMaisBemAvaliadasPorGenero(ps);
                	break;
                case 99:
                    ps.salvar();
                    break;
                case -1:
                	System.out.println("Opção invalida");
                	break;
                default:
                    System.out.printf("A opção %d é inválida\n", option);
                    break;
            }
            
            System.out.println("Pressione a tecla ENTER para continuar");
            
            read.nextLine();
        }

        read.close();
    }
    
    private static void printMenu() {
    	System.out.print("\n--------------------------------------------------\n");
        System.out.print("---------------------- MENU ----------------------\n");
        System.out.printf("Usuário logado: %s\n", ps.getClienteAtual().getNomeUsuario());
        System.out.print("""
                \nEscolha uma operação:
                \n--- Gerenciar Clientes ---
                1. Fazer login com outro usuário
                2. Cadastrar novo cliente
                3. Tornar-se cliente profissional
                \n--- Gerenciar Mídias ---
                4. Cadastrar nova mídia
                5. Assistir midia
                6. Ver audiência de uma midia
                7. Ver minha lista de midias para assistir
                8. Ver minha lista de midias já vistas
                9. Filtrar minhas midias
                10. Buscar mídia por nome
                11. Ver catálogo completo
                12. Adicionar série na sua lista para assistir
                13. Adicionar série na sua lista dos assistidos
                \n--- Gerenciar avaliações ---
                14. Avaliar mídia
                15. Ver minhas avaliações
                \n--- Relatórios ---
                16. Qual cliente assistiu mais midias e quantas midias
                17. Qual cliente tem mais avaliações e quantas avaliações
                18. Qual a porcentagem de clientes com pelo menos 15 avaliações 
                19. Quais são as 10 midias mais vistas
                20. Quais são as 10 midias mais vistas por genero
                21. Quais são as 10 midias com melhor avaliação (minimo 100 avaliações)
                22. Quais são as 10 midias com melhor avaliação (minimo 100 avaliações) por genero

                \n--- Outros ---
                99. Salvar e sair
                ------------------------------------------------
                ------------------------------------------------
                \n""");
    }

    private static void addSerieListaAssistidos(PlataformaStreaming ps) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Qual serie voce deseja adicionar na sua lista de séries assistidas?");
        String nomeSerie = sc.nextLine();

        Midia serie = null;

        for (Midia m : ps.getSeries()) {
            if (m.getNome().equals(nomeSerie)) {
                serie = m;
            }
        }
        if (serie != null) {
            ps.getClienteAtual().adicionarNaListaJaVistos(serie);
        } else {
            System.out.println("Série não encontrada");
        }

    }

    private static void addSerieListaParaAssistir(PlataformaStreaming ps) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Qual serie você deseja assistir?");
        String nomeSerie = sc.nextLine();

        Midia serie = null;

        for (Midia m : ps.getSeries()) {
            if (m.getNome().equals(nomeSerie)) {
                serie = m;
            }
        }

        if (serie != null) {
            ps.getClienteAtual().adicionarNaListaParaVer(serie);
        } else {
            System.out.println("Série não encontrada");
        }

    }

    /*
     * 
     * Método estático
     * para fazer
     * login com
     * um usuário
     * existente na plataforma**
     * 
     * @param plat Plataforma streaming
     */

    public static void fazerLogin(PlataformaStreaming plat) {
        String userName, password;

        System.out.print("---------- Fazer Login ----------\n");

        System.out.print("Insira o ID do usuário: ");
        userName = read.nextLine();

        System.out.print("Insira a SENHA do usuário: ");
        password = read.nextLine();

        try {
            plat.login(userName, password);
        } catch (LoginInvalidoException e) {
            System.out.print(e.getMessage());
        }
    }

    /**
     * Método estático para cadastrar novo cliente à plataforma
     *
     * @param plat Plataforma streaming
     */
    public static void cadastrarCliente(PlataformaStreaming plat) {
        String userName, userId, userPassword;

        System.out.print("---------- Cadastrar Cliente ----------\n");

        System.out.print("Nome do usuário: ");
        userName = read.nextLine();

        System.out.print("ID do usuário: ");
        userId = read.nextLine();

        System.out.print("Senha do usuário: ");
        userPassword = read.nextLine();

        try {
            plat.adicionarCliente(userName, userId, userPassword);
            System.out.print("Usuário cadastrado com sucesso.\n");
        } catch (ClienteJaExisteException e) {
            System.out.print(e.getMessage());
        }
    }

    public static void tornarProfissional(PlataformaStreaming plat) {
        plat.getClienteAtual().tornarProfissional();
        System.out.println("Agora você tem permissão para assistir lançamentos!");
    }

    /**
     * Método estático para cadastrar uma nova mídia ao catálogo
     *
     * @param plat Plataforma streaming
     */
    public static void cadastrarMidia(PlataformaStreaming plat) {
        String midiaId, midiaName, midiaGenre, midiaIdiom, midiaRelease, midiaType;
        boolean isLancamento;

        System.out.print("---------- Cadastrar Midia ----------\n");

        System.out.print("Nome: ");
        midiaName = read.nextLine();

        System.out.print("ID: ");
        midiaId = read.nextLine();

        System.out.print("Gêneros disponíveis: Comédia, Ação, Terror, Drama, Romance, Aventura, Animação, Suspense\n");
        System.out.print("Gênero: ");
        midiaGenre = read.nextLine();

        System.out.print("Idiomas disponíveis: Português, Inglês, Esperanto, Romeno\n");
        System.out.print("Idioma: ");
        midiaIdiom = read.nextLine();

        System.out.println("Obs.: caso inserida uma data inválida em relação ao calendário, será atribuída a data mais próxima.");
        System.out.print("Data de lançamento (dd-mm-aaaa): ");
        midiaRelease = read.nextLine();

        System.out.print("""
                A mídia é regular ou lançamento? \s
                    Digite (A) para mídia regular
                    Digite (B) para lançamento
                """);

        midiaType = read.nextLine();

        switch (midiaType) {
            case "A":
                isLancamento = false;
                break;
            case "B":
                isLancamento = true;
                break;
        }

        Date midiaReleaseDate = null;

        try {
            midiaReleaseDate = new SimpleDateFormat("dd-MM-yyyy").parse(midiaRelease);
        } catch (ParseException e) {
            System.out.print("Formato de data invalido! Utilize o formato dd-mm-yyyy\n");
        }

        System.out.print("""
                Tipo de mídia: \s
                    Digite (A) para Série
                    Digite (B) para Filme
                """);

        midiaType = read.nextLine().toUpperCase();

        switch (midiaType) {
            case "A":
                System.out.println("Série selecionada!");
                System.out.println("Digite a quantidade de episodios existentes: ");
                int serieQtdEp = read.nextInt();
                read.nextLine();

                try {
                    plat.adicionarMidia(Integer.valueOf(midiaId),
                            new Serie(midiaId, midiaName, midiaGenre, midiaIdiom, midiaReleaseDate, false, serieQtdEp));
                    System.out.println("Série cadastrada com sucesso!\n");
                } catch (MidiaJaExisteException | NullPointerException e) {
                    System.out.println(e.getMessage());
                }
                break;

            case "B":
                System.out.println("Filme selecionado!");
                System.out.print("Duração do filme (min): ");
                int filmLength = read.nextInt();
                read.nextLine();

                try {
                    plat.adicionarMidia(Integer.valueOf(midiaId),
                            new Filme(midiaId, midiaName, midiaGenre, midiaIdiom, midiaReleaseDate, false, filmLength));
                    System.out.print("Filme cadastrado com sucesso!\n");
                } catch (MidiaJaExisteException | NullPointerException e) {
                    System.out.println(e.getMessage());
                }
                break;

            default:
                System.out.println("Opção inválida.");
                break;
        }
    }

    /**
     * Método estático para assistir uma mídia disponível no catálogo
     *
     * @param plat Plataforma streaming
     */
    public static void assistirMidia(PlataformaStreaming plat) {
        Scanner scan = new Scanner(System.in);

        System.out.println("\n---------- Assistir mídia ----------");
        System.out.println("Digite o id da mídia que deseja assistir: ");
        String id = scan.nextLine();

        try {
            plat.getClienteAtual().assistirMidia(plat.findMidiaById(Integer.parseInt(id)));
            System.out.println("A mídia " + plat.findMidiaById(Integer.parseInt(id)).getNome() + " foi assistida!");
        } catch (MidiaNaoEncontradaException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Método estático para imprimir a audiência de uma mídia
     *
     * @param plat Plataforma streaming
     */
    public static void verAudiencia(PlataformaStreaming plat) {
        Scanner scan = new Scanner(System.in);

        System.out.println("\n---------- Ver Audiência ----------");
        System.out.println("Digite o id da mídia que deseja ver a audiência: ");
        String id = scan.nextLine();

        Midia midia = plat.findMidiaById(Integer.parseInt(id));

        if (midia != null) {
            System.out.println("Midia: " + midia.getNome() + " \nAudiência: " + midia.getAudiencia());
        } else {
            System.out.println("Midia não encontrada");
        }
    }

    /**
     * Método estático para imprimir as mídias da lista para ver do cliente logado
     *
     * @param plat Plataforma streaming
     */
    public static void midiasParaAssistir(PlataformaStreaming plat) {
        System.out.println("\n---------- Mídias para assistir ----------");
        for (Midia midia : plat.getClienteAtual().getListaParaVer().toList()) {
            System.out.printf("%s\n", midia.getNome());
        }
    }

    /**
     * Método estático para exibir todas as mídias já assistidas pelo cliente logado
     *
     * @param plat Plataforma streaming
     */
    public static void midiasAssistidas(PlataformaStreaming plat) {
        List<Midia> listaJaVistas = plat.getClienteAtual().getListaJaVistas().toList();

        System.out.println("\n---------- Mídias já vistas ----------");
        for (Midia midia : listaJaVistas) {
            System.out.printf(midia.getId() + " - " + midia.getNome() + "\n");
        }
    }

    /**
     * Método estático para filtrar mídias por gênero, idioma ou quantidade de
     * episódios
     *
     * @param plat Plataforma streaming
     */
    public static void filtrarMidias(PlataformaStreaming plat) {
        Scanner scan = new Scanner(System.in);

        Lista<Midia> arrayFiltradas = new Lista<>();

        System.out.println("---------- Filtar mídias ----------\n");

        System.out.println("""
                Selecione o critério de busca:
                    1. Gênero
                    2. Idioma
                    3. Quantidade de episódios
                """);
        String op = scan.nextLine();

        switch (Integer.parseInt(op)) {
            case 1:
                System.out.print(
                        "Gêneros disponíveis: Comédia, Ação, Terror, Drama, Romance, Aventura, Animação, Suspense\n");
                System.out.println("Digite um gênero para buscar: ");
                String genero = scan.nextLine();
                arrayFiltradas = plat.filtrarPorGenero(genero);
                break;
            case 2:
                System.out.print("Idiomas disponíveis: Português, Inglês, Esperanto, Romeno\n");
                System.out.println("Digite um idioma para buscar: ");
                String idioma = scan.nextLine();
                arrayFiltradas = plat.filtrarPorIdioma(idioma);
                break;
            case 3:
                System.out.println("Digite uma quantidade de episódios para buscar: ");
                String qtdEpisodios = scan.nextLine();
                arrayFiltradas = plat.filtrarPorQtdEpisodios(Integer.parseInt(qtdEpisodios));
                break;
            default:
                System.out.println("A opção escolhida é inválida");
                break;
        }

        System.out.println("\"---------- Resultados encontrados ----------\\n\"");
        for (int i = 0; i < arrayFiltradas.size(); i++) {
            if (arrayFiltradas.get(i) != null)
                System.out.println(arrayFiltradas.get(i).getNome());
        }
    }

    /**
     * Método estático para exibir todas as midias disponíveis no catálogo
     *
     * @param plat Plataforma streaming
     */
    public static void imprimirMidias(PlataformaStreaming plat) {
        plat.getMidiasMap().forEach((key, value) -> {
            if (value instanceof Filme)
                System.out.println(value.getId() + " - " + value.getNome() + " - Filme");
            else if (value instanceof Serie)
                System.out.println(value.getId() + " - " + value.getNome() + " - Série");
        });
    }

    /**
     * Método estático para pesquisar mídia por nome
     *
     * @param plat Plataforma streaming
     */
    private static void buscarMidia(PlataformaStreaming plat) {
        AtomicBoolean midiaEncontrada = new AtomicBoolean(false);
        Scanner scan = new Scanner(System.in);

        System.out.println("Nome da mídia: ");
        String searchedName = scan.nextLine();

        plat.getMidiasMap().forEach((key, value) -> {
            if (value.getNome().equalsIgnoreCase(searchedName)) {
                System.out.println("A seguinte mídia foi encontrada:\n");
                System.out.println(value);
                midiaEncontrada.set(true);
            }
        });

        if (!midiaEncontrada.get())
            System.out.println("Não foi possível encontrar a mídia de nome " + searchedName);
    }

    /**
     * Método estático para avaliar uma mídia
     *
     * @param plat Plataforma streaming
     */
    public static void avaliarMidia(PlataformaStreaming plat) {
        Scanner scan = new Scanner(System.in);
        Lista<Midia> listaJaVistas = plat.getClienteAtual().getListaJaVistas();

        System.out.println("\n---------- Avaliar mídia ----------");

        System.out.println("Midias assistidas:");
        midiasAssistidas(plat);

        System.out.println("\nDigite o id da mídia que você deseja avaliar:");
        String idMidia = scan.nextLine();

        if (plat.getMidiasMap().containsKey(Integer.parseInt(idMidia))
                && listaJaVistas.contains(plat.findMidiaById(Integer.parseInt(idMidia)))) {

            System.out.println("Insira uma nota de 1 a 5: ");
            String nota = scan.nextLine();

            try {
                if (plat.getClienteAtual().getModoAvaliacao() != null) {
                    System.out.println("Insira um comentário: ");
                    String comentario = scan.nextLine();

                    plat.getClienteAtual().avaliarMidia(plat.findMidiaById(Integer.parseInt(idMidia)),
                            Integer.parseInt(nota), comentario);
                } else {
                    plat.getClienteAtual().avaliarMidia(plat.findMidiaById(Integer.parseInt(idMidia)),
                            Integer.parseInt(nota));
                }

                System.out.println("Mídia avaliada com sucesso!");

            } catch (IllegalStateException e) {
                System.out.println("Erro: " + e.getMessage());
            }

        } else {
            System.out.println("Não foi possível encontrar a mídia de id " + idMidia);
        }
    }

    /**
     * Método estático para visualizar avaliações do cliente
     *
     * @param plat Plataforma streaming
     */
    public static void midiasAvaliadas(PlataformaStreaming plat) {
        System.out.println("\n---------- Mídias avaliadas ----------");
        for (Midia midia : plat.getClienteAtual().getListaJaVistas().toList()) {
            if (midia.getAvaliacoes().size() != 0) {
                midia.getAvaliacoes().forEach(avaliacao -> {
                    if (avaliacao.getCliente().equals(plat.getClienteAtual())) {
                        System.out.println(midia.getNome());
                        System.out.println(avaliacao);
                        System.out.println("\n");
                    }
                });
            }
        }
    }

    public static void imprimirClienteComMaisMidias(PlataformaStreaming plat) {
        Cliente c = plat.qualClienteAssistiuMaisMidias();
        System.out.println("\n---------- Cliente que assistir mais mídias ----------");
        System.out.printf("Nome: %s\nQuantidade de mídias: %d\n", c.getNomeUsuario(), c.getListaJaVistas().size());
    }

    public static void imprimirClienteComMaisAvaliacoes(PlataformaStreaming plat) {
            Map<Cliente, Integer> clienteMaisAvaliacoes = plat.qualClienteTemMaisAvaliacoes();
            Cliente clienteAvaliacoes = new Cliente(null, null, null);
            int qtdAtual = 0;

            for(Cliente c : clienteMaisAvaliacoes.keySet()) {
                if(clienteMaisAvaliacoes.get(c) > qtdAtual) {
                    qtdAtual = clienteMaisAvaliacoes.get(c);
                    clienteAvaliacoes = c;
                }
            }

            System.out.printf("\n---------- Cliente que mais avaliou ----------");
            System.out.printf("\nNome: %s\nQuantidade de avaliaçõe: %d\n", clienteAvaliacoes.getNomeUsuario(), qtdAtual);
    }

    public static void imprimirPorcentagemClientes(PlataformaStreaming plat) {
        int porcentagem = plat.clientesComQuinzeAvaliacoes();
        System.out.println("\n---------- Cliente com pelo menos 15 avaliações ----------");
        System.out.printf("Porcentagem: %d\n", porcentagem);
    }

    public static void imprimirMidiasMaisVistas(PlataformaStreaming plat) {
    	List<Midia> midiasMaisVistas = plat.midiasMaisVistas();
    	for(Midia midia : midiasMaisVistas) {
    		System.out.println(midia.toString());
    		System.out.println("Audiência " + midia.getAudiencia());
    		System.out.println("----------------");
    	}
    }
    
    public static void imprimirMidiasMaisVistasPorGenero(PlataformaStreaming plat){
    	List<Midia> midiasMaisVistasPorGenero = plat.midiasMaisVistasPorGenero();
    	for(Midia midia : midiasMaisVistasPorGenero) {
    		System.out.println(midia.toString());
		System.out.println("Audiência " + midia.getAudiencia());
    		System.out.println("----------------");
    	}
    }
    
    public static void imprimirMidiasMaisBemAvaliadas(PlataformaStreaming plat) {
    	try {
    			List<Midia> midiasMaisBemAvaliadas = plat.midiasMaisBemAvaliadas();
	    		for(int i = 0; i < midiasMaisBemAvaliadas.size() || i <= 9; i++) {
	        		System.out.println(midiasMaisBemAvaliadas.get(i).toString());
	        		System.out.println("----------------");
	        	}
    		}
    		catch(Exception e) {
    			System.out.println("Não existe 10 midias com mais de 100 avaliações");
    		}
    }
    
    public static void imprimirMidiasMaisBemAvaliadasPorGenero(PlataformaStreaming plat) {
    	try {
			List<Midia> midiasMaisBemAvaliadasPorGenero = plat.midiasMaisBemAvaliadas();
    		for(int i = 0; i < midiasMaisBemAvaliadasPorGenero.size() || i <= 9; i++) {
        		System.out.println(midiasMaisBemAvaliadasPorGenero.get(i).toString());
        		System.out.println("----------------");
        	}
		}
		catch(Exception e) {
			System.out.println("Não existe 10 midias com mais de 100 avaliações");
		}
    }

}
