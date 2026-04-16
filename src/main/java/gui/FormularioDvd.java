package com.mediateca.gui;

import com.mediateca.bd.DvdCrud;
import com.mediateca.modelos.DVD;
import javax.swing.*;
import java.awt.*;

public class FormularioDvd extends JFrame {
    private JTextField txtCodigo, txtTitulo, txtGenero, txtDuracion, txtDirector;

    public FormularioDvd() {
        setTitle("Gestión de DVDs");
        setSize(400, 350);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel panel = new JPanel(new GridLayout(6, 2, 5, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        panel.add(new JLabel("Código (Auto):"));
        txtCodigo = new JTextField();
        txtCodigo.setEditable(false);
        txtCodigo.setBackground(new Color(220, 220, 220));
        txtCodigo.setText(new DvdCrud().generarSiguienteCodigo());
        panel.add(txtCodigo);

        panel.add(new JLabel("Título:")); txtTitulo = new JTextField(); panel.add(txtTitulo);
        panel.add(new JLabel("Género:")); txtGenero = new JTextField(); panel.add(txtGenero);
        panel.add(new JLabel("Duración:")); txtDuracion = new JTextField(); panel.add(txtDuracion);
        panel.add(new JLabel("Director:")); txtDirector = new JTextField(); panel.add(txtDirector);

        JButton btnGuardar = new JButton("Guardar DVD");
        btnGuardar.addActionListener(e -> guardar());

        add(panel, BorderLayout.CENTER);
        add(btnGuardar, BorderLayout.SOUTH);
    }

    private void guardar() {
        try {
            if (txtTitulo.getText().isEmpty() || txtDirector.getText().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Por favor, llena los campos principales.", "Atención", JOptionPane.WARNING_MESSAGE);
                return;
            }

            DVD dvd = new DVD(txtCodigo.getText(), txtTitulo.getText(), txtGenero.getText(), txtDuracion.getText(), txtDirector.getText());

            if (new DvdCrud().registrarDVD(dvd)) {
                // --- LA NUEVA MAGIA AQUÍ ---
                int respuesta = JOptionPane.showConfirmDialog(this, "DVD guardado con éxito.\n¿Deseas registrar otro DVD?", "Éxito", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                if (respuesta == JOptionPane.YES_OPTION) { limpiar(); } else { this.dispose(); }
            } else {
                JOptionPane.showMessageDialog(this, "Error al guardar en la BD.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (Exception ex) { JOptionPane.showMessageDialog(this, "Error en los datos ingresados.", "Error", JOptionPane.ERROR_MESSAGE); }
    }

    private void limpiar() {
        txtTitulo.setText(""); txtGenero.setText(""); txtDuracion.setText(""); txtDirector.setText("");
        txtCodigo.setText(new DvdCrud().generarSiguienteCodigo());
    }
}