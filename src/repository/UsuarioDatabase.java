package repository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import db.Biblio;
import models.Usuario;

public class UsuarioDatabase {
	private Biblio biblio;
	
	public UsuarioDatabase() {
		this.biblio = new Biblio();
	}
	
	/*
	 * Método usado para logar o usuário com sua senha e email
	*/	
	public Usuario logarUsuario(String email, String senha) {
		Usuario usuario = null;
		
		String sql = "SELECT * FROM tbl_pessoa WHERE email = ? AND senha = ?;";
		
		Statement stmt = null;
		ResultSet result = null;
		
		try {
			this.biblio.conectar();
			stmt = this.biblio.getStatement();
			
			result = stmt.executeQuery(sql);
			
			while(result.next()) {
				usuario = new Usuario(result.getString("nome"), result.getString("email"), result.getString("senha"));
				usuario.setId(result.getInt("id"));
			}
			
		} catch (SQLException e) {
			// TODO: handle exception
			e.fillInStackTrace();
			System.out.println("Erro ao logar usuário");
		} finally {			
			if (stmt != null) {
				try {
					stmt.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				try {
					result.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				this.biblio.desconectar();
			}
		}
		
		return usuario;
	}
	
	/*
	 * Método de criação da tabela tbl_usuario
	*/
	public boolean criarTabela() {
		boolean criado = false;
		String sql = "CREATE TABLE IF NOT EXISTS tbl_usuario"
				+ "("
				+ "id INTEGER PRIMARY KEY AUTOINCREMENT, "
				+ "nome TEXT NOT NULL, "
				+ "email TEXT NOT NULL, "
				+ "senha TEXT NOT NULL "
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
			System.err.println("Tabela Usuário não criada! =[");
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
		
		System.out.println("Tabela Usuário criada com sucesso!");		
		return criado;
	}

}
