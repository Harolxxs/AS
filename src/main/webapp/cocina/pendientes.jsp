<%-- 
    Document   : pendientes
    Created on : 7/11/2026, 10:45:11 p. m.
    Author     : harol
--%>


<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.util.List"%>
<%@page import="com.esquinapp.modelo.Comanda"%>

<%
    List<Comanda> comandas =
        (List<Comanda>) request.getAttribute("comandas");
%>

<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, initial-scale=1.0">

    <title>Cocina - LA ESQUINAPP</title>

    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #f2f4f7;
            margin: 0;
        }

        header {
            background-color: #263238;
            color: white;
            text-align: center;
            padding: 22px;
        }

        header h1 {
            margin: 0;
        }

        .contenedor {
            max-width: 1000px;
            margin: 35px auto;
            padding: 25px;
            background-color: white;
            border-radius: 8px;
        }

        h2 {
            color: #333;
        }

        .acciones {
            margin-bottom: 20px;
        }

        .boton {
            display: inline-block;
            background-color: #1976d2;
            color: white;
            padding: 10px 16px;
            border: none;
            border-radius: 5px;
            text-decoration: none;
            cursor: pointer;
            font-size: 14px;
        }

        .boton:hover {
            background-color: #125aa0;
        }

        .secundario {
            background-color: #607d8b;
        }

        .tabla {
            overflow-x: auto;
        }

        table {
            width: 100%;
            border-collapse: collapse;
        }

        th, td {
            padding: 12px;
            border: 1px solid #ddd;
            text-align: center;
        }

        th {
            background-color: #eceff1;
        }

        tr:nth-child(even) {
            background-color: #fafafa;
        }

        .estado {
            font-weight: bold;
        }

        .vacio {
            text-align: center;
            color: #777;
            padding: 25px;
        }

        form {
            margin: 0;
        }
    </style>
</head>

<body>

<header>
    <h1>LA ESQUINAPP</h1>
    <p>Restaurante La Terraza</p>
</header>

<div class="contenedor">

    <h2>Comandas de Cocina</h2>

    <div class="acciones">

        <a class="boton secundario"
           href="<%= request.getContextPath() %>/">
            Menú principal
        </a>

        <a class="boton"
           href="<%= request.getContextPath() %>/CocinaServlet">
            Actualizar listado
        </a>

    </div>

    <div class="tabla">

        <table>

            <thead>
                <tr>
                    <th>ID Comanda</th>
                    <th>ID Pedido</th>
                    <th>ID Cocina</th>
                    <th>Estado</th>
                    <th>Acción</th>
                </tr>
            </thead>

            <tbody>

                <% if (comandas != null && !comandas.isEmpty()) {

                    for (Comanda comanda : comandas) {

                        String estado =
                            comanda.getEstadoPreparacion();

                        String siguienteEstado = null;
                        String textoBoton = "";

                        if ("Pendiente".equals(estado)) {
                            siguienteEstado = "En preparación";
                            textoBoton = "Iniciar preparación";

                        } else if ("En preparación".equals(estado)) {
                            siguienteEstado = "Lista";
                            textoBoton = "Marcar como lista";

                        } else if ("Lista".equals(estado)) {
                            siguienteEstado = "Despachada";
                            textoBoton = "Despachar";
                        }
                %>

                <tr>

                    <td>
                        <%= comanda.getIdComanda() %>
                    </td>

                    <td>
                        <%= comanda.getIdPedido() %>
                    </td>

                    <td>
                        <%= comanda.getIdCocina() == null
                            ? "Sin asignar"
                            : comanda.getIdCocina() %>
                    </td>

                    <td class="estado">
                        <%= estado %>
                    </td>

                    <td>

                        <% if (siguienteEstado != null) { %>

                        <form action="<%= request.getContextPath() %>/CocinaServlet"
                              method="post">

                            <input type="hidden"
                                   name="idComanda"
                                   value="<%= comanda.getIdComanda() %>">

                            <input type="hidden"
                                   name="nuevoEstado"
                                   value="<%= siguienteEstado %>">

                            <button type="submit" class="boton">
                                <%= textoBoton %>
                            </button>

                        </form>

                        <% } else { %>

                            Sin acciones disponibles

                        <% } %>

                    </td>

                </tr>

                <% } } else { %>

                <tr>
                    <td colspan="5" class="vacio">
                        No hay comandas pendientes.
                    </td>
                </tr>

                <% } %>

            </tbody>

        </table>

    </div>

</div>

</body>
</html>

