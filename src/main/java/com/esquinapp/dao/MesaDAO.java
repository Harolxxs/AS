/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.esquinapp.dao;

import com.esquinapp.config.ConexionBD;
import com.esquinapp.modelo.Mesa;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MesaDAO {

    // Consultar todas las mesas
    public List<Mesa> listar() throws SQLException {

        List<Mesa> mesas = new ArrayList<>();

        String sql = "SELECT * FROM mesa ORDER BY numero";

        try (Connection conexion = ConexionBD.conectar();
             PreparedStatement ps = conexion.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {

                Mesa mesa = new Mesa();

                mesa.setIdMesa(rs.getInt("idMesa"));
                mesa.setNumero(rs.getInt("numero"));
                mesa.setCapacidad(rs.getInt("capacidad"));
                mesa.setEstado(rs.getString("estado"));

                mesas.add(mesa);
            }
        }

        return mesas;
    }

    // Buscar una mesa por su ID
    public Mesa buscarPorId(int idMesa) throws SQLException {

        String sql = "SELECT * FROM mesa WHERE idMesa = ?";

        try (Connection conexion = ConexionBD.conectar();
             PreparedStatement ps = conexion.prepareStatement(sql)) {

            ps.setInt(1, idMesa);

            try (ResultSet rs = ps.executeQuery()) {

                if (rs.next()) {

                    Mesa mesa = new Mesa();

                    mesa.setIdMesa(rs.getInt("idMesa"));
                    mesa.setNumero(rs.getInt("numero"));
                    mesa.setCapacidad(rs.getInt("capacidad"));
                    mesa.setEstado(rs.getString("estado"));

                    return mesa;
                }
            }
        }

        return null;
    }

    // Cambiar el estado de una mesa
    public boolean actualizarEstado(int idMesa, String estado)
            throws SQLException {

        String sql = "UPDATE mesa SET estado = ? WHERE idMesa = ?";

        try (Connection conexion = ConexionBD.conectar();
             PreparedStatement ps = conexion.prepareStatement(sql)) {

            ps.setString(1, estado);
            ps.setInt(2, idMesa);

            return ps.executeUpdate() > 0;
        }
    }
}
