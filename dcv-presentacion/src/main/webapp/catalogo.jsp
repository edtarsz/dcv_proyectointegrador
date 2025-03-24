<%-- Document : catalogo Created on : Mar 22, 2025, 1:43:42 PM Author : Ramos --%>
<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">

    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">

        <%-- Estilos --%>
        <link rel="stylesheet" href="style/style.css">
        <link rel="stylesheet" href="style/header.css">
        <link rel="stylesheet" href="style/catalogo.css">

        <%-- Fonts --%>
        <link rel="preconnect" href="https://fonts.googleapis.com">
        <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
        <link
            href="https://fonts.googleapis.com/css2?family=Ubuntu:ital,wght@0,300;0,400;0,500;0,700;1,300;1,400;1,500;1,700&display=swap"
            rel="stylesheet">
        <title>Catalogo</title>
    </head>

    <body>
        <%@ include file="/partials/header.jspf" %>
        <main class="main-catalogo">
            <div class="container-catalogo">
                <header class="header-main">
                    <div class="search-bar-container">
                        <input type="search" name="search-bar" id="search-bar" placeholder="Buscar producto">
                        <img src="svg/search.svg" alt="">
                    </div>
                    <div class="nav-catalogo">
                        <ul class="nav-catalogo-list">
                            <li>
                                <a href="" class="items-catalogo">
                                    Tazas
                                    <img src="svg/arrow-down.svg" alt="">
                                </a>
                            </li>
                            <li>
                                <a href="" class="items-catalogo">
                                    Vasos
                                    <img src="svg/arrow-down.svg" alt="">
                                </a>
                            </li>
                            <li>
                                <a href="" class="items-catalogo">
                                    Cartas
                                    <img src="svg/arrow-down.svg" alt="">
                                </a>
                            </li>
                            <li>
                                <a href="" class="items-catalogo">
                                    Etiquetas
                                    <img src="svg/arrow-down.svg" alt="">
                                </a>
                            </li>
                            <li>
                                <a href="" class="items-catalogo">
                                    Popular
                                    <img src="svg/arrow-down.svg" alt="">
                                </a>
                            </li>
                            <li>
                                <a href="" class="items-catalogo">
                                    Temporadas
                                    <img src="svg/arrow-down.svg" alt="">
                                </a>
                            </li>
                        </ul>
                    </div>
                </header>
                <section class="contenido-catalogo">
                    <h1 class="titulo-productos">Productos</h1>
                    <section class="grid-productos-catalogo">
                        <%-- Iteración de productos con JSTL --%>
                        <c:forEach var="producto" items="${productos}">
                            <div class="card-producto">
                                <h4>${producto.nombre}</h4>
                                <div class="producto-info">
                                    <div class="producto-cantidad">
                                        <button class="btn-cantidad btn-menos">−</button>
                                        <input type="text" value="1" class="input-cantidad">
                                        <button class="btn-cantidad btn-mas">+</button>
                                    </div>
                                    <div class="producto-acciones">
                                        <button class="btn-personalizar">Personalizar</button>
                                        <button class="btn-agregar" onclick="agregarAlCarrito(
                                                ${producto.id},
                                                        '${producto.nombre}',
                                                ${producto.precio},
                                                        document.querySelector(`[data-id='${producto.id}'] .input-personalizar`)?.value || 'No',
                                                        '${producto.descripcion || 'Sin descripción'}',
                                                        parseInt(document.querySelector(`[data-id='${producto.id}'] .input-cantidad`)?.value || 1)
                                                        )">
                                            Agregar al carrito <img src="svg/cart.svg" alt="">
                                        </button>

                                    </div>
                                    <div class="producto-precio">${producto.precio}</div>
                                </div>
                            </div>
                        </c:forEach>
                    </section>
                </section>
            </div>
            <aside class="aside-catalogo">
                <header class="header-aside">
                    <h1>TAZAS</h1>
                    <button class="btn-categoria">Navidad</button>
                </header>
                <section class="description-producto">
                    <p>Dale un toque especial a tus celebraciones con nuestros vasos decorados con bastones de caramelo. Personalizados con el nombre o mensaje que quieras, ideales para fiestas, regalos y eventos.</p>
                </section>
                <section class="section-cantidad">
                    <h4>CANTIDAD</h4>
                    <div class="control-cantidad">
                        <button class="btn-cantidad btn-menos">−</button>
                        <input type="text" value="1" class="input-cantidad">
                        <button class="btn-cantidad btn-mas">+</button>
                    </div>
                </section>
                <section class="section-personalizar">
                    <h4>PERSONALIZAR</h4>
                    <textarea placeholder="Introduzca sus detalles..." class="input-personalizar"></textarea>
                    <textarea placeholder="Extra..." class="input-extra"></textarea>
                </section>
                <section class="section-total">
                    <div class="total-info">
                        <span>Total:</span>
                        <span class="precio-total">$110.99</span>
                        <button class="btn-editar-total"><img src="svg/edit.svg" alt="Editar"></button>
                    </div>
                    <div class="total-acciones">
                        <button class="btn-limpiar">Limpiar <img src="svg/brush.svg" alt=""></button>
                        <button class="btn-agregar-total">Agregar al carrito <img src="svg/cart.svg" alt=""></button>
                    </div>

                    <script>
                        // Estructura para guardar el carrito
                        let carrito = [];

// Función para agregar un producto al carrito
                        function agregarAlCarrito(idProducto, nombre, precio, esPersonalizado, personalizacion, cantidad) {
                            // Validar cantidad mayor a 0
                            if (cantidad <= 0) {
                                cantidad = 1;
                            }

                            // Validar datos del producto
                            if (!nombre) {
                                console.error("El nombre del producto no es válido:", nombre);
                                return;
                            }

                            if (!personalizacion) {
                                personalizacion = "Sin descripción";
                            }

                            // Verificar si el producto ya está en el carrito
                            const productoExistente = carrito.find(item => item.idProducto === idProducto);

                            if (productoExistente) {
                                // Si el producto ya existe, aumentar la cantidad
                                productoExistente.cantidad += cantidad;
                            } else {
                                // Si no existe, agregar el producto
                                carrito.push({
                                    idProducto: idProducto,
                                    nombre: nombre,
                                    precio: precio,
                                    personalizado: esPersonalizado,
                                    personalizacion: personalizacion,
                                    cantidad: cantidad
                                });
                            }

                            // Guardar el carrito en localStorage
                            localStorage.setItem("carrito", JSON.stringify(carrito));
                            console.log("Carrito actualizado:", carrito);
                        }

// Función para cargar el carrito desde localStorage
                        function cargarCarrito() {
                            const carritoGuardado = localStorage.getItem("carrito");
                            if (carritoGuardado) {
                                carrito = JSON.parse(carritoGuardado);
                                console.log("Carrito cargado:", carrito);
                            }
                        }

// Función para limpiar el carrito
                        function limpiarCarrito() {
                            carrito = [];
                            localStorage.removeItem("carrito");
                            console.log("Carrito vaciado.");
                        }

// Llamar esta función al inicio para cargar el carrito existente
                        document.addEventListener("DOMContentLoaded", cargarCarrito);

                    </script>
                </section>
            </aside>
        </main>
    </body>

</html>