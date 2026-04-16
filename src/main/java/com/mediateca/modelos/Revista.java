package com.mediateca.modelos;

public class Revista {
    private String codigoInterno;
    private String titulo;
    private String editorial;
    private String periodicidad;
    private String fechaPublicacion;
    private int unidadesDisponibles;

    public Revista(String codigoInterno, String titulo, String editorial, String periodicidad, String fechaPublicacion, int unidadesDisponibles) {
        this.codigoInterno = codigoInterno;
        this.titulo = titulo;
        this.editorial = editorial;
        this.periodicidad = periodicidad;
        this.fechaPublicacion = fechaPublicacion;
        this.unidadesDisponibles = unidadesDisponibles;
    }

    public String getCodigoInterno() { return codigoInterno; }
    public void setCodigoInterno(String codigoInterno) { this.codigoInterno = codigoInterno; }

    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }

    public String getEditorial() { return editorial; }
    public void setEditorial(String editorial) { this.editorial = editorial; }

    public String getPeriodicidad() { return periodicidad; }
    public void setPeriodicidad(String periodicidad) { this.periodicidad = periodicidad; }

    public String getFechaPublicacion() { return fechaPublicacion; }
    public void setFechaPublicacion(String fechaPublicacion) { this.fechaPublicacion = fechaPublicacion; }

    public int getUnidadesDisponibles() { return unidadesDisponibles; }
    public void setUnidadesDisponibles(int unidadesDisponibles) { this.unidadesDisponibles = unidadesDisponibles; }
}