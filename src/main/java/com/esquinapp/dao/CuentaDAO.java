/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */


package com.esquinapp.dao;

import com.esquinapp.config.ConexionBD;
import com.esquinapp.modelo.Cuenta;

import java.sql.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class CuentaDAO {

    // Abrir una cuenta para una mesa libre
    public int abrirCuenta(int idMesa, int idCaja)
            throws SQLException {

        try (Connection conexion = ConexionBD.conectar()) {

            conexion.setAutoCommit(false);

            try {

                // Ocupar la mesa si está libre
                String sqlMesa = "UPDATE mesa SET estado = 'Ocupada' "
                        + "WHERE idMesa = ? AND estado = 'Libre'";

                try (PreparedStatement ps =
                        conexion.prepareStatement(sqlMesa)) {

                    ps.setInt(1, idMesa);

                    if (ps.executeUpdate() == 0) {
                        throw new SQLException(
                            "La mesa no existe o está ocupada.");
                    }
                }

                // Registrar la nueva cuenta
                String sqlCuenta = "INSERT INTO cuenta "
                        + "(total, estado, idMesa, idCaja) "
                        + "VALUES (0, 'Abierta', ?, ?)";

                int idCuenta;

                try (PreparedStatement ps =
                        conexion.prepareStatement(
                            sqlCuenta, Statement.RETURN_GENERATED_KEYS)) {

                    ps.setInt(1, idMesa);
                    ps.setInt(2, idCaja);

                    ps.executeUpdate();

                    try (ResultSet rs = ps.getGeneratedKeys()) {

                        if (!rs.next()) {
                            throw new SQLException(
                                "No se pudo crear la cuenta.");
                        }

                        idCuenta = rs.getInt(1);
                    }
                }

                conexion.commit();
                return idCuenta;

            } catch (SQLException | RuntimeException e) {
                conexion.rollback();
                throw e;
            }
        }
    }

    // Buscar una cuenta por su ID
    public Cuenta buscarPorId(int idCuenta) throws SQLException {

        String sql = "SELECT * FROM cuenta WHERE idCuenta = ?";

        try (Connection conexion = ConexionBD.conectar();
             PreparedStatement ps = conexion.prepareStatement(sql)) {

            ps.setInt(1, idCuenta);

            try (ResultSet rs = ps.executeQuery()) {

                if (rs.next()) {
                    return convertirCuenta(rs);
                }
            }
        }

        return null;
    }

    // Listar las cuentas abiertas
    public List<Cuenta> listarAbiertas() throws SQLException {

        List<Cuenta> cuentas = new ArrayList<>();

        String sql = "SELECT * FROM cuenta "
                + "WHERE estado = 'Abierta' "
                + "ORDER BY idCuenta DESC";

        try (Connection conexion = ConexionBD.conectar();
             PreparedStatement ps = conexion.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                cuentas.add(convertirCuenta(rs));
            }
        }

        return cuentas;
    }

    // Cerrar una cuenta pagada y liberar la mesa
    public boolean cerrarCuenta(int idCuenta) throws SQLException {

        try (Connection conexion = ConexionBD.conectar()) {

            conexion.setAutoCommit(false);

            try {

                // Cerrar solamente cuentas pagadas
                String sqlCerrar = "UPDATE cuenta "
                        + "SET estado = 'Cerrada', "
                        + "fechaCierre = NOW() "
                        + "WHERE idCuenta = ? AND estado = 'Pagada'";

                try (PreparedStatement ps =
                        conexion.prepareStatement(sqlCerrar)) {

                    ps.setInt(1, idCuenta);

                    if (ps.executeUpdate() == 0) {
                        conexion.rollback();
                        return false;
                    }
                }

                // Liberar la mesa asociada
                String sqlMesa = "UPDATE mesa SET estado = 'Libre' "
                        + "WHERE idMesa = "
                        + "(SELECT idMesa FROM cuenta WHERE idCuenta = ?)";

                try (PreparedStatement ps =
                        conexion.prepareStatement(sqlMesa)) {

                    ps.setInt(1, idCuenta);

                    if (ps.executeUpdate() == 0) {
                        throw new SQLException(
                            "No se pudo liberar la mesa.");
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

    // Convertir un resultado de MySQL en un objeto Cuenta
    private Cuenta convertirCuenta(ResultSet rs)
            throws SQLException {

        Cuenta cuenta = new Cuenta();

        cuenta.setIdCuenta(rs.getInt("idCuenta"));
        cuenta.setFecha(
            rs.getTimestamp("fechaApertura").toLocalDateTime().toLocalDate()
        );
        cuenta.setTotal(rs.getBigDecimal("total"));
        cuenta.setEstado(rs.getString("estado"));
        cuenta.setIdMesa(rs.getInt("idMesa"));
        cuenta.setIdCaja(rs.getInt("idCaja"));

        return cuenta;
    }
}

