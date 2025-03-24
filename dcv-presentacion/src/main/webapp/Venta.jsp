<%-- 
    Document   : Venta
    Created on : 24 mar 2025, 05:13:38
    Author     : crist
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="es">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <link rel="stylesheet" href="style/style.css">
        <link rel="stylesheet" href="style/header.css">
        <link rel="stylesheet" href="style/datos.css">
        <link rel="preconnect" href="https://fonts.googleapis.com">
        <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
        <link href="https://fonts.googleapis.com/css2?family=Ubuntu:wght@300;400;500;700&display=swap" rel="stylesheet">
        <title>Información del Cliente</title>
    </head>
    <body>
        <%@ include file="/partials/header.jspf" %>
        <main class="main-catalogo">
            <div class="titulo-seccion">
                <h2>COMPRA</h2>
            </div>
            <div class="container-catalogo">
                <form class="form-cliente" action="SVVenta" method="POST">
                     <input type="hidden" name="clienteId" value="${param.clienteId}" />
                    <div class="form-group">
                        <label for="metodoPago">Metodo de pago</label>
                        <div class="input-container">
                            <input type="text" id="metodoPago" name="metodoPago" required>
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="envio">Solicita Envio?</label>
                        <div class="input-container">
                            <input type="text" id="envio" name="envio" required>
                        </div>
                    </div>
                    
                    <div class="btn-container">
                        <button type="submit" class="btn-continuar">COMPRAR</button>
                    </div>
                </form>
            </div>
        </main>
    </body>
</html>
