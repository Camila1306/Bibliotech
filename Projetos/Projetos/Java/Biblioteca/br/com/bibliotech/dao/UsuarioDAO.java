package br.com.bibliotech.dao;

import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

import Java.Biblioteca.br.com.bibliotech.model.Livro;
import Java.util.Arquivo;
import br.com.bibliotech.model.Usuario;

public class UsuarioDAO
{
    private static final String ARQUIVO = "usuarios.json";

    public void inserir(Usuario usuario)
    {
        List<Usuario> usuarios = listar();

        usuario.setId(usuarios.size() + 1);

        usuarios.add(usuario);
        salvar(usuarios);
    }

    public List<Usuario> listar()
    {
        String texto = Arquivo.ler(ARQUIVO);
        
        Usuario[] vetor = Arquivo.GSON.fromJson(texto, Usuario[].class);

        return Arrays.asList(vetor);
    }

    public Usuario buscarPorId(int id) 
    {
        return listar().stream()
                .filter(u -> u.getId() == id)
                .findFirst()
                .orElse(null);
    }

    public void atualizar(Usuario usuarioAlterado) 
    {
        List<Usuario> usuarios = listar();
        IntStream.range(0, usuarios.size())
                .filter(i -> usuarios.get(i).getId() == usuarioAlterado.getId())
                .findFirst()
                .ifPresent(i -> usuarios.set(i, usuarioAlterado));

        salvar(usuarios);
    }

    private void salvar(List<Usuario> usuarios) 
    {
        String texto = Arquivo.GSON.toJson(usuarios);
        Arquivo.gravar(ARQUIVO, texto);
    }
}
