package sv.edu.udb.model;

public abstract class Material {

    protected String codigo;
    protected String titulo;
    protected int unidades;

    public Material(String codigo, String titulo, int unidades) {
        this.codigo = codigo;
        this.titulo = titulo;
        this.unidades = unidades;
    }

    public String getCodigo() {
        return codigo;
    }

    public String getTitulo() {
        return titulo;
    }

    public int getUnidades() {
        return unidades;
    }
}