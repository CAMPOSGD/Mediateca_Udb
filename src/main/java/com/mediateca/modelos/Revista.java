package com.mediateca.modelos;

public class Revista extends MaterialEscrito {
    private String periodicidad;
    private String fechaPublicacion;

    public Revista(String codigo, String titulo, String editorial, String periodicidad, String fecha, int unidades) {
        super(codigo, titulo, editorial, unidades);
        this.periodicidad = periodicidad;
        this.fechaPublicacion = fecha;
    }

    public String getPeriodicidad() { return periodicidad; }
    public String getFechaPublicacion() { return fechaPublicacion; }
}