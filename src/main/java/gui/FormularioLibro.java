package com.mediateca.gui;

import com.mediateca.bd.LibroCrud;
import com.mediateca.modelos.Libro;
import javax.swing.*;
import java.awt.*;

public class FormularioLibro extends JFrame {
    private JTextField txtCodigo, txtTitulo, txtEditorial, txtAutor, txtPaginas, txtIsbn, txtAnio, txtUnidades;

    public FormularioLibro() {
        setTitle("Gestión de Libros");
        setSize(400, 450);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel panelFormulario = new JPanel(new GridLayout(8, 2, 5, 10));
        panelFormulario.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        panelFormulario.add(new JLabel("Código (Auto):"));
        txtCodigo = new JTextField();
        txtCodigo.setEditable(false);
        txtCodigo.setBackground(new Color(220, 220, 220));
        txtCodigo.setText(new LibroCrud().generarSiguienteCodigo());
        panelFormulario.add(txtCodigo);

        panelFormulario.add(new JLabel("Título:")); txtTitulo = new JTextField(); panelFormulario.add(txtTitulo);
        panelFormulario.add(new JLabel("Editorial:")); txtEditorial = new JTextField(); panelFormulario.add(txtEditorial);
        panelFormulario.add(new JLabel("Autor:")); txtAutor = new JTextField(); panelFormulario.add(txtAutor);
        panelFormulario.add(new JLabel("N° de Páginas:")); txtPaginas = new JTextField(); panelFormulario.add(txtPaginas);
        panelFormulario.add(new JLabel("ISBN:")); txtIsbn = new JTextField(); panelFormulario.add(txtIsbn);
        panelFormulario.add(new JLabel("Año Publicación:")); txtAnio = new JTextField(); panelFormulario.add(txtAnio);
        panelFormulario.add(new JLabel("Unidades:")); txtUnidades = new JTextField(); panelFormulario.add(txtUnidades);

        JButton btnGuardar = new JButton("Guardar Libro");
        btnGuardar.addActionListener(e -> guardar());

        add(panelFormulario, BorderLayout.CENTER);
        add(btnGuardar, BorderLayout.SOUTH);
    }

    private void guardar() {
        try {
            if (txtTitulo.getText().isEmpty() || txtEditorial.getText().isEmpty() || txtAutor.getText().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Por favor, llena los campos principales.", "Atención", JOptionPane.WARNING_MESSAGE);
                return;
            }

            Libro libro = new Libro(txtCodigo.getText(), txtTitulo.getText(), txtEditorial.getText(), txtAutor.getText(),
                    Integer.parseInt(txtPaginas.getText()), txtIsbn.getText(), Integer.parseInt(txtAnio.getText()), Integer.parseInt(txtUnidades.getText()));

            if (new LibroCrud().registrarLibro(libro)) {
                // --- LA NUEVA MAGIA AQUÍ ---
                int respuesta = JOptionPane.showConfirmDialog(this, "Libro guardado con éxito.\n¿Deseas registrar otro libro?", "Éxito", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                if (respuesta == JOptionPane.YES_OPTION) { limpiar(); } else { this.dispose(); }
            } else {
                JOptionPane.showMessageDialog(this, "Error al guardar en la BD.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (NumberFormatException ex) { JOptionPane.showMessageDialog(this, "Revisa los campos numéricos.", "Error", JOptionPane.ERROR_MESSAGE); }
    }

    private void limpiar() {
        txtTitulo.setText(""); txtEditorial.setText(""); txtAutor.setText(""); txtPaginas.setText(""); txtIsbn.setText(""); txtAnio.setText(""); txtUnidades.setText("");
        txtCodigo.setText(new LibroCrud().generarSiguienteCodigo());
    }
}