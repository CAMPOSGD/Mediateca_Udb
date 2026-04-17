package com.mediateca.gui;

import com.mediateca.bd.LibroCrud;
import com.mediateca.modelos.Libro;
import javax.swing.*;
import java.awt.*;

public class FormularioLibro extends JFrame {
    private JTextField txtCod, txtTit, txtAut, txtPag, txtEdi, txtIsbn, txtAnio, txtUni;

    public FormularioLibro() {
        setTitle("Registrar Nuevo Libro");
        setSize(450, 550);
        setLayout(new GridLayout(9, 2, 10, 10));
        setLocationRelativeTo(null);

        add(new JLabel(" Código (Auto):"));
        txtCod = new JTextField(new LibroCrud().generarSiguienteCodigo());
        txtCod.setEditable(false);
        add(txtCod);

        add(new JLabel(" Título:")); txtTit = new JTextField(); add(txtTit);
        add(new JLabel(" Autor:")); txtAut = new JTextField(); add(txtAut);
        add(new JLabel(" N° Páginas:")); txtPag = new JTextField(); add(txtPag);
        add(new JLabel(" Editorial:")); txtEdi = new JTextField(); add(txtEdi);
        add(new JLabel(" ISBN:")); txtIsbn = new JTextField(); add(txtIsbn);
        add(new JLabel(" Año Publicación:")); txtAnio = new JTextField(); add(txtAnio);
        add(new JLabel(" Unidades:")); txtUni = new JTextField(); add(txtUni);

        JButton btnGuardar = new JButton("Guardar");
        btnGuardar.addActionListener(e -> guardar());
        add(btnGuardar);

        JButton btnCerrar = new JButton("Cancelar");
        btnCerrar.addActionListener(e -> dispose());
        add(btnCerrar);
    }

    private void guardar() {
        try {
            if (txtTit.getText().isEmpty() || txtAut.getText().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Título y Autor son obligatorios.");
                return;
            }

            Libro l = new Libro(
                    txtCod.getText(), txtTit.getText(), txtAut.getText(),
                    Integer.parseInt(txtPag.getText().trim()), txtEdi.getText(),
                    txtIsbn.getText(), Integer.parseInt(txtAnio.getText().trim()),
                    Integer.parseInt(txtUni.getText().trim())
            );

            if (new LibroCrud().registrarLibro(l)) {
                JOptionPane.showMessageDialog(this, "Libro registrado.");
                dispose();
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Error: Páginas, Año y Unidades deben ser números.");
        }
    }
}