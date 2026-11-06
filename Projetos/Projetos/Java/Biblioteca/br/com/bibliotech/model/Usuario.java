package br.com.bibliotech.model;

import java.util.Objects;

public class Usuario 
{
    public static final int LIMITE_EMPRESTIMOS = 3;
 
    private long id;
    private String nome;
    private String email;
    private int emprestimosAtivos;
 
    public Usuario(String nome, String email) 
    {
        setNome(nome);
        setEmail(email);
    }
 
    public long  getId() { return id; }

    public String getNome() { return nome; }

    public String getEmail() { return email; }

    public int getEmprestimosAtivos() { return emprestimosAtivos; }
 
    public void setId(long id) 
    {
        this.id = id;
    }

    public void setNome(String nome) 
    {
        this.nome = nome.trim();
    }
 
    public void setEmail(String email) 
    {        
        this.email = email.trim().toLowerCase();
    }
 
    public boolean podeEmprestar() 
    {
        return emprestimosAtivos < LIMITE_EMPRESTIMOS;
    }
 
    public void registrarEmprestimo() 
    {
        if (!podeEmprestar()) 
            throw new IllegalStateException("Limite de " + LIMITE_EMPRESTIMOS + " empréstimos atingido");
        
        emprestimosAtivos++;
    }
 
    public void registrarDevolucao() 
    {
        if (emprestimosAtivos == 0) 
            throw new IllegalStateException("Usuário não possui empréstimos ativos");
        
        emprestimosAtivos--;
    }
 
    @Override
    public boolean equals(Object o) 
    {
        if (this == o) 
            return true;
    
        if (!(o instanceof Usuario outro)) 
            return false;

        return id == outro.id;
    }
 
    @Override
    public int hashCode() { return Objects.hash(id); }
 
    @Override
    public String toString() 
    {
        return id + " | " + nome + " | " + email;
    }
}