<%-- 
    Document   : registrar
    Created on : 7/11/2026, 10:24:41 p. m.
    Author     : harol
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.util.List"%>
<%@page import="com.esquinapp.modelo.Cuenta"%>
<%@page import="com.esquinapp.modelo.Producto"%>

<%
    List<Cuenta> cuentas =
        (List<Cuenta>) request.getAttribute("cuentas");

    List<Producto> productos =
        (List<Producto>) request.getAttribute("productos");

    boolean hayCuentas = cuentas != null && !cuentas.isEmpty();
    boolean hayProductos = productos != null && !productos.isEmpty();
%>

<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">

    <title>Registrar Pedido - LA ESQUINAPP</title>

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
            max-width: 750px;
            margin: 35px auto;
            background: white;
            padding: 30px;
            border-radius: 8px;
            box-sizing: border-box;
        }

        h2 {
            margin-top: 0;
            color: #333;
        }

        label {
            display: block;
            margin-top: 15px;
            margin-bottom: 6px;
            font-weight: bold;
        }

        input, select, textarea {
            width: 100%;
            padding: 10px;
            border: 1px solid #ccc;
            border-radius: 5px;
            box-sizing: border-box;
            font-family: Arial, sans-serif;
        }

        .fila {
            display: grid;
            grid-template-columns: 2fr 1fr;
            gap: 15px;
        }

        .producto {
            background-color: #f7f9fa;
            border: 1px solid #ddd;
            border-radius: 6px;
            padding: 15px;
            margin-top: 15px;
        }

        .producto h4 {
            margin: 0;
        }

        .boton {
            display: inline-block;
            background-color: #1976d2;
            color: white;
            border: none;
            padding: 12px 18px;
            border-radius: 5px;
            cursor: pointer;
            text-decoration: none;
            margin-top: 20px;
            font-size: 14px;
        }

        .boton:hover {
            background-color: #125aa0;
        }

        .secundario {
            background-color: #607d8b;
             border: none;
            padding: 12px 18px;
            border-radius: 5px;
            cursor: pointer;
            text-decoration: none;
            margin-top: 20px;
            font-size: 14px;
        }

        .mensaje {
            background: #fff3cd;
            color: #664d03;
            padding: 12px;
            border-radius: 5px;
            margin-bottom: 15px;
        }

        @media (max-width: 600px) {
            .fila {
                grid-template-columns: 1fr;
            }
        }
    </style>
</head>

<body>

<header>
    <h1>LA ESQUINAPP</h1>
    <p>Restaurante La Terraza</p>
</header>

<div class="contenedor">

    <h2>Registrar Nuevo Pedido</h2>

    <% if (!hayCuentas) { %>
        <div class="mensaje">
            No hay cuentas abiertas.
            Primero debes abrir una cuenta en el módulo Cuentas.
        </div>
    <% } %>

    <% if (!hayProductos) { %>
        <div class="mensaje">
            No hay productos disponibles para registrar pedidos.
        </div>
    <% } %>

    <form id="formPedido"
          action="<%= request.getContextPath() %>/PedidoServlet"
          method="post">

        <label for="idCuenta">Cuenta abierta:</label>

        <select name="idCuenta" id="idCuenta"
                onchange="actualizarMesa()" required>

            <option value="">Seleccione una cuenta</option>

            <% if (cuentas != null) {
                for (Cuenta cuenta : cuentas) { %>

                <option value="<%= cuenta.getIdCuenta() %>"
                        data-mesa="<%= cuenta.getIdMesa() %>">

                    Cuenta #<%= cuenta.getIdCuenta() %>
                    - Mesa ID <%= cuenta.getIdMesa() %>

                </option>

            <% } } %>

        </select>

        <input type="hidden" name="idMesa" id="idMesa">

        <label for="mesaVisible">Mesa seleccionada:</label>
        <input type="text" id="mesaVisible" readonly
               placeholder="Se completa al seleccionar la cuenta">

        <label for="idMesero">ID del mesero:</label>
        <input type="number" name="idMesero"
               id="idMesero" value="1" min="1" required>

        <h3>Productos del pedido</h3>

        <% for (int i = 1; i <= 3; i++) { %>

            <div class="producto">

                <h4>Producto <%= i %></h4>

                <div class="fila">

                    <div>
                        <label>Producto:</label>

                        <select name="idProducto"
                                class="productoSelect"
                                <% if (i == 1) { %>required<% } %>>

                            <option value="">Seleccione un producto</option>

                            <% if (productos != null) {
                                for (Producto producto : productos) { %>

                                <option value="<%= producto.getIdProducto() %>">
                                    <%= producto.getNombre()
                                        .replace("&", "&amp;")
                                        .replace("<", "&lt;")
                                        .replace(">", "&gt;")
                                        .replace("\"", "&quot;") %>
                                    - $<%= producto.getPrecio() %>
                                </option>

                            <% } } %>

                        </select>
                    </div>

                    <div>
                        <label>Cantidad:</label>
                        <input type="number" name="cantidad"
                               class="cantidadInput"
                               value="1" min="1" required>
                    </div>

                </div>

            </div>

        <% } %>

        <label for="observaciones">Observaciones:</label>

        <textarea name="observaciones" id="observaciones"
                  rows="3"
                  placeholder="Ejemplo: Sin cebolla"></textarea>

        <button class="boton" type="submit"
                <% if (!hayCuentas || !hayProductos) { %>
                    disabled
                <% } %>>
            Registrar Pedido
        </button>

        <a class="boton secundario"
           href="<%= request.getContextPath() %>/PedidoServlet">
            Volver al listado
        </a>

    </form>

</div>

<script>
    function actualizarMesa() {
        const select = document.getElementById("idCuenta");
        const opcion = select.options[select.selectedIndex];

        const idMesa = opcion.getAttribute("data-mesa") || "";

        document.getElementById("idMesa").value = idMesa;
        document.getElementById("mesaVisible").value = idMesa;
    }

    document.getElementById("formPedido")
        .addEventListener("submit", function() {

            const productos =
                document.querySelectorAll(".productoSelect");

            const cantidades =
                document.querySelectorAll(".cantidadInput");

            for (let i = 0; i < productos.length; i++) {

                // Omitir las filas opcionales sin producto
                if (productos[i].value === "") {
                    productos[i].disabled = true;
                    cantidades[i].disabled = true;
                }
            }
        });
</script>

</body>
</html>
