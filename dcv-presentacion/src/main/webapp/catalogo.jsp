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
                    <header class="header-main">
                        <div class="search-bar-container">
                            <input type="search" name="search-bar" id="search-bar" placeholder="Buscar producto">
                            <img src="svg/search.svg" alt="">
                        </div>
                    </header>
                </main>
        </body>

        </html>