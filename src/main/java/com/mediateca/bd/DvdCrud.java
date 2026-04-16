package com.mediateca.bd;

import com.mediateca.modelos.DVD;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DvdCrud {
    private static final Logger logger = LogManager.getLogger(DvdCrud.class);
    private Conexion conexion;

    public DvdCrud() { this.conexion = new Conexion(); }

    public boolean registrarDVD(DVD d) {
        String sqlMat = "INSERT INTO Material (codigo_interno, titulo, estado) VALUES (?, ?, 'Activo')";
        String sqlAud = "INSERT INTO MaterialAudiovisual (codigo_interno, duracion, genero) VALUES (?, ?, ?)";
        String sqlDVD = "INSERT INTO DVD (codigo_interno, director) VALUES (?, ?)";

        Connection conn = conexion.getConexion();
        try {
            conn.setAutoCommit(false);
            try (PreparedStatement s1 = conn.prepareStatement(sqlMat);
                 PreparedStatement s2 = conn.prepareStatement(sqlAud);
                 PreparedStatement s3 = conn.prepareStatement(sqlDVD)) {

                s1.setString(1, d.getCodigoInterno());
                s1.setString(2, d.getTitulo());
                s1.executeUpdate();

                s2.setString(1, d.getCodigoInterno());
                s2.setString(2, d.getDuracion());
                s2.setString(3, d.getGenero());
                s2.executeUpdate();

                s3.setString(1, d.getCodigoInterno());
                s3.setString(2, d.getDirector());
                s3.executeUpdate();

                conn.commit();
                return true;
            } catch (SQLException e) {
                if (conn != null) conn.rollback();
                logger.error("Error en transacción DVD: " + e.getMessage());
                return false;
            }
        } catch (SQLException e) { return false; }
        finally { cerrarConexion(conn); }
    }

    public List<DVD> obtenerDVDs() {
        List<DVD> lista = new ArrayList<>();
        String sql = "SELECT m.codigo_interno, m.titulo, ma.genero, ma.duracion, d.director " +
                "FROM DVD d " +
                "INNER JOIN MaterialAudiovisual ma ON d.codigo_interno = ma.codigo_interno " +
                "INNER JOIN Material m ON d.codigo_interno = m.codigo_interno " +
                "WHERE m.estado = 'Activo'";

        try (Connection conn = conexion.getConexion();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                lista.add(new DVD(
                        rs.getString("codigo_interno"),
                        rs.getString("titulo"),
                        rs.getString("genero"),
                        rs.getString("duracion"),
                        rs.getString("director")
                ));
            }
        } catch (SQLException e) { logger.error("Error al obtener DVDs: " + e.getMessage()); }
        return lista;
    }

    public DVD buscarDvdPorCodigo(String codigo) {
        String sql = "SELECT m.codigo_interno, m.titulo, ma.genero, ma.duracion, d.director " +
                "FROM DVD d " +
                "INNER JOIN MaterialAudiovisual ma ON d.codigo_interno = ma.codigo_interno " +
                "INNER JOIN Material m ON d.codigo_interno = m.codigo_interno " +
                "WHERE m.codigo_interno = ? AND m.estado = 'Activo'";

        try (Connection conn = conexion.getConexion(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, codigo);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new DVD(
                        rs.getString("codigo_interno"),
                        rs.getString("titulo"),
                        rs.getString("genero"),
                        rs.getString("duracion"),
                        rs.getString("director")
                );
            }
        } catch (SQLException e) { logger.error("Error al buscar DVD: " + e.getMessage()); }
        return null;
    }

    public boolean actualizarDVD(DVD d) {
        String sqlMat = "UPDATE Material SET titulo = ? WHERE codigo_interno = ?";
        String sqlAud = "UPDATE MaterialAudiovisual SET genero = ?, duracion = ? WHERE codigo_interno = ?";
        String sqlDVD = "UPDATE DVD SET director = ? WHERE codigo_interno = ?";

        Connection conn = conexion.getConexion();
        try {
            conn.setAutoCommit(false);
            try (PreparedStatement s1 = conn.prepareStatement(sqlMat);
                 PreparedStatement s2 = conn.prepareStatement(sqlAud);
                 PreparedStatement s3 = conn.prepareStatement(sqlDVD)) {

                s1.setString(1, d.getTitulo()); s1.setString(2, d.getCodigoInterno()); s1.executeUpdate();
                s2.setString(1, d.getGenero()); s2.setString(2, d.getDuracion()); s2.setString(3, d.getCodigoInterno()); s2.executeUpdate();
                s3.setString(1, d.getDirector()); s3.setString(2, d.getCodigoInterno()); s3.executeUpdate();

                conn.commit();
                return true;
            } catch (SQLException e) {
                if (conn != null) conn.rollback();
                return false;
            }
        } catch (SQLException e) { return false; }
        finally { cerrarConexion(conn); }
    }

    public String generarSiguienteCodigo() {
        String sql = "SELECT MAX(codigo_interno) FROM Material WHERE codigo_interno LIKE 'DVD%'";
        try (Connection conn = conexion.getConexion(); Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            if (rs.next() && rs.getString(1) != null) {
                int num = Integer.parseInt(rs.getString(1).substring(3));
                return String.format("DVD%05d", num + 1);
            }
        } catch (Exception e) { logger.error(e.getMessage()); }
        return "DVD00001";
    }

    private void cerrarConexion(Connection conn) {
        try { if (conn != null) { conn.setAutoCommit(true); conn.close(); } } catch (SQLException e) {}
    }
}