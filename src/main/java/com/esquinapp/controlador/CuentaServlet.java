/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.esquinapp.controlador;

import com.esquinapp.dao.CuentaDAO;
import com.esquinapp.dao.MesaDAO;
import com.esquinapp.modelo.Cuenta;
import com.esquinapp.modelo.Mesa;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet("/CuentaServlet")
public class CuentaServlet extends HttpServlet {

    private final CuentaDAO cuentaDAO = new CuentaDAO();
    private final MesaDAO mesaDAO = new MesaDAO();

    // Mostrar las cuentas y las mesas disponibles
    @Override
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response)
            throws ServletException, IOException {

        try {

            // Consultar cuentas abiertas
            List<Cuenta> cuentas = cuentaDAO.listarAbiertas();

            // Consultar mesas
            List<Mesa> mesas = mesaDAO.listar();

            // Enviar información a la vista
            request.setAttribute("cuentas", cuentas);
            request.setAttribute("mesas", mesas);

            // Buscar una cuenta específica si se solicita
            String idCuentaTexto = request.getParameter("idCuenta");

            if (idCuentaTexto != null && !idCuentaTexto.isBlank()) {

                int idCuenta = Integer.parseInt(idCuentaTexto);

                Cuenta cuenta = cuentaDAO.buscarPorId(idCuenta);

                request.setAttribute("cuentaSeleccionada", cuenta);
            }

            request.getRequestDispatcher(
                    "/cuentas/gestionar.jsp"
            ).forward(request, response);

        } catch (NumberFormatException e) {

            response.sendError(
                    HttpServletResponse.SC_BAD_REQUEST,
                    "El identificador de la cuenta no es válido.");

        } catch (SQLException e) {

            throw new ServletException(
                    "Error al consultar las cuentas", e);
        }
    }

    // Abrir una nueva cuenta
    @Override
    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");

        try {

            // Recibir los datos del formulario
            int idMesa = Integer.parseInt(
                    request.getParameter("idMesa"));

            int idCaja = Integer.parseInt(
                    request.getParameter("idCaja"));

            // Crear la cuenta mediante el DAO
            int idCuenta = cuentaDAO.abrirCuenta(idMesa, idCaja);

            // Regresar al módulo de cuentas
            response.sendRedirect(
                    request.getContextPath()
                    + "/CuentaServlet?idCuenta=" + idCuenta);

        } catch (NumberFormatException e) {

            response.sendError(
                    HttpServletResponse.SC_BAD_REQUEST,
                    "Los datos de la mesa o caja no son válidos.");

        } catch (SQLException e) {

            throw new ServletException(
                    "Error al abrir la cuenta: "
                    + e.getMessage(), e);
        }
    }
}

