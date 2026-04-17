package com.mediateca.gui;

import com.mediateca.bd.DvdCrud;
import com.mediateca.modelos.DVD;
import javax.swing.*;
import java.awt.*;

public class FormularioDvd extends JFrame {
    private JTextField txtCodigo, txtTitulo, txtDirector, txtDuracion, txtGenero, txtUnidades;

    public FormularioDvd() {
        setTitle("Gestión de DVDs");
        setSize(450, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel panel = new JPanel(new GridLayout(7, 2, 5, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        panel.add(new JLabel("Código (Auto):"));
        txtCodigo = new JTextField();
        txtCodigo.setEditable(false);
        txtCodigo.setBackground(new Color(220, 220, 220));
        txtCodigo.setText(new DvdCrud().generarSiguienteCodigo());
        panel.add(txtCodigo);

        panel.add(new JLabel("Título:")); txtTitulo = new JTextField(); panel.add(txtTitulo);
        panel.add(new JLabel("Director:")); txtDirector = new JTextField(); panel.add(txtDirector);
        panel.add(new JLabel("Duración:")); txtDuracion = new JTextField(); panel.add(txtDuracion);
        panel.add(new JLabel("Género:")); txtGenero = new JTextField(); panel.add(txtGenero);
        panel.add(new JLabel("Unidades:")); txtUnidades = new JTextField(); panel.add(txtUnidades);

        JButton btnGuardar = new JButton("Guardar DVD");
        btnGuardar.addActionListener(e -> guardar());

        add(panel, BorderLayout.CENTER);
        add(btnGuardar, BorderLayout.SOUTH);
    }

    private void guardar() {
        try {
            if (txtTitulo.getText().isEmpty() || txtDirector.getText().isEmpty() || txtUnidades.getText().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Completa los campos obligatorios.", "Atención", JOptionPane.WARNING_MESSAGE);
                return;
            }

            DVD dvd = new DVD(
                    txtCodigo.getText(),
                    txtTitulo.getText(),
                    txtDirector.getText(),
                    txtDuracion.getText(),
                    txtGenero.getText(),
                    Integer.parseInt(txtUnidades.getText().trim())
            );

            if (new DvdCrud().registrarDVD(dvd)) {
                int respuesta = JOptionPane.showConfirmDialog(this, "DVD guardado.\n¿Registrar otro?", "Éxito", JOptionPane.YES_NO_OPTION);
                if (respuesta == JOptionPane.YES_OPTION) { limpiar(); } else { this.dispose(); }
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error en los datos ingresados.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void limpiar() {
        txtTitulo.setText(""); txtDirector.setText(""); txtDuracion.setText("");
        txtGenero.setText(""); txtUnidades.setText("");
        txtCodigo.setText(new DvdCrud().generarSiguienteCodigo());
    }
}