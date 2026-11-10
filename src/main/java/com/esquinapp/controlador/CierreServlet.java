/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.esquinapp.controlador;

import com.esquinapp.dao.CuentaDAO;
import com.esquinapp.modelo.Cuenta;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/CierreServlet")
public class CierreServlet extends HttpServlet {

    private final CuentaDAO cuentaDAO = new CuentaDAO();

    // Buscar una cuenta para realizar el cierre
    @Override
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response)
            throws ServletException, IOException {

        try {

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

            request.getRequestDispatcher(
                    "/cierre/cerrar.jsp"
            ).forward(request, response);

        } catch (NumberFormatException e) {

            response.sendError(
                    HttpServletResponse.SC_BAD_REQUEST,
                    "El identificador de la cuenta no es válido.");

        } catch (SQLException e) {

            throw new ServletException(
                    "Error al consultar la cuenta", e);
        }
    }

    // Cerrar una cuenta pagada
    @Override
    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");

        try {

            int idCuenta = Integer.parseInt(
                    request.getParameter("idCuenta"));

            // Cerrar la cuenta y liberar la mesa
            boolean cerrada =
                    cuentaDAO.cerrarCuenta(idCuenta);

            if (!cerrada) {
                response.sendError(
                        HttpServletResponse.SC_BAD_REQUEST,
                        "La cuenta no existe o todavía no está pagada.");
                return;
            }

            // Volver a la pantalla de cierre
            response.sendRedirect(
                    request.getContextPath()
                    + "/CierreServlet?idCuenta="
                    + idCuenta + "&cerrada=1");

        } catch (NumberFormatException e) {

            response.sendError(
                    HttpServletResponse.SC_BAD_REQUEST,
                    "El identificador de la cuenta no es válido.");

        } catch (SQLException e) {

            throw new ServletException(
                    "Error al cerrar la cuenta: "
                    + e.getMessage(), e);
        }
    }
}
