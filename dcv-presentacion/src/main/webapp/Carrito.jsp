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
                            // Mostrar el carrito en la página
                            function mostrarCarrito() {
                                const carrito = JSON.parse(localStorage.getItem("carrito")) || [];
                                const carritoContainer = document.querySelector(".carrito-table");
                                carritoContainer.innerHTML = ""; // Limpiar contenido previo

                                // Si el carrito está vacío, mostrar un mensaje
                                if (carrito.length === 0) {
                                    carritoContainer.innerHTML = `<p class="empty-carrito">Tu carrito está vacío.</p>`;
                                    return;
                                }

                                // Crear filas de productos en el carrito
                                carrito.forEach((item, index) => {
                                    const total = (item.precio * item.cantidad).toFixed(2);
                                    carritoContainer.innerHTML += `
                <div class="carrito-item" data-id="${item.idProducto}">
                    <div class="col-detalles">
                        <h3 class="item-title">${item.nombre}</h3>
                        <p class="item-description">${item.descripcion || "Sin descripción"}</p>
                    </div>
                    <div class="col-cantidad">
                        <div class="quantity-control">
                            <button class="quantity-btn decrement" onclick="actualizarCantidad(${index}, -1)">−</button>
                            <input type="text" class="quantity-input" value="${item.cantidad}" readonly>
                            <button class="quantity-btn increment" onclick="actualizarCantidad(${index}, 1)">+</button>
                        </div>
                    </div>
                    <div class="col-precio">$${item.precio}</div>
                    <div class="col-total">$${total}</div>
                    <div class="col-actions">
                        <button class="btn-delete" onclick="eliminarDelCarrito(${index})"><img src="svg/delete.svg" alt="Eliminar"></button>
                    </div>
                </div>
            `;
                                });
                            }

                            // Actualizar la cantidad de un producto en el carrito
                            function actualizarCantidad(index, cambio) {
                                const carrito = JSON.parse(localStorage.getItem("carrito")) || [];
                                carrito[index].cantidad += cambio;

                                // Evitar cantidades menores a 1
                                if (carrito[index].cantidad <= 0) {
                                    carrito[index].cantidad = 1;
                                }

                                // Guardar el carrito actualizado en localStorage
                                localStorage.setItem("carrito", JSON.stringify(carrito));
                                mostrarCarrito(); // Actualizar el DOM
                            }

                            // Eliminar un producto del carrito
                            function eliminarDelCarrito(index) {
                                const carrito = JSON.parse(localStorage.getItem("carrito")) || [];
                                carrito.splice(index, 1); // Eliminar producto por índice

                                // Guardar el carrito actualizado en localStorage
                                localStorage.setItem("carrito", JSON.stringify(carrito));
                                mostrarCarrito(); // Actualizar el DOM
                            }

                            // Confirmar la compra y enviar datos al servidor
                            function confirmarCompra() {
                                const carrito = JSON.parse(localStorage.getItem("carrito")) || [];

                                if (carrito.length === 0) {
                                    alert("El carrito está vacío.");
                                    return;
                                }

                                // Transformar el carrito en detalles de venta
                                const detallesVenta = carrito.map(item => ({
                                        cantidad: item.cantidad,
                                        esPersonalizado: item.personalizado === 'Sí' ? 1 : 0,
                                        personalizacion: item.descripcion || "Sin descripción",
                                        precioUnitario: item.precio,
                                        subtotal: (item.precio * item.cantidad).toFixed(2),
                                        producto_id: item.idProducto,
                                        venta_id: null // Se asignará en el backend
                                    }));

                                // Crear el objeto de datos de la venta
                                const ventaData = {
                                    detallesVenta: detallesVenta,
                                    cliente: {
                                        nombre: "Nombre del Cliente", // Aquí puedes reemplazar con datos reales
                                        telefono: "Teléfono",
                                        correo: "Correo opcional"
                                    },
                                    envio: {
                                        requiereEnvio: true, // Cambiar según selección del usuario
                                        direccion: "Dirección de entrega",
                                        costo: 50.0 // Cambiar por el costo real
                                    },
                                    metodoPago: "Efectivo", // Cambiar según método seleccionado
                                    estado: "En proceso",
                                    fecha: new Date().toISOString().split('T')[0]
                                };

                                // Enviar los datos al servidor como JSON
                                fetch('/api/ventas', {
                                    method: 'POST',
                                    headers: {
                                        'Content-Type': 'application/json'
                                    },
                                    body: JSON.stringify(ventaData)
                                })
                                        .then(response => {
                                            if (!response.ok) {
                                                throw new Error("Error en la solicitud");
                                            }
                                            return response.json();
                                        })
                                        .then(data => {
                                            console.log("Venta registrada exitosamente:", data);

                                            // Limpiar el carrito y redirigir o mostrar mensaje de éxito
                                            localStorage.removeItem("carrito");
                                            mostrarCarrito();
                                            alert("Compra confirmada exitosamente. ¡Gracias por tu pedido!");
                                            window.location.href = "/catalogo.jsp"; // Redirigir si es necesario
                                        })
                                        .catch(error => {
                                            console.error("Error al registrar la venta:", error);
                                            alert("Hubo un problema al confirmar tu compra. Por favor, inténtalo nuevamente.");
                                        });
                            }

                            // Asignar evento al botón de continuar compra
                            document.addEventListener("DOMContentLoaded", function () {
                                document.querySelector(".btn-continuar").addEventListener("click", function (event) {
                                    event.preventDefault();
                                    confirmarCompra();
                                });

                                mostrarCarrito();
                            });
                        </script>

                    </div>
                </div>
            </div>
        </main>
    </body>
</html>