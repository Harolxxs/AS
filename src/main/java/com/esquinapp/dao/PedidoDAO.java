/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.esquinapp.dao;

import com.esquinapp.config.ConexionBD;
import com.esquinapp.modelo.Pedido;
import com.esquinapp.modelo.DetallePedido;

import java.sql.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class PedidoDAO {

    // Registrar pedido con sus productos
    public boolean registrar(Pedido pedido,
                             List<DetallePedido> detalles)
            throws SQLException {

        if (detalles == null || detalles.isEmpty()) {
            throw new SQLException("El pedido debe tener productos.");
        }

        try (Connection conexion = ConexionBD.conectar()) {

            conexion.setAutoCommit(false);

            try {
                // Verificar que la cuenta esté abierta
                String sqlCuenta = "SELECT idMesa FROM cuenta "
                        + "WHERE idCuenta = ? AND estado = 'Abierta'";

                try (PreparedStatement ps =
                        conexion.prepareStatement(sqlCuenta)) {

                    ps.setInt(1, pedido.getIdCuenta());

                    try (ResultSet rs = ps.executeQuery()) {
                        if (!rs.next() ||
                            rs.getInt("idMesa") != pedido.getIdMesa()) {
                            throw new SQLException(
                                "La cuenta no está abierta para esta mesa.");
                        }
                    }
                }

                // Registrar el pedido
                String sqlPedido = "INSERT INTO pedido "
                        + "(fecha, hora, estado, observaciones, "
                        + "idMesa, idCliente, idMesero, idCuenta) "
                        + "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

                int idPedido;

                try (PreparedStatement ps =
                        conexion.prepareStatement(
                            sqlPedido, Statement.RETURN_GENERATED_KEYS)) {

                    ps.setDate(1, Date.valueOf(LocalDate.now()));
                    ps.setTime(2, Time.valueOf(LocalTime.now()));
                    ps.setString(3, "Pendiente");
                    ps.setString(4, pedido.getObservaciones());
                    ps.setInt(5, pedido.getIdMesa());

                    if (pedido.getIdCliente() == null) {
                        ps.setNull(6, Types.INTEGER);
                    } else {
                        ps.setInt(6, pedido.getIdCliente());
                    }

                    ps.setInt(7, pedido.getIdMesero());
                    ps.setInt(8, pedido.getIdCuenta());

                    ps.executeUpdate();

                    try (ResultSet rs = ps.getGeneratedKeys()) {
                        if (!rs.next()) {
                            throw new SQLException(
                                "No se pudo obtener el ID del pedido.");
                        }
                        idPedido = rs.getInt(1);
                    }
                }

                // Guardar los productos del pedido
                BigDecimal totalPedido = BigDecimal.ZERO;

                String sqlProducto = "SELECT precio FROM producto "
                        + "WHERE idProducto = ? AND estado = 'Disponible'";

                String sqlDetalle = "INSERT INTO detalle_pedido "
                        + "(idPedido, idProducto, cantidad, subtotal) "
                        + "VALUES (?, ?, ?, ?)";

                try (PreparedStatement psProducto =
                        conexion.prepareStatement(sqlProducto);
                     PreparedStatement psDetalle =
                        conexion.prepareStatement(sqlDetalle)) {

                    for (DetallePedido detalle : detalles) {

                        if (detalle.getCantidad() <= 0) {
                            throw new SQLException(
                                "La cantidad debe ser mayor que cero.");
                        }

                        psProducto.setInt(1, detalle.getIdProducto());

                        BigDecimal precio;

                        try (ResultSet rs = psProducto.executeQuery()) {
                            if (!rs.next()) {
                                throw new SQLException(
                                    "Producto no disponible: "
                                    + detalle.getIdProducto());
                            }
                            precio = rs.getBigDecimal("precio");
                        }

                        BigDecimal subtotal = precio.multiply(
                            BigDecimal.valueOf(detalle.getCantidad()));

                        psDetalle.setInt(1, idPedido);
                        psDetalle.setInt(2, detalle.getIdProducto());
                        psDetalle.setInt(3, detalle.getCantidad());
                        psDetalle.setBigDecimal(4, subtotal);
                        psDetalle.executeUpdate();

                        totalPedido = totalPedido.add(subtotal);
                    }
                }

                // Obtener el encargado de cocina
                int idCocina;

                String sqlCocina = "SELECT idCocina FROM cocina "
                        + "ORDER BY idCocina LIMIT 1";

                try (PreparedStatement ps =
                        conexion.prepareStatement(sqlCocina);
                     ResultSet rs = ps.executeQuery()) {

                    if (!rs.next()) {
                        throw new SQLException(
                            "No hay personal de cocina registrado.");
                    }
                    idCocina = rs.getInt("idCocina");
                }

                // Crear comanda para cocina
                String sqlComanda = "INSERT INTO comanda "
                        + "(hora, estado, idPedido, idCocina) "
                        + "VALUES (?, 'Pendiente', ?, ?)";

                try (PreparedStatement ps =
                        conexion.prepareStatement(sqlComanda)) {

                    ps.setTime(1, Time.valueOf(LocalTime.now()));
                    ps.setInt(2, idPedido);
                    ps.setInt(3, idCocina);
                    ps.executeUpdate();
                }

                // Sumar el pedido al total de la cuenta
                String sqlTotal = "UPDATE cuenta SET total = total + ? "
                        + "WHERE idCuenta = ?";

                try (PreparedStatement ps =
                        conexion.prepareStatement(sqlTotal)) {

                    ps.setBigDecimal(1, totalPedido);
                    ps.setInt(2, pedido.getIdCuenta());
                    ps.executeUpdate();
                }

                // Marcar la mesa como ocupada
                String sqlMesa = "UPDATE mesa SET estado = 'Ocupada' "
                        + "WHERE idMesa = ?";

                try (PreparedStatement ps =
                        conexion.prepareStatement(sqlMesa)) {

                    ps.setInt(1, pedido.getIdMesa());
                    ps.executeUpdate();
                }

                // Confirmar todas las operaciones
                conexion.commit();
                pedido.setIdPedido(idPedido);

                return true;

            } catch (SQLException | RuntimeException e) {
                conexion.rollback();
                throw e;
            }
        }
    }

    // Consultar todos los pedidos
    public List<Pedido> listar() throws SQLException {

        List<Pedido> pedidos = new ArrayList<>();

        String sql = "SELECT * FROM pedido ORDER BY idPedido DESC";

        try (Connection conexion = ConexionBD.conectar();
             PreparedStatement ps = conexion.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {

                Pedido pedido = new Pedido();

                pedido.setIdPedido(rs.getInt("idPedido"));
                pedido.setFecha(
                    rs.getDate("fecha").toLocalDate());
                pedido.setHora(
                    rs.getTime("hora").toLocalTime());
                pedido.setEstado(rs.getString("estado"));
                pedido.setObservaciones(
                    rs.getString("observaciones"));
                pedido.setIdMesa(rs.getInt("idMesa"));

                int idCliente = rs.getInt("idCliente");
                pedido.setIdCliente(
                    rs.wasNull() ? null : idCliente);

                pedido.setIdMesero(rs.getInt("idMesero"));
                pedido.setIdCuenta(rs.getInt("idCuenta"));

                pedidos.add(pedido);
            }
        }

        return pedidos;
    }
}
