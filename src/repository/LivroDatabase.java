package repository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import db.Biblio;
import models.Livro;

public class LivroDatabase {
	private Biblio biblio;
	
	public LivroDatabase() {
		this.biblio = new Biblio();
	}
	
	/*
	 * Filtrar livro por título
	*/	
	public ArrayList<Livro> buscarLivro(String palavra) {
		String sql = "SELECT * FROM tbl_livro WHERE titulo LIKE '%" + palavra + "%';";		
		return this.listarLivros(sql);
	}
	
	/*
	 * Criação da tabela tbl_livro
	*/
	public boolean criarTabela() {
		boolean criado = false;
		String sql = "CREATE TABLE IF NOT EXISTS tbl_livro"
				+ "("
				+ "id INTEGER PRIMARY KEY AUTOINCREMENT, "
				+ "titulo TEXT NOT NULL, "
				+ "autor TEXT NOT NULL, "
				+ "editora TEXT NOT NULL, "
				+ "ano INTEGER NOT NULL, "
				+ "capa TEXT NOT NULL"
				+ ");";
		
		boolean conectou = this.biblio.conectar();
		Statement stmt = null;
		
		try {
			stmt = this.biblio.getStatement();
			stmt.execute(sql);
			
			criado = true;
			
		} catch (SQLException e) {
			// TODO: handle exception
			e.printStackTrace();
			System.err.println("Tabela Livro não criada! =[");
			return criado;
			
		} finally {
			if (stmt != null) {
				try {
					stmt.close();
				} catch (SQLException e2) {
					// TODO Auto-generated catch block
					e2.printStackTrace();
				}
			}
			
			if (conectou) {
				this.biblio.desconectar();
			}
		}
		
		System.out.println("Tabela Livro criada com sucesso!");		
		return criado;
	}
	
	/*
	 * Método retorna um ArrayList dos livros que estão alugados
	*/
	public ArrayList<Livro> listarLivrosAlugados(){
		String sql = "SELECT "
				+ "livro.id AS id, "
				+ "livro.titulo AS titulo, "
				+ "livro.autor AS autor,"
				+ "livro.editora AS editora, "
				+ "livro.ano AS ano, "
				+ "livro.capa AS capa, "
				+ "FROM tbl_aluguel AS aluguel INNER JOIN tbl_livro AS livro ON aluguel.idLivro = livro.id";
		
		return this.listarLivros(sql);
	}
	
	/*
	 * Método retorna um ArrayList dos livros que estão disponíveis;
	*/
	public ArrayList<Livro> listarLivrosDisponiveis(){
		String sql = "SELECT "
				+ "livro.id AS id, "
				+ "livro.titulo AS titulo, "
				+ "livro.autor AS autor,"
				+ "livro.editora AS editora, "
				+ "livro.ano AS ano, "
				+ "livro.capa AS capa "
				+ "FROM tbl_aluguel AS aluguel INNER JOIN tbl_livro AS livro ON aluguel.idLivro != livro.id";
		
		return this.listarLivros(sql);
	}
	
	/*
	 * Método que lista todos os livros do catálogo
	*/
	public ArrayList<Livro> listarTodosOsLivros() {
		String sql = "SELECT * FROM tbl_livro";
		return this.listarLivros(sql);
	}
	
	/*
	 * Método privado para listar os resultados do banco
	*/
	private ArrayList<Livro> listarLivros(String sql) {
		ArrayList<Livro> livros = new ArrayList<Livro>();
		
		Statement stmt = null;
		ResultSet result = null;
		
		try {
			this.biblio.conectar();
			
			stmt = this.biblio.getStatement();
			result = stmt.executeQuery(sql);
			
			while (result.next()) {
				Livro livro = new Livro(result.getString("titulo"), result.getString("autor"), result.getString("editora"), result.getInt("ano"), result.getString("capa"));
				livro.setId(result.getInt("id"));
				
				livros.add(livro);
			}
			
		} catch (SQLException e) {
			// TODO: handle exception
			e.printStackTrace();
			return null;
		} finally {
			if (stmt != null) {
				try {
					stmt.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
			if (result != null) {
				try {
					result.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
			this.biblio.desconectar();
		}
		
		
		return livros;
	}

}
