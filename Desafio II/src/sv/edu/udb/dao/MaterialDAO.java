package sv.edu.udb.dao;

import sv.edu.udb.conexion.Conexion;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class MaterialDAO {

    public void insertar(String codigo, String titulo, int unidades) {
        try {
            Connection conn = Conexion.getConnection();

            String sql = "INSERT INTO material (codigo, titulo, unidades) VALUES (?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(sql);

            stmt.setString(1, codigo);
            stmt.setString(2, titulo);
            stmt.setInt(3, unidades);

            stmt.executeUpdate();

            System.out.println("Dato insertado correctamente ✔");

        } catch (Exception e) {
            System.out.println("Error al insertar ❌");
            e.printStackTrace();
        }
    }

    public boolean existeCodigo(String codigo) {
        try {
            Connection conn = Conexion.getConnection();

            String sql = "SELECT * FROM material WHERE codigo = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, codigo);

            ResultSet rs = stmt.executeQuery();

            return rs.next();

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public String listar() {
        StringBuilder datos = new StringBuilder();

        try {
            Connection conn = Conexion.getConnection();

            String sql = "SELECT * FROM material";
            PreparedStatement stmt = conn.prepareStatement(sql);

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                datos.append("Código: ").append(rs.getString("codigo"))
                        .append(" | Título: ").append(rs.getString("titulo"))
                        .append(" | Unidades: ").append(rs.getInt("unidades"))
                        .append("\n");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return datos.toString();
    }

    public void eliminar(String codigo) {
        try {
            Connection conn = Conexion.getConnection();

            String sql = "DELETE FROM material WHERE codigo = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, codigo);

            int filas = stmt.executeUpdate();

            if (filas > 0) {
                System.out.println("Registro eliminado ✔");
            } else {
                System.out.println("No existe ese código ❌");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String buscar(String codigo) {
        try {
            Connection conn = Conexion.getConnection();

            String sql = "SELECT * FROM material WHERE codigo = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, codigo);

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return "Código: " + rs.getString("codigo") +
                        " | Título: " + rs.getString("titulo") +
                        " | Unidades: " + rs.getInt("unidades");
            } else {
                return "No encontrado ❌";
            }

        } catch (Exception e) {
            e.printStackTrace();
            return "Error ❌";
        }
    }
}