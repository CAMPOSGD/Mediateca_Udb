package com.mediateca.gui;

import com.mediateca.bd.LibroCrud;
import com.mediateca.modelos.Libro;
import javax.swing.*;
import java.awt.*;

public class FormularioLibro extends JFrame {
    private JTextField txtCodigo, txtTitulo, txtAutor, txtPaginas, txtEditorial, txtIsbn, txtAnio, txtUnidades;

    public FormularioLibro() {
        setTitle("Gestión de Libros");
        setSize(450, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel panel = new JPanel(new GridLayout(9, 2, 5, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        panel.add(new JLabel("Código (Auto):"));
        txtCodigo = new JTextField();
        txtCodigo.setEditable(false);
        txtCodigo.setBackground(new Color(220, 220, 220));
        txtCodigo.setText(new LibroCrud().generarSiguienteCodigo());
        panel.add(txtCodigo);

        panel.add(new JLabel("Título:")); txtTitulo = new JTextField(); panel.add(txtTitulo);
        panel.add(new JLabel("Autor:")); txtAutor = new JTextField(); panel.add(txtAutor);
        panel.add(new JLabel("N° Páginas:")); txtPaginas = new JTextField(); panel.add(txtPaginas);
        panel.add(new JLabel("Editorial:")); txtEditorial = new JTextField(); panel.add(txtEditorial);
        panel.add(new JLabel("ISBN:")); txtIsbn = new JTextField(); panel.add(txtIsbn);
        panel.add(new JLabel("Año Publicación:")); txtAnio = new JTextField(); panel.add(txtAnio);
        panel.add(new JLabel("Unidades:")); txtUnidades = new JTextField(); panel.add(txtUnidades);

        JButton btnGuardar = new JButton("Guardar Libro");
        btnGuardar.addActionListener(e -> guardar());

        add(panel, BorderLayout.CENTER);
        add(btnGuardar, BorderLayout.SOUTH);
    }

    private void guardar() {
        try {
            if (txtTitulo.getText().isEmpty() || txtAutor.getText().isEmpty() || txtUnidades.getText().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Completa los campos obligatorios.", "Atención", JOptionPane.WARNING_MESSAGE);
                return;
            }

            Libro libro = new Libro(
                    txtCodigo.getText(),
                    txtTitulo.getText(),
                    txtAutor.getText(),
                    Integer.parseInt(txtPaginas.getText().trim()),
                    txtEditorial.getText(),
                    txtIsbn.getText(),
                    Integer.parseInt(txtAnio.getText().trim()),
                    Integer.parseInt(txtUnidades.getText().trim())
            );

            if (new LibroCrud().registrarLibro(libro)) {
                int respuesta = JOptionPane.showConfirmDialog(this, "Libro guardado.\n¿Registrar otro?", "Éxito", JOptionPane.YES_NO_OPTION);
                if (respuesta == JOptionPane.YES_OPTION) { limpiar(); } else { this.dispose(); }
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error en los datos ingresados.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void limpiar() {
        txtTitulo.setText(""); txtAutor.setText(""); txtPaginas.setText("");
        txtEditorial.setText(""); txtIsbn.setText(""); txtAnio.setText(""); txtUnidades.setText("");
        txtCodigo.setText(new LibroCrud().generarSiguienteCodigo());
    }
}