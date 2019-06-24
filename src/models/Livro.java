package models;

public class Livro {
	private int id;
	private String titulo;
	private String autor;
	private String editora;
	private int ano;
	private String capa;
	
	public Livro(String titulo, String autor, String editora, int ano, String capa) {
		this.titulo = titulo;
		this.autor = autor;
		this.editora = editora;
		this.ano = ano;
		this.capa = capa;
	}
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		if (id > 0) {
			this.id = id;			
		}
	}
	
	public String getTitulo() {
		return titulo;
	}
	
	public String getAutor() {
		return autor;
	}
	
	public String getEditora() {
		return editora;
	}
	
	public int getAno() {
		return ano;
	}
	
	public String getCapa() {
		return capa;
	}
	
	
}
