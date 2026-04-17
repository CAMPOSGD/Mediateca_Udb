package com.mediateca.bd;

import com.mediateca.modelos.Revista;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RevistaCrud {

    public String generarSiguienteCodigo() {
        String sql = "SELECT MAX(codigo_interno) FROM Revista";
        try (Connection con = new Conexion().getConexion();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            if (rs.next() && rs.getString(1) != null) {
                int id = Integer.parseInt(rs.getString(1).substring(3)) + 1;
                return String.format("REV%05d", id);
            }
        } catch (SQLException e) { System.out.println(e.getMessage()); }
        return "REV00001";
    }

    public boolean registrarRevista(Revista r) {
        String sql = "INSERT INTO Revista (codigo_interno, titulo, editorial, periodicidad, fecha_publicacion, unidades_disponibles, estado) VALUES (?, ?, ?, ?, ?, ?, 1)";
        try (Connection con = new Conexion().getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, r.getCodigoInterno());
            ps.setString(2, r.getTitulo());
            ps.setString(3, r.getEditorial());
            ps.setString(4, r.getPeriodicidad());
            ps.setString(5, r.getFechaPublicacion());
            ps.setInt(6, r.getUnidadesDisponibles());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) { return false; }
    }

    public List<Revista> obtenerRevistas() {
        List<Revista> lista = new ArrayList<>();
        String sql = "SELECT * FROM Revista WHERE estado = 1";
        try (Connection con = new Conexion().getConexion();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                lista.add(new Revista(
                        rs.getString("codigo_interno"), rs.getString("titulo"),
                        rs.getString("editorial"), rs.getString("periodicidad"),
                        rs.getString("fecha_publicacion"), rs.getInt("unidades_disponibles")
                ));
            }
        } catch (SQLException e) { System.out.println(e.getMessage()); }
        return lista;
    }

    public Revista buscarRevistaPorCodigo(String codigo) {
        String sql = "SELECT * FROM Revista WHERE codigo_interno = ? AND estado = 1";
        try (Connection con = new Conexion().getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, codigo);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new Revista(
                        rs.getString("codigo_interno"), rs.getString("titulo"),
                        rs.getString("editorial"), rs.getString("periodicidad"),
                        rs.getString("fecha_publicacion"), rs.getInt("unidades_disponibles")
                );
            }
        } catch (SQLException e) { System.out.println(e.getMessage()); }
        return null;
    }

    public boolean actualizarRevista(Revista r) {
        String sql = "UPDATE Revista SET titulo=?, editorial=?, periodicidad=?, fecha_publicacion=?, unidades_disponibles=? WHERE codigo_interno=?";
        try (Connection con = new Conexion().getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, r.getTitulo());
            ps.setString(2, r.getEditorial());
            ps.setString(3, r.getPeriodicidad());
            ps.setString(4, r.getFechaPublicacion());
            ps.setInt(5, r.getUnidadesDisponibles());
            ps.setString(6, r.getCodigoInterno());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) { return false; }
    }
}