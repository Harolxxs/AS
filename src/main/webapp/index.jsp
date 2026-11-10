<%-- 
    Document   : index
    Created on : 7/11/2026, 8:58:59 p. m.
    Author     : harol
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">

    <title>LA ESQUINAPP - Menú Principal</title>

    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #f2f4f7;
            margin: 0;
            padding: 0;
        }

        header {
            background-color: #263238;
            color: white;
            text-align: center;
            padding: 25px;
        }

        header h1 {
            margin: 0;
        }

        header p {
            margin-bottom: 0;
        }

        .contenedor {
            max-width: 850px;
            margin: 45px auto;
            padding: 20px;
        }

        h2 {
            text-align: center;
            color: #333;
            margin-bottom: 30px;
        }

        .menu {
            display: grid;
            grid-template-columns: repeat(2, 1fr);
            gap: 20px;
        }

        .opcion {
            background-color: white;
            border: 1px solid #ddd;
            border-radius: 8px;
            padding: 25px;
            text-align: center;
        }

        .opcion h3 {
            color: #263238;
            margin-top: 0;
        }

        .opcion p {
            color: #666;
            font-size: 14px;
            min-height: 35px;
        }

        .boton {
            display: inline-block;
            background-color: #1976d2;
            color: white;
            text-decoration: none;
            padding: 10px 20px;
            border-radius: 5px;
            margin-top: 10px;
        }

        .boton:hover {
            background-color: #125aa0;
        }

        footer {
            text-align: center;
            color: #777;
            margin-top: 35px;
            font-size: 13px;
        }

        @media (max-width: 600px) {
            .menu {
                grid-template-columns: 1fr;
            }
        }
    </style>
</head>

<body>

    <header>
        <h1>LA ESQUINAPP</h1>
        <p>Sistema de gestión - Restaurante La Terraza</p>
    </header>

    <div class="contenedor">

        <h2>Menú Principal</h2>

        <div class="menu">

            <div class="opcion">
                <h3>Pedidos</h3>
                <p>Registrar y consultar los pedidos del restaurante.</p>
                <a class="boton"
                   href="${pageContext.request.contextPath}/PedidoServlet">
                    Ingresar
                </a>
            </div>

            <div class="opcion">
                <h3>Cocina</h3>
                <p>Consultar comandas y actualizar su preparación.</p>
                <a class="boton"
                   href="${pageContext.request.contextPath}/CocinaServlet">
                    Ingresar
                </a>
            </div>

            <div class="opcion">
                <h3>Cuentas</h3>
                <p>Abrir cuentas y consultar el consumo de las mesas.</p>
                <a class="boton"
                   href="${pageContext.request.contextPath}/CuentaServlet">
                    Ingresar
                </a>
            </div>

            <div class="opcion">
                <h3>Cobro</h3>
                <p>Consultar el total de las cuentas y registrar pagos.</p>
                <a class="boton"
                   href="${pageContext.request.contextPath}/CobroServlet">
                    Ingresar
                </a>
            </div>

            <div class="opcion">
                <h3>Cierre</h3>
                <p>Cerrar cuentas pagadas y liberar las mesas.</p>
                <a class="boton"
                   href="${pageContext.request.contextPath}/CierreServlet">
                    Ingresar
                </a>
            </div>

        </div>

        <footer>
            LA ESQUINAPP - Restaurante La Terraza
        </footer>

    </div>

</body>
</html>

