package br.com.bibliotech.model;

public class Livro
{
    private long id;
    private String titulo;
    private String autor;
    private String isbn;
    private int quantidade;
    private int disponivel;


    public Livro(String titulo, String autor, String isbn, int quantidade)
    {
        this.titulo = titulo;
        this.autor = autor;
        this.isbn = isbn;
        this.quantidade = quantidade;
        this.disponivel = quantidade;
    }

    public long getId()
    {
        return id;
    }

    public String getTitulo()
    {
        return titulo;
    }

    public String getAutor()
    {
        return autor;
    }

    public String getIsbn()
    {
        return isbn;
    }

    public int getQuantidade()
    {
        return quantidade;
    }

    public int getDisponivel()
    {
        return disponivel;
    }

    public void setId(long id)
    {
        this.id = id;
    }

    public void setDisponivel(int disponivel)
    {
        this.disponivel += disponivel;
    }

    @Override
    public String toString()
    {
        return  id + " | " + titulo + " | " + autor
                    + " | disponíveis: " + disponivel + " de " + quantidade;
    }
}