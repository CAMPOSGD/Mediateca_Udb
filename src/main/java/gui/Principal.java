package com.mediateca.gui;

import com.mediateca.bd.*;
import com.mediateca.modelos.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;

public class Principal extends JFrame {

    private String tipoActual = "";

    public Principal() {
        setTitle("Dashboard - Mediateca UDB");
        setSize(1100, 750);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        UIManager.put("TabbedPane.selected", new Color(44, 62, 80));
        UIManager.put("TabbedPane.contentAreaColor", Color.WHITE);
        UIManager.put("TabbedPane.font", new Font("Segoe UI", Font.BOLD, 14));

        JTabbedPane panelPestañas = new JTabbedPane();
        panelPestañas.setCursor(new Cursor(Cursor.HAND_CURSOR));

        panelPestañas.addTab("  Agregar Material  ", crearPanelAgregar());
        panelPestañas.addTab("  Modificar Material  ", crearPanelModificar());
        panelPestañas.addTab("  Borrar Material  ", crearPanelBorrar());
        panelPestañas.addTab("  Materiales disponibles  ", crearPanelDisponibles());

        add(panelPestañas);
    }

    private JPanel crearPanelAgregar() {
        JPanel panel = new JPanel(new BorderLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
                GradientPaint gp = new GradientPaint(0, 0, new Color(210, 215, 220), 0, getHeight(), new Color(245, 245, 245));
                g2d.setPaint(gp);
                g2d.fillRect(0, 0, getWidth(), getHeight());
            }
        };

        JPanel panelTitulo = new JPanel(new BorderLayout());
        panelTitulo.setOpaque(false);

