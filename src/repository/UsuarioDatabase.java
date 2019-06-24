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
	 * M�todo usado para logar o usu�rio com sua senha e email
	*/	
	public boolean logarUsuario(String email, String senha) {
		boolean logado = false;
		
		String sql = "SELECT * FROM tbl_pessoa WHERE email = ? AND senha = ?;";
		
		Statement stmt = null;
		ResultSet result = null;
		
		try {
			this.biblio.conectar();
			stmt = this.biblio.getStatement();
			
			result = stmt.executeQuery(sql);
			int contador = 0;
			
			while(result.next()) {
				contador++;
			}
			
			if (contador == 1) {
				System.out.println("Usu�rio logado");
				logado = true;
			} else if (contador == 0) {
				System.out.println("Nenhum usu�rio encontrado");
			} else {
				System.out.println("Mais de um usu�rio com o mesmo email e senha");
			}
			
		} catch (SQLException e) {
			// TODO: handle exception
			e.fillInStackTrace();
			System.out.println("Erro ao logar usu�rio");
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
		
		return logado;
	}
	
	/*
	 * M�todo de cria��o da tabela tbl_usuario
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
			System.err.println("Tabela Usu�rio n�o criada! =[");
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
		
		System.out.println("Tabela Usu�rio criada com sucesso!");		
		return criado;
	}

}