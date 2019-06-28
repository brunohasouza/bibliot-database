package repository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import db.Biblio;
import models.Aluguel;
import models.Livro;
import models.Usuario;

public class AluguelDatabase {
	private Biblio biblio;
	
	public AluguelDatabase() {
		this.biblio = new Biblio();
	}
	
	/*
	 * Método para validar se o livro está alugado pelo usuário, passando o id de ambos.
	*/
	public boolean verificarLivroAlugadoPeloUsuario(int idUsuario, int idLivro) {
		String sql = "SELECT "
				+ "count(*) "
				+ "FROM tbl_aluguel a "
				+ "JOIN tbl_livro AS l ON a.idLivro = l.id "
				+ "JOIN tbl_usuario AS u ON a.idUsuario = u.id "
				+ "WHERE a.idUsuario = ? "
				+ "AND a.idLivros = "
				+ idLivro + " "
				+ "AND a.devolvido = 0";
		
		int contador = this.contarAluguel(sql, idUsuario, "Aluguel encontrado", "Erro ao contar livro alugado pelo usuário");
		
		return contador > 0;
	}
	
	/*
	 * Método usado para listar os livros alugados passando o id do usuário
	*/
	public ArrayList<Aluguel> listarAlugadosDeUsuario(int idUsuario) {
		String sql = "SELECT "
				+ "u.id AS idUsuario, "
				+ "u.nome AS nomeUsuario, "
				+ "u.email AS emailUsuario, "
				+ "u.senha AS senhaUsuario, "
				+ "l.titulo AS tituloLivro, "
				+ "l.autor AS autorLivro, "
				+ "l.capa AS capaLivro, "
				+ "l.editora AS editoraLivro, "
				+ "l.ano AS anoLivro, "
				+ "l.id AS idLivro, "
				+ "a.dataColeta AS dataColeta, "
				+ "a.dataDevolucao AS dataDevolucao, "
				+ "a.devolvido AS devolvido "
				+ "FROM tbl_aluguel a "
				+ "JOIN tbl_livro AS l ON a.idLivro = l.id "
				+ "JOIN tbl_usuario AS u ON a.idUsuario = u.id "
				+ "WHERE a.idUsuario = "
				+ idUsuario + " "
				+ "AND a.devolvido = 0";
		
		return this.listarAlugueis(sql);
	}
	
