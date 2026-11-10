/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */


package com.esquinapp.controlador;

import com.esquinapp.dao.ComandaDAO;
import com.esquinapp.modelo.Comanda;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet("/CocinaServlet")
public class CocinaServlet extends HttpServlet {

    private final ComandaDAO comandaDAO = new ComandaDAO();

    // Mostrar comandas en el navegador
    @Override
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response)
            throws ServletException, IOException {

        try {

            // Consultar las comandas pendientes
            List<Comanda> comandas =
                    comandaDAO.listarPendientes();

            // Enviar los datos a la vista JSP
            request.setAttribute("comandas", comandas);

            request.getRequestDispatcher(
                    "/cocina/pendientes.jsp"
            ).forward(request, response);

        } catch (SQLException e) {

            throw new ServletException(
                    "Error al consultar las comandas", e);
        }
    }

    // Actualizar el estado de una comanda
    @Override
    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");

        try {

            // Recibir datos del formulario
            int idComanda = Integer.parseInt(
                    request.getParameter("idComanda"));

            String nuevoEstado =
                    request.getParameter("nuevoEstado");

            // Validar que el estado no esté vacío
            if (nuevoEstado == null || nuevoEstado.isBlank()) {
                throw new IllegalArgumentException(
                        "Debe seleccionar un estado.");
            }

            // Actualizar mediante el DAO
            boolean actualizado = comandaDAO.actualizarEstado(
                    idComanda, nuevoEstado);

            if (!actualizado) {
                response.sendError(
                        HttpServletResponse.SC_BAD_REQUEST,
                        "No se pudo actualizar la comanda.");
                return;
            }

            // Regresar a la pantalla de Cocina
            response.sendRedirect(
                    request.getContextPath() + "/CocinaServlet");

        } catch (NumberFormatException e) {

            response.sendError(
                    HttpServletResponse.SC_BAD_REQUEST,
                    "El identificador de la comanda no es válido.");

        } catch (IllegalArgumentException e) {

            response.sendError(
                    HttpServletResponse.SC_BAD_REQUEST,
                    e.getMessage());

        } catch (SQLException e) {

            throw new ServletException(
                    "Error al actualizar la comanda: "
                    + e.getMessage(), e);
        }
    }
}
