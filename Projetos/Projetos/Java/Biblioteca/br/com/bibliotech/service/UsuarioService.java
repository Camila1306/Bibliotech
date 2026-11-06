package br.com.bibliotech.service;

import br.com.bibliotech.dao.UsuarioDAO;
import br.com.bibliotech.model.Usuario;

public class UsuarioService
{
    private UsuarioDAO usuarioDAO = new UsuarioDAO();

    public void cadastrar(String nome, String email) 
    {
        if (nome == null || nome.isBlank()) 
            throw new IllegalArgumentException("Nome é obrigatório");

        if (email == null || !email.contains("@") || email.startsWith("@") || email.endsWith("@")) 
            throw new IllegalArgumentException("E-mail inválido");

        Usuario usuario = new Usuario(nome, email);
        usuarioDAO.inserir(usuario);
    }
}
