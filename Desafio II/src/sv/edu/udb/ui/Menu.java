package sv.edu.udb.ui;

import javax.swing.*;
import sv.edu.udb.dao.MaterialDAO;

public class Menu {

    public void iniciar() {

        MaterialDAO dao = new MaterialDAO();

        while (true) {

            String opcion = JOptionPane.showInputDialog(null,
                    "===== SISTEMA MEDIATECA =====\n\n" +
                            "1. Agregar material\n" +
                            "2. Listar materiales\n" +
                            "3. Buscar material\n" +
                            "4. Eliminar material\n" +
                            "5. Salir\n\n" +
                            "Seleccione una opción:",
                    "MENÚ PRINCIPAL",
                    JOptionPane.QUESTION_MESSAGE
            );

            if (opcion == null) return;

            switch (opcion) {

                case "1":
                    agregar(dao);
                    break;

                case "2":
                    listar(dao);
                    break;

                case "3":
                    buscar(dao);
                    break;

                case "4":
                    eliminar(dao);
                    break;

                case "5":
                    JOptionPane.showMessageDialog(null, "Saliendo del sistema...");
                    return;

                default:
                    JOptionPane.showMessageDialog(null, "Opción inválida ❌");
            }
        }
    }

    // ================== AGREGAR ==================
    private void agregar(MaterialDAO dao) {

        String tipo = JOptionPane.showInputDialog(
                "Seleccione tipo:\n1. Libro\n2. Revista\n3. CD\n4. DVD"
        );

        if (tipo == null) return;

        String codigo = JOptionPane.showInputDialog("Código:");

        if (codigo == null || codigo.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Código inválido ❌");
            return;
        }

        if (dao.existeCodigo(codigo)) {
            JOptionPane.showMessageDialog(null, "El código ya existe ❌");
            return;
        }

        String titulo = JOptionPane.showInputDialog("Título:");

        int unidades;
        try {
            unidades = Integer.parseInt(
                    JOptionPane.showInputDialog("Unidades:")
            );
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Unidades inválidas ❌");
            return;
        }

        switch (tipo) {

            case "1": // LIBRO
                String autor = JOptionPane.showInputDialog("Autor:");
                JOptionPane.showMessageDialog(null,
                        "Libro agregado:\nAutor: " + autor);
                break;

            case "2": // REVISTA
                String editorial = JOptionPane.showInputDialog("Editorial:");
                JOptionPane.showMessageDialog(null,
                        "Revista agregada:\nEditorial: " + editorial);
                break;

            case "3": // CD
                String artista = JOptionPane.showInputDialog("Artista:");
                JOptionPane.showMessageDialog(null,
                        "CD agregado:\nArtista: " + artista);
                break;

            case "4": // DVD
                String director = JOptionPane.showInputDialog("Director:");
                JOptionPane.showMessageDialog(null,
                        "DVD agregado:\nDirector: " + director);
                break;

            default:
                JOptionPane.showMessageDialog(null, "Tipo inválido ❌");
                return;
        }

        dao.insertar(codigo, titulo, unidades);
        JOptionPane.showMessageDialog(null, "Material guardado correctamente ✔");
    }

    // ================== LISTAR ==================
    private void listar(MaterialDAO dao) {

        String datos = dao.listar();

        if (datos.isEmpty()) {
            JOptionPane.showMessageDialog(null, "No hay registros");
        } else {
            JOptionPane.showMessageDialog(null,
                    "===== LISTA DE MATERIALES =====\n\n" + datos
            );
        }
    }

    // ================== BUSCAR ==================
    private void buscar(MaterialDAO dao) {

        String codigo = JOptionPane.showInputDialog("Ingrese código a buscar:");

        if (codigo == null || codigo.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Código inválido ❌");
            return;
        }

        String resultado = dao.buscar(codigo);

        JOptionPane.showMessageDialog(null, resultado);
    }

    // ================== ELIMINAR ==================
    private void eliminar(MaterialDAO dao) {

        String codigo = JOptionPane.showInputDialog("Ingrese código a eliminar:");

        if (codigo == null || codigo.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Código inválido ❌");
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(
                null,
                "¿Seguro que desea eliminar?",
                "Confirmar",
                JOptionPane.YES_NO_OPTION
        );

        if (confirm == JOptionPane.YES_OPTION) {
            dao.eliminar(codigo);
            JOptionPane.showMessageDialog(null, "Operación realizada ✔");
        }
    }
}