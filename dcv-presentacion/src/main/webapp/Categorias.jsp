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
        <title>Catalogo</title>
    </head>

    <body>
        <%@ include file="/partials/header.jspf" %>

        <div class="container">
            <div class="search-container">
                <div class="search-box">
                    <input type="text" class="search-input" placeholder="Buscar categoría..." id="searchInput">
                    <span class="search-icon"></span>
                </div>
            </div>

            <div class="categories-header">
                <h1 class="categories-title">Categorías</h1>
                <button class="add-category-btn" onclick="openAddModal()">Agregar Categoría +</button>
            </div>

            <div class="categories-grid" id="categoriesGrid">
                <!-- Las categorías se cargarán aquí dinámicamente -->
            </div>
        </div>

        <!-- Modal para Agregar/Editar Categoría -->
        <div class="modal" id="categoryModal">
            <div class="modal-content">
                <div class="modal-header">
                    <h2 class="modal-title" id="modalTitle">Agregar Nueva Categoría</h2>
                    <button class="close-btn" onclick="closeModal()">&times;</button>
                </div>
                <form id="categoryForm">
                    <div class="form-group">
                        <label for="categoryName">Nombre de la Categoría</label>
                        <input type="text" id="categoryName" name="categoryName" required>
                    </div>
                    <div class="form-group">
                        <label for="categoryDescription">Descripción (Opcional)</label>
                        <textarea id="categoryDescription" name="categoryDescription" rows="3"></textarea>
                    </div>
                    <div class="modal-actions">
                        <button type="button" class="cancel-btn" onclick="closeModal()">Cancelar</button>
                        <button type="submit" class="save-btn">Guardar</button>
                    </div>
                </form>
            </div>
        </div>

        <!-- Modal de Confirmación para Eliminar -->
        <div class="modal confirm-modal" id="confirmModal">
            <div class="modal-content">
                <div class="confirm-icon">??</div>
                <p class="confirm-text">¿Estás seguro de que deseas eliminar esta categoría?</p>
                <div class="confirm-actions">
                    <button class="cancel-btn" onclick="closeConfirmModal()">Cancelar</button>
                    <button class="confirm-btn" onclick="confirmDelete()">Eliminar</button>
                </div>
            </div>
        </div>

        <script>
            // prueba
            let categories = [
                {id: 1, name: 'NUEVOS PRODUCTOS', description: ''},
                {id: 2, name: 'TEMPORADAS ESPECIALES', description: 'Navidad, Graduaciones'},
                {id: 3, name: 'REGALOS PERSONALIZADOS', description: ''},
                {id: 4, name: 'IMPRESIONES', description: ''},
                {id: 5, name: 'NAVIDAD', description: 'MONOS DECORADOS COMO NUEVA TEMÁTICA'},
                {id: 6, name: 'Fiestas', description: 'esta es una categoría relacionadas con'}
            ];

            let currentEditingId = null;
            let deletingId = null;

            // Renderizar categorías
            function renderCategories(categoriesToRender = categories) {
                const grid = document.getElementById('categoriesGrid');
                grid.innerHTML = '';

                categoriesToRender.forEach(category => {
                    const categoryCard = document.createElement('div');
                    categoryCard.className = 'category-card';
                    categoryCard.innerHTML = `
                        <div class="category-info">
                            <h3>${category.name}</h3>
                            <p>${category.description}</p>
                        </div>
                        <div class="category-actions">
                            <button class="action-btn view-btn" onclick="viewProducts(${category.id})">Ver Productos</button>
                            <button class="action-btn edit-btn" onclick="editCategory(${category.id})">Editar</button>
                            <button class="action-btn delete-btn" onclick="deleteCategory(${category.id})">Eliminar</button>
                        </div>
                    `;
                    grid.appendChild(categoryCard);
                });
            }

            // Búsqueda de categorías
            document.getElementById('searchInput').addEventListener('input', function (e) {
                const searchTerm = e.target.value.toLowerCase();
                const filteredCategories = categories.filter(category =>
                    category.name.toLowerCase().includes(searchTerm) ||
                            category.description.toLowerCase().includes(searchTerm)
                );
                renderCategories(filteredCategories);
            });

            // Abrir modal para agregar categoría
            function openAddModal() {
                document.getElementById('modalTitle').textContent = 'Agregar Nueva Categoría';
                document.getElementById('categoryForm').reset();
                currentEditingId = null;
                document.getElementById('categoryModal').style.display = 'block';
            }

            // Editar categoría
            function editCategory(id) {
                const category = categories.find(c => c.id === id);
                if (category) {
                    document.getElementById('modalTitle').textContent = 'Editar Categoría';
                    document.getElementById('categoryName').value = category.name;
                    document.getElementById('categoryDescription').value = category.description;
                    currentEditingId = id;
                    document.getElementById('categoryModal').style.display = 'block';
                }
            }

            // Eliminar categoría
            function deleteCategory(id) {
                deletingId = id;
                document.getElementById('confirmModal').style.display = 'block';
            }

            // Confirmar eliminación
            function confirmDelete() {
                if (deletingId) {
                    categories = categories.filter(c => c.id !== deletingId);
                    renderCategories();
                    closeConfirmModal();
                    deletingId = null;
                }
            }

            // Ver productos (placeholder)
            function viewProducts(id) {
                alert(`Ver productos de la categoría con ID: ${id}`);
            }

            // Cerrar modales
            function closeModal() {
                document.getElementById('categoryModal').style.display = 'none';
            }

            function closeConfirmModal() {
                document.getElementById('confirmModal').style.display = 'none';
            }

            // Manejar formulario
            document.getElementById('categoryForm').addEventListener('submit', function (e) {
                e.preventDefault();

                const name = document.getElementById('categoryName').value.trim();
                const description = document.getElementById('categoryDescription').value.trim();

                if (!name) {
                    alert('El nombre de la categoría es obligatorio');
                    return;
                }

                if (currentEditingId) {
                    // Editar categoría existente
                    const categoryIndex = categories.findIndex(c => c.id === currentEditingId);
                    if (categoryIndex !== -1) {
                        categories[categoryIndex].name = name;
                        categories[categoryIndex].description = description;
                    }
                } else {
                    // Agregar nueva categoría
                    const newId = Math.max(...categories.map(c => c.id)) + 1;
                    categories.push({
                        id: newId,
                        name: name,
                        description: description
                    });
                }

                renderCategories();
                closeModal();
            });

            // Cerrar modales al hacer clic fuera
            window.addEventListener('click', function (e) {
                const categoryModal = document.getElementById('categoryModal');
                const confirmModal = document.getElementById('confirmModal');

                if (e.target === categoryModal) {
                    closeModal();
                }
                if (e.target === confirmModal) {
                    closeConfirmModal();
                }
            });

            // Renderizar categorías iniciales
            renderCategories();
        </script>
    </body>
</html>