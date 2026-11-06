package br.com.bibliotech.service;

import br.com.bibliotech.dao.LivroDAO;
import br.com.bibliotech.model.Livro;
import java.util.List;

public class LivroService
 {
    private LivroDAO livroDAO = new LivroDAO();

    public void cadastrar(String titulo, String autor, String isbn, int quantidade)
    {
        if (titulo.isBlank())
            throw new IllegalArgumentException("Informe o título do livro.");

        if (autor.isBlank())
            throw new IllegalArgumentException("Informe o autor do livro.");

        if (quantidade < 1) 
            throw new IllegalArgumentException("A quantidade precisa ser pelo menos 1.");

        Livro livro = new Livro(titulo, autor, isbn, quantidade);
        livroDAO.inserir(livro);
    }

    public List<Livro> listar()
    {
        return livroDAO.listar();
    }
}