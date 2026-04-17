package com.mediateca.modelos;

public class DVD extends MaterialAudiovisual {
    private String director;

    public DVD(String codigo, String titulo, String director, String duracion, String genero, int unidades) {
        super(codigo, titulo, genero, duracion, unidades);
        this.director = director;
    }

    public String getDirector() { return director; }
}