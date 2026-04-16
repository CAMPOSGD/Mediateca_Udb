package com.mediateca.modelos;

public class CD {
    private String codigoInterno;
    private String titulo;
    private String genero;
    private String duracion;
    private String artista;
    private int numCanciones;
    private int unidadesDisponibles;

    public CD(String codigoInterno, String titulo, String genero, String duracion, String artista, int numCanciones, int unidadesDisponibles) {
        this.codigoInterno = codigoInterno;
        this.titulo = titulo;
        this.genero = genero;
        this.duracion = duracion;
        this.artista = artista;
        this.numCanciones = numCanciones;
        this.unidadesDisponibles = unidadesDisponibles;
    }


    public String getCodigoInterno() { return codigoInterno; }
    public void setCodigoInterno(String codigoInterno) { this.codigoInterno = codigoInterno; }

    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }

    public String getGenero() { return genero; }
    public void setGenero(String genero) { this.genero = genero; }

    public String getDuracion() { return duracion; }
    public void setDuracion(String duracion) { this.duracion = duracion; }

    public String getArtista() { return artista; }
    public void setArtista(String artista) { this.artista = artista; }

    // Este es el nombre clave: getNumCanciones
    public int getNumCanciones() { return numCanciones; }
    public void setNumCanciones(int numCanciones) { this.numCanciones = numCanciones; }

    public int getUnidadesDisponibles() { return unidadesDisponibles; }
    public void setUnidadesDisponibles(int unidadesDisponibles) { this.unidadesDisponibles = unidadesDisponibles; }
}