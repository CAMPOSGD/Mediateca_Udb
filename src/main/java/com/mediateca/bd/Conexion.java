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
        Connection con = null;
        try {
            con = DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (SQLException e) {
            logger.error("Error crítico al conectar con la base de datos mediateca_db", e);
        }
        return con;
    }
}