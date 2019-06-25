package main;

import java.util.ArrayList;

import models.Aluguel;
import models.Livro;
import models.Usuario;
import repository.AluguelDatabase;
import repository.LivroDatabase;
import repository.UsuarioDatabase;

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
//		String titulos[] = {
//				"O Alienista", "A Metamorfose", "A Ilha Perdida", "O Corpo Fala", "O Poder do Agora", 
//				"Mulheres Que Correm Com os Lobos", "Pedagogia do Oprimido", "Rápido e Devagar: Duas Formas de Pensar", "Atenção Plena: Mindfulness",
//				"A Mágica da Arrumação", "A Mente Influente", "A Ciência da Meditação", "O Capital",
//				"Admirável Mundo Novo", "Pedagogia da Autonomia", "Manifesto do Partido Comunista", "Hibisco Roxo", "1984",
//				"Star Wars: Herdeiro do Império", "Wayne de Gotham"};
//		
//		String autores[] = {
//				"Machado de Assis", "Franz Kafka", "Maria José Dupré", "Pierre Weil e Roland Tompakow", 
//				"Eckhart Tolle", "Clarissa Pinkola Estés", "Paulo Freire", "Daniel Kahneman", "Mark Williams e Danny Penman",
//				"Marie Kondo", "Tali Sharot", "Daniel Goleman", "Karl Marx", "Aldous Huxley", "Paulo Freire", "Karl Marx e Friedrich Engels",
//				"Chimamanda Ngozi Adichie", "George Orwell", "Timothy Zahn", "Tracy Hickman"};
//		
//		String editoras[] = {
//				"Atica", "Lpm Pocket", "Atica", "Vozes", "Sextante", "Rocco", "Paz Terra", "Objetiva", "Sextante", "Sextante", 
//				"Rocco", "Objetiva", "Nova Cultural", "Globo", "Paz Terra", "Expressão Popular", "Companhia das Letras", "Companhia das Lestras", "Aleph", "Leya"};
//		
//		int anos[] = {1975, 2011, 1978, 2004, 2002, 2018, 2005, 2012, 2015, 2015, 2018, 2017, 1988, 1977, 2008, 2008, 2017, 2016, 2014, 2015};
//		
//		String capas[] = {
//				"oAlienista.jpg", "aMetamorfose.jpg", "aIlhaPerdida.jpg", "oCorpoFala.jpg", 
//				"oPoderDoAgora.jpg", "mulheresQueCorremComOsLobos.jpg", "pedagogiaDoOprimido.jpg", "rapidoEDevagarDuasFormasDePensar.jpg",
//				"atencaoPlenaMindfulness.jpg", "aMagicaDaArrumacao.jpg", "aMenteInfluente.jpg", "aCienciaDaMeditacao.jpg", "oCapital.jpg",
//				"admiravelMundoNovo.jpg", "pedagogiaDaAutonomia.jpg", "manifestoDoPartidoComunista.jpg", "hibiscoRoxo.jpg", "1984.jpg",
//				"starWarsHerdeiroDoImperio.jpg", "wayneDeGotham.jpg"};
//		
//		ArrayList<Livro> livros = new ArrayList<Livro>();
//		
//		for (int i = 0; i < titulos.length; i++) {
//			Livro livro = new Livro(titulos[i], autores[i], editoras[i], anos[i], capas[i]);
//			livros.add(livro);
//		}
//		
//		
//		LivroDatabase livroDb = new LivroDatabase();
//		
//		boolean criou = livroDb.criarTabela();
		
//		if (criou) {
//			livroDb.adicionarLivros(livros);			
//		}
		
//		ArrayList<Livro> listaLivros = livroDb.buscarLivro("DO PARTIDO");
//		
//		for(Livro livro : listaLivros) {
//			System.out.println("Titulo: " + livro.getTitulo());
//			System.out.println("Autor: " + livro.getAutor());
//			System.out.println("Editora: " + livro.getEditora());
//			System.out.println("Ano: " + livro.getAno());
//			System.out.println("Id: " + livro.getId());
//			System.out.println("-----------------------------");
//		}
//		
//		UsuarioDatabase usuarioDb = new UsuarioDatabase();
//		usuarioDb.criarTabela();
		
//		AluguelDatabase aluguelDb = new AluguelDatabase();
//		Aluguel aluguel = aluguelDb.obterAluguelPorUsuarioELivro(3, 20);
		
//		for (Aluguel aluguel : alugueis) {	
//		if (aluguel != null) {
//			System.out.println("Titulo: " + aluguel.getLivro().getTitulo());
//			System.out.println("Autor: " + aluguel.getLivro().getAutor());
//			System.out.println("Usuario: " + aluguel.getUsuario().getNome());
//			System.out.println("Email: " + aluguel.getUsuario().getEmail());
//			System.out.println("Coleta: " + aluguel.getDataColeta());
//			System.out.println("-----------------------------");
//		}
		
		
//		aluguelDb.criarTabela();
		
		UsuarioDatabase usuadioDB = new UsuarioDatabase();
		Usuario usuario = usuadioDB.logarUsuario("bruno@email.com", "123123");
		
		if (usuario != null) {
			System.out.println("[Logado] Nome: " + usuario.getNome());
		} else {
			System.out.println("Nenhum usuário encontrado");
		}
	}

}
