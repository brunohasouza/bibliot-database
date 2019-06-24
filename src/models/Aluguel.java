package models;

public class Aluguel {
	private Usuario usuario;
	private Livro livro;
	private String dataColeta;
	private String dataDevolucao;
	private int devolvido;
	
	public Aluguel(Usuario usuario, Livro livro, String dataColeta, String dataDevolucao, int devolvido) {
		this.usuario = usuario;
		this.livro = livro;
		this.dataColeta = dataColeta;
		this.dataDevolucao = dataDevolucao;
		this.devolvido = devolvido;
	}
	
	public void setDevolvido(int devolvido) {
		this.devolvido = devolvido;
	}

	public Usuario getUsuario() {
		return usuario;
	}
	
	public Livro getLivro() {
		return livro;
	}
	
	public String getDataColeta() {
		return dataColeta;
	}
	
	public String getDataDevolucao() {
		return dataDevolucao;
	}
	
	public int getDevolvido() {
		return devolvido;
	}
}
