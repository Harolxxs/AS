/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.esquinapp.dao;

import com.esquinapp.config.ConexionBD;
import com.esquinapp.modelo.Producto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProductoDAO {

    // Consultar todos los productos
    public List<Producto> listar() throws SQLException {

        List<Producto> productos = new ArrayList<>();

        String sql = "SELECT * FROM producto ORDER BY nombre";

        try (Connection conexion = ConexionBD.conectar();
             PreparedStatement ps = conexion.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {

                Producto producto = new Producto();

                producto.setIdProducto(rs.getInt("idProducto"));
                producto.setNombre(rs.getString("nombre"));
                producto.setPrecio(rs.getBigDecimal("precio"));
                producto.setDisponible(
                    rs.getString("estado").equalsIgnoreCase("Disponible")
                );

                productos.add(producto);
            }
        }

        return productos;
    }

    // Buscar un producto por su ID
    public Producto buscarPorId(int idProducto) throws SQLException {

        String sql = "SELECT * FROM producto WHERE idProducto = ?";

        try (Connection conexion = ConexionBD.conectar();
             PreparedStatement ps = conexion.prepareStatement(sql)) {

            ps.setInt(1, idProducto);

            try (ResultSet rs = ps.executeQuery()) {

                if (rs.next()) {

                    Producto producto = new Producto();

                    producto.setIdProducto(rs.getInt("idProducto"));
                    producto.setNombre(rs.getString("nombre"));
                    producto.setPrecio(rs.getBigDecimal("precio"));
                    producto.setDisponible(
                        rs.getString("estado").equalsIgnoreCase("Disponible")
                    );

                    return producto;
                }
            }
        }

        return null;
    }

    // Consultar solamente productos disponibles
    public List<Producto> listarDisponibles() throws SQLException {

        List<Producto> productos = new ArrayList<>();

        String sql = "SELECT * FROM producto "
                   + "WHERE estado = 'Disponible' ORDER BY nombre";

        try (Connection conexion = ConexionBD.conectar();
             PreparedStatement ps = conexion.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {

                Producto producto = new Producto();

                producto.setIdProducto(rs.getInt("idProducto"));
                producto.setNombre(rs.getString("nombre"));
                producto.setPrecio(rs.getBigDecimal("precio"));
                producto.setDisponible(true);

                productos.add(producto);
            }
        }

        return productos;
    }
}
