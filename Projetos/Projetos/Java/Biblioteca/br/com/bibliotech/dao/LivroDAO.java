package br.com.bibliotech.dao;

import br.com.bibliotech.model.Livro;
import br.com.bibliotech.util.Arquivo;

import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

public class LivroDAO
{
    private static final String ARQUIVO = "livros.json";

    public void inserir(Livro livro)
    {
        List<Livro> livros = listar();

        livro.setId(livros.size() + 1);

        livros.add(livro);
        salvar(livros);
    }

    public List<Livro> listar()
    {
        String texto = Arquivo.ler(ARQUIVO);
        
        Livro[] vetor = Arquivo.GSON.fromJson(texto, Livro[].class);

        return Arrays.asList(vetor);
    }

    public Livro buscarPorId(int id) 
    {
        return listar().stream()
                .filter(l -> l.getId() == id)
                .findFirst()
                .orElse(null);
    }

    public void atualizar(Livro livroAlterado) 
    {
        List<Livro> livros = listar();
        IntStream.range(0, livros.size())
                .filter(i -> livros.get(i).getId() == livroAlterado.getId())
                .findFirst()
                .ifPresent(i -> livros.set(i, livroAlterado));

        salvar(livros);
    }

    private void salvar(List<Livro> livros) 
    {
        String texto = Arquivo.GSON.toJson(livros);
        Arquivo.gravar(ARQUIVO, texto);
    }
}
