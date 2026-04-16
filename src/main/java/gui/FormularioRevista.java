package com.mediateca.gui;

import com.mediateca.bd.RevistaCrud;
import com.mediateca.modelos.Revista;
import javax.swing.*;
import java.awt.*;

public class FormularioRevista extends JFrame {
    private JTextField txtCodigo, txtTitulo, txtEditorial, txtPeriodicidad, txtFecha, txtUnidades;

    public FormularioRevista() {
        setTitle("Gestión de Revistas");
        setSize(400, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel panel = new JPanel(new GridLayout(7, 2, 5, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        panel.add(new JLabel("Código (Auto):"));
        txtCodigo = new JTextField();
        txtCodigo.setEditable(false);
        txtCodigo.setBackground(new Color(220, 220, 220));
        txtCodigo.setText(new RevistaCrud().generarSiguienteCodigo());
        panel.add(txtCodigo);

        panel.add(new JLabel("Título:")); txtTitulo = new JTextField(); panel.add(txtTitulo);
        panel.add(new JLabel("Editorial:")); txtEditorial = new JTextField(); panel.add(txtEditorial);
        panel.add(new JLabel("Periodicidad:")); txtPeriodicidad = new JTextField(); panel.add(txtPeriodicidad);
        panel.add(new JLabel("Fecha (YYYY-MM-DD):")); txtFecha = new JTextField(); panel.add(txtFecha);
        panel.add(new JLabel("Unidades:")); txtUnidades = new JTextField(); panel.add(txtUnidades);

        JButton btnGuardar = new JButton("Guardar Revista");
        btnGuardar.addActionListener(e -> guardar());

        add(panel, BorderLayout.CENTER);
        add(btnGuardar, BorderLayout.SOUTH);
    }

    private void guardar() {
        try {
            if (txtTitulo.getText().isEmpty() || txtEditorial.getText().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Por favor, llena los campos principales.", "Atención", JOptionPane.WARNING_MESSAGE);
                return;
            }

            Revista r = new Revista(txtCodigo.getText(), txtTitulo.getText(), txtEditorial.getText(),
                    txtPeriodicidad.getText(), txtFecha.getText(), Integer.parseInt(txtUnidades.getText()));

            if (new RevistaCrud().registrarRevista(r)) {
                // --- LA NUEVA MAGIA AQUÍ ---
                int respuesta = JOptionPane.showConfirmDialog(this, "Revista guardada con éxito.\n¿Deseas registrar otra revista?", "Éxito", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                if (respuesta == JOptionPane.YES_OPTION) { limpiar(); } else { this.dispose(); }
            } else {
                JOptionPane.showMessageDialog(this, "Error al guardar en la BD.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (NumberFormatException ex) { JOptionPane.showMessageDialog(this, "Revisa los campos numéricos.", "Error", JOptionPane.ERROR_MESSAGE); }
    }

    private void limpiar() {
        txtTitulo.setText(""); txtEditorial.setText(""); txtPeriodicidad.setText(""); txtFecha.setText(""); txtUnidades.setText("");
        txtCodigo.setText(new RevistaCrud().generarSiguienteCodigo());
    }
}