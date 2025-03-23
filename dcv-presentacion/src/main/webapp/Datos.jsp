<%-- 
    Document   : Datos
    Created on : 23 mar 2025, 1:43:39 p.m.
    Author     : esmeraldamolinaestrada
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
                <h2>Información del cliente</h2>
            </div>
            <div class="container-catalogo">
                <form class="form-cliente" action="procesar_datos.jsp" method="POST">
                    <div class="form-group">
                        <label for="nombre">Nombre completo</label>
                        <div class="input-container">
                            <img src="svg/user.svg" alt="Usuario" class="input-icon">
                            <input type="text" id="nombre" name="nombre" required>
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="telefono">Teléfono celular</label>
                        <div class="input-container">
                            <img src="svg/phone.svg" alt="Teléfono" class="input-icon">
                            <input type="tel" id="telefono" name="telefono" required>
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="correo">Correo electrónico</label>
                        <div class="input-container">
                            <img src="svg/email.svg" alt="Correo" class="input-icon">
                            <input type="email" id="correo" name="correo" required>
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="direccion">Dirección</label>
                        <div class="input-container">
                            <img src="svg/location.svg" alt="Ubicación" class="input-icon">
                            <input type="text" id="direccion" name="direccion" required>
                        </div>
                    </div>
                    <div class="btn-container">
                        <button type="submit" class="btn-continuar">Continuar</button>
                        <script>
                       document.querySelector('.btn-continuar').addEventListener('click', function() {
                           window.location.href = 'Pedidos.jsp';
                       });
                        </script>
                    </div>
                </form>
            </div>
        </main>
    </body>
</html>