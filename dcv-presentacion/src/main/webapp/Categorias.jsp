<%-- 
    Document   : Categorias
    Created on : 24 may 2025, 2:09:06?a.m.
    Author     : esmeraldamolinaestrada
--%>

<!DOCTYPE html>
<html lang="es">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">

        <%-- Estilos --%>
        <link rel="stylesheet" href="style/style.css">
        <link rel="stylesheet" href="style/header.css">
        <link rel="stylesheet" href="style/catalogo.css">
        <link rel="stylesheet" href="style/categorias.css">

        <%-- Fonts --%>
        <link rel="preconnect" href="https://fonts.googleapis.com">
        <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
        <link href="https://fonts.googleapis.com/css2?family=Ubuntu:ital,wght@0,300;0,400;0,500;0,700;1,300;1,400;1,500;1,700&display=swap" rel="stylesheet">

        <%-- Cart Script --%>
        <script defer src="script/carrito.js"></script>
        <title>Administrar Categorias</title>
    </head>

    <body>
        <%@ include file="/partials/header.jspf" %>

        <div class="container">
            <div class="search-container">
                <div class="search-box">
                    <input type="text" class="search-input" placeholder="Buscar categor�a..." id="searchInput">
                    <span class="search-icon"></span>
                </div>
            </div>

            <div class="categories-header">
                <h1 class="categories-title">Categor�as</h1>
                <button class="add-category-btn" onclick="openAddModal()">Agregar Categor�a +</button>
            </div>

            <div class="categories-grid" id="categoriesGrid">
                <!-- Las categor�as se cargar�n aqu� din�micamente -->
            </div>
        </div>

        <!-- Modal para Agregar/Editar Categor�a -->
        <div class="modal" id="categoryModal">
            <div class="modal-content">
                <div class="modal-header">
                    <h2 class="modal-title" id="modalTitle">Agregar Nueva Categor�a</h2>
                    <button class="close-btn" onclick="closeModal()">&times;</button>
                </div>
                <form id="categoryForm">
                    <div class="form-group">
                        <label for="categoryName">Nombre de la Categor�a</label>
                        <input type="text" id="categoryName" name="categoryName" required>
                    </div>
                    <div class="form-group">
                        <label for="categoryDescription">Descripci�n (Opcional)</label>
                        <textarea id="categoryDescription" name="categoryDescription" rows="3"></textarea>
                    </div>
                    <div class="modal-actions">
                        <button type="button" class="cancel-btn" onclick="closeModal()">Cancelar</button>
                        <button type="submit" class="save-btn">Guardar</button>
                    </div>
                </form>
            </div>
        </div>

        <!-- Modal de Confirmaci�n para Eliminar -->
        <div class="modal confirm-modal" id="confirmModal">
            <div class="modal-content">
                <div class="confirm-icon">??</div>
                <p class="confirm-text">�Est�s seguro de que deseas eliminar esta categor�a?</p>
                <div class="confirm-actions">
                    <button class="cancel-btn" onclick="closeConfirmModal()">Cancelar</button>
                    <button class="confirm-btn" onclick="confirmDelete()">Eliminar</button>
                </div>
            </div>
        </div>

        <script defer src="script/categorias.js"></script>
    </body>
</html>