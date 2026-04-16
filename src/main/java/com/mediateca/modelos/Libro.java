package com.mediateca.modelos;

public class Libro {
    private String codigoInterno;
    private String titulo;
    private String editorial;
    private String autor;
    private int numeroPaginas;
    private String isbn;
    private int anioPublicacion;
    private int unidadesDisponibles;

    public Libro(String codigoInterno, String titulo, String editorial, String autor, int numeroPaginas, String isbn, int anioPublicacion, int unidadesDisponibles) {
        this.codigoInterno = codigoInterno;
        this.titulo = titulo;
        this.editorial = editorial;
        this.autor = autor;
        this.numeroPaginas = numeroPaginas;
        this.isbn = isbn;
        this.anioPublicacion = anioPublicacion;
        this.unidadesDisponibles = unidadesDisponibles;
    }

    public String getCodigoInterno() { return codigoInterno; }
    public void setCodigoInterno(String codigoInterno) { this.codigoInterno = codigoInterno; }

    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }

    public String getEditorial() { return editorial; }
    public void setEditorial(String editorial) { this.editorial = editorial; }

    public String getAutor() { return autor; }
    public void setAutor(String autor) { this.autor = autor; }

    public int getNumeroPaginas() { return numeroPaginas; }
    public void setNumeroPaginas(int numeroPaginas) { this.numeroPaginas = numeroPaginas; }

    public String getIsbn() { return isbn; }
    public void setIsbn(String isbn) { this.isbn = isbn; }

    public int getAnioPublicacion() { return anioPublicacion; }
    public void setAnioPublicacion(int anioPublicacion) { this.anioPublicacion = anioPublicacion; }

    public int getUnidadesDisponibles() { return unidadesDisponibles; }
    public void setUnidadesDisponibles(int unidadesDisponibles) { this.unidadesDisponibles = unidadesDisponibles; }
}