<%-- 
    Document   : Carrito
    Created on : 23 mar 2025, 1:20:40 a.m.
    Author     : esmeraldamolinaestrada
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>

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
    </head>
    <body>
        <%@ include file="/partials/header.jspf" %>
        <main class="main-carrito">
            <div class="container-carrito">
                <div class="carrito-content">
                    <h1 class="carrito-title">Carrito</h1>
                    <p class="productos-count">3 productos</p>

                    <div class="carrito-table">
                        <div class="carrito-header">
                            <div class="col-detalles">DETALLES DEL PRODUCTO</div>
                            <div class="col-cantidad">CANTIDAD</div>
                            <div class="col-precio">PRECIO</div>
                            <div class="col-total">TOTAL</div>
                        </div>

                        <!-- Producto 1 -->
                        <div class="carrito-item">
                            <div class="col-detalles">
                                <h3 class="item-title">Tazas</h3>
                                <p class="item-description">Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.</p>
                            </div>
                            <div class="col-cantidad">
                                <div class="quantity-control">
                                    <button class="quantity-btn decrement">−</button>
                                    <input type="text" class="quantity-input" value="2" readonly>
                                    <button class="quantity-btn increment">+</button>
                                </div>
                            </div>
                            <div class="col-precio">$44.00</div>
                            <div class="col-total">$88.00</div>
                            <div class="col-actions">
                                <button class="btn-delete"><img src="svg/delete.svg" alt="Eliminar"></button>
                                <button class="btn-edit"><img src="svg/edit.svg" alt="Editar"></button>
                            </div>
                        </div>

                        <!-- Producto 2 -->
                        <div class="carrito-item">
                            <div class="col-detalles">
                                <h3 class="item-title">Tazas <span class="tag-navidad">Navidad</span></h3>
                                <p class="item-description">Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.</p>
                            </div>
                            <div class="col-cantidad">
                                <div class="quantity-control">
                                    <button class="quantity-btn decrement">−</button>
                                    <input type="text" class="quantity-input" value="2" readonly>
                                    <button class="quantity-btn increment">+</button>
                                </div>
                            </div>
                            <div class="col-precio">$44.00</div>
                            <div class="col-total">$88.00</div>
                            <div class="col-actions">
                                <button class="btn-delete"><img src="svg/delete.svg" alt="Eliminar"></button>
                                <button class="btn-edit"><img src="svg/edit.svg" alt="Editar"></button>
                            </div>
                        </div>

                        <!-- Producto 3 -->
                        <div class="carrito-item">
                            <div class="col-detalles">
                                <h3 class="item-title">Tazas</h3>
                                <p class="item-description">Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.</p>
                            </div>
                            <div class="col-cantidad">
                                <div class="quantity-control">
                                    <button class="quantity-btn decrement">−</button>
                                    <input type="text" class="quantity-input" value="2" readonly>
                                    <button class="quantity-btn increment">+</button>
                                </div>
                            </div>
                            <div class="col-precio">$44.00</div>
                            <div class="col-total">$88.00</div>
                            <div class="col-actions">
                                <button class="btn-delete"><img src="svg/delete.svg" alt="Eliminar"></button>
                                <button class="btn-edit"><img src="svg/edit.svg" alt="Editar"></button>
                            </div>
                        </div>
                    </div>
                </div>

                <div class="resumen-pedido">
                    <h2 class="resumen-title">Resúmen del pedido</h2>
                    <div class="resumen-content">
                        <div class="total-line">
                            <span class="total-label">Total</span>
                            <span class="total-value">$110.99</span>
                            <button class="btn-edit-total"><img src="svg/edit.svg" alt="Editar"></button>
                        </div>
                        <form id="confirmarCompraForm" action="SVProducto" method="POST">
                            <input type="hidden" name="action" value="updateStock">

                            <!-- Campos ocultos para los productos -->
                            <div id="productosContainer"></div>

                            <button type="submit" class="btn-continuar">Confirmar compra</button>
                        </form>

                        <script>
                            document.querySelector(".btn-continuar").addEventListener("click", function (event) {
                                event.preventDefault();

                                let productosContainer = document.getElementById("productosContainer");
                                productosContainer.innerHTML = "";  // Limpiar contenido previo

                                document.querySelectorAll(".carrito-item").forEach(item => {
                                    let id = item.getAttribute("data-id");
                                    let cantidad = item.querySelector(".quantity-input").value;

                                    // Crear inputs ocultos
                                    let inputId = document.createElement("input");
                                    inputId.type = "hidden";
                                    inputId.name = "id[]";
                                    inputId.value = id;

                                    let inputCantidad = document.createElement("input");
                                    inputCantidad.type = "hidden";
                                    inputCantidad.name = "cantidad[]";
                                    inputCantidad.value = cantidad;

                                    productosContainer.appendChild(inputId);
                                    productosContainer.appendChild(inputCantidad);
                                });

                                document.getElementById("confirmarCompraForm").submit();
                            });
                        </script>
                    </div>
                </div>
            </div>
        </main>
    </body>
</html>