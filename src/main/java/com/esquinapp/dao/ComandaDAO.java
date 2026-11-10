/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */


package com.esquinapp.dao;

import com.esquinapp.config.ConexionBD;
import com.esquinapp.modelo.Comanda;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ComandaDAO {

    // Consultar todas las comandas
    public List<Comanda> listar() throws SQLException {

        List<Comanda> comandas = new ArrayList<>();

        String sql = "SELECT * FROM comanda ORDER BY idComanda DESC";

        try (Connection conexion = ConexionBD.conectar();
             PreparedStatement ps = conexion.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {

                Comanda comanda = new Comanda();

                comanda.setIdComanda(rs.getInt("idComanda"));
                comanda.setIdPedido(rs.getInt("idPedido"));
                comanda.setIdCocina(rs.getInt("idCocina"));
                comanda.setEstadoPreparacion(rs.getString("estado"));

                comandas.add(comanda);
            }
        }

        return comandas;
    }

    // Consultar comandas que cocina debe atender
    public List<Comanda> listarPendientes() throws SQLException {

        List<Comanda> comandas = new ArrayList<>();

        String sql = "SELECT * FROM comanda "
           + "WHERE estado IN ('Pendiente', 'En preparación', 'Lista') "
           + "ORDER BY idComanda ASC";

        try (Connection conexion = ConexionBD.conectar();
             PreparedStatement ps = conexion.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {

                Comanda comanda = new Comanda();

                comanda.setIdComanda(rs.getInt("idComanda"));
                comanda.setIdPedido(rs.getInt("idPedido"));
                comanda.setIdCocina(rs.getInt("idCocina"));
                comanda.setEstadoPreparacion(rs.getString("estado"));

                comandas.add(comanda);
            }
        }

        return comandas;
    }

    // Actualizar estado de comanda y pedido
    public boolean actualizarEstado(int idComanda, String nuevoEstado)
            throws SQLException {

        if (!nuevoEstado.equals("En preparación")
                && !nuevoEstado.equals("Lista")
                && !nuevoEstado.equals("Despachada")) {

            throw new SQLException("Estado de comanda no válido.");
        }

        try (Connection conexion = ConexionBD.conectar()) {

            conexion.setAutoCommit(false);

            try {

                String sqlComanda = "UPDATE comanda SET estado = ? "
                        + "WHERE idComanda = ? "
                        + "AND estado IN ('Pendiente', "
                        + "'En preparación', 'Lista')";

                int filas;

                try (PreparedStatement ps =
                        conexion.prepareStatement(sqlComanda)) {

                    ps.setString(1, nuevoEstado);
                    ps.setInt(2, idComanda);

                    filas = ps.executeUpdate();
                }

                if (filas == 0) {
                    conexion.rollback();
                    return false;
                }

                String sqlPedido = "UPDATE pedido SET estado = ? "
                        + "WHERE idPedido = "
                        + "(SELECT idPedido FROM comanda "
                        + "WHERE idComanda = ?)";

                try (PreparedStatement ps =
                        conexion.prepareStatement(sqlPedido)) {

                    ps.setString(1, nuevoEstado);
                    ps.setInt(2, idComanda);

                    if (ps.executeUpdate() == 0) {
                        throw new SQLException(
                            "No se pudo actualizar el pedido.");
                    }
                }

                conexion.commit();
                return true;

            } catch (SQLException | RuntimeException e) {
                conexion.rollback();
                throw e;
            }
        }
    }
}
