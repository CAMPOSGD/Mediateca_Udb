package com.mediateca.gui;

import com.mediateca.bd.RevistaCrud;
import com.mediateca.modelos.Revista;
import javax.swing.*;
import java.awt.*;

public class FormularioRevista extends JFrame {
    private JTextField txtCodigo, txtTitulo, txtEditorial, txtPeriodicidad, txtFecha, txtUnidades;

    public FormularioRevista() {
        setTitle("Gestión de Revistas");
        setSize(450, 400);
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
            if (txtTitulo.getText().isEmpty() || txtUnidades.getText().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Completa los campos obligatorios.", "Atención", JOptionPane.WARNING_MESSAGE);
                return;
            }

            Revista revista = new Revista(
                    txtCodigo.getText(),
                    txtTitulo.getText(),
                    txtEditorial.getText(),
                    txtPeriodicidad.getText(),
                    txtFecha.getText(),
                    Integer.parseInt(txtUnidades.getText().trim())
            );

            if (new RevistaCrud().registrarRevista(revista)) {
                int respuesta = JOptionPane.showConfirmDialog(this, "Revista guardada.\n¿Registrar otra?", "Éxito", JOptionPane.YES_NO_OPTION);
                if (respuesta == JOptionPane.YES_OPTION) { limpiar(); } else { this.dispose(); }
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error en los datos ingresados.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void limpiar() {
        txtTitulo.setText(""); txtEditorial.setText(""); txtPeriodicidad.setText("");
        txtFecha.setText(""); txtUnidades.setText("");
        txtCodigo.setText(new RevistaCrud().generarSiguienteCodigo());
    }
}