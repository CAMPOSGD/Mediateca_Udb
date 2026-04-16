package sv.edu.udb.model;

public class Libro extends Material {

    private String autor;

    public Libro(String codigo, String titulo, int unidades, String autor) {
        super(codigo, titulo, unidades);
        this.autor = autor;
    }

    public String getAutor() {
        return autor;
    }
}