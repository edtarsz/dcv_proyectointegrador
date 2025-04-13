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
        <link href="https://fonts.googleapis.com/css2?family=Ubuntu:ital,wght@0,300;0,400;0,500;0,700;1,300;1,400;1,500;1,700&display=swap" rel="stylesheet">
        <title>Carrito de compras</title>
        <script src="script/carrito-page.js" defer></script>
    </head>
    <body>
        <%@ include file="/partials/header.jspf" %>
        <main class="main-carrito">
            <div class="container-carrito">
                <!-- Contenedor de mensajes de error -->
                <div id="mensajes-error" class="mensajes-error" style="display: none;"></div>

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
                            <p class="productos-count">${carrito.size()} producto<c:if test="${carrito.size() != 1}">s</c:if></p>
                                <div class="carrito-table">
                                    <div class="carrito-header">
                                        <div class="col-detalles">DETALLES DEL PRODUCTO</div>
                                        <div class="col-cantidad">CANTIDAD</div>
                                        <div class="col-precio">PRECIO</div>
                                        <div class="col-total">TOTAL</div>
                                        <div class="col-actions">ACCIONES</div>
                                    </div>

                                <c:set var="subtotal" value="0" />
                                <c:forEach var="item" items="${carrito}" varStatus="status">
                                    <c:set var="total" value="${item.precioUnitario * item.cantidad}" />
                                    <c:set var="subtotal" value="${subtotal + total}" />

                                    <div class="carrito-item" 
                                         data-id="${item.producto.id}" 
                                         data-precio="${item.precioUnitario}"
                                         data-nombre="${item.producto.nombre}">

                                        <div class="col-detalles">
                                            <h3 class="item-title">${item.producto.nombre}</h3>
                                            <p class="item-description">
                                                ${not empty item.personalizacion ? item.personalizacion : 'Sin personalización'}
                                            </p>
                                        </div>
                                        <div class="col-cantidad">
                                            <div class="quantity-control">
                                                <button type="button" class="quantity-btn decrement" title="Disminuir cantidad">−</button>
                                                <input type="number" 
                                                       class="quantity-input" 
                                                       value="${item.cantidad}" 
                                                       min="1" 
                                                       readonly>
                                                <button type="button" class="quantity-btn increment" title="Aumentar cantidad">+</button>
                                            </div>
                                        </div>
                                        <div class="col-precio">$${String.format("%.2f", item.precioUnitario)}</div>
                                        <div class="col-total">$${String.format("%.2f", total)}</div>
                                        <div class="col-actions">
                                            <button type="button" 
                                                    class="btn-edit" 
                                                    data-id="${item.producto.id}"
                                                    data-personalizacion="${item.personalizacion}"
                                                    title="Editar personalización">
                                                <img src="svg/edit.svg" alt="Editar">
                                            </button>
                                            <button type="button" 
                                                    class="btn-delete" 
                                                    data-id="${item.producto.id}"
                                                    title="Eliminar producto">
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
                        <h2 class="resumen-title">Resumen del pedido</h2>
                        <div class="resumen-content">
                            <div class="total-line">
                                <span class="total-label">Total</span>
                                <span class="total-value">$${String.format("%.2f", subtotal)}</span>
                            </div>
                            <form id="confirmarCompraForm" action="Datos.jsp" method="POST">
                                <input type="hidden" name="action" value="procederCompra">
                                <div id="productosContainer">
                                    <c:forEach var="item" items="${carrito}" varStatus="status">
                                        <input type="hidden" name="idProducto[]" value="${item.producto.id}">
                                        <input type="hidden" name="cantidad[]" value="${item.cantidad}">
                                        <input type="hidden" name="personalizacion[]" value="${item.personalizacion}">
                                        <input type="hidden" name="precioUnitario[]" value="${item.precioUnitario}">
                                        <input type="hidden" name="subtotal[]" value="${item.subtotal}">
                                    </c:forEach>
                                    <input type="hidden" name="totalPedido" value="${subtotal}">
                                </div>
                                <button type="submit" class="btn-continuar">Confirmar compra</button>
                            </form>
                        </div>
                    </div>
                </c:if>
            </div>
        </main>

        <!-- Modal para editar personalización -->
        <div id="editarModal" class="modal" style="display: none;">
            <div class="modal-content">
                <span class="close">&times;</span>
                <h2>Editar Personalización</h2>
                <form id="editarPersonalizacionForm">
                    <input type="hidden" id="editProductoId" name="idProducto">

                    <div class="form-group">
                        <label for="editPersonalizacion">Detalles de personalización:</label>
                        <textarea id="editPersonalizacion" name="personalizacion" rows="5" class="form-control"></textarea>
                    </div>

                    <div class="form-group">
                        <label for="editCostoExtra">Costo extra:</label>
                        <input type="number" id="editCostoExtra" name="costoExtra" class="form-control"
                               placeholder="Ej. 15.50" min="0" step="any">
                    </div>

                    <div class="form-buttons">
                        <button type="button" id="cancelarEdicion" class="btn-format-card">Cancelar</button>
                        <button type="submit" class="btn-format-card btn-personalizar-card">Guardar cambios</button>
                    </div>
                </form>
            </div>
        </div>

    </body>
</html>