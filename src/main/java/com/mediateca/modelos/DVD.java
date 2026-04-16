package com.mediateca.modelos;

public class DVD {
    private String codigoInterno;
    private String titulo;
    private String genero;
    private String duracion;
    private String director;

    public DVD(String codigoInterno, String titulo, String genero, String duracion, String director) {
        this.codigoInterno = codigoInterno;
        this.titulo = titulo;
        this.genero = genero;
        this.duracion = duracion;
        this.director = director;
    }

    public String getCodigoInterno() { return codigoInterno; }
    public void setCodigoInterno(String codigoInterno) { this.codigoInterno = codigoInterno; }

    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }

    public String getGenero() { return genero; }
    public void setGenero(String genero) { this.genero = genero; }

    public String getDuracion() { return duracion; }
    public void setDuracion(String duracion) { this.duracion = duracion; }

    public String getDirector() { return director; }
    public void setDirector(String director) { this.director = director; }
}