package com.mediateca.modelos;

public abstract class MaterialAudiovisual extends Material {
    protected String genero;
    protected String duracion;

    public MaterialAudiovisual(String codigoInterno, String titulo, String genero, String duracion, int unidadesDisponibles) {
        super(codigoInterno, titulo, unidadesDisponibles);
        this.genero = genero;
        this.duracion = duracion;
    }

    public String getGenero() { return genero; }
    public void setGenero(String genero) { this.genero = genero; }
    public String getDuracion() { return duracion; }
    public void setDuracion(String duracion) { this.duracion = duracion; }
}