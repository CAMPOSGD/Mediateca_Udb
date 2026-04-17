package com.mediateca.modelos;

public class Libro extends MaterialEscrito {
    private String autor;
    private int numeroPaginas;
    private String isbn;
    private int anioPublicacion;

    public Libro(String codigo, String titulo, String autor, int paginas, String editorial, String isbn, int anio, int unidades) {
        super(codigo, titulo, editorial, unidades);
        this.autor = autor;
        this.numeroPaginas = paginas;
        this.isbn = isbn;
        this.anioPublicacion = anio;
    }

    public String getAutor() { return autor; }
    public int getNumeroPaginas() { return numeroPaginas; }
    public String getIsbn() { return isbn; }
    public int getAnioPublicacion() { return anioPublicacion; }
}