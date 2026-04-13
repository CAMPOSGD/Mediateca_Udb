package com.mediateca.bd;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexion {

    private static final Logger logger = LogManager.getLogger(Conexion.class);

    private static final String URL = "jdbc:mysql://localhost:3306/mediateca_db";
    private static final String USER = "root";
    private static final String PASSWORD = "admin";

    public Connection getConexion() {
        Connection conexion = null;
        try {
            // Intentamos establecer la conexión
            conexion = DriverManager.getConnection(URL, USER, PASSWORD);
            logger.info("Conexión a la base de datos establecida con éxito.");

        } catch (SQLException e) {
            logger.error("Error al conectar con la base de datos: " + e.getMessage());
        }
        return conexion;
    }
}