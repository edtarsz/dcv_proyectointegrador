<%-- 
    Document   : Carrito
    Created on : 23 mar 2025, 1:20:40 a.m.
    Author     : esmeraldamolinaestrada
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html lang="en">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <%-- Estilos --%>
        <link rel="stylesheet" href="style/style.css">
        <link rel="stylesheet" href="style/header.css">
        <link rel="stylesheet" href="style/catalogo.css">
        <link rel="stylesheet" href="style/carrito.css">
        <%-- Fonts --%>
        <link rel="preconnect" href="https://fonts.googleapis.com">
        <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
        <link
            href="https://fonts.googleapis.com/css2?family=Ubuntu:ital,wght@0,300;0,400;0,500;0,700;1,300;1,400;1,500;1,700&display=swap"
            rel="stylesheet">
        <title>Carrito de compras</title>
        <script src="script/carrito-page.js" defer></script>
    </head>
    <body>
        <%@ include file="/partials/header.jspf" %>
        <main class="main-carrito">
            <div class="container-carrito">
                <div class="carrito-content">
                    <h1 class="carrito-title">Carrito</h1>

                    <c:set var="carrito" value="${sessionScope.carrito}" />

                    <c:choose>
                        <c:when test="${empty carrito}">
                            <p class="productos-count">0 productos</p>
                            <div class="carrito-vacio">
                                <p>No hay productos en el carrito</p>
                                <a href="catalogo.jsp" class="btn-seguir-comprando">Seguir comprando</a>
                            </div>
                        </c:when>
                        <c:otherwise>
                            <p class="productos-count">${carrito.size()} productos</p>
                            <div class="carrito-table">
                                <div class="carrito-header">
                                    <div class="col-detalles">DETALLES DEL PRODUCTO</div>
                                    <div class="col-cantidad">CANTIDAD</div>
                                    <div class="col-precio">PRECIO</div>
                                    <div class="col-total">TOTAL</div>
                                </div>

                                <c:set var="subtotal" value="0" />
                                <c:forEach var="item" items="${carrito}" varStatus="status">
                                    <!-- Calcular total por producto -->
                                    <c:set var="total" value="${item.precioUnitario * item.cantidad}" />
                                    <c:set var="subtotal" value="${subtotal + total}" />

                                    <div class="carrito-item" data-id="${item.producto.id}">
                                        <div class="col-detalles">
                                            <h3 class="item-title">${item.producto.nombre}</h3>
                                            <p class="item-description">
                                                ${not empty item.personalizacion ? item.personalizacion : 'Sin personalización'}
                                            </p>
                                        </div>
                                        <div class="col-cantidad">
                                            <div class="quantity-control">
                                                <button class="quantity-btn decrement" data-action="decrement">−</button>
                                                <input type="text" class="quantity-input" value="${item.cantidad}" readonly>
                                                <button class="quantity-btn increment" data-action="increment">+</button>
                                            </div>
                                        </div>
                                        <div class="col-precio">$${item.precioUnitario}</div>
                                        <div class="col-total">$${total}</div>
                                        <div class="col-actions">
                                            <button class="btn-delete">
                                                <img src="svg/delete.svg" alt="Eliminar">
                                            </button>
                                        </div>
                                    </div>
                                </c:forEach>
                            </div>
                        </c:otherwise>
                    </c:choose>
                </div>

                <c:if test="${not empty carrito}">
                    <div class="resumen-pedido">
                        <h2 class="resumen-title">Resúmen del pedido</h2>
                        <div class="resumen-content">
                            <div class="total-line">
                                <span class="total-label">Total</span>
                                <span class="total-value">$${subtotal}</span>
                                <button class="btn-edit-total">
                                    <img src="svg/edit.svg" alt="Editar">
                                </button>
                            </div>
                            <form id="confirmarCompraForm" action="SVProducto" method="POST">
                                <input type="hidden" name="action" value="updateStock">
                                <div id="productosContainer">
                                    <c:forEach var="item" items="${carrito}" varStatus="status">
                                        <input type="hidden" name="idProducto[]" value="${item.producto.id}">
                                        <input type="hidden" name="cantidad[]" value="${item.cantidad}">
                                        <input type="hidden" name="personalizacion[]" value="${item.personalizacion}">
                                    </c:forEach>
                                </div>
                                <button type="submit" class="btn-continuar">Confirmar compra</button>
                            </form>
                        </div>
                    </div>
                </c:if>
            </div>
        </main>
    </body>
</html>