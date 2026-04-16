package com.mediateca.bd;

import com.mediateca.modelos.CD;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CdCrud {
    private static final Logger logger = LogManager.getLogger(CdCrud.class);
    private Conexion conexion;

    public CdCrud() {
        this.conexion = new Conexion();
    }

    public boolean registrarCD(CD cd) {
        String sqlMaterial = "INSERT INTO Material (codigo_interno, titulo, estado) VALUES (?, ?, 'Activo')";
        String sqlAudioVisual = "INSERT INTO MaterialAudiovisual (codigo_interno, duracion, genero) VALUES (?, ?, ?)";
        String sqlCD = "INSERT INTO CD_Audio (codigo_interno, artista, num_canciones, unidades_disponibles) VALUES (?, ?, ?, ?)";

        Connection conn = conexion.getConexion();
        try {
            conn.setAutoCommit(false);
            try (PreparedStatement stmt1 = conn.prepareStatement(sqlMaterial)) {
                stmt1.setString(1, cd.getCodigoInterno());
                stmt1.setString(2, cd.getTitulo());
                stmt1.executeUpdate();
            }
            try (PreparedStatement stmt2 = conn.prepareStatement(sqlAudioVisual)) {
                stmt2.setString(1, cd.getCodigoInterno());
                stmt2.setString(2, cd.getDuracion());
                stmt2.setString(3, cd.getGenero());
                stmt2.executeUpdate();
            }
            try (PreparedStatement stmt3 = conn.prepareStatement(sqlCD)) {
                stmt3.setString(1, cd.getCodigoInterno());
                stmt3.setString(2, cd.getArtista());
                stmt3.setInt(3, cd.getNumCanciones());
                stmt3.setInt(4, cd.getUnidadesDisponibles());
                stmt3.executeUpdate();
            }
            conn.commit();
            return true;
        } catch (SQLException e) {
            try { if (conn != null) conn.rollback(); } catch (SQLException ex) {}
            logger.error("Error al registrar CD: " + e.getMessage());
            return false;
        } finally { cerrarConexion(conn); }
    }

    public List<CD> obtenerCDs() {
        List<CD> lista = new ArrayList<>();
        String sql = "SELECT m.codigo_interno, m.titulo, ma.genero, ma.duracion, c.artista, c.num_canciones, c.unidades_disponibles " +
                "FROM CD_Audio c " +
                "INNER JOIN MaterialAudiovisual ma ON c.codigo_interno = ma.codigo_interno " +
                "INNER JOIN Material m ON c.codigo_interno = m.codigo_interno " +
                "WHERE m.estado = 'Activo'";

        try (Connection conn = conexion.getConexion();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                lista.add(new CD(
                        rs.getString("codigo_interno"), rs.getString("titulo"), rs.getString("genero"),
                        rs.getString("duracion"), rs.getString("artista"),
                        rs.getInt("num_canciones"), rs.getInt("unidades_disponibles")
                ));
            }
        } catch (SQLException e) { logger.error("Error al obtener CDs: " + e.getMessage()); }
        return lista;
    }

    public CD buscarCdPorCodigo(String codigo) {
        String sql = "SELECT m.codigo_interno, m.titulo, ma.genero, ma.duracion, c.artista, c.num_canciones, c.unidades_disponibles " +
                "FROM CD_Audio c " +
                "INNER JOIN MaterialAudiovisual ma ON c.codigo_interno = ma.codigo_interno " +
                "INNER JOIN Material m ON c.codigo_interno = m.codigo_interno " +
                "WHERE m.codigo_interno = ? AND m.estado = 'Activo'";

        try (Connection conn = conexion.getConexion(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, codigo);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new CD(
                        rs.getString("codigo_interno"), rs.getString("titulo"), rs.getString("genero"),
                        rs.getString("duracion"), rs.getString("artista"),
                        rs.getInt("num_canciones"), rs.getInt("unidades_disponibles")
                );
            }
        } catch (SQLException e) { logger.error("Error al buscar CD: " + e.getMessage()); }
        return null;
    }

    public boolean actualizarCD(CD cd) {
        String sqlMat = "UPDATE Material SET titulo = ? WHERE codigo_interno = ?";
        String sqlAud = "UPDATE MaterialAudiovisual SET genero = ?, duracion = ? WHERE codigo_interno = ?";
        String sqlCD = "UPDATE CD_Audio SET artista = ?, num_canciones = ?, unidades_disponibles = ? WHERE codigo_interno = ?";

        Connection conn = conexion.getConexion();
        try {
            conn.setAutoCommit(false);
            try (PreparedStatement s1 = conn.prepareStatement(sqlMat);
                 PreparedStatement s2 = conn.prepareStatement(sqlAud);
                 PreparedStatement s3 = conn.prepareStatement(sqlCD)) {

                s1.setString(1, cd.getTitulo()); s1.setString(2, cd.getCodigoInterno()); s1.executeUpdate();
                s2.setString(1, cd.getGenero()); s2.setString(2, cd.getDuracion()); s2.setString(3, cd.getCodigoInterno()); s2.executeUpdate();
                s3.setString(1, cd.getArtista()); s3.setInt(2, cd.getNumCanciones());
                s3.setInt(3, cd.getUnidadesDisponibles()); s3.setString(4, cd.getCodigoInterno()); s3.executeUpdate();

                conn.commit();
                return true;
            } catch (SQLException e) {
                if (conn != null) conn.rollback();
                return false;
            }
        } catch (SQLException e) { return false; }
        finally { cerrarConexion(conn); }
    }

    public boolean eliminarMaterial(String codigoInterno) {
        String sql = "UPDATE Material SET estado = 'Inactivo' WHERE codigo_interno = ?";
        try (Connection conn = conexion.getConexion(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, codigoInterno);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) { return false; }
    }

    public String generarSiguienteCodigo() {
        String sql = "SELECT MAX(codigo_interno) FROM Material WHERE codigo_interno LIKE 'CDA%'";
        try (Connection conn = conexion.getConexion(); PreparedStatement stmt = conn.prepareStatement(sql); ResultSet rs = stmt.executeQuery()) {
            if (rs.next() && rs.getString(1) != null) {
                int numero = Integer.parseInt(rs.getString(1).substring(3));
                return String.format("CDA%05d", numero + 1);
            }
        } catch (SQLException e) { logger.error(e.getMessage()); }
        return "CDA00001";
    }

    private void cerrarConexion(Connection conn) {
        try { if (conn != null) { conn.setAutoCommit(true); conn.close(); } } catch (SQLException e) {}
    }
}