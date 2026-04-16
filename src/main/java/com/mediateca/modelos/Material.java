package com.mediateca.modelos;

public abstract class Material {
    protected String codigoInterno;
    protected String titulo;

    public Material(String codigoInterno, String titulo) {
        this.codigoInterno = codigoInterno;
        this.titulo = titulo;
    }

    public String getCodigoInterno() { return codigoInterno; }
    public void setCodigoInterno(String codigoInterno) { this.codigoInterno = codigoInterno; }

    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }
}