package com.mediateca.modelos;

public abstract class MaterialEscrito extends Material {
    protected String editorial;

    public MaterialEscrito(String codigoInterno, String titulo, String editorial, int unidadesDisponibles) {
        super(codigoInterno, titulo, unidadesDisponibles);
        this.editorial = editorial;
    }

    public String getEditorial() { return editorial; }
    public void setEditorial(String editorial) { this.editorial = editorial; }
}