        JLabel lblTitulo = new JLabel("Mediateca UDB", SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 36));
        lblTitulo.setForeground(new Color(21, 67, 96));
        lblTitulo.setBorder(BorderFactory.createEmptyBorder(40, 0, 10, 0));

        JLabel lblSubtitulo = new JLabel("¿Qué deseas registrar hoy?", SwingConstants.CENTER);
        lblSubtitulo.setFont(new Font("Segoe UI", Font.PLAIN, 20));
        lblSubtitulo.setForeground(new Color(84, 110, 122));
        lblSubtitulo.setBorder(BorderFactory.createEmptyBorder(0, 0, 30, 0));

        panelTitulo.add(lblTitulo, BorderLayout.NORTH);
        panelTitulo.add(lblSubtitulo, BorderLayout.SOUTH);
        panel.add(panelTitulo, BorderLayout.NORTH);

        JPanel panelBotones = new JPanel(new GridLayout(2, 2, 40, 40));
        panelBotones.setOpaque(false);
        panelBotones.setBorder(BorderFactory.createEmptyBorder(10, 120, 100, 120));

        JButton btnLibros = crearBotonEstilizado("Registrar Libro", new Color(44, 62, 80));
        JButton btnRevistas = crearBotonEstilizado("Registrar Revista", new Color(84, 106, 123));
        JButton btnCds = crearBotonEstilizado("Registrar CD de Audio", new Color(120, 40, 40));
        JButton btnDvds = crearBotonEstilizado("Registrar DVD", new Color(39, 73, 109));

        btnLibros.addActionListener(e -> new FormularioLibro().setVisible(true));
        btnRevistas.addActionListener(e -> new FormularioRevista().setVisible(true));
        btnCds.addActionListener(e -> new FormularioCd().setVisible(true));
        btnDvds.addActionListener(e -> new FormularioDvd().setVisible(true));

        panelBotones.add(btnLibros);
        panelBotones.add(btnRevistas);
        panelBotones.add(btnCds);
        panelBotones.add(btnDvds);

        panel.add(panelBotones, BorderLayout.CENTER);
        return panel;
    }

    private JPanel crearPanelModificar() {
        JPanel panel = new JPanel(new BorderLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
                GradientPaint gp = new GradientPaint(0, 0, new Color(210, 215, 220), 0, getHeight(), new Color(245, 245, 245));
                g2d.setPaint(gp);
                g2d.fillRect(0, 0, getWidth(), getHeight());
            }
        };

        JPanel panelBusqueda = new JPanel(new FlowLayout(FlowLayout.LEFT, 25, 25));
        panelBusqueda.setOpaque(false);

        JLabel lblCod = new JLabel("Código de material:");
        lblCod.setFont(new Font("Segoe UI", Font.BOLD, 15));
        lblCod.setForeground(new Color(44, 62, 80));

        JTextField txtBusqueda = new JTextField(15);
        txtBusqueda.setFont(new Font("Segoe UI", Font.PLAIN, 15));

        JButton btnBuscar = crearBotonEstilizado("Buscar", new Color(39, 73, 109));
        btnBuscar.setPreferredSize(new Dimension(140, 38));

        panelBusqueda.add(lblCod);
        panelBusqueda.add(txtBusqueda);
        panelBusqueda.add(btnBuscar);

        JPanel panelCampos = new JPanel(new GridLayout(0, 2, 15, 15));
        panelCampos.setOpaque(false);
        panelCampos.setBorder(BorderFactory.createEmptyBorder(20, 60, 20, 60));

        JScrollPane scrollPane = new JScrollPane(panelCampos);
        scrollPane.setOpaque(false);
        scrollPane.getViewport().setOpaque(false);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());

        JButton btnActualizar = crearBotonEstilizado("GUARDAR CAMBIOS", new Color(44, 62, 80));
        btnActualizar.setVisible(false);
        btnActualizar.setPreferredSize(new Dimension(320, 55));

        JPanel panelBotonInferior = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 30));
        panelBotonInferior.setOpaque(false);
        panelBotonInferior.add(btnActualizar);

        btnBuscar.addActionListener(e -> {
            String cod = txtBusqueda.getText().toUpperCase().trim();
            panelCampos.removeAll();
            tipoActual = "";
            if (cod.startsWith("LIB")) {
                Libro l = new LibroCrud().buscarLibroPorCodigo(cod);
                if (l != null) {
                    tipoActual = "LIBRO";
                    agregarCampo(panelCampos, "Título:", l.getTitulo());
                    agregarCampo(panelCampos, "Editorial:", l.getEditorial());
                    agregarCampo(panelCampos, "Autor:", l.getAutor());
                    agregarCampo(panelCampos, "Páginas:", String.valueOf(l.getNumeroPaginas()));
                    agregarCampo(panelCampos, "ISBN:", l.getIsbn());
                    agregarCampo(panelCampos, "Año:", String.valueOf(l.getAnioPublicacion()));
                    agregarCampo(panelCampos, "Unidades:", String.valueOf(l.getUnidadesDisponibles()));
                }
            } else if (cod.startsWith("REV")) {
                Revista r = new RevistaCrud().buscarRevistaPorCodigo(cod);
                if (r != null) {
                    tipoActual = "REVISTA";
                    agregarCampo(panelCampos, "Título:", r.getTitulo());
                    agregarCampo(panelCampos, "Editorial:", r.getEditorial());
                    agregarCampo(panelCampos, "Periodicidad:", r.getPeriodicidad());
                    agregarCampo(panelCampos, "Fecha (YYYY-MM-DD):", r.getFechaPublicacion());
                    agregarCampo(panelCampos, "Unidades:", String.valueOf(r.getUnidadesDisponibles()));
                }
            } else if (cod.startsWith("CDA")) {
                CD cd = new CdCrud().buscarCdPorCodigo(cod);
                if (cd != null) {
                    tipoActual = "CD";
                    agregarCampo(panelCampos, "Título:", cd.getTitulo());
                    agregarCampo(panelCampos, "Género:", cd.getGenero());
                    agregarCampo(panelCampos, "Duración:", cd.getDuracion());
                    agregarCampo(panelCampos, "Artista:", cd.getArtista());
                    agregarCampo(panelCampos, "N° Canciones:", String.valueOf(cd.getNumCanciones()));
                    agregarCampo(panelCampos, "Unidades:", String.valueOf(cd.getUnidadesDisponibles()));
                }
            } else if (cod.startsWith("DVD")) {
                DVD d = new DvdCrud().buscarDvdPorCodigo(cod);
                if (d != null) {
                    tipoActual = "DVD";
                    agregarCampo(panelCampos, "Título:", d.getTitulo());
                    agregarCampo(panelCampos, "Género:", d.getGenero());
                    agregarCampo(panelCampos, "Duración:", d.getDuracion());
                    agregarCampo(panelCampos, "Director:", d.getDirector());
                }
            }
            if (!tipoActual.equals("")) {
                btnActualizar.setVisible(true);
            } else {
                JOptionPane.showMessageDialog(this, "Material no encontrado o inactivo.");
                btnActualizar.setVisible(false);
            }
            panelCampos.revalidate();
            panelCampos.repaint();
        });

        btnActualizar.addActionListener(e -> {
            String cod = txtBusqueda.getText().toUpperCase().trim();
            try {
                Component[] comps = panelCampos.getComponents();
                if (tipoActual.equals("LIBRO")) {
                    Libro l = new Libro(cod, ((JTextField)comps[1]).getText(), ((JTextField)comps[3]).getText(), ((JTextField)comps[5]).getText(),
                            Integer.parseInt(((JTextField)comps[7]).getText()), ((JTextField)comps[9]).getText(),
                            Integer.parseInt(((JTextField)comps[11]).getText()), Integer.parseInt(((JTextField)comps[13]).getText()));
                    if (new LibroCrud().actualizarLibro(l)) JOptionPane.showMessageDialog(this, "¡Libro actualizado!");
                } else if (tipoActual.equals("REVISTA")) {
                    Revista r = new Revista(cod, ((JTextField)comps[1]).getText(), ((JTextField)comps[3]).getText(),
                            ((JTextField)comps[5]).getText(), ((JTextField)comps[7]).getText(), Integer.parseInt(((JTextField)comps[9]).getText()));
                    if (new RevistaCrud().actualizarRevista(r)) JOptionPane.showMessageDialog(this, "¡Revista actualizada!");
                } else if (tipoActual.equals("CD")) {
                    CD cd = new CD(cod, ((JTextField)comps[1]).getText(), ((JTextField)comps[3]).getText(), ((JTextField)comps[5]).getText(),
                            ((JTextField)comps[7]).getText(), Integer.parseInt(((JTextField)comps[9]).getText()),
                            Integer.parseInt(((JTextField)comps[11]).getText()));
                    if (new CdCrud().actualizarCD(cd)) JOptionPane.showMessageDialog(this, "¡CD actualizado!");
                } else if (tipoActual.equals("DVD")) {
                    DVD d = new DVD(cod, ((JTextField)comps[1]).getText(), ((JTextField)comps[3]).getText(),
                            ((JTextField)comps[5]).getText(), ((JTextField)comps[7]).getText());
                    if (new DvdCrud().actualizarDVD(d)) JOptionPane.showMessageDialog(this, "¡DVD actualizado!");
                }
                panelCampos.removeAll();
                panelCampos.revalidate();
                panelCampos.repaint();
                btnActualizar.setVisible(false);
                txtBusqueda.setText("");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error en los datos. Revise los campos numéricos.");
            }
        });

        panel.add(panelBusqueda, BorderLayout.NORTH);
        panel.add(scrollPane, BorderLayout.CENTER);
        panel.add(panelBotonInferior, BorderLayout.SOUTH);
        return panel;
    }

    private JPanel crearPanelBorrar() {
        JPanel panel = new JPanel(new BorderLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
                GradientPaint gp = new GradientPaint(0, 0, new Color(210, 215, 220), 0, getHeight(), new Color(245, 245, 245));
                g2d.setPaint(gp);
                g2d.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JPanel panelBusqueda = new JPanel(new FlowLayout(FlowLayout.CENTER, 25, 45));
        panelBusqueda.setOpaque(false);

        JLabel lblCod = new JLabel("Código a inactivar:");
        lblCod.setFont(new Font("Segoe UI", Font.BOLD, 16));
        lblCod.setForeground(new Color(44, 62, 80));

        JTextField txtCodigoBorrar = new JTextField(15);
        txtCodigoBorrar.setFont(new Font("Segoe UI", Font.PLAIN, 16));

        JButton btnBuscarBorrar = crearBotonEstilizado("Verificar", new Color(39, 73, 109));
        btnBuscarBorrar.setPreferredSize(new Dimension(150, 42));

        panelBusqueda.add(lblCod);
        panelBusqueda.add(txtCodigoBorrar);
        panelBusqueda.add(btnBuscarBorrar);

        JLabel lblInfo = new JLabel("Esperando verificación...", SwingConstants.CENTER);
        lblInfo.setFont(new Font("Segoe UI", Font.ITALIC, 22));
        lblInfo.setForeground(new Color(80, 80, 80));

        JButton btnEliminar = crearBotonEstilizado("DAR DE BAJA DEFINITIVA", new Color(120, 40, 40));
        btnEliminar.setEnabled(false);
        btnEliminar.setPreferredSize(new Dimension(380, 65));

        JPanel panelBotonInferior = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 50));
        panelBotonInferior.setOpaque(false);
        panelBotonInferior.add(btnEliminar);

        btnBuscarBorrar.addActionListener(e -> {
            String cod = txtCodigoBorrar.getText().toUpperCase().trim();
            boolean encontrado = false;
            if (cod.startsWith("LIB")) encontrado = (new LibroCrud().buscarLibroPorCodigo(cod) != null);
            else if (cod.startsWith("REV")) encontrado = (new RevistaCrud().buscarRevistaPorCodigo(cod) != null);
            else if (cod.startsWith("CDA")) encontrado = (new CdCrud().buscarCdPorCodigo(cod) != null);
            else if (cod.startsWith("DVD")) encontrado = (new DvdCrud().buscarDvdPorCodigo(cod) != null);

            if (encontrado) {
                lblInfo.setText("Material encontrado. Listo para inactivar.");
                lblInfo.setForeground(new Color(31, 97, 141));
                btnEliminar.setEnabled(true);
            } else {
                lblInfo.setText("No encontrado o ya está inactivo.");
                lblInfo.setForeground(new Color(120, 40, 40));
                btnEliminar.setEnabled(false);
            }
        });

        btnEliminar.addActionListener(e -> {
            String cod = txtCodigoBorrar.getText().toUpperCase().trim();
            int confirm = JOptionPane.showConfirmDialog(this, "¿Está seguro de inactivar el material " + cod + "?", "Confirmar baja", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                if (new CdCrud().eliminarMaterial(cod)) {
                    JOptionPane.showMessageDialog(this, "Material inactivado correctamente.");
                    txtCodigoBorrar.setText("");
                    btnEliminar.setEnabled(false);
                    lblInfo.setText("Esperando verificación...");
                    lblInfo.setForeground(new Color(80, 80, 80));
                }
            }
        });

        panel.add(panelBusqueda, BorderLayout.NORTH);
        panel.add(lblInfo, BorderLayout.CENTER);
        panel.add(panelBotonInferior, BorderLayout.SOUTH);
        return panel;
    }

    private JPanel crearPanelDisponibles() {
        JPanel panel = new JPanel(new BorderLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
                GradientPaint gp = new GradientPaint(0, 0, new Color(210, 215, 220), 0, getHeight(), new Color(245, 245, 245));
                g2d.setPaint(gp);
                g2d.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        String[] columnas = {"Código", "Título", "Tipo de Material", "Autor / Artista / Director", "Disponibles"};
        DefaultTableModel modeloTabla = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) { return false; }
        };

        JTable tabla = new JTable(modeloTabla);
        tabla.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        tabla.setRowHeight(35);
        tabla.setShowGrid(true);
        tabla.setGridColor(new Color(230, 230, 230));

        tabla.getTableHeader().setDefaultRenderer(new javax.swing.table.DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                setBackground(new Color(44, 62, 80));
                setForeground(Color.WHITE);
                setFont(new Font("Segoe UI", Font.BOLD, 15));
                setHorizontalAlignment(JLabel.CENTER);
                setBorder(BorderFactory.createMatteBorder(0, 0, 0, 1, Color.WHITE));
                return this;
            }
        });

        tabla.getTableHeader().setReorderingAllowed(false);
        tabla.getTableHeader().setPreferredSize(new Dimension(0, 45));

        JScrollPane scrollPane = new JScrollPane(tabla);
        scrollPane.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200)));
        scrollPane.getViewport().setBackground(Color.WHITE);

        TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(modeloTabla);
        tabla.setRowSorter(sorter);

        JPanel panelSuperior = new JPanel(new BorderLayout(15, 15));
        panelSuperior.setOpaque(false);
        panelSuperior.setBorder(BorderFactory.createEmptyBorder(0, 0, 25, 0));

        JLabel lblTitulo = new JLabel("CATÁLOGO DE MATERIALES ACTIVOS", SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 30));
        lblTitulo.setForeground(new Color(44, 62, 80));

        JPanel panelFiltro = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 0));
        panelFiltro.setOpaque(false);

        JLabel lblBuscar = new JLabel("Buscar Material:");
        lblBuscar.setFont(new Font("Segoe UI", Font.BOLD, 15));
        lblBuscar.setForeground(new Color(44, 62, 80));

        JTextField txtBuscar = new JTextField(25);
        txtBuscar.setFont(new Font("Segoe UI", Font.PLAIN, 15));

        JButton btnRefrescar = crearBotonEstilizado("Actualizar Tabla", new Color(39, 73, 109));
        btnRefrescar.setPreferredSize(new Dimension(200, 42));

        panelFiltro.add(lblBuscar);
        panelFiltro.add(txtBuscar);
        panelFiltro.add(btnRefrescar);

        panelSuperior.add(lblTitulo, BorderLayout.NORTH);
        panelSuperior.add(panelFiltro, BorderLayout.SOUTH);

        txtBuscar.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) { aplicarFiltro(); }
            @Override
            public void removeUpdate(DocumentEvent e) { aplicarFiltro(); }
            @Override
            public void changedUpdate(DocumentEvent e) { aplicarFiltro(); }
            private void aplicarFiltro() {
                String texto = txtBuscar.getText().trim();
                if (texto.length() == 0) sorter.setRowFilter(null);
                else sorter.setRowFilter(RowFilter.regexFilter("(?i)" + texto));
            }
        });

        btnRefrescar.addActionListener(e -> {
            modeloTabla.setRowCount(0);
            txtBuscar.setText("");
            new LibroCrud().obtenerLibros().forEach(l -> modeloTabla.addRow(new Object[]{l.getCodigoInterno(), l.getTitulo(), "Libro", l.getAutor(), l.getUnidadesDisponibles()}));
            new RevistaCrud().obtenerRevistas().forEach(r -> modeloTabla.addRow(new Object[]{r.getCodigoInterno(), r.getTitulo(), "Revista", "N/A", r.getUnidadesDisponibles()}));
            new CdCrud().obtenerCDs().forEach(c -> modeloTabla.addRow(new Object[]{c.getCodigoInterno(), c.getTitulo(), "CD Audio", c.getArtista(), c.getUnidadesDisponibles()}));
            new DvdCrud().obtenerDVDs().forEach(d -> modeloTabla.addRow(new Object[]{d.getCodigoInterno(), d.getTitulo(), "DVD", d.getDirector(), "N/A"}));
        });

        btnRefrescar.doClick();

        panel.add(panelSuperior, BorderLayout.NORTH);
        panel.add(scrollPane, BorderLayout.CENTER);
        return panel;
    }

    private JButton crearBotonEstilizado(String texto, Color colorFondo) {
        JButton boton = new JButton(texto) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                if (getModel().isRollover()) g2.setColor(colorFondo.brighter());
                else g2.setColor(colorFondo);
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 20, 20);
                super.paintComponent(g);
                g2.dispose();
            }
        };
        boton.setFont(new Font("Segoe UI", Font.BOLD, 17));
        boton.setForeground(Color.WHITE);
        boton.setFocusPainted(false);
        boton.setContentAreaFilled(false);
        boton.setBorderPainted(false);
        boton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return boton;
    }

    private void agregarCampo(JPanel p, String label, String valor) {
        JLabel lbl = new JLabel(label);
        lbl.setFont(new Font("Segoe UI", Font.BOLD, 15));
        lbl.setForeground(new Color(44, 62, 80));
        JTextField txt = new JTextField(valor);
        txt.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        txt.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(new Color(180, 180, 180), 1), BorderFactory.createEmptyBorder(6, 12, 6, 12)));
        p.add(lbl);
        p.add(txt);
    }

    public static void main(String[] args) {
        try { UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName()); } catch (Exception e) {}
        SwingUtilities.invokeLater(() -> new Principal().setVisible(true));
    }
}