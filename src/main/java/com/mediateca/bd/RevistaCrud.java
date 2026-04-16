package com.mediateca.bd;

import com.mediateca.modelos.Revista;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RevistaCrud {
    private static final Logger logger = LogManager.getLogger(RevistaCrud.class);
    private Conexion conexion;

    public RevistaCrud() { this.conexion = new Conexion(); }

    public boolean registrarRevista(Revista r) {
        String sqlMat = "INSERT INTO Material (codigo_interno, titulo, estado) VALUES (?, ?, 'Activo')";
        String sqlEsc = "INSERT INTO MaterialEscrito (codigo_interno, editorial) VALUES (?, ?)";
        String sqlRev = "INSERT INTO Revista (codigo_interno, periodicidad, fecha_publicacion, unidades_disponibles) VALUES (?, ?, ?, ?)";

        Connection conn = conexion.getConexion();
        try {
            conn.setAutoCommit(false);
            try (PreparedStatement s1 = conn.prepareStatement(sqlMat);
                 PreparedStatement s2 = conn.prepareStatement(sqlEsc);
                 PreparedStatement s3 = conn.prepareStatement(sqlRev)) {

                s1.setString(1, r.getCodigoInterno()); s1.setString(2, r.getTitulo()); s1.executeUpdate();
                s2.setString(1, r.getCodigoInterno()); s2.setString(2, r.getEditorial()); s2.executeUpdate();
                s3.setString(1, r.getCodigoInterno()); s3.setString(2, r.getPeriodicidad());
                s3.setString(3, r.getFechaPublicacion()); s3.setInt(4, r.getUnidadesDisponibles()); s3.executeUpdate();

                conn.commit();
                return true;
            } catch (SQLException e) { conn.rollback(); return false; }
        } catch (SQLException e) { return false; }
        finally { cerrarConexion(conn); }
    }

    public List<Revista> obtenerRevistas() {
        List<Revista> lista = new ArrayList<>();
        String sql = "SELECT m.codigo_interno, m.titulo, me.editorial, r.periodicidad, r.fecha_publicacion, r.unidades_disponibles " +
                "FROM Revista r " +
                "INNER JOIN MaterialEscrito me ON r.codigo_interno = me.codigo_interno " +
                "INNER JOIN Material m ON r.codigo_interno = m.codigo_interno " +
                "WHERE m.estado = 'Activo'";
        try (Connection conn = conexion.getConexion(); Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                lista.add(new Revista(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getInt(6)));
            }
        } catch (SQLException e) { logger.error(e.getMessage()); }
        return lista;
    }

    public Revista buscarRevistaPorCodigo(String codigo) {
        String sql = "SELECT m.codigo_interno, m.titulo, me.editorial, r.periodicidad, r.fecha_publicacion, r.unidades_disponibles " +
                "FROM Revista r INNER JOIN MaterialEscrito me ON r.codigo_interno = me.codigo_interno " +
                "INNER JOIN Material m ON r.codigo_interno = m.codigo_interno WHERE m.codigo_interno = ? AND m.estado = 'Activo'";
        try (Connection conn = conexion.getConexion(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, codigo);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) return new Revista(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getInt(6));
        } catch (SQLException e) { logger.error(e.getMessage()); }
        return null;
    }

    public boolean actualizarRevista(Revista r) {
        String sqlMat = "UPDATE Material SET titulo = ? WHERE codigo_interno = ?";
        String sqlEsc = "UPDATE MaterialEscrito SET editorial = ? WHERE codigo_interno = ?";
        String sqlRev = "UPDATE Revista SET periodicidad = ?, fecha_publicacion = ?, unidades_disponibles = ? WHERE codigo_interno = ?";
        Connection conn = conexion.getConexion();
        try {
            conn.setAutoCommit(false);
            try (PreparedStatement s1 = conn.prepareStatement(sqlMat);
                 PreparedStatement s2 = conn.prepareStatement(sqlEsc);
                 PreparedStatement s3 = conn.prepareStatement(sqlRev)) {
                s1.setString(1, r.getTitulo()); s1.setString(2, r.getCodigoInterno()); s1.executeUpdate();
                s2.setString(1, r.getEditorial()); s2.setString(2, r.getCodigoInterno()); s2.executeUpdate();
                s3.setString(1, r.getPeriodicidad()); s3.setString(2, r.getFechaPublicacion());
                s3.setInt(3, r.getUnidadesDisponibles()); s3.setString(4, r.getCodigoInterno()); s3.executeUpdate();
                conn.commit(); return true;
            } catch (SQLException e) { conn.rollback(); return false; }
        } catch (SQLException e) { return false; }
        finally { cerrarConexion(conn); }
    }

    public String generarSiguienteCodigo() {
        String sql = "SELECT MAX(codigo_interno) FROM Material WHERE codigo_interno LIKE 'REV%'";
        try (Connection conn = conexion.getConexion(); Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            if (rs.next() && rs.getString(1) != null) {
                int num = Integer.parseInt(rs.getString(1).substring(3));
                return String.format("REV%05d", num + 1);
            }
        } catch (Exception e) { logger.error(e.getMessage()); }
        return "REV00001";
    }

    private void cerrarConexion(Connection conn) {
        try { if (conn != null) { conn.setAutoCommit(true); conn.close(); } } catch (SQLException e) {}
    }
}