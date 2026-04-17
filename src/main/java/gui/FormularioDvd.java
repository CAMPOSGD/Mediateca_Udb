package com.mediateca.gui;

import com.mediateca.bd.DvdCrud;
import com.mediateca.modelos.DVD;
import javax.swing.*;
import java.awt.*;

public class FormularioDvd extends JFrame {
    private JTextField txtCod, txtTit, txtDir, txtDur, txtGen, txtUni;

    public FormularioDvd() {
        setTitle("Registrar DVD");
        setSize(450, 420);
        setLayout(new GridLayout(7, 2, 10, 10));
        setLocationRelativeTo(null);

        add(new JLabel(" Código (Auto):"));
        txtCod = new JTextField(new DvdCrud().generarSiguienteCodigo());
        txtCod.setEditable(false);
        add(txtCod);

        add(new JLabel(" Título:")); txtTit = new JTextField(); add(txtTit);
        add(new JLabel(" Director:")); txtDir = new JTextField(); add(txtDir);
        add(new JLabel(" Duración:")); txtDur = new JTextField(); add(txtDur);
        add(new JLabel(" Género:")); txtGen = new JTextField(); add(txtGen);
        add(new JLabel(" Unidades:")); txtUni = new JTextField(); add(txtUni);

        JButton btnGuardar = new JButton("Guardar");
        btnGuardar.addActionListener(e -> guardar());
        add(btnGuardar);
        add(new JButton("Cancelar") {{ addActionListener(e -> dispose()); }});
    }

    private void guardar() {
        try {
            DVD d = new DVD(
                    txtCod.getText(), txtTit.getText(), txtDir.getText(),
                    txtDur.getText(), txtGen.getText(),
                    Integer.parseInt(txtUni.getText().trim())
            );

            if (new DvdCrud().registrarDVD(d)) {
                JOptionPane.showMessageDialog(this, "DVD registrado.");
                dispose();
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Error: Unidades debe ser un número.");
        }
    }
}