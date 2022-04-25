import java.time.LocalDateTime;
import java.util.List;

public class Pacote {

	private String identificador;
	private String origem;
	private LocalDateTime dataHoraEnvio;
	private String conteudo;
	private List<String> destinatarios;

	public Pacote(String identificador, String origem, LocalDateTime dataHoraEnvio, String conteudo, List<String> destinatarios) {
		this.identificador = identificador;
		this.origem = origem;
		this.dataHoraEnvio = dataHoraEnvio;
		this.conteudo = conteudo;
		this.destinatarios = destinatarios;
	}
	
	public String getIdentificador() {
		return identificador;
	}
	public void setIdentificador(String identificador) {
		this.identificador = identificador;
	}
	public String getOrigem() {
		return origem;
	}
	public void setOrigem(String origem) {
		this.origem = origem;
	}
	public LocalDateTime getDataHoraEnvio() {
		return dataHoraEnvio;
	}
	public void setDataHoraEnvio(LocalDateTime dataHoraEnvio) {
		this.dataHoraEnvio = dataHoraEnvio;
	}
	public String getConteudo() {
		return conteudo;
	}
	public void setConteudo(String conteudo) {
		this.conteudo = conteudo;
	}
	public List<String> getDestinatarios() {
		return destinatarios;
	}
	public void setDestinatarios(List<String> conexoes) {
		this.destinatarios = conexoes;
	}
	
}
