package com.mediateca.gui;

import com.mediateca.bd.CdCrud;
import com.mediateca.modelos.CD;
import javax.swing.*;
import java.awt.*;

public class FormularioCd extends JFrame {
    private JTextField txtCodigo, txtTitulo, txtGenero, txtDuracion, txtArtista, txtCanciones, txtUnidades;

    public FormularioCd() {
        setTitle("Gestión de CDs de Audio");
        setSize(400, 450);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel panel = new JPanel(new GridLayout(8, 2, 5, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        panel.add(new JLabel("Código (Auto):"));
        txtCodigo = new JTextField();
        txtCodigo.setEditable(false);
        txtCodigo.setBackground(new Color(220, 220, 220));
        txtCodigo.setText(new CdCrud().generarSiguienteCodigo());
        panel.add(txtCodigo);

        panel.add(new JLabel("Título:")); txtTitulo = new JTextField(); panel.add(txtTitulo);
        panel.add(new JLabel("Género:")); txtGenero = new JTextField(); panel.add(txtGenero);
        panel.add(new JLabel("Duración:")); txtDuracion = new JTextField(); panel.add(txtDuracion);
        panel.add(new JLabel("Artista:")); txtArtista = new JTextField(); panel.add(txtArtista);
        panel.add(new JLabel("N° Canciones:")); txtCanciones = new JTextField(); panel.add(txtCanciones);
        panel.add(new JLabel("Unidades:")); txtUnidades = new JTextField(); panel.add(txtUnidades);

        JButton btnGuardar = new JButton("Guardar CD");
        btnGuardar.addActionListener(e -> guardar());

        add(panel, BorderLayout.CENTER);
        add(btnGuardar, BorderLayout.SOUTH);
    }

    private void guardar() {
        try {
            if (txtTitulo.getText().isEmpty() || txtArtista.getText().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Por favor, llena los campos principales.", "Atención", JOptionPane.WARNING_MESSAGE);
                return;
            }

            CD cd = new CD(txtCodigo.getText(), txtTitulo.getText(), txtGenero.getText(), txtDuracion.getText(),
                    txtArtista.getText(), Integer.parseInt(txtCanciones.getText()), Integer.parseInt(txtUnidades.getText()));

            if (new CdCrud().registrarCD(cd)) {
                // --- LA NUEVA MAGIA AQUÍ ---
                int respuesta = JOptionPane.showConfirmDialog(this, "CD guardado con éxito.\n¿Deseas registrar otro CD?", "Éxito", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                if (respuesta == JOptionPane.YES_OPTION) { limpiar(); } else { this.dispose(); }
            } else {
                JOptionPane.showMessageDialog(this, "Error al guardar en la BD.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (NumberFormatException ex) { JOptionPane.showMessageDialog(this, "Revisa los campos numéricos.", "Error", JOptionPane.ERROR_MESSAGE); }
    }

    private void limpiar() {
        txtTitulo.setText(""); txtGenero.setText(""); txtDuracion.setText(""); txtArtista.setText(""); txtCanciones.setText(""); txtUnidades.setText("");
        txtCodigo.setText(new CdCrud().generarSiguienteCodigo());
    }
}