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
        <link rel="stylesheet" href="style/venta.css">

        <link rel="preconnect" href="https://fonts.googleapis.com">
        <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
        <link href="https://fonts.googleapis.com/css2?family=Ubuntu:wght@300;400;500;700&display=swap" rel="stylesheet">

        <style>
            .error-message {
                background-color: #ffebee;
                color: #c62828;
                padding: 15px;
                margin: 10px auto;
                border-radius: 4px;
                border: 1px solid #ef9a9a;
                text-align: center;
                max-width: 80%;
            }

            .resumen-compra {
                background-color: #f5f5f5;
                padding: 20px;
                margin: 20px auto;
                border-radius: 4px;
                max-width: 80%;
            }

            .resumen-compra h3 {
                margin-bottom: 15px;
                color: #333;
            }

            .resumen-item {
                display: flex;
                justify-content: space-between;
                margin-bottom: 10px;
            }
        </style>

        <title>Finalizar Compra</title>
    </head>
<script src="script/venta.js" defer></script>
    <body>
        <%@ include file="/partials/header.jspf" %>
        <main class="main-catalogo">
            <div class="titulo-seccion">
                <h2>Finalizar Compra</h2>
            </div>

            <!-- Mensajes de error -->
            <c:if test="${not empty requestScope.error}">
                <div class="error-message">
                    ${requestScope.error}
                </div>
            </c:if>

            <!-- Resumen de la compra -->
            <div class="resumen-compra">
                <h3>Resumen de tu compra</h3>
                <c:set var="total" value="0"/>
                <c:forEach var="item" items="${sessionScope.carrito}">
                    <div class="resumen-item">
                        <span>${item.producto.nombre} (${item.cantidad})</span>
                        <span>$${item.subtotal}</span>
                    </div>
                    <c:set var="total" value="${total + item.subtotal}"/>
                </c:forEach>
                <div class="resumen-item" style="font-weight: bold; margin-top: 15px;">
                    <span>Total:</span>
                    <span>$${total}</span>
                </div>
            </div>

            <div class="container-catalogo">
                <form class="form-cliente" action="SVVenta" method="POST" id="formVenta">
                    <div class="form-group">
                        <label for="metodoPago">Método de pago</label>
                        <div class="input-container">
                            <select id="metodoPago" name="metodoPago" required>
                                <option value="">Seleccione un método de pago</option>
                                <option value="1">Tarjeta de débito</option>
                                <option value="2">Efectivo</option>
                            </select>
                        </div>
                    </div>

                    <div class="form-group">
                        <label for="envio">Solicita envío</label>
                        <div class="input-container">
                            <label class="radio-label">
                                <input type="radio" id="envioSi" name="envio" value="si" required 
                                       onchange="toggleDireccionEnvio(this.value)">
                                <span>Sí</span>
                            </label>
                            <label class="radio-label">
                                <input type="radio" id="envioNo" name="envio" value="no" required
                                       onchange="toggleDireccionEnvio(this.value)">
                                <span>No</span>
                            </label>
                        </div>
                    </div>

                    <!-- Campo de dirección que se muestra/oculta según la selección -->
                    <div id="direccionEnvioContainer" class="form-group" style="display: none;">
                        <label for="direccionEnvio">Dirección de envío</label>
                        <div class="input-container">
                            <img src="svg/location.svg" alt="Ubicación" class="input-icon">
                            <input type="text" id="direccionEnvio" name="direccionEnvio" 
                                   placeholder="Ingrese la dirección de envío">
                        </div>
                    </div>

                    <div class="btn-container">
                        <button type="submit" class="btn-continuar">CONFIRMAR COMPRA</button>
                    </div>
                </form>
            </div>
        </main>

        <script>
            document.getElementById('formVenta').addEventListener('submit', function (e) {
                const metodoPago = document.getElementById('metodoPago').value;
                const envioSeleccionado = document.querySelector('input[name="envio"]:checked');

                if (!metodoPago) {
                    e.preventDefault();
                    alert('Por favor, seleccione un método de pago');
                    return;
                }

                if (!envioSeleccionado) {
                    e.preventDefault();
                    alert('Por favor, indique si requiere envío');
                    return;
                }
            });
        </script>
    </body>
</html>