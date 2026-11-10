<%-- 
    Document   : listar
    Created on : 7/11/2026, 9:15:56 p. m.
    Author     : harol
--%>


<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.util.List"%>
<%@page import="com.esquinapp.modelo.Pedido"%>

<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">

    <title>Listado de Pedidos - LA ESQUINAPP</title>

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
            max-width: 1050px;
            margin: 35px auto;
            padding: 20px;
            background-color: white;
            border-radius: 8px;
        }

        h2 {
            color: #333;
        }

        .acciones {
            margin-bottom: 25px;
        }

        .boton {
            display: inline-block;
            padding:  20px 32px;
            background-color: #1976d2;
            color: white;
            text-decoration: none;
            border-radius: 10px;
            margin-right: 20px;
            margin-bottom: 16px;
        }

        .boton:hover {
            background-color: #125aa0;
        }

        .secundario {
            background-color: #607d8b;
        }

        .secundario:hover {
            background-color: #455a64;
        }

        .tabla-contenedor {
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
            color: #333;
        }

        tr:nth-child(even) {
            background-color: #fafafa;
        }

        .vacio {
            text-align: center;
            color: #777;
            padding: 25px;
        }
    </style>
</head>

<body>

    <header>
        <h1>LA ESQUINAPP</h1>
        <p>Restaurante La Terraza</p>
    </header>

    <div class="contenedor">

        <h2>Listado de Pedidos</h2>

        <div class="acciones">

            <a class="boton"
              href="<%= request.getContextPath() %>/PedidoServlet?accion=registrar"/>
                Registrar nuevo pedido
            </a>

            <a class="boton secundario"
               href="<%= request.getContextPath() %>/">
                Menú principal
            </a>

        </div>

        <%
            List<Pedido> pedidos =
                (List<Pedido>) request.getAttribute("pedidos");
        %>

        <div class="tabla-contenedor">

            <table>
                <thead>
                    <tr>
                        <th>ID Pedido</th>
                        <th>Mesa</th>
                        <th>Fecha</th>
                        <th>Hora</th>
                        <th>Estado</th>
                        <th>Observaciones</th>
                    </tr>
                </thead>

                <tbody>

                    <% if (pedidos != null && !pedidos.isEmpty()) { %>

                        <% for (Pedido pedido : pedidos) { %>

                            <tr>
                                <td><%= pedido.getIdPedido() %></td>
                                <td><%= pedido.getIdMesa() %></td>
                                <td><%= pedido.getFecha() %></td>
                                <td><%= pedido.getHora() %></td>
                                <td><%= pedido.getEstado() %></td>
                                <td><%=
                                    pedido.getObservaciones() == null
                                    ? ""
                                    : pedido.getObservaciones()
                                %></td>
                            </tr>

                        <% } %>

                    <% } else { %>

                        <tr>
                            <td colspan="6" class="vacio">
                                No hay pedidos registrados.
                            </td>
                        </tr>

                    <% } %>

                </tbody>
            </table>

        </div>

    </div>

</body>
</html>

