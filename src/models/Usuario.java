package models;

public class Usuario {
	private String nome;
	private String email;
	private String senha;	
	private int id;
	
	public Usuario(String nome, String email, String senha) {
		this.nome = nome;
		this.email = email;
		this.senha = senha;
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}
	
	public String getEmail() {
		return email;
	}
	
	public String getSenha() {
		return senha;
	}
	
	
}
