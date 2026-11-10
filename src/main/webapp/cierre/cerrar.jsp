<%-- 
    Document   : cerrar
    Created on : 7/11/2026, 11:14:36 p. m.
    Author     : harol
--%>


<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="com.esquinapp.modelo.Cuenta"%>

<%
    Cuenta seleccionada =
        (Cuenta) request.getAttribute("cuentaSeleccionada");

    boolean cerrada =
        "1".equals(request.getParameter("cerrada"))
        && seleccionada != null
        && "Cerrada".equals(seleccionada.getEstado());

    boolean puedeCerrar =
        seleccionada != null
        && "Pagada".equals(seleccionada.getEstado());
%>

<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, initial-scale=1.0">

    <title>Cierre de Cuenta - LA ESQUINAPP</title>

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
            padding: 30px;
            background: white;
            border-radius: 8px;
            box-sizing: border-box;
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

        input {
            width: 100%;
            padding: 10px;
            border: 1px solid #ccc;
            border-radius: 5px;
            box-sizing: border-box;
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
            font-size: 14px;
            margin-top: 15px;
            margin-right: 8px;
        }

        .boton:hover {
            background: #125aa0;
        }

        .secundario {
            background: #607d8b;
        }

        .verde {
            background: #2e7d32;
        }

        .verde:hover {
            background: #1b5e20;
        }

        .mensaje {
            background: #e8f5e9;
            color: #256029;
            padding: 15px;
            border-radius: 5px;
            margin-top: 20px;
        }

        .aviso {
            background: #fff3cd;
            color: #664d03;
            padding: 15px;
            border-radius: 5px;
            margin-top: 20px;
        }

        .datos p {
            padding: 6px 0;
            border-bottom: 1px solid #eee;
        }

        .total {
            background: #f2f4f7;
            padding: 20px;
            text-align: center;
            border-radius: 6px;
            margin-top: 15px;
        }

        .total h2 {
            margin: 8px 0;
            color: #263238;
            font-size: 28px;
        }
    </style>
</head>

<body>

<header>
    <h1>LA ESQUINAPP</h1>
    <p>Restaurante La Terraza</p>
</header>

<div class="contenedor">

    <h2>Cierre de Cuentas</h2>

    <% if (cerrada) { %>

        <div class="mensaje">
            Cuenta #<%= seleccionada.getIdCuenta() %>
            cerrada correctamente.
            La mesa asociada ha sido liberada.
        </div>

    <% } %>

    <div class="seccion">

        <h3>Buscar cuenta</h3>

        <form action="<%= request.getContextPath() %>/CierreServlet"
              method="get">

            <label for="idCuenta">ID de la cuenta:</label>

            <input type="number"
                   id="idCuenta"
                   name="idCuenta"
                   min="1"
                   value="<%= seleccionada != null
                           ? seleccionada.getIdCuenta() : "" %>"
                   placeholder="Ingrese el número de cuenta"
                   required>

            <button type="submit" class="boton">
                Consultar Cuenta
            </button>

        </form>

    </div>

    <% if (seleccionada != null) { %>

    <div class="seccion">

        <h3>Información de la cuenta</h3>

        <div class="datos">

            <p>
                <strong>ID Cuenta:</strong>
                <%= seleccionada.getIdCuenta() %>
            </p>

            <p>
                <strong>ID Mesa:</strong>
                <%= seleccionada.getIdMesa() %>
            </p>

            <p>
                <strong>Estado:</strong>
                <%= seleccionada.getEstado() %>
            </p>

        </div>

        <div class="total">
            <p>Total de la cuenta</p>
            <h2>$<%= seleccionada.getTotal() %></h2>
        </div>

        <% if (puedeCerrar) { %>

            <div class="mensaje">
                Esta cuenta ya fue pagada y puede cerrarse.
            </div>

            <form action="<%= request.getContextPath() %>/CierreServlet"
                  method="post">

                <input type="hidden"
                       name="idCuenta"
                       value="<%= seleccionada.getIdCuenta() %>">

                <button type="submit"
                        class="boton verde"
                        onclick="return confirm('¿Deseas cerrar esta cuenta y liberar la mesa?');">
                    Cerrar Cuenta y Liberar Mesa
                </button>

            </form>

        <% } else if ("Abierta".equals(
                seleccionada.getEstado())) { %>

            <div class="aviso">
                Esta cuenta todavía está abierta.
                Primero debes registrar su pago
                desde el módulo de Cobro.
            </div>

            <a class="boton"
               href="<%= request.getContextPath() %>/CobroServlet?idCuenta=<%= seleccionada.getIdCuenta() %>">
                Ir a Cobro
            </a>

        <% } else if ("Cerrada".equals(
                seleccionada.getEstado())) { %>

            <div class="mensaje">
                Esta cuenta ya está cerrada.
                No es necesario realizar otro cierre.
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
