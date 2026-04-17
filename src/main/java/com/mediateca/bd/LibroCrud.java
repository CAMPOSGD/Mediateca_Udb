package com.mediateca.bd;

import com.mediateca.modelos.Libro;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class LibroCrud {

    private static final Logger logger = LogManager.getLogger(LibroCrud.class);

    public String generarSiguienteCodigo() {
        String sql = "SELECT MAX(codigo_interno) FROM Libro";
        try (Connection con = new Conexion().getConexion();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            if (rs.next() && rs.getString(1) != null) {
                int id = Integer.parseInt(rs.getString(1).substring(3)) + 1;
                return String.format("LIB%05d", id);
            }
        } catch (SQLException e) {
            logger.error("Error al generar el siguiente correlativo para Libro", e);
        }
        return "LIB00001";
    }

    public boolean registrarLibro(Libro l) {
        String sql = "INSERT INTO Libro (codigo_interno, titulo, autor, paginas, editorial, isbn, anio_publicacion, unidades_disponibles, estado) VALUES (?, ?, ?, ?, ?, ?, ?, ?, 1)";
        try (Connection con = new Conexion().getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, l.getCodigoInterno());
            ps.setString(2, l.getTitulo());
            ps.setString(3, l.getAutor());
            ps.setInt(4, l.getNumeroPaginas());
            ps.setString(5, l.getEditorial());
            ps.setString(6, l.getIsbn());
            ps.setInt(7, l.getAnioPublicacion());
            ps.setInt(8, l.getUnidadesDisponibles());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            logger.error("Error al intentar registrar el libro: " + l.getCodigoInterno(), e);
            return false;
        }
    }

    public List<Libro> obtenerLibros() {
        List<Libro> lista = new ArrayList<>();
        String sql = "SELECT * FROM Libro WHERE estado = 1";
        try (Connection con = new Conexion().getConexion();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                lista.add(new Libro(
                        rs.getString("codigo_interno"),
                        rs.getString("titulo"),
                        rs.getString("autor"),
                        rs.getInt("paginas"),
                        rs.getString("editorial"),
                        rs.getString("isbn"),
                        rs.getInt("anio_publicacion"),
                        rs.getInt("unidades_disponibles")
                ));
            }
        } catch (SQLException e) {
            logger.error("Error al recuperar la lista de libros desde la base de datos", e);
        }
        return lista;
    }

    public Libro buscarLibroPorCodigo(String codigo) {
        String sql = "SELECT * FROM Libro WHERE codigo_interno = ? AND estado = 1";
        try (Connection con = new Conexion().getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, codigo);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new Libro(
                        rs.getString("codigo_interno"),
                        rs.getString("titulo"),
                        rs.getString("autor"),
                        rs.getInt("paginas"),
                        rs.getString("editorial"),
                        rs.getString("isbn"),
                        rs.getInt("anio_publicacion"),
                        rs.getInt("unidades_disponibles")
                );
            }
        } catch (SQLException e) {
            logger.error("Error al buscar el libro con código: " + codigo, e);
        }
        return null;
    }

    public boolean actualizarLibro(Libro l) {
        String sql = "UPDATE Libro SET titulo=?, autor=?, paginas=?, editorial=?, isbn=?, anio_publicacion=?, unidades_disponibles=? WHERE codigo_interno=?";
        try (Connection con = new Conexion().getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, l.getTitulo());
            ps.setString(2, l.getAutor());
            ps.setInt(3, l.getNumeroPaginas());
            ps.setString(4, l.getEditorial());
            ps.setString(5, l.getIsbn());
            ps.setInt(6, l.getAnioPublicacion());
            ps.setInt(7, l.getUnidadesDisponibles());
            ps.setString(8, l.getCodigoInterno());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            logger.error("Error al actualizar los datos del libro: " + l.getCodigoInterno(), e);
            return false;
        }
    }
}