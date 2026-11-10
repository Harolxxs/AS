<%-- 
    Document   : cobrar
    Created on : 7/11/2026, 11:05:56 p. m.
    Author     : harol
--%>


<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.util.List"%>
<%@page import="java.math.BigDecimal"%>
<%@page import="com.esquinapp.modelo.Cuenta"%>

<%
    List<Cuenta> cuentas =
        (List<Cuenta>) request.getAttribute("cuentas");

    Cuenta seleccionada =
        (Cuenta) request.getAttribute("cuentaSeleccionada");

    boolean pagado =
        "1".equals(request.getParameter("pagado"));

    boolean puedeCobrar =
        seleccionada != null
        && "Abierta".equals(seleccionada.getEstado())
        && seleccionada.getTotal() != null
        && seleccionada.getTotal().compareTo(BigDecimal.ZERO) > 0;
%>

<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, initial-scale=1.0">

    <title>Cobro - LA ESQUINAPP</title>

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
            max-width: 750px;
            margin: 35px auto;
            background: white;
            padding: 30px;
            border-radius: 8px;
        }

        h2, h3 {
            color: #333;
        }

        .seccion {
            border: 1px solid #ddd;
            border-radius: 6px;
            padding: 20px;
            margin-top: 20px;
        }

        label {
            display: block;
            margin-top: 15px;
            margin-bottom: 6px;
            font-weight: bold;
        }

        input, select {
            width: 100%;
            padding: 10px;
            border: 1px solid #ccc;
            border-radius: 5px;
            box-sizing: border-box;
        }

        .total {
            background: #e8f5e9;
            text-align: center;
            padding: 20px;
            border-radius: 6px;
            margin-top: 20px;
        }

        .total h2 {
            color: #256029;
            font-size: 30px;
            margin: 8px 0;
        }

        .boton {
            display: inline-block;
            background: #1976d2;
            color: white;
            padding: 12px 18px;
            border: none;
            border-radius: 5px;
            cursor: pointer;
            text-decoration: none;
            margin-top: 20px;
            margin-right: 8px;
            font-size: 14px;
        }

        .boton:hover {
            background: #125aa0;
        }

        .secundario {
            background: #607d8b;
        }

        .mensaje {
            background: #e8f5e9;
            color: #256029;
            padding: 15px;
            border-radius: 5px;
            margin-top: 15px;
        }

        .aviso {
            background: #fff3cd;
            color: #664d03;
            padding: 15px;
            border-radius: 5px;
            margin-top: 15px;
        }
    </style>
</head>

<body>

<header>
    <h1>LA ESQUINAPP</h1>
    <p>Restaurante La Terraza</p>
</header>

<div class="contenedor">

    <h2>Registro de Cobros</h2>

    <% if (pagado && seleccionada != null
            && "Pagada".equals(seleccionada.getEstado())) { %>

        <div class="mensaje">
            Pago registrado correctamente.
            La cuenta #<%= seleccionada.getIdCuenta() %>
            ahora se encuentra Pagada.
        </div>

        <a class="boton"
           href="<%= request.getContextPath() %>/CierreServlet?idCuenta=<%= seleccionada.getIdCuenta() %>">
            Ir a Cerrar Cuenta
        </a>

    <% } %>

    <div class="seccion">

        <h3>Seleccionar cuenta</h3>

        <form action="<%= request.getContextPath() %>/CobroServlet"
              method="get">

            <label for="idCuenta">Cuentas abiertas:</label>

            <select name="idCuenta" id="idCuenta" required>
                <option value="">Seleccione una cuenta</option>

                <% if (cuentas != null) {
                    for (Cuenta cuenta : cuentas) { %>

                    <option value="<%= cuenta.getIdCuenta() %>"
                        <%= seleccionada != null
                            && seleccionada.getIdCuenta()
                               == cuenta.getIdCuenta()
                            ? "selected" : "" %>>

                        Cuenta #<%= cuenta.getIdCuenta() %>
                        - Mesa ID <%= cuenta.getIdMesa() %>
                        - $<%= cuenta.getTotal() %>

                    </option>

                <% } } %>
            </select>

            <button type="submit" class="boton">
                Consultar Cuenta
            </button>

        </form>

    </div>

    <% if (seleccionada != null) { %>

    <div class="seccion">

        <h3>Información de la cuenta</h3>

        <p>
            <strong>Cuenta:</strong>
            #<%= seleccionada.getIdCuenta() %>
        </p>

        <p>
            <strong>Mesa:</strong>
            <%= seleccionada.getIdMesa() %>
        </p>

        <p>
            <strong>Estado:</strong>
            <%= seleccionada.getEstado() %>
        </p>

        <div class="total">
            <p>Total a pagar</p>
            <h2>$<%= seleccionada.getTotal() %></h2>
        </div>

        <% if (puedeCobrar) { %>

        <form action="<%= request.getContextPath() %>/CobroServlet"
              method="post">

            <input type="hidden"
                   name="idCuenta"
                   value="<%= seleccionada.getIdCuenta() %>">

            <input type="hidden"
                   name="idCaja"
                   value="1">

            <label for="metodoPago">Método de pago:</label>

            <select name="metodoPago" id="metodoPago" required>
                <option value="">Seleccione un método</option>
                <option value="Efectivo">Efectivo</option>
                <option value="Tarjeta">Tarjeta</option>
                <option value="Transferencia">Transferencia</option>
            </select>

            <label for="monto">Monto recibido:</label>

            <input type="number"
                   id="monto"
                   name="monto"
                   min="0.01"
                   step="0.01"
                   value="<%= seleccionada.getTotal() %>"
                   required>

            <button type="submit" class="boton">
                Registrar Pago
            </button>

        </form>

        <% } else if ("Abierta".equals(
                seleccionada.getEstado())) { %>

            <div class="aviso">
                Esta cuenta no tiene consumo para cobrar.
            </div>

        <% } else { %>

            <div class="mensaje">
                Esta cuenta ya no está abierta para cobro.
            </div>

        <% } %>

    </div>

    <% } %>

    <a class="boton secundario"
       href="<%= request.getContextPath() %>/">
        Menú principal
    </a>

</div>

</body>
</html>

