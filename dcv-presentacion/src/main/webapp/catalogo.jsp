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
        <link href="https://fonts.googleapis.com/css2?family=Ubuntu:ital,wght@0,300;0,400;0,500;0,700;1,300;1,400;1,500;1,700&display=swap" rel="stylesheet">

        <%-- Cart Script --%>
        <script defer src="script/carrito.js"></script>
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
                            <%-- Idealmente, estas categorías también se generarían dinámicamente desde el backend --%>
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
                        <c:forEach var="producto" items="${productos}"> <%-- Asumiendo que ${productos} es la lista de productos pasada por el servlet --%>
                            <div class="card-producto" data-producto-id="${producto.id}">
                                <div class="child-card-producto">
                                    <div class="content-card-producto">
                                        <div class="group-left-card">
                                            <h4>${producto.nombre}</h4>
                                            <c:if test="${not empty producto.categorias}">
                                                <div class="categoria-producto">
                                                    <%-- CAMBIO AQUÍ para iterar la lista de categorías --%>
                                                    <c:forEach var="cat" items="${producto.categorias}" varStatus="loop">
                                                        <span>${cat.nombre}</span><c:if test="${not loop.last}">, </c:if>
                                                    </c:forEach>
                                                </div>
                                            </c:if>
                                            <c:if test="${empty producto.categorias}">
                                                <div class="categoria-producto">
                                                    <span>Sin categoría</span>
                                                </div>
                                            </c:if>
                                        </div>
                                        <div class="group-right-card producto-info">
                                            <button 
                                                class="btn-format-card btn-personalizar-card"
                                                onclick="seleccionarProducto(
                                                ${producto.id},
                                                                '${producto.nombre}',
                                                                '${producto.descripcion}',
                                                ${producto.precio}
                                                        )">
                                                Personalizar
                                            </button>
                                        </div>
                                    </div>
                                    <div class="cost-card-producto">
                                        <p>$${producto.precio}</p>
                                    </div>
                                </div>
                            </div>
                        </c:forEach>
                    </section>
                </section>
            </div>
            <aside class="aside-catalogo">
                <header class="header-aside">
                    <h1>PRODUCTO</h1>
                </header>
                <section class="aside-descripcion">
                    <p>Lorem ipsum dolor, sit amet consectetur adipisicing elit. Vero accusamus iure minus
                        placeat vitae. Est natus accusamus cupiditate ducimus atque.</p>
                </section>
                <section class="aside-cantidad">
                    <h4>CANTIDAD</h4>
                    <div class="counter-container">
                        <button class="counter-btn decrease">−</button>
                        <div class="counter-value">1</div>
                        <button class="counter-btn increase">+</button>
                    </div>
                    <p class="stock-message" style="color: red; display: none;"></p>
                </section>
                <section>
                    <h4>PERSONALIZAR</h4>
                </section>
                <section class="aside-personalizar">
                    <textarea 
                        id="detalles-personalizacion" 
                        placeholder="Introduzca los detalles..." 
                        rows="8"
                        maxlength="100"
                        ></textarea>
                    <small id="detalles-aviso" style="color: red; display: none; margin-top: 0.4rem;"></small>
                </section>
                <section class="aside-personalizar">
                    <textarea name="" id="extra-personalizacion" placeholder="Extra..." rows="5"></textarea>
                </section>
                <section class="total-aside">
                    <h4>Total: $110.99</h4>
                </section>
                <section class="btn-aside-group">
                    <section class="btn-aside-group">
                        <button class="btn-format-card btn-limpiar-carrito" onclick="limpiarFormulario()">Limpiar</button>
                        <button class="btn-format-card btn-add-carrito" onclick="agregarAlCarrito()">Agregar al carrito</button>
                    </section>
                </section>
            </aside>
        </main>
    </body>
    <script>
        document.addEventListener("DOMContentLoaded", () => {
            const searchInput = document.getElementById("search-bar");
            const productCards = document.querySelectorAll(".card-producto");

            searchInput.addEventListener("input", () => {
                const query = searchInput.value.toLowerCase();

                productCards.forEach(card => {
                    const text = card.innerText.toLowerCase();
                    const visible = text.includes(query);
                    card.style.display = visible ? "block" : "none";
                });
            });
        });
    </script>

</html>