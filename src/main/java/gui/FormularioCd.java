package com.mediateca.gui;

import com.mediateca.bd.CdCrud;
import com.mediateca.modelos.CD;
import javax.swing.*;
import java.awt.*;

public class FormularioCd extends JFrame {
    private JTextField txtCod, txtTit, txtArt, txtGen, txtDur, txtCan, txtUni;

    public FormularioCd() {
        setTitle("Registrar CD de Audio");
        setSize(450, 480);
        setLayout(new GridLayout(8, 2, 10, 10));
        setLocationRelativeTo(null);

        add(new JLabel(" Código (Auto):"));
        txtCod = new JTextField(new CdCrud().generarSiguienteCodigo());
        txtCod.setEditable(false);
        add(txtCod);

        add(new JLabel(" Título:")); txtTit = new JTextField(); add(txtTit);
        add(new JLabel(" Artista:")); txtArt = new JTextField(); add(txtArt);
        add(new JLabel(" Género:")); txtGen = new JTextField(); add(txtGen);
        add(new JLabel(" Duración (min):")); txtDur = new JTextField(); add(txtDur);
        add(new JLabel(" N° Canciones:")); txtCan = new JTextField(); add(txtCan);
        add(new JLabel(" Unidades:")); txtUni = new JTextField(); add(txtUni);

        JButton btnGuardar = new JButton("Guardar");
        btnGuardar.addActionListener(e -> guardar());
        add(btnGuardar);
        add(new JButton("Cancelar") {{ addActionListener(e -> dispose()); }});
    }

    private void guardar() {
        try {
            CD cd = new CD(
                    txtCod.getText(), txtTit.getText(), txtArt.getText(),
                    txtGen.getText(), txtDur.getText(),
                    Integer.parseInt(txtCan.getText().trim()),
                    Integer.parseInt(txtUni.getText().trim())
            );

            if (new CdCrud().registrarCD(cd)) {
                JOptionPane.showMessageDialog(this, "CD registrado.");
                dispose();
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Error: Canciones y Unidades deben ser números.");
        }
    }
}