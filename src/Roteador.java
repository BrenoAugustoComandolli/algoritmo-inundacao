import java.util.ArrayList;
import java.util.List;

public class Roteador {
	
	private String identificador;
	private List<String> lConexoes;
	private List<Pacote> lPacotesRecebidos = new ArrayList<>();

	public Roteador(String identificador, List<String> conexoes) {
		this.identificador = identificador;
		this.lConexoes = conexoes;
	}
	
	public String getIdentificador() {
		return identificador;
	}
	public void setIdentificador(String identificador) {
		this.identificador = identificador;
	}
	public List<String> getlConexoes() {
		return lConexoes;
	}
	public void setlConexoes(List<String> lConexoes) {
		this.lConexoes = lConexoes;
	}
	public List<Pacote> getlPacotesRecebidos() {
		return lPacotesRecebidos;
	}
	public void setlPacotesRecebidos(List<Pacote> lPacotesRecebidos) {
		this.lPacotesRecebidos = lPacotesRecebidos;
	}
	
}
