package com.mediateca.modelos;

public class CD extends MaterialAudiovisual {
    private String artista;
    private int numCanciones;

    public CD(String codigo, String titulo, String artista, String genero, String duracion, int canciones, int unidades) {
        super(codigo, titulo, genero, duracion, unidades);
        this.artista = artista;
        this.numCanciones = canciones;
    }

    public String getArtista() { return artista; }
    public int getNumCanciones() { return numCanciones; }
}