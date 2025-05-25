<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<!DOCTYPE html>

<html>
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0"> 

        <%-- Estilos --%>
        <link rel="stylesheet" href="style/style.css">
        <link rel="stylesheet" href="style/header.css">
        <link rel="stylesheet" href="style/carrito.css">
        <link rel="stylesheet" href="style/pedidos.css">

        <%-- Fonts --%>
        <link rel="preconnect" href="https://fonts.googleapis.com">
        <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
        <link href="https://fonts.googleapis.com/css2?family=Ubuntu:wght@300;400;500;700&display=swap" rel="stylesheet">

        <style>
            .no-pedidos {
                text-align: center;
                padding: 20px;
                color: #666;
                font-style: italic;
            }

            .error-message {
                color: #dc3545;
                padding: 10px;
                margin: 10px 0;
                border: 1px solid #dc3545;
                border-radius: 4px;
                background-color: #f8d7da;
                display: none;
            }

            .no-detalles {
                text-align: center;
                padding: 20px;
                color: #666;
                font-style: italic;
            }

            .pedido-estado.en-proceso {
                background-color: #ffd700;
            }
            .pedido-estado.completado {
                background-color: #90ee90;
            }
            .pedido-estado.esperando {
                background-color: #ff9800;
            }
        </style>

        <title>Pedidos</title>
    </head>
    <script src="script/pedidos.js" defer></script>
    <body>
        <%@ include file="/partials/header.jspf" %>

        <div id="mensajes-error" class="error-message"></div>

        <main class="main-pedidos">
            <div class="pedidos-container">
                <div class="pedidos-izquierda">
                    <h1 class="pedidos-title">Pedidos</h1>

                    <div class="search-filter-container">
                        <div class="search-bar-container">
                            <input type="search" name="search-bar" id="search-bar" placeholder="Buscar por nombre">
                            <button class="search-button" id="btnBuscar">
                                <img src="svg/search.svg" alt="Buscar">
                            </button>
                        </div>

                        <div class="filtros-container">
                            <div class="filtro" data-filtro="fecha">
                                <span>Fecha</span>
                                <img src="svg/arrow-down.svg" alt="Desplegable">
                            </div>
                            <div class="filtro" data-filtro="producto">
                                <span>Producto</span>
                                <img src="svg/arrow-down.svg" alt="Desplegable">
                            </div>
                            <div class="filtro" data-filtro="costo">
                                <span>Costo</span>
                                <img src="svg/arrow-down.svg" alt="Desplegable">
                            </div>
                            <div class="filtro" data-filtro="estado">
                                <span>Estado</span>
                                <img src="svg/arrow-down.svg" alt="Desplegable">
                            </div>
                        </div>
                    </div>

                    <div class="pedidos-lista">
                        <c:choose>
                            <c:when test="${empty ventas}">
                                <div class="no-pedidos">
                                    <p>No hay pedidos disponibles</p>
                                </div>
                            </c:when>
                            <c:otherwise>
                                <c:forEach var="venta" items="${ventas}">
                                    <div class="pedido-item ${venta.id == ventaSeleccionada.id ? 'selected' : ''}" 
                                         data-id="${venta.id}">
                                        <div class="pedido-numero">#${venta.id}</div>
                                        <div class="pedido-cliente">${venta.cliente.nombreCompleto}</div>
                                        <div class="pedido-fecha">
                                            <fmt:formatDate value="${venta.fecha}" pattern="dd/MM/yyyy"/>
                                        </div>
                                        <div class="pedido-estado ${fn:toLowerCase(venta.estado)}">
                                            <span>${venta.estado}</span>
                                            <img src="svg/arrow-down.svg" alt="Desplegable">
                                        </div>
                                    </div>
                                </c:forEach>
                            </c:otherwise>
                        </c:choose>
                    </div>
                </div>

                <div class="pedidos-derecha">
                    <h2 class="detalles-title">DETALLES</h2>

                    <c:choose>
                        <c:when test="${empty ventaSeleccionada}">
                            <div class="no-detalles">
                                <p>Seleccione un pedido para ver sus detalles</p>
                            </div>
                        </c:when>
                        <c:otherwise>
                            <div class="detalles-header">
                                <div class="detalles-cliente">${ventaSeleccionada.cliente.nombreCompleto}</div>
                                <div class="detalles-fecha">
                                    <fmt:formatDate value="${ventaSeleccionada.fecha}" pattern="dd/MM/yyyy"/>
                                </div>
                            </div>

                            <div class="detalles-items">
                                <c:forEach var="detalle" items="${ventaSeleccionada.detallesVenta}">
                                    <div class="detalle-item">
                                        <div class="item-info">
                                            <div class="item-title">${detalle.producto.nombre}</div>
                                            <div class="item-descripcion">Editar Pedido: ${detalle.personalizacion}</div>
                                            <div class="item-cantidad">Cantidad: ${detalle.cantidad}</div>
                                        </div>
                                        <div class="item-acciones">
                                            <div class="item-precio">$${detalle.subtotal}</div>
                                            <button class="item-editar" data-id="${detalle.id}">
                                                <img src="svg/edit.svg" alt="Editar">
                                            </button>

                                        </div>
                                    </div>
                                </c:forEach>
                            </div>

                            <div class="total-container">
                                <div class="total-label">TOTAL</div>
                                <div class="total-valor">$${ventaSeleccionada.total}</div>
                            </div>
                        </c:otherwise>
                    </c:choose>
                </div>
            </div>
        </main>
        <div id="editarModal" class="modal" style="display: none;">
            <div class="modal-content">
                <span class="close">&times;</span>
                <h2>Editar Personalización</h2>
                <form id="editarPersonalizacionForm">
                    <input type="hidden" id="editProductoId" name="idProducto">
                    <div class="form-group">
                        <label for="editPersonalizacion">Detalles del pedido:</label>
                        <textarea id="editPersonalizacion" name="personalizacion" rows="5" class="form-control"></textarea>
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