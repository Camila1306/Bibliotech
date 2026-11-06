package br.com.bibliotech;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import br.com.bibliotech.model.Livro;
import br.com.bibliotech.model.Usuario;
import br.com.bibliotech.service.LivroService;
import br.com.bibliotech.service.UsuarioService;

public class App
{
    static Scanner sc = new Scanner(System.in);
    static LivroService livroService = new LivroService();
    static UsuarioService usuarioService = new UsuarioService();
    static EmprestimoService emprestimoService = new EmprestimoService();
 
    public static void main(String[] args) 
    {
        System.out.println("====================================");
        System.out.println("       BIBLIOTECH - BIBLIOTECA");
        System.out.println("====================================");

        List<Livro> livros = new ArrayList<>();
 
        int opcao = -1;

        while (opcao != 0)
        {
            mostrarMenu();
            opcao = lerNumero("Escolha uma opção: ");

            try
            {
                switch (opcao)
                {
                    case 1 -> cadastrarLivro();
                    case 2 -> listarLivros();
                    case 3 -> cadastrarUsuario();
                    case 4 -> listarUsuarios();
                    case 5 -> emprestar();
                    case 6 -> devolver();
                    case 7 -> listarEmprestimosAbertos();
                    case 8 -> listarHistorico();
                    case 0 -> System.out.println("Até logo!");
                    default -> System.out.println("Opção inválida.");
                }
            }
            catch (RuntimeException e)
            {
                System.out.println("Atenção: " + e.getMessage());
            }

            sc.close();
        }
 
        sc.close();
    }
 
    static void mostrarMenu()
    {
        System.out.println();
        System.out.println("--------------- MENU ---------------");
        System.out.println("1 - Cadastrar livro");
        System.out.println("2 - Listar livros");
        System.out.println("3 - Cadastrar usuário");
        System.out.println("4 - Listar usuários");
        System.out.println("5 - Realizar empréstimo");
        System.out.println("6 - Registrar devolução");
        System.out.println("7 - Listar empréstimos em aberto");
        System.out.println("8 - Histórico de empréstimos");
        System.out.println("0 - Sair");
        System.out.println("------------------------------------");
    }


    static void cadastrarLivro() 
    {
        System.out.println("\n--- Cadastro de livro ---");
        String titulo = lerTexto("Título: ");
        String autor = lerTexto("Autor: ");
        String isbn = lerTexto("ISBN (opcional): ");
        int quantidade = lerNumero("Quantidade de exemplares: ");

        livroService.cadastrar(titulo, autor, isbn, quantidade);
        System.out.println("Livro cadastrado com sucesso!");
    }

    static void listarLivros() 
    {
        System.out.println("\n--- Livros ---");
        List<Livro> livros = livroService.listar();

        if (livros.isEmpty())
        {
            System.out.println("Nenhum livro cadastrado.");
            return;
        }

        for (Livro livro : livros)
            System.out.println(livro.toString());
    }

    private static Livro getLivro(List<Livro> livros)
    {
        System.out.print("Título: ");
        String titulo = sc.nextLine().trim();
        System.out.print("Autor: ");
        String autor = sc.nextLine().trim();

        return livros.stream()
                .filter(l -> l.getTitulo().equals(titulo) &&
                                l.getAutor().equals(autor) )
                .findFirst()
                .orElse(null);
    }


    static void cadastrarUsuario()
    {
        System.out.println("\n--- Cadastro de usuário ---");
        String nome = lerTexto("Nome: ");
        String email = lerTexto("E-mail: ");

        usuarioService.cadastrar(nome, email);
        System.out.println("Usuário cadastrado com sucesso!");
    }

    static void listarUsuarios()
    {
        System.out.println("\n--- Usuários ---");
        List<Usuario> usuarios = usuarioService.listar();

        if (usuarios.isEmpty()) 
        {
            System.out.println("Nenhum usuário cadastrado.");
            return;
        }

        for (Usuario usuario : usuarios)
            System.out.println(usuario.toString());
    }


    static void emprestar() {
        System.out.println("\n--- Realizar empréstimo ---");
        listarLivros();    // mostra os livros para o usuário ver os IDs
        int livroId = lerNumero("ID do livro: ");
        listarLeitores();  // mostra os leitores para o usuário ver os IDs
        int leitorId = lerNumero("ID do leitor: ");

        emprestimoService.emprestar(livroId, leitorId);
        System.out.println("Empréstimo realizado! Prazo: 14 dias.");
    }

    static void devolver() {
        System.out.println("\n--- Registrar devolução ---");
        listarEmprestimosAbertos();
        int id = lerNumero("ID do empréstimo: ");

        emprestimoService.devolver(id);
        System.out.println("Devolução registrada com sucesso!");
    }

    static void listarEmprestimosAbertos() {
        System.out.println("\n--- Empréstimos em aberto ---");
        mostrarEmprestimos(emprestimoService.listarAbertos());
    }

    static void listarHistorico() {
        System.out.println("\n--- Histórico de empréstimos ---");
        mostrarEmprestimos(emprestimoService.listarTodos());
    }

    /** Mostra uma linha para cada empréstimo da lista. */
    static void mostrarEmprestimos(ArrayList<Emprestimo> emprestimos) {
        if (emprestimos.isEmpty()) {
            System.out.println("Nenhum empréstimo encontrado.");
            return;
        }

        for (Emprestimo e : emprestimos) {
            String situacao;
            if (e.estaAtrasado()) {
                situacao = "ATRASADO";
            } else if (e.estaAberto()) {
                situacao = "em aberto";
            } else {
                situacao = "devolvido em " + e.getDataDevolucao();
            }

            System.out.println(e.getId() + " | " + e.getTituloLivro() + " | " + e.getNomeLeitor()
                    + " | emprestado em " + e.getDataEmprestimo()
                    + " | devolver até " + e.getDataPrevista()
                    + " | " + situacao);
        }
    }


    static String lerTexto(String str) 
    {
        System.out.print(str);
        return sc.nextLine().trim();
    }

    static int lerNumero(String str) 
    {
        while (true) 
        {
            String texto = lerTexto(str);

            try
            {
                return Integer.parseInt(texto);   // converte "5" em 5
            }
            catch (NumberFormatException e) 
            {
                System.out.println("Digite apenas números.");
            }
        }
    }
}