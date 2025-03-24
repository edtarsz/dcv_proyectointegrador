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
                        <c:forEach var="producto" items="${productos}">
                            <div class="card-producto" data-producto-id="${producto.id}">
                                <div class="child-card-producto">
                                    <div class="content-card-producto">
                                        <div class="group-left-card">
                                            <h4>${producto.nombre}</h4>
                                            <c:if test="${not empty producto.categorias}">
                                                <div class="categoria-producto">
                                                    <c:forEach var="categoria" items="${producto.categorias}" varStatus="status">
                                                        ${categoria.nombre}${!status.last ? ', ' : ''}
                                                    </c:forEach>
                                                </div>
                                            </c:if>
                                        </div>
                                        <div class="group-right-card producto-info">
                                            <div class="counter-container">
                                                <button class="counter-btn decrease">−</button>
                                                <div class="counter-value">1</div>
                                                <button class="counter-btn increase">+</button>
                                            </div>
                                            <button 
                                                class="btn-format-card btn-personalizar-card"
                                                onclick="agregarAlCarrito(
                                                ${producto.id},
                                                                '${producto.nombre}',
                                                                '${producto.descripcion}',
                                                ${producto.precio},
                                                                this
                                                                )">
                                                Personalizar
                                            </button>
                                            <button 
                                                class="btn-format-card btn-add-carrito"
                                                onclick="agregarAlCarrito(
                                                ${producto.id},
                                                                '${producto.nombre}',
                                                                '${producto.descripcion}',
                                                ${producto.precio},
                                                                this
                                                                )">
                                                Agregar al carrito
                                            </button>
                                        </div>
                                    </div>
                                    <div class="cost-card-producto">
                                        <p>$${producto.precio}</p>
                                    </div>
                                </div>
                                <div class="btn-operation-group">
                                    <a href="">
                                        <img src="svg/pen.svg" alt="">
                                    </a>
                                    <a href="">
                                        <img src="svg/trash.svg" alt="">
                                    </a>
                                </div>
                            </div>
                        </c:forEach>
                    </section>
                </section>
            </div>
            <aside class="aside-catalogo">
                <header class="header-aside">
                    <h1>PRODUCTO</h1>
                    <div>Categoria</div>
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
                </section>
                <section>
                    <h4>PERSONALIZAR</h4>
                </section>
                <section class="aside-personalizar">
                    <textarea name="" id="detalles-personalizacion" placeholder="Introduzca los detalles..." rows="8"></textarea>
                </section>
                <section class="aside-personalizar">
                    <textarea name="" id="extra-personalizacion" placeholder="Extra..." rows="5"></textarea>
                </section>
                <section class="total-aside">
                    <h4>Total: $110.99</h4>
                    <img src="svg/pen.svg" alt="">
                </section>
                <section class="btn-aside-group">
                    <button class="btn-format-card btn-limpiar-carrito" onclick="limpiarCarrito()">Limpiar</button>
                    <button class="btn-format-card btn-add-carrito" onclick="agregarAlCarrito(
                                    null,
                                    'PRODUCTO PERSONALIZADO',
                                    document.getElementById('detalles-personalizacion').value + ' ' + document.getElementById('extra-personalizacion').value,
                                    110.99,
                                    this
                                    )">Agregar al carrito</button>
                </section>
            </aside>
        </main>
    </body>
</html>