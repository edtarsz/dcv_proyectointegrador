<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

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
    <c:if test="${not empty requestScope.error}">
        <div class="error-message">
            ${requestScope.error}
        </div>
    </c:if>
    <script src="script/datos.js" defer></script>
    <body>
        <%@ include file="/partials/header.jspf" %>
        <main class="main-catalogo">
            <div class="titulo-seccion">
                <h2>Información del cliente</h2>
            </div>
            <div class="container-catalogo">
                <form class="form-cliente" action="SVDatos" method="POST">
                    <input type="hidden" name="action" value="procesarDatos">

                    <div class="form-group">
                        <label for="nombre">Nombre completo</label>
                        <div class="input-container">
                            <img src="svg/user.svg" alt="Usuario" class="input-icon">
                            <input type="text" id="nombre" name="nombre" required 
                                   value="${param.nombre}" pattern=".{3,}" 
                                   title="El nombre debe tener al menos 3 caracteres">
                        </div>
                    </div>

                    <div class="form-group">
                        <label for="telefono">Teléfono celular</label>
                        <div class="input-container">
                            <img src="svg/phone.svg" alt="Teléfono" class="input-icon">
                            <input type="tel" id="telefono" name="telefono" required 
                                   value="${param.telefono}" pattern="[0-9]{10}" 
                                   title="Ingrese un número de 10 dígitos">
                        </div>
                    </div>

                    <div class="form-group">
                        <label for="correo">Correo electrónico</label>
                        <div class="input-container">
                            <img src="svg/email.svg" alt="Correo" class="input-icon">
                            <input type="email" id="correo" name="correo" required 
                                   value="${param.correo}">
                        </div>
                    </div>

                    <div class="btn-container">
                        <button type="submit" class="btn-continuar">Continuar</button>
                    </div>
                </form>
            </div>
        </main>
    </body>
</html>