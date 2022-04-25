import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Menu {
	
	private static Integer identificadorPacote = 0;
	private static Integer identificadorRoteador = 0; 
	private static List<Roteador> lRotedores = new ArrayList<>();
	
	public static void main(String[] args) {
		var sc = new Scanner(System.in);
		var opcao = "";
		
		do {
			mostrarMenu();
			opcao = sc.next();
			
			switch (opcao) {
			case "1": { cadastrarRoteador(sc); break;}
			case "2": { enviarMensagem(sc); break;}
			case "3": { visualizarMensagens(sc); break;}
			case "4": { break;}
			default:
				System.err.println("Opção Inválida!");
			}
		}while(!opcao.equals("4"));
		
		sc.close();
	}

	/**
	 * 
	 * Menu - Opções
	 *
	 * @author Comandolli
	 */
	private static void mostrarMenu() {
		System.out.println("-------------------------------------");
		System.out.println(" 1 - Cadastrar novo roteador         ");
		System.out.println(" 2 - Enviar mensagem                 ");
		System.out.println(" 3 - Visualizar mensagens recebidas  ");
		System.out.println(" 4 - Sair                            ");
		System.out.println("-------------------------------------");
	}
	
	/**
	 * 
	 * Cadastrar roteadores
	 *
	 * @author Comandolli
	 */
	private static void cadastrarRoteador(Scanner sc) {
		List<String> lConexao = new ArrayList<>();
		setIdentificadorRoteador(getIdentificadorRoteador()+1);
		var opcao = "";
		
		System.out.println("ID: ROT"+getIdentificadorRoteador());
		do {
			System.out.println("-------------------------------------");
			System.out.println(" 1 - Nova conexão                    ");
			System.out.println(" 2 - Voltar                          ");
			System.out.println("-------------------------------------");
			opcao = sc.next();
			
			if(opcao.equals("1")) {
				System.out.println("-------------------------------------");
				System.out.println("Informe ID Roteador: ");
				System.out.println("-------------------------------------");
				String conexao = sc.next();
				lConexao.add(conexao);
			}
			
		}while(!opcao.equals("2"));
		
		var roteador = new Roteador("ROT"+getIdentificadorRoteador(), lConexao);
		getlRotedores().add(roteador);
	}
	
	/**
	 * 
	 * Enviar mensagens aos roteadores de conexão
	 *
	 * @author Comandolli
	 */
	private static void enviarMensagem(Scanner sc) {
		System.out.println("Informe o roteador de origem: ");
		var roteador = sc.next();
		
		System.out.println("Informe o conteúdo: ");
		sc.nextLine();
		var conteudo = sc.nextLine();
		
		var dataHoraEnvio = LocalDateTime.now();
		List<String> lDestino = new ArrayList<>();
		
		var umRoteador = recuperaRoteador(roteador);
		if(umRoteador != null) {
			lDestino.addAll(umRoteador.getlConexoes());
		}
				
		setIdentificadorPacote(getIdentificadorPacote()+1);
		if(umRoteador != null) {
			var pacote = new Pacote("PAC"+getIdentificadorPacote(), umRoteador.getIdentificador(), dataHoraEnvio, conteudo, lDestino);
			enviarParaConexoes(umRoteador, pacote);
		}
	}
	
	/**
	 * 
	 * Enviando para todas as conexões 
	 *
	 * @author Comandolli
	 * @param roteador
	 * @param pacote
	 */
	private static void enviarParaConexoes(Roteador roteador, Pacote pacote) {
		for (String umaConexao : roteador.getlConexoes()) {
			var umRoteador = recuperaRoteador(umaConexao);
			if(pacote.getIdentificador() != null && umRoteador != null && 
			   !verificarPacoteExiteRoteador(pacote.getIdentificador(), umRoteador) &&
			   !pacote.getOrigem().equals(umRoteador.getIdentificador())) {
				
				umRoteador.getlPacotesRecebidos().add(pacote);
				
				new Thread() {
				    @Override
				    public void run() {
				    	enviarParaConexoes(umRoteador, pacote);
				    }
				}.start();
			}
		}
	}
	
	/**
	 * 
	 * Verificar se o pacote já foi enviado para esse roteador
	 *
	 * @author Comandolli
	 * @param identificador
	 * @param umRoteador
	 * @return true/false
	 */
	private static boolean verificarPacoteExiteRoteador(String identificador, Roteador umRoteador) {
		if(umRoteador.getlPacotesRecebidos() != null) {
			for (Pacote umPacote : umRoteador.getlPacotesRecebidos()) {
				if(umPacote.getIdentificador().equals(identificador)) {
					return true;
				}
			}
		}
		return false;
	}
	
	/**
	 * 
	 * Recupera o roteador na lista
	 *
	 * @author Comandolli
	 * @param roteador
	 * @return umRoteador
	 */
	private static Roteador recuperaRoteador(String roteador) {
		for (Roteador umRoteador : getlRotedores()) {
			if(umRoteador.getIdentificador().equals(roteador)) {
				return umRoteador;
			}
		}
		return null;
	}
	
	/**
	 * 
	 * Mostra todas as mensagens recebidas pelo roteador
	 *
	 * @author Comandolli
	 */
	private static void visualizarMensagens(Scanner sc) {
		System.out.println("Informe o roteador: ");
		var roteador = sc.next();
		var umRoteador = recuperaRoteador(roteador);
		
		if(umRoteador != null) {
			System.out.println("-------------------------------------");
			System.out.println(" Mensagens enviadas - "+umRoteador.getIdentificador());
			System.out.println("-------------------------------------");
			
			if(!umRoteador.getlPacotesRecebidos().isEmpty()) {
				for (Pacote umPacote : umRoteador.getlPacotesRecebidos()) {
					System.out.println("\n");
					System.out.println("-------------------------------------");
					System.out.println("Identificador: "+umPacote.getIdentificador());
					System.out.println("-------------------------------------");
					System.out.println("Origem: "+umPacote.getOrigem());
					System.out.println("Data: "+umPacote.getDataHoraEnvio());
					System.out.println("Conteúdo: "+umPacote.getConteudo());
					System.out.println("Destinatarios: ");
					
					Integer ind = 0;
					for (String umDestinatario : umPacote.getDestinatarios()) {
						ind++;
						System.out.println("-------------------------------------");
						System.out.println(ind+". "+umDestinatario);
					}
					
					System.out.println("-------------------------------------");
				}
			}else {
				System.out.println("-------------------------------------");
				System.out.println(" Nenhuma mensagem recebida           ");
				System.out.println("-------------------------------------");
			}
		}else {
			System.out.println("-------------------------------------");
			System.out.println(" Roteador não encontrado             ");
			System.out.println("-------------------------------------");
		}
	}

	public static Integer getIdentificadorRoteador() {
		return identificadorRoteador;
	}

	public static void setIdentificadorRoteador(Integer identificador) {
		Menu.identificadorRoteador = identificador;
	}

	public static List<Roteador> getlRotedores() {
		return lRotedores;
	}

	public static void setlRotedores(List<Roteador> lRotedores) {
		Menu.lRotedores = lRotedores;
	}

	public static Integer getIdentificadorPacote() {
		return identificadorPacote;
	}

	public static void setIdentificadorPacote(Integer identificadorPacote) {
		Menu.identificadorPacote = identificadorPacote;
	}
	
}





