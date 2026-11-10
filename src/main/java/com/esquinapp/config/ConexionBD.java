/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */


package com.esquinapp.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexionBD {

    private static final String URL =
            "jdbc:mysql://localhost:3306/la_esquinapp";

    private static final String USUARIO = "root";
    private static final String PASSWORD = "123456";

    public static Connection conectar() throws SQLException {

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            return DriverManager.getConnection(
                    URL, USUARIO, PASSWORD);

        } catch (ClassNotFoundException e) {
            throw new SQLException(
                    "No se encontró el driver JDBC de MySQL.", e);
        }
    }
}

