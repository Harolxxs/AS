/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */


package com.esquinapp.controlador;

import com.esquinapp.dao.PedidoDAO;
import com.esquinapp.modelo.Pedido;
import com.esquinapp.modelo.DetallePedido;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import com.esquinapp.dao.CuentaDAO;
import com.esquinapp.dao.ProductoDAO;
import com.esquinapp.modelo.Cuenta;
import com.esquinapp.modelo.Producto;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/PedidoServlet")
public class PedidoServlet extends HttpServlet {

    private final PedidoDAO pedidoDAO = new PedidoDAO();
    private final CuentaDAO cuentaDAO = new CuentaDAO();
private final ProductoDAO productoDAO = new ProductoDAO();
  
        
@Override
protected void doGet(HttpServletRequest request,
                     HttpServletResponse response)
        throws ServletException, IOException {

    try {

        String accion = request.getParameter("accion");

        
        if ("registrar".equals(accion)) {

            List<Cuenta> cuentas = cuentaDAO.listarAbiertas();

            List<Producto> productos =
                    productoDAO.listarDisponibles();

            request.setAttribute("cuentas", cuentas);
            request.setAttribute("productos", productos);

            request.getRequestDispatcher(
                    "/pedidos/registrar.jsp"
            ).forward(request, response);

        } else {

            
            List<Pedido> pedidos = pedidoDAO.listar();

            request.setAttribute("pedidos", pedidos);

            request.getRequestDispatcher(
                    "/pedidos/listar.jsp"
            ).forward(request, response);
        }

    } catch (SQLException e) {

        throw new ServletException(
                "Error al consultar la información de pedidos", e);
    }
}


    
    @Override
    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");

        try {

            
            int idMesa = Integer.parseInt(
                    request.getParameter("idMesa"));

            int idCuenta = Integer.parseInt(
                    request.getParameter("idCuenta"));

            int idMesero = Integer.parseInt(
                    request.getParameter("idMesero"));

            String observaciones =
                    request.getParameter("observaciones");

            
            Pedido pedido = new Pedido();

            pedido.setIdMesa(idMesa);
            pedido.setIdCuenta(idCuenta);
            pedido.setIdMesero(idMesero);
            pedido.setObservaciones(observaciones);

            
            String cliente = request.getParameter("idCliente");

            if (cliente != null && !cliente.isBlank()) {
                pedido.setIdCliente(
                        Integer.parseInt(cliente));
            }

            
            String[] productos =
                    request.getParameterValues("idProducto");

            String[] cantidades =
                    request.getParameterValues("cantidad");

            if (productos == null || cantidades == null
                    || productos.length == 0
                    || productos.length != cantidades.length) {

                throw new IllegalArgumentException(
                        "Debe seleccionar productos y cantidades.");
            }

            List<DetallePedido> detalles = new ArrayList<>();

            for (int i = 0; i < productos.length; i++) {

                DetallePedido detalle = new DetallePedido();

                detalle.setIdProducto(
                        Integer.parseInt(productos[i]));

                detalle.setCantidad(
                        Integer.parseInt(cantidades[i]));

                detalles.add(detalle);
            }

            
            pedidoDAO.registrar(pedido, detalles);

            
            response.sendRedirect(
                    request.getContextPath() + "/PedidoServlet");

        } catch (NumberFormatException e) {

            response.sendError(
                    HttpServletResponse.SC_BAD_REQUEST,
                    "Los datos numéricos del pedido no son válidos.");

        } catch (IllegalArgumentException e) {

            response.sendError(
                    HttpServletResponse.SC_BAD_REQUEST,
                    e.getMessage());

        } catch (SQLException e) {

            throw new ServletException(
                    "Error al registrar el pedido: "
                    + e.getMessage(), e);
        }
    }
}
