package br.com.bibliotech.util;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class Arquivo 
{
    private static final String PASTA = "dados";

    public static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

    public static String ler(String nomeArquivo) 
    {
        Path caminho = Path.of(PASTA, nomeArquivo);   // monta o caminho: dados/livros.json

        if (!Files.exists(caminho))
            return "[]";   // primeira vez: ainda não há nada gravado

        try 
        {
            String texto = Files.readString(caminho);
            
            if (texto.isBlank()) 
                return "[]";   // arquivo existe, mas está vazio
            
            return texto;
        } 
        catch (IOException e) 
        {
            throw new RuntimeException("Não foi possível ler o arquivo " + caminho);
        }
    }

    public static void gravar(String nomeArquivo, String texto) 
    {
        Path caminho = Path.of(PASTA, nomeArquivo);

        try 
        {
            Files.createDirectories(Path.of(PASTA));
            Files.writeString(caminho, texto);
        } 
        catch (IOException e) 
        {
            throw new RuntimeException("Não foi possível gravar o arquivo " + caminho);
        }
    }
}
