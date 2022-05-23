package model.seletor;

public class TelefoneSeletor {
	
	private String nomeDonoTelefone;
	private String cpfDonoTelefone;
	
	private int limite;
	private int pagina;
	
	public TelefoneSeletor() {
		this.pagina = -1;
		this.limite = -1;
	}
	
	/**
	 * Verifica se este seletor tem algum campo preenchido
	 *
	 * @return verdadeiro se existe algum campo de filtro preenchido
	 */
	public boolean temFiltro() {
		boolean temFiltro = false;
		
		if(nomeDonoTelefone != null && !nomeDonoTelefone.trim().isEmpty()) {
			temFiltro = true;
		}
		
		if(cpfDonoTelefone != null && !cpfDonoTelefone.trim().isEmpty()) {
			temFiltro = true;
		}
		
		return temFiltro;
	}
	

	public int getOffset() {
		return (this.limite * (this.pagina - 1));
	}
	
	public boolean temPaginacao() {
		return ((this.limite > 0) && (this.pagina > -1));
	}


	public String getNomeDonoTelefone() {
		return nomeDonoTelefone;
	}


	public void setNomeDonoTelefone(String nomeDonoTelefone) {
		this.nomeDonoTelefone = nomeDonoTelefone;
	}


	public String getCpfDonoTelefone() {
		return cpfDonoTelefone;
	}


	public void setCpfDonoTelefone(String cpfDonoTelefone) {
		this.cpfDonoTelefone = cpfDonoTelefone;
	}

	public int getLimite() {
		return limite;
	}

	public void setLimite(int limite) {
		this.limite = limite;
	}

	public int getPagina() {
		return pagina;
	}

	public void setPagina(int pagina) {
		this.pagina = pagina;
	}
	
	
}
