<%-- 
    Document   : gestionar.jsp
    Created on : 7/11/2026, 10:33:29 p. m.
    Author     : harol
--%>


<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.util.List"%>
<%@page import="com.esquinapp.modelo.Cuenta"%>
<%@page import="com.esquinapp.modelo.Mesa"%>

<%
    List<Cuenta> cuentas =
        (List<Cuenta>) request.getAttribute("cuentas");

    List<Mesa> mesas =
        (List<Mesa>) request.getAttribute("mesas");

    Cuenta seleccionada =
        (Cuenta) request.getAttribute("cuentaSeleccionada");

    boolean hayLibres = false;

    if (mesas != null) {
        for (Mesa mesa : mesas) {
            if ("Libre".equalsIgnoreCase(mesa.getEstado())) {
                hayLibres = true;
                break;
            }
        }
    }
%>

<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, initial-scale=1.0">

    <title>Cuentas - LA ESQUINAPP</title>

    <style>
        body {
            font-family: Arial, sans-serif;
            background: #f2f4f7;
            margin: 0;
        }

        header {
            background: #263238;
            color: white;
            text-align: center;
            padding: 22px;
        }

        header h1 {
            margin: 0;
        }

        .contenedor {
            max-width: 950px;
            margin: 35px auto;
            padding: 25px;
            background: white;
            border-radius: 8px;
        }

        h2, h3 {
            color: #333;
        }

        .seccion {
            margin-bottom: 30px;
            padding: 20px;
            border: 1px solid #ddd;
            border-radius: 6px;
        }

        label {
            display: block;
            margin: 12px 0 6px;
            font-weight: bold;
        }

        select, input {
            padding: 10px;
            width: 100%;
            max-width: 400px;
            border: 1px solid #ccc;
            border-radius: 5px;
            box-sizing: border-box;
        }

        .boton {
            display: inline-block;
            padding: 11px 18px;
            margin-top: 15px;
            margin-right: 8px;
            background: #1976d2;
            color: white;
            text-decoration: none;
            border: none;
            border-radius: 5px;
            cursor: pointer;
        }

        .boton:hover {
            background: #125aa0;
        }

        .secundario {
            background: #607d8b;
        }

        .boton:disabled {
            background: #aaa;
            cursor: not-allowed;
        }

        .tabla {
            overflow-x: auto;
        }

        table {
            width: 100%;
            border-collapse: collapse;
        }

        th, td {
            border: 1px solid #ddd;
            padding: 12px;
            text-align: center;
        }

        th {
            background: #eceff1;
        }

        .mensaje {
            padding: 12px;
            background: #e8f5e9;
            color: #256029;
            border-radius: 5px;
            margin-bottom: 20px;
        }

        .vacio {
            color: #777;
            text-align: center;
        }
    </style>
</head>

<body>

<header>
    <h1>LA ESQUINAPP</h1>
    <p>Restaurante La Terraza</p>
</header>

<div class="contenedor">

    <h2>Gestión de Cuentas</h2>

    <% if (seleccionada != null) { %>
        <div class="mensaje">
            Cuenta #<%= seleccionada.getIdCuenta() %>
            - Mesa ID <%= seleccionada.getIdMesa() %>
            - Estado: <%= seleccionada.getEstado() %>
        </div>
    <% } %>

    <div class="seccion">

        <h3>Abrir nueva cuenta</h3>

        <form action="<%= request.getContextPath() %>/CuentaServlet"
              method="post">

            <label for="idMesa">Seleccionar mesa libre:</label>

            <select name="idMesa" id="idMesa" required>
                <option value="">Seleccione una mesa</option>

                <% if (mesas != null) {
                    for (Mesa mesa : mesas) {
                        if ("Libre".equalsIgnoreCase(
                                mesa.getEstado())) { %>

                    <option value="<%= mesa.getIdMesa() %>">
                        Mesa <%= mesa.getNumero() %>
                        - Capacidad: <%= mesa.getCapacidad() %>
                    </option>

                <% } } } %>
            </select>

            <label for="idCaja">ID de caja:</label>

            <input type="number"
                   id="idCaja"
                   name="idCaja"
                   value="1"
                   min="1"
                   required>

            <p>
                <small>
                    Utilizaremos la caja registrada con ID 1.
                </small>
            </p>

            <button type="submit" class="boton"
                    <%= hayLibres ? "" : "disabled" %>>
                Abrir Cuenta
            </button>

        </form>

        <% if (!hayLibres) { %>
            <p>No hay mesas libres disponibles.</p>
        <% } %>

    </div>

    <div class="seccion">

        <h3>Mesas del restaurante</h3>

        <div class="tabla">
            <table>
                <tr>
                    <th>Número</th>
                    <th>Capacidad</th>
                    <th>Estado</th>
                </tr>

                <% if (mesas != null) {
                    for (Mesa mesa : mesas) { %>

                    <tr>
                        <td><%= mesa.getNumero() %></td>
                        <td><%= mesa.getCapacidad() %></td>
                        <td><%= mesa.getEstado() %></td>
                    </tr>

                <% } } %>
            </table>
        </div>

    </div>

    <div class="seccion">

        <h3>Cuentas abiertas</h3>

        <div class="tabla">
            <table>
                <tr>
                    <th>ID Cuenta</th>
                    <th>ID Mesa</th>
                    <th>Total</th>
                    <th>Estado</th>
                </tr>

                <% if (cuentas != null && !cuentas.isEmpty()) {
                    for (Cuenta cuenta : cuentas) { %>

                    <tr>
                        <td><%= cuenta.getIdCuenta() %></td>
                        <td><%= cuenta.getIdMesa() %></td>
                        <td>$<%= cuenta.getTotal() %></td>
                        <td><%= cuenta.getEstado() %></td>
                    </tr>

                <% } } else { %>

                    <tr>
                        <td colspan="4" class="vacio">
                            No hay cuentas abiertas.
                        </td>
                    </tr>

                <% } %>
            </table>
        </div>

    </div>

    <a class="boton"
       href="<%= request.getContextPath() %>/PedidoServlet?accion=registrar">
        Ir a Registrar Pedido
    </a>

    <a class="boton secundario"
       href="<%= request.getContextPath() %>/">
        Menú principal
    </a>

</div>

</body>
</html>

