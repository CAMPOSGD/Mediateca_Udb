package com.mediateca.bd;

import com.mediateca.modelos.DVD;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DvdCrud {

    public String generarSiguienteCodigo() {
        String sql = "SELECT MAX(codigo_interno) FROM DVD";
        try (Connection con = new Conexion().getConexion();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            if (rs.next() && rs.getString(1) != null) {
                int id = Integer.parseInt(rs.getString(1).substring(3)) + 1;
                return String.format("DVD%05d", id);
            }
        } catch (SQLException e) { System.out.println(e.getMessage()); }
        return "DVD00001";
    }

    public boolean registrarDVD(DVD d) {
        String sql = "INSERT INTO DVD (codigo_interno, titulo, director, duracion, genero, unidades_disponibles, estado) VALUES (?, ?, ?, ?, ?, ?, 1)";
        try (Connection con = new Conexion().getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, d.getCodigoInterno());
            ps.setString(2, d.getTitulo());
            ps.setString(3, d.getDirector());
            ps.setString(4, d.getDuracion());
            ps.setString(5, d.getGenero());
            ps.setInt(6, d.getUnidadesDisponibles());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) { return false; }
    }

    public List<DVD> obtenerDVDs() {
        List<DVD> lista = new ArrayList<>();
        String sql = "SELECT * FROM DVD WHERE estado = 1";
        try (Connection con = new Conexion().getConexion();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                lista.add(new DVD(
                        rs.getString("codigo_interno"),
                        rs.getString("titulo"),
                        rs.getString("director"),
                        rs.getString("duracion"),
                        rs.getString("genero"),
                        rs.getInt("unidades_disponibles")
                ));
            }
        } catch (SQLException e) { System.out.println(e.getMessage()); }
        return lista;
    }

    public DVD buscarDvdPorCodigo(String codigo) {
        String sql = "SELECT * FROM DVD WHERE codigo_interno = ? AND estado = 1";
        try (Connection con = new Conexion().getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, codigo);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new DVD(
                        rs.getString("codigo_interno"),
                        rs.getString("titulo"),
                        rs.getString("director"),
                        rs.getString("duracion"),
                        rs.getString("genero"),
                        rs.getInt("unidades_disponibles")
                );
            }
        } catch (SQLException e) { System.out.println(e.getMessage()); }
        return null;
    }

    public boolean actualizarDVD(DVD d) {
        String sql = "UPDATE DVD SET titulo=?, director=?, duracion=?, genero=?, unidades_disponibles=? WHERE codigo_interno=?";
        try (Connection con = new Conexion().getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, d.getTitulo());
            ps.setString(2, d.getDirector());
            ps.setString(3, d.getDuracion());
            ps.setString(4, d.getGenero());
            ps.setInt(5, d.getUnidadesDisponibles());
            ps.setString(6, d.getCodigoInterno());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) { return false; }
    }
}