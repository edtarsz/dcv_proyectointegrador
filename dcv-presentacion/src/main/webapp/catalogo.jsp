<%-- Document : catalogo Created on : Mar 22, 2025, 1:43:42 PM Author : Ramos --%>
    <%@page contentType="text/html" pageEncoding="UTF-8" %>
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
                    <title>Document</title>
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
                            <h1>Productos</h1>
                            <section class="grid-productos-catalogo">
                                <div class="card-producto">
                                    <div class="content-card-producto">
                                        <div class="group-left-card">
                                            <h4>TAZAS</h4>
                                            <div class="categoria-producto">
                                                Categoria
                                            </div>
                                        </div>
                                        <div class="group-right-card">
                                            <button class="btn-format-card btn-personalizar-card">Personalizar</button>
                                            <button class="btn-format-card btn-add-carrito">Agregar al carrito</button>
                                            <div class="counter-container">
                                                <button class="counter-btn decrease">−</button>
                                                <div class="counter-value">1</div>
                                                <button class="counter-btn increase">+</button>
                                            </div>
                                            <p>$Costo.00</p>
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
                                <div class="card-producto">
                                    <div class="content-card-producto">
                                        <div class="group-left-card">
                                            <h4>TAZAS</h4>
                                            <div class="categoria-producto">
                                                Categoria
                                            </div>
                                        </div>
                                        <div class="group-right-card">
                                            <button class="btn-format-card btn-personalizar-card">Personalizar</button>
                                            <button class="btn-format-card btn-add-carrito">Agregar al carrito</button>
                                            <div class="counter-container">
                                                <button class="counter-btn decrease">−</button>
                                                <div class="counter-value">1</div>
                                                <button class="counter-btn increase">+</button>
                                            </div>
                                            <p>$Costo.00</p>
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
                                <div class="card-producto">
                                    <div class="content-card-producto">
                                        <div class="group-left-card">
                                            <h4>TAZAS</h4>
                                            <div class="categoria-producto">
                                                Categoria
                                            </div>
                                        </div>
                                        <div class="group-right-card">
                                            <button class="btn-format-card btn-personalizar-card">Personalizar</button>
                                            <button class="btn-format-card btn-add-carrito">Agregar al carrito</button>
                                            <div class="counter-container">
                                                <button class="counter-btn decrease">−</button>
                                                <div class="counter-value">1</div>
                                                <button class="counter-btn increase">+</button>
                                            </div>
                                            <p>$Costo.00</p>
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
                                <div class="card-producto">
                                    <div class="content-card-producto">
                                        <div class="group-left-card">
                                            <h4>TAZAS</h4>
                                            <div class="categoria-producto">
                                                Categoria
                                            </div>
                                        </div>
                                        <div class="group-right-card">
                                            <button class="btn-format-card btn-personalizar-card">Personalizar</button>
                                            <button class="btn-format-card btn-add-carrito">Agregar al carrito</button>
                                            <div class="counter-container">
                                                <button class="counter-btn decrease">−</button>
                                                <div class="counter-value">1</div>
                                                <button class="counter-btn increase">+</button>
                                            </div>
                                            <p>$Costo.00</p>
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
                                <div class="card-producto">
                                    <div class="content-card-producto">
                                        <div class="group-left-card">
                                            <h4>TAZAS</h4>
                                            <div class="categoria-producto">
                                                Categoria
                                            </div>
                                        </div>
                                        <div class="group-right-card">
                                            <button class="btn-format-card btn-personalizar-card">Personalizar</button>
                                            <button class="btn-format-card btn-add-carrito">Agregar al carrito</button>
                                            <div class="counter-container">
                                                <button class="counter-btn decrease">−</button>
                                                <div class="counter-value">1</div>
                                                <button class="counter-btn increase">+</button>
                                            </div>
                                            <p>$Costo.00</p>
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
                            </section>
                        </section>
                    </div>
                    <aside class="aside-catalogo">
                        <header class="header-aside">
                            <h1>
                                PRODUCTO
                            </h1>
                            <div>
                                Categoria
                            </div>
                        </header>
                        <section class="aside-descripcion">
                            <p>Lorem ipsum dolor, sit amet consectetur adipisicing elit. Vero accusamus iure minus
                                placeat vitae. Est natus accusamus cupiditate ducimus atque. Quis aperiam eius ratione
                                minus, maiores sint perspiciatis nobis libero.
                                Ullam molestias autem incidunt consequuntur consectetur, atque eaque minima, dicta
                                commodi minus accusantium nihil ipsum omnis vitae odit dignissimos impedit suscipit
                                corrupti consequatur. Eveniet sit rerum, expedita cupiditate quibusdam molestias!</p>
                        </section>
                        <section class="aside-cantidad">
                            <h4>
                                CANTIDAD
                            </h4>
                            <div class="counter-container">
                                <button class="counter-btn decrease">−</button>
                                <div class="counter-value">1</div>
                                <button class="counter-btn increase">+</button>
                            </div>
                        </section>
                        <section>
                            <h4>
                                PERSONALIZAR
                            </h4>
                        </section>
                        <section class="aside-personalizar">
                            <textarea name="" id="" placeholder="Introduzca los detalles..." rows="8"></textarea>
                        </section>
                        <section class="aside-personalizar">
                            <textarea name="" id="" placeholder="Extra..." rows="5"></textarea>
                        </section>
                    </aside>
                </main>
        </body>

        </html>