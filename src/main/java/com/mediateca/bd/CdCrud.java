package com.mediateca.bd;

import com.mediateca.modelos.CD;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CdCrud {

    private static final Logger logger = LogManager.getLogger(CdCrud.class);

    public String generarSiguienteCodigo() {
        String sql = "SELECT MAX(codigo_interno) FROM CD_Audio";
        try (Connection con = new Conexion().getConexion();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            if (rs.next() && rs.getString(1) != null) {
                int id = Integer.parseInt(rs.getString(1).substring(3)) + 1;
                return String.format("CDA%05d", id);
            }
        } catch (SQLException e) {
            logger.error("Error al generar correlativo de CD", e);
        }
        return "CDA00001";
    }

    public boolean registrarCD(CD cd) {
        String sql = "INSERT INTO CD_Audio (codigo_interno, titulo, artista, genero, duracion, num_canciones, unidades_disponibles, estado) VALUES (?, ?, ?, ?, ?, ?, ?, 1)";
        try (Connection con = new Conexion().getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, cd.getCodigoInterno());
            ps.setString(2, cd.getTitulo());
            ps.setString(3, cd.getArtista());
            ps.setString(4, cd.getGenero());
            ps.setString(5, cd.getDuracion());
            ps.setInt(6, cd.getNumCanciones());
            ps.setInt(7, cd.getUnidadesDisponibles());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            logger.error("Error al registrar CD en CD_Audio: " + cd.getCodigoInterno(), e);
            return false;
        }
    }

    public List<CD> obtenerCDs() {
        List<CD> lista = new ArrayList<>();
        String sql = "SELECT * FROM CD_Audio WHERE estado = 1";
        try (Connection con = new Conexion().getConexion();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                lista.add(new CD(
                        rs.getString("codigo_interno"), rs.getString("titulo"),
                        rs.getString("artista"), rs.getString("genero"),
                        rs.getString("duracion"), rs.getInt("num_canciones"),
                        rs.getInt("unidades_disponibles")
                ));
            }
        } catch (SQLException e) {
            logger.error("Error al listar CDs desde CD_Audio", e);
        }
        return lista;
    }

    public CD buscarCdPorCodigo(String codigo) {
        String sql = "SELECT * FROM CD_Audio WHERE codigo_interno = ? AND estado = 1";
        try (Connection con = new Conexion().getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, codigo);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new CD(
                        rs.getString("codigo_interno"), rs.getString("titulo"),
                        rs.getString("artista"), rs.getString("genero"),
                        rs.getString("duracion"), rs.getInt("num_canciones"),
                        rs.getInt("unidades_disponibles")
                );
            }
        } catch (SQLException e) {
            logger.error("Error al buscar CD en CD_Audio: " + codigo, e);
        }
        return null;
    }

    public boolean actualizarCD(CD cd) {
        String sql = "UPDATE CD_Audio SET titulo=?, artista=?, genero=?, duracion=?, num_canciones=?, unidades_disponibles=? WHERE codigo_interno=?";
        try (Connection con = new Conexion().getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, cd.getTitulo());
            ps.setString(2, cd.getArtista());
            ps.setString(3, cd.getGenero());
            ps.setString(4, cd.getDuracion());
            ps.setInt(5, cd.getNumCanciones());
            ps.setInt(6, cd.getUnidadesDisponibles());
            ps.setString(7, cd.getCodigoInterno());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            logger.error("Error al actualizar CD en CD_Audio: " + cd.getCodigoInterno(), e);
            return false;
        }
    }

    public boolean eliminarMaterial(String codigo) {
        String tabla = "";
        if (codigo.startsWith("LIB")) tabla = "Libro";
        else if (codigo.startsWith("REV")) tabla = "Revista";
        else if (codigo.startsWith("CDA")) tabla = "CD_Audio";
        else if (codigo.startsWith("DVD")) tabla = "DVD";

        String sql = "UPDATE " + tabla + " SET estado = 0 WHERE codigo_interno = ?";
        try (Connection con = new Conexion().getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, codigo);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            logger.error("Error al inactivar material en la tabla " + tabla + ": " + codigo, e);
            return false;
        }
    }
}