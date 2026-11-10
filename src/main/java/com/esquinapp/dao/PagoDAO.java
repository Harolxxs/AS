/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.esquinapp.dao;

import com.esquinapp.config.ConexionBD;
import com.esquinapp.modelo.Pago;

import java.sql.*;
import java.math.BigDecimal;

public class PagoDAO {

    // Registrar el pago de una cuenta
    public boolean registrarPago(Pago pago, int idCaja)
            throws SQLException {

        try (Connection conexion = ConexionBD.conectar()) {

            conexion.setAutoCommit(false);

            try {

                // Consultar y bloquear la cuenta durante el cobro
                String sqlCuenta = "SELECT total, estado FROM cuenta "
                        + "WHERE idCuenta = ? FOR UPDATE";

                BigDecimal totalCuenta;

                try (PreparedStatement ps =
                        conexion.prepareStatement(sqlCuenta)) {

                    ps.setInt(1, pago.getIdCuenta());

                    try (ResultSet rs = ps.executeQuery()) {

                        if (!rs.next()) {
                            throw new SQLException(
                                "La cuenta no existe.");
                        }

                        if (!rs.getString("estado")
                                .equals("Abierta")) {

                            throw new SQLException(
                                "La cuenta no está abierta.");
                        }

                        totalCuenta = rs.getBigDecimal("total");
                    }
                }

                // Verificar el monto del pago
                if (pago.getMonto() == null ||
                    totalCuenta.compareTo(BigDecimal.ZERO) <= 0 ||
                    pago.getMonto().compareTo(totalCuenta) != 0) {

                    throw new SQLException(
                        "El pago debe ser igual al total de la cuenta.");
                }

                // Registrar el pago
                String sqlPago = "INSERT INTO pago "
                        + "(monto, metodo, estado, idCuenta, idCaja) "
                        + "VALUES (?, ?, 'Registrado', ?, ?)";

                int idPago;

                try (PreparedStatement ps =
                        conexion.prepareStatement(
                            sqlPago, Statement.RETURN_GENERATED_KEYS)) {

                    ps.setBigDecimal(1, pago.getMonto());
                    ps.setString(2, pago.getMetodoPago());
                    ps.setInt(3, pago.getIdCuenta());
                    ps.setInt(4, idCaja);

                    ps.executeUpdate();

                    try (ResultSet rs = ps.getGeneratedKeys()) {

                        if (!rs.next()) {
                            throw new SQLException(
                                "No se pudo registrar el pago.");
                        }

                        idPago = rs.getInt(1);
                    }
                }

                // Cambiar el estado de la cuenta a Pagada
                String sqlActualizar = "UPDATE cuenta "
                        + "SET estado = 'Pagada' "
                        + "WHERE idCuenta = ?";

                try (PreparedStatement ps =
                        conexion.prepareStatement(sqlActualizar)) {

                    ps.setInt(1, pago.getIdCuenta());
                    ps.executeUpdate();
                }

                // Generar comprobante
                String sqlComprobante = "INSERT INTO comprobante "
                        + "(total, idCuenta) VALUES (?, ?)";

                try (PreparedStatement ps =
                        conexion.prepareStatement(sqlComprobante)) {

                    ps.setBigDecimal(1, pago.getMonto());
                    ps.setInt(2, pago.getIdCuenta());
                    ps.executeUpdate();
                }

                // Confirmar las operaciones
                conexion.commit();

                pago.setIdPago(idPago);
                pago.setEstado("Registrado");

                return true;

            } catch (SQLException | RuntimeException e) {

                conexion.rollback();
                throw e;
            }
        }
    }

    // Consultar un pago por su ID
    public Pago buscarPorId(int idPago) throws SQLException {

        String sql = "SELECT * FROM pago WHERE idPago = ?";

        try (Connection conexion = ConexionBD.conectar();
             PreparedStatement ps = conexion.prepareStatement(sql)) {

            ps.setInt(1, idPago);

            try (ResultSet rs = ps.executeQuery()) {

                if (rs.next()) {

                    Pago pago = new Pago();

                    pago.setIdPago(rs.getInt("idPago"));
                    pago.setIdCuenta(rs.getInt("idCuenta"));
                    pago.setFecha(
                        rs.getTimestamp("fecha")
                          .toLocalDateTime().toLocalDate()
                    );
                    pago.setMonto(rs.getBigDecimal("monto"));
                    pago.setMetodoPago(rs.getString("metodo"));
                    pago.setEstado(rs.getString("estado"));

                    return pago;
                }
            }
        }

        return null;
    }
}
 