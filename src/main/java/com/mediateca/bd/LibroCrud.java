package com.mediateca.bd;

import com.mediateca.modelos.Libro;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class LibroCrud {
    private static final Logger logger = LogManager.getLogger(LibroCrud.class);
    private Conexion conexion;

    public LibroCrud() {
        this.conexion = new Conexion();
    }

    public boolean registrarLibro(Libro libro) {
        String sqlMaterial = "INSERT INTO Material (codigo_interno, titulo, estado) VALUES (?, ?, 'Activo')";
        String sqlEscrito = "INSERT INTO MaterialEscrito (codigo_interno, editorial) VALUES (?, ?)";
        String sqlLibro = "INSERT INTO Libro (codigo_interno, autor, numero_paginas, isbn, año_publicacion, unidades_disponibles) VALUES (?, ?, ?, ?, ?, ?)";

        Connection conn = conexion.getConexion();
        try {
            conn.setAutoCommit(false);
            try (PreparedStatement stmt1 = conn.prepareStatement(sqlMaterial)) {
                stmt1.setString(1, libro.getCodigoInterno());
                stmt1.setString(2, libro.getTitulo());
                stmt1.executeUpdate();
            }
            try (PreparedStatement stmt2 = conn.prepareStatement(sqlEscrito)) {
                stmt2.setString(1, libro.getCodigoInterno());
                stmt2.setString(2, libro.getEditorial());
                stmt2.executeUpdate();
            }
            try (PreparedStatement stmt3 = conn.prepareStatement(sqlLibro)) {
                stmt3.setString(1, libro.getCodigoInterno());
                stmt3.setString(2, libro.getAutor());
                stmt3.setInt(3, libro.getNumeroPaginas());
                stmt3.setString(4, libro.getIsbn());
                stmt3.setInt(5, libro.getAnioPublicacion());
                stmt3.setInt(6, libro.getUnidadesDisponibles());
                stmt3.executeUpdate();
            }
            conn.commit();
            return true;
        } catch (SQLException e) {
            try { if (conn != null) conn.rollback(); } catch (SQLException ex) {}
            return false;
        } finally { cerrarConexion(conn); }
    }

    public List<Libro> obtenerLibros() {
        List<Libro> lista = new ArrayList<>();
        String sql = "SELECT m.codigo_interno, m.titulo, me.editorial, l.autor, l.numero_paginas, l.isbn, l.año_publicacion, l.unidades_disponibles " +
                "FROM Libro l " +
                "INNER JOIN MaterialEscrito me ON l.codigo_interno = me.codigo_interno " +
                "INNER JOIN Material m ON l.codigo_interno = m.codigo_interno " +
                "WHERE m.estado = 'Activo'";

        try (Connection conn = conexion.getConexion();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                lista.add(new Libro(
                        rs.getString("codigo_interno"), rs.getString("titulo"), rs.getString("editorial"),
                        rs.getString("autor"), rs.getInt("numero_paginas"), rs.getString("isbn"),
                        rs.getInt("año_publicacion"), rs.getInt("unidades_disponibles")
                ));
            }
        } catch (SQLException e) { logger.error(e.getMessage()); }
        return lista;
    }

    public boolean eliminarLibro(String codigoInterno) {
        String sql = "UPDATE Material SET estado = 'Inactivo' WHERE codigo_interno = ?";
        try (Connection conn = conexion.getConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, codigoInterno);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            logger.error("Error al eliminar: " + e.getMessage());
            return false;
        }
    }

    public String generarSiguienteCodigo() {
        String sql = "SELECT MAX(codigo_interno) FROM Material WHERE codigo_interno LIKE 'LIB%'";

        try (Connection conn = conexion.getConexion();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            if (rs.next() && rs.getString(1) != null) {
                String ultimoCodigo = rs.getString(1);
                int numero = Integer.parseInt(ultimoCodigo.substring(3));
                return String.format("LIB%05d", numero + 1);
            }
        } catch (SQLException e) {
            logger.error("Error al generar nuevo código: " + e.getMessage());
        }
        return "LIB00001";
    }

    private void cerrarConexion(Connection conn) {
        try { if (conn != null) { conn.setAutoCommit(true); conn.close(); } } catch (SQLException e) {}
    }

    public Libro buscarLibroPorCodigo(String codigo) {
        String sql = "SELECT m.codigo_interno, m.titulo, me.editorial, l.autor, l.numero_paginas, l.isbn, l.año_publicacion, l.unidades_disponibles " +
                "FROM Libro l " +
                "INNER JOIN MaterialEscrito me ON l.codigo_interno = me.codigo_interno " +
                "INNER JOIN Material m ON l.codigo_interno = m.codigo_interno " +
                "WHERE m.codigo_interno = ? AND m.estado = 'Activo'";

        try (Connection conn = conexion.getConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, codigo);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return new Libro(
                        rs.getString("codigo_interno"), rs.getString("titulo"), rs.getString("editorial"),
                        rs.getString("autor"), rs.getInt("numero_paginas"), rs.getString("isbn"),
                        rs.getInt("año_publicacion"), rs.getInt("unidades_disponibles")
                );
            }
        } catch (SQLException e) { logger.error(e.getMessage()); }
        return null; // Si no lo encuentra
    }

    public boolean actualizarLibro(Libro libro) {
        String sqlMaterial = "UPDATE Material SET titulo = ? WHERE codigo_interno = ?";
        String sqlEscrito = "UPDATE MaterialEscrito SET editorial = ? WHERE codigo_interno = ?";
        String sqlLibro = "UPDATE Libro SET autor = ?, numero_paginas = ?, isbn = ?, año_publicacion = ?, unidades_disponibles = ? WHERE codigo_interno = ?";

        Connection conn = conexion.getConexion();
        try {
            conn.setAutoCommit(false);
            try (PreparedStatement stmt1 = conn.prepareStatement(sqlMaterial)) {
                stmt1.setString(1, libro.getTitulo());
                stmt1.setString(2, libro.getCodigoInterno());
                stmt1.executeUpdate();
            }
            try (PreparedStatement stmt2 = conn.prepareStatement(sqlEscrito)) {
                stmt2.setString(1, libro.getEditorial());
                stmt2.setString(2, libro.getCodigoInterno());
                stmt2.executeUpdate();
            }
            try (PreparedStatement stmt3 = conn.prepareStatement(sqlLibro)) {
                stmt3.setString(1, libro.getAutor());
                stmt3.setInt(2, libro.getNumeroPaginas());
                stmt3.setString(3, libro.getIsbn());
                stmt3.setInt(4, libro.getAnioPublicacion());
                stmt3.setInt(5, libro.getUnidadesDisponibles());
                stmt3.setString(6, libro.getCodigoInterno());
                stmt3.executeUpdate();
            }
            conn.commit();
            return true;
        } catch (SQLException e) {
            try { if (conn != null) conn.rollback(); } catch (SQLException ex) {}
            return false;
        } finally { cerrarConexion(conn); }
    }
}