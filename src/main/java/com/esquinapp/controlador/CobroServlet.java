/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.esquinapp.controlador;

import com.esquinapp.dao.CuentaDAO;
import com.esquinapp.dao.PagoDAO;
import com.esquinapp.modelo.Cuenta;
import com.esquinapp.modelo.Pago;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;

@WebServlet("/CobroServlet")
public class CobroServlet extends HttpServlet {

    private final CuentaDAO cuentaDAO = new CuentaDAO();
    private final PagoDAO pagoDAO = new PagoDAO();

    // Mostrar cuentas y consultar el total a pagar
    @Override
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response)
            throws ServletException, IOException {

        try {

            // Consultar las cuentas abiertas
            List<Cuenta> cuentas = cuentaDAO.listarAbiertas();

            request.setAttribute("cuentas", cuentas);

            // Consultar una cuenta específica
            String idCuentaTexto =
                    request.getParameter("idCuenta");

            if (idCuentaTexto != null
                    && !idCuentaTexto.isBlank()) {

                int idCuenta =
                        Integer.parseInt(idCuentaTexto);

                Cuenta cuenta =
                        cuentaDAO.buscarPorId(idCuenta);

                if (cuenta == null) {
                    response.sendError(
                            HttpServletResponse.SC_NOT_FOUND,
                            "La cuenta no existe.");
                    return;
                }

                request.setAttribute(
                        "cuentaSeleccionada", cuenta);
            }

            // Mostrar la vista de cobro
            request.getRequestDispatcher(
                    "/cobro/cobrar.jsp"
            ).forward(request, response);

        } catch (NumberFormatException e) {

            response.sendError(
                    HttpServletResponse.SC_BAD_REQUEST,
                    "El identificador de cuenta no es válido.");

        } catch (SQLException e) {

            throw new ServletException(
                    "Error al consultar las cuentas", e);
        }
    }

    // Registrar el pago de una cuenta
    @Override
    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");

        try {

            // Recibir datos del formulario
            int idCuenta = Integer.parseInt(
                    request.getParameter("idCuenta"));

            int idCaja = Integer.parseInt(
                    request.getParameter("idCaja"));

            String metodoPago =
                    request.getParameter("metodoPago");

            String montoTexto =
                    request.getParameter("monto");

            // Validar los datos
            if (metodoPago == null
                    || metodoPago.isBlank()
                    || montoTexto == null
                    || montoTexto.isBlank()) {

                throw new IllegalArgumentException(
                        "Debe completar los datos del pago.");
            }

            if (!metodoPago.equals("Efectivo")
                    && !metodoPago.equals("Tarjeta")
                    && !metodoPago.equals("Transferencia")) {

                throw new IllegalArgumentException(
                        "Método de pago no válido.");
            }

            BigDecimal monto = new BigDecimal(montoTexto);

            // Crear el objeto Pago
            Pago pago = new Pago();

            pago.setIdCuenta(idCuenta);
            pago.setMonto(monto);
            pago.setMetodoPago(metodoPago);

            // Registrar mediante PagoDAO
            boolean registrado =
                    pagoDAO.registrarPago(pago, idCaja);

            if (!registrado) {
                response.sendError(
                        HttpServletResponse.SC_BAD_REQUEST,
                        "No se pudo registrar el pago.");
                return;
            }

            // Mostrar confirmación del cobro
            response.sendRedirect(
                    request.getContextPath()
                    + "/CobroServlet?idCuenta="
                    + idCuenta + "&pagado=1");

        } catch (NumberFormatException e) {

            response.sendError(
                    HttpServletResponse.SC_BAD_REQUEST,
                    "Los datos numéricos del pago no son válidos.");

        } catch (IllegalArgumentException e) {

            response.sendError(
                    HttpServletResponse.SC_BAD_REQUEST,
                    e.getMessage());

        } catch (SQLException e) {

            throw new ServletException(
                    "Error al registrar el pago: "
                    + e.getMessage(), e);
        }
    }
}
