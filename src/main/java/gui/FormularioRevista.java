package com.mediateca.gui;

import com.mediateca.bd.RevistaCrud;
import com.mediateca.modelos.Revista;
import javax.swing.*;
import java.awt.*;

public class FormularioRevista extends JFrame {
    private JTextField txtCod, txtTit, txtEdi, txtPer, txtFec, txtUni;

    public FormularioRevista() {
        setTitle("Registrar Nueva Revista");
        setSize(450, 450);
        setLayout(new GridLayout(7, 2, 10, 10));
        setLocationRelativeTo(null);

        add(new JLabel(" Código (Auto):"));
        txtCod = new JTextField(new RevistaCrud().generarSiguienteCodigo());
        txtCod.setEditable(false);
        add(txtCod);

        add(new JLabel(" Título:")); txtTit = new JTextField(); add(txtTit);
        add(new JLabel(" Editorial:")); txtEdi = new JTextField(); add(txtEdi);
        add(new JLabel(" Periodicidad:")); txtPer = new JTextField(); add(txtPer);
        add(new JLabel(" Fecha (AAAA-MM-DD):")); txtFec = new JTextField(); add(txtFec);
        add(new JLabel(" Unidades:")); txtUni = new JTextField(); add(txtUni);

        JButton btnGuardar = new JButton("Guardar");
        btnGuardar.addActionListener(e -> guardar());
        add(btnGuardar);
        add(new JButton("Cancelar") {{ addActionListener(e -> dispose()); }});
    }

    private void guardar() {
        try {
            if (!txtFec.getText().matches("\\d{4}-\\d{2}-\\d{2}")) {
                JOptionPane.showMessageDialog(this, "Formato de fecha inválido. Use AAAA-MM-DD");
                return;
            }

            Revista r = new Revista(
                    txtCod.getText(), txtTit.getText(), txtEdi.getText(),
                    txtPer.getText(), txtFec.getText(),
                    Integer.parseInt(txtUni.getText().trim())
            );

            if (new RevistaCrud().registrarRevista(r)) {
                JOptionPane.showMessageDialog(this, "Revista registrada.");
                dispose();
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Error: Unidades debe ser un número.");
        }
    }
}