	/*
	 * Método usado para listar os livros devolvidos passando o id do usuário
	*/
	public ArrayList<Aluguel> listarDevolvidosDeUsuario(int idUsuario) {
		String sql = "SELECT "
				+ "u.id AS idUsuario, "
				+ "u.nome AS nomeUsuario, "
				+ "u.email AS emailUsuario, "
				+ "u.senha AS senhaUsuario, "
				+ "l.titulo AS tituloLivro, "
				+ "l.autor AS autorLivro, "
				+ "l.capa AS capaLivro, "
				+ "l.editora AS editoraLivro, "
				+ "l.ano AS anoLivro, "
				+ "l.id AS idLivro, "
				+ "a.dataColeta AS dataColeta, "
				+ "a.dataDevolucao AS dataDevolucao, "
				+ "a.devolvido AS devolvido "
				+ "FROM tbl_aluguel a "
				+ "JOIN tbl_livro AS l ON a.idLivro = l.id "
				+ "JOIN tbl_usuario AS u ON a.idUsuario = u.id "
				+ "WHERE a.idUsuario = "
				+ idUsuario + " "
				+ "AND a.devolvido = 1";
		
		return this.listarAlugueis(sql);
	}
	
	
	/*
	 * Este método é privado e serve para contar linhas da tabela tbl_aluguel
	*/
	private int contarAluguel(String sql, int id, String sucessoMsg, String erroMsg) {
		int contador = 0;
		
		PreparedStatement pstmt = null;
		ResultSet result = null;
		
		try {
			this.biblio.conectar();
			pstmt = this.biblio.getPreparedStatement(sql);
			pstmt.setInt(1, id);
			result = pstmt.executeQuery();
			
			while(result.next()) {
				contador = result.getInt(1);
			}
			
			System.out.println(sucessoMsg);
			
		} catch (SQLException e) {
			// TODO: handle exception
			e.printStackTrace();
			System.out.println(erroMsg);
		} finally {
			if (pstmt != null) {
				try {
					pstmt.close();
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
		
		return contador;
		
	}
	
	/*
	 * Método público que conta quantos alugueis em aberto existem no banco para determinado usuário
	 * Valor máximo de 3 alugueis por usuário
	*/
	public boolean usuarioPodeAlugar(int idUsuario) {
		String sql = "SELECT count(*) FROM tbl_aluguel WHERE idUsuario = ? AND devolvido = 0;";
		int contador = this.contarAluguel(sql, idUsuario, "Livros alugados contados com sucesso", "Não foi possível contar os livros deste usuário");
		
		return contador < 3;
	}
	
	/*
	 * Método que verifica se o livro selecionado está alugado
	*/
	public boolean livroEstaAlugado(int idLivro) {		
		String sql = "SELECT count(*) FROM tbl_aluguel WHERE idLivro = ? AND devolvido = 0";	
		int contador = this.contarAluguel(sql, idLivro, "Este livro está alugado", "Erro ao validar aluguel deste livro");
		
		return contador == 0 ;
	}

	/*
	 * Método privado para listar alugueis de acordo com a string passada
	*/
	private ArrayList<Aluguel> listarAlugueis(String sql) {
		ArrayList<Aluguel> alugueis = new ArrayList<Aluguel>();
		
		Statement stmt = null;
		ResultSet result = null;
		
		try {
			this.biblio.conectar();
			
			stmt = this.biblio.getStatement();
			result = stmt.executeQuery(sql);
			
			while (result.next()) {
				Livro livro = new Livro(result.getString("tituloLivro"), result.getString("autorLivro"), result.getString("editoraLivro"), result.getInt("anoLivro"), result.getString("capaLivro"));
				livro.setId(result.getInt("idLivro"));
				
				Usuario usuario = new Usuario(result.getString("nomeUsuario"), result.getString("emailUsuario"), result.getString("senhaUsuario"));
				usuario.setId(result.getInt("idUsuario"));
				
				Aluguel aluguel = new Aluguel(usuario, livro, result.getString("dataColeta"), result.getString("dataDevolucao"), result.getInt("devolvido"));
				
				alugueis.add(aluguel);
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
		
		
		return alugueis;
		
	}

	/*
	 * Método que lista todos os livros que estão alugados e os que já foram devolvidos
	*/
	public ArrayList<Aluguel> listarAlugadosEDevolvidos() {
		String sql = "SELECT "
				+ "usuario.id AS idUsuario, "
				+ "usuario.nome AS nomeUsuario, "
				+ "usuario.email AS emailUsuario, "
				+ "usuario.senha AS senhaUsuario, "
				+ "livro.titulo AS tituloLivro, "
				+ "livro.autor AS autorLivro, "
				+ "livro.capa AS capaLivro, "
				+ "livro.editora AS editoraLivro, "
				+ "livro.ano AS anoLivro, "
				+ "livro.id AS idLivro, "
				+ "aluguel.dataColeta AS dataColeta, "
				+ "aluguel.dataDevolucao as dataDevolucao, "
				+ "aluguel.devolvido AS devolvido "
				+ "FROM tbl_aluguel AS aluguel "
				+ "LEFT JOIN tbl_livro AS livro, "
				+ "tbl_usuario AS usuario "
				+ "WHERE aluguel.idLivro = livro.id AND aluguel.idUsuario = usuario.id;";
		
		return this.listarAlugueis(sql);		
	}

	/*
	 * Método lista todos os livros que já foram devolvidos
	*/
	public ArrayList<Aluguel> listarDevolvidos() {		
		String sql = "SELECT "
				+ "usuario.id AS idUsuario, "
				+ "usuario.nome AS nomeUsuario, "
				+ "usuario.email AS emailUsuario, "
				+ "usuario.senha AS senhaUsuario, "
				+ "livro.titulo AS tituloLivro, "
				+ "livro.autor AS autorLivro, "
				+ "livro.capa AS capaLivro, "
				+ "livro.editora AS editoraLivro, "
				+ "livro.ano AS anoLivro, "
				+ "livro.id AS idLivro, "
				+ "aluguel.dataColeta AS dataColeta, "
				+ "aluguel.dataDevolucao as dataDevolucao, "
				+ "aluguel.devolvido AS devolvido "
				+ "FROM tbl_aluguel AS aluguel "
				+ "LEFT JOIN tbl_livro AS livro, "
				+ "tbl_usuario AS usuario "
				+ "WHERE aluguel.idLivro = livro.id AND aluguel.idUsuario = usuario.id AND aluguel.devolvido = 1;";
		
		return this.listarAlugueis(sql);		
	}

	/*
	 * Método lista todos os livros que estão alugados
	*/
	public ArrayList<Aluguel> listarAlugados() {		
		String sql = "SELECT "
				+ "usuario.id AS idUsuario, "
				+ "usuario.nome AS nomeUsuario, "
				+ "usuario.email AS emailUsuario, "
				+ "usuario.senha AS senhaUsuario, "
				+ "livro.titulo AS tituloLivro, "
				+ "livro.autor AS autorLivro, "
				+ "livro.capa AS capaLivro, "
				+ "livro.editora AS editoraLivro, "
				+ "livro.ano AS anoLivro, "
				+ "livro.id AS idLivro, "
				+ "aluguel.dataColeta AS dataColeta, "
				+ "aluguel.dataDevolucao as dataDevolucao, "
				+ "aluguel.devolvido AS devolvido "
				+ "FROM tbl_aluguel AS aluguel "
				+ "LEFT JOIN tbl_livro AS livro, "
				+ "tbl_usuario AS usuario "
				+ "WHERE aluguel.idLivro = livro.id AND aluguel.idUsuario = usuario.id AND aluguel.devolvido = 0;";
		
		return this.listarAlugueis(sql);
	}
		
	/*
	 * Método que muda a coluna "devolvido" de 0 para 1 de acordo com o id
	*/
	public boolean devolverLivro(int idAluguel) {
		boolean devolvido = false;
		
		String sql = "UPDATE tbl_aluguel SET devolvido = 1 WHERE id = ?";
		PreparedStatement pstmt = null;
		
		try {
			this.biblio.conectar();
			
			pstmt = this.biblio.getPreparedStatement(sql);
			pstmt.setInt(1, idAluguel);
			
			pstmt.executeUpdate();
			
			System.out.println("Aluguel atualizado com sucesso");
			
			devolvido = true;
		} catch (SQLException e) {
			// TODO: handle exception
			e.printStackTrace();
			System.out.println("Não foi possível devolver o livro");
		} finally {
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
			this.biblio.desconectar();
		}
		
		return devolvido;
	}
	
	/*
	 * Método que obtém um único aluguel em aberto (devolvido = 0) de acordo com o id do usuário e id do livro
	 * Caso não encontre o método retorna null
	*/
	public Aluguel obterAluguelPorUsuarioELivro(int idUsuario, int idLivro) {
		Aluguel aluguel = null;
		
		String sql = "SELECT "
				+ "usuario.id AS idUsuario, "
				+ "usuario.nome AS nomeUsuario, "
				+ "usuario.email AS emailUsuario, "
				+ "usuario.senha AS senhaUsuario, "
				+ "livro.titulo AS tituloLivro, "
				+ "livro.autor AS autorLivro, "
				+ "livro.capa AS capaLivro, "
				+ "livro.editora AS editoraLivro, "
				+ "livro.ano AS anoLivro, "
				+ "livro.id AS idLivro, "
				+ "aluguel.dataColeta AS dataColeta, "
				+ "aluguel.dataDevolucao as dataDevolucao, "
				+ "aluguel.devolvido AS devolvido "
				+ "FROM tbl_aluguel AS aluguel "
				+ "LEFT JOIN tbl_livro AS livro, "
				+ "tbl_usuario AS usuario "
				+ "WHERE livro.id = ? AND usuario.id = ? AND aluguel.devolvido = 0;";
		
		PreparedStatement pstmt = null;
		ResultSet result = null;
		
		try {
			this.biblio.conectar();
			pstmt = this.biblio.getPreparedStatement(sql);
			pstmt.setInt(1, idLivro);
			pstmt.setInt(2, idUsuario);
			
			result = pstmt.executeQuery();
			
			while(result.next()) {
				Livro livro = new Livro(result.getString("tituloLivro"), result.getString("autorLivro"), result.getString("editoraLivro"), result.getInt("anoLivro"), result.getString("capaLivro"));
				livro.setId(result.getInt("idLivro"));
				
				Usuario usuario = new Usuario(result.getString("nomeUsuario"), result.getString("emailUsuario"), result.getString("senhaUsuario"));
				usuario.setId(result.getInt("idUsuario"));
				
				aluguel = new Aluguel(usuario, livro, result.getString("dataColeta"), result.getString("dataDevolucao"), result.getInt("devolvido"));
			}
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			System.out.println("Não foi possível obter aluguel");
		} finally {
			if (result != null) {
				try {
					result.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
			this.biblio.desconectar();
		}
		
		return aluguel;
	}
	
	/*
	 * Método de criação da tabela tbl_aluguel
	*/
	public boolean criarTabela() {
		boolean criado = false;
		String sql = "CREATE TABLE IF NOT EXISTS tbl_aluguel"
				+ "("
				+ "id INTEGER PRIMARY KEY AUTOINCREMENT, "
				+ "idUsuario INTEGER NOT NULL, "
				+ "idLivro INTEGER NOT NULL, "
				+ "dataColeta TEXT DEFAULT (datetime('now', 'localtime')), "
				+ "dataDevolucao TEXT DEFAULT (datetime('now', '+10 days', 'localtime')), "
				+ "devolvido INT DEFAULT 0, "
				+ "FOREIGN KEY(idUsuario) REFERENCES tbl_usuario(id), "
				+ "FOREIGN KEY(idLivro) REFERENCES tbl_livro(id)"
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
			System.err.println("Tabela Aluguel não criada! =[");
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
		
		System.out.println("Tabela Aluguel criada com sucesso!");		
		return criado;
	}
	
	/*
	 * Método que troca o atributo "devolvido" para o valor 1 de acordo com o id do usuário e id do livro
	*/
	public boolean alugarLivro(int idUsuario, int idLivro) {
		boolean alugado = false;
		
		String sql = "INSERT INTO tbl_aluguel(idUsuario, idLivro) VALUES(?, ?)";			
		PreparedStatement pstmt = null; 
		
		try {
			this.biblio.conectar();
			pstmt = this.biblio.getPreparedStatement(sql);
			
			pstmt.setInt(1, idUsuario);
			pstmt.setInt(2, idLivro);
			
			int resultado = pstmt.executeUpdate();
			
			if (resultado == 1) {
				System.out.println("Aluguel criado com sucesso");
				alugado = true;
			} else {
				System.out.println("Aluguel não inserido");
			}		
			
		} catch (SQLException e) {
			// TODO: handle exception
			e.printStackTrace();
			System.out.println("Erro ao criar novo aluguel");
		} finally {
			if(pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
			this.biblio.desconectar();
		}
		
		return alugado;
	}
}
