package com.mediateca.modelos;

public abstract class Material {
    protected String codigoInterno;
    protected String titulo;
    protected int unidadesDisponibles;

    public Material(String codigoInterno, String titulo, int unidadesDisponibles) {
        this.codigoInterno = codigoInterno;
        this.titulo = titulo;
        this.unidadesDisponibles = unidadesDisponibles;
    }

    public String getCodigoInterno() { return codigoInterno; }
    public void setCodigoInterno(String codigoInterno) { this.codigoInterno = codigoInterno; }
    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }
    public int getUnidadesDisponibles() { return unidadesDisponibles; }
    public void setUnidadesDisponibles(int unidadesDisponibles) { this.unidadesDisponibles = unidadesDisponibles; }
}