package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

public class Biblio {
	private Connection conexao;	
	
	public boolean conectar() {
		String url = "jdbc:sqlite:banco_de_dados/biblio.db";
		
		try {
			this.conexao = DriverManager.getConnection(url);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		
		System.out.println("Conectado");
		return true;
	}
	
	public boolean desconectar() {
		try {
			if (!this.conexao.isClosed()) {
				this.conexao.close();
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		
		System.out.println("Desconetado");
		return true;
	}
	
	public Statement getStatement() {		
		try {
			Statement stmt = this.conexao.createStatement();			
			System.out.println("Statement criado");
			return stmt;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();			
			return null;
		}
	}
	
	public PreparedStatement getPreparedStatement(String sql) {
		try {
			PreparedStatement pstmt = this.conexao.prepareStatement(sql);			
			System.out.println("Prepared Statement criado");
			return pstmt;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
	
}
