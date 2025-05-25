<%@page import="com.mycompany.dcventidades.Categoria"%>
<%@ page import="java.util.List" %>
<%@ page import="java.util.Map" %>
<%@ page import="com.mycompany.dcventidades.Producto" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Productos</title>
    <link rel="stylesheet" href="style/style.css">
    <link rel="stylesheet" href="style/header.css">
    <link rel="stylesheet" href="style/catalogo.css">
    <link rel="stylesheet" href="style/inventario.css">
    <link href="https://fonts.googleapis.com/css2?family=Ubuntu:wght@300;400;500;700&display=swap" rel="stylesheet">
</head>
<body>
<%@ include file="/partials/header.jspf" %>
<main class="main-content">
    <div class="page-header">
        <div class="search-container">
            <input type="text" class="search-input" placeholder="Buscar producto" id="searchInput">
            <button class="search-button" onclick="searchProducts()"></button>
        </div>
        <div class="action-buttons">
            <button class="btn btn-primary" onclick="openAddModal()">Agregar</button>
        </div>
    </div>

    <div class="table-container">
        <table class="table" id="inventoryTable">
            <thead>
            <tr>
                <th>ID del producto</th>
                <th>Nombre</th>
                <th>Descripción</th>
                <th>Precio</th>
                <th>Categoría</th>
                <th>Acciones</th>
            </tr>
            </thead>
            <% List<Producto> productos = (List<Producto>) request.getAttribute("productos"); %>

            <tbody>
                <% if (productos != null) {
        for (Producto p : productos) {%>
                <tr>
                    <td><%= p.getId()%></td>
                    <td><%= p.getNombre()%></td>
                    <td><%= p.getDescripcion()%></td>
                    <td>$<%= p.getPrecio()%></td>
                    <% if (p.getCategorias() != null && !p.getCategorias().isEmpty()) {%>
                    <td><%= p.getCategorias().get(0).getNombre()%></td>
                    <% } else { %>
                    <td>Sin categoría</td>
                    <% }%>

                    <td>
                        <button type="button" class="btn btn-secondary btn-sm"
                                onclick="editarProducto(
                        '<%= p.getId()%>',
                        '<%= p.getNombre().replace("'", "\\'")%>',
                        '<%= p.getDescripcion().replace("'", "\\'")%>',
                        '<%= p.getPrecio()%>'
                    )">Editar</button>

                        <form action="SVAdministrarProductos" method="post" style="display:inline;">
                            <input type="hidden" name="accion" value="eliminar">
                            <input type="hidden" name="id" value="<%= p.getId()%>">
                            <button type="submit" class="btn btn-danger btn-sm">Eliminar</button>
                        </form>
                    </td>
                </tr>
                <% }
    }%>
            </tbody>
        </table>
    </div>
</main>

<!-- Modal -->
<div class="modal" id="productModal">
    <div class="modal-content">
        <div class="modal-header">
            <h2 class="modal-title" id="modalTitle">Agregar Producto</h2>
            <button class="close-btn" onclick="closeModal()">&times;</button>
        </div>
        <form id="productForm" action="SVAdministrarProductos" method="post">
            <input type="hidden" id="accion" name="accion" value="crear">
            <input type="hidden" id="productoId" name="id">
            <input type="hidden" name="usuario_id" value="1">

            <div class="form-group">
                <label class="form-label" for="nombre">Nombre</label>
                <input type="text" class="form-input" name="nombre" id="nombre" required>
            </div>

            <div class="form-group">
                <label class="form-label">Descripcion</label>
                <input type="text" class="form-input" name="descripcion" id="descripcion" required>
            </div>

            <div class="form-group">
                <label class="form-label" for="precio">Precio</label>
                <input type="number" class="form-input" name="precio" id="precio" required min="1">
            </div>

            <div class="form-group">
                <label class="form-label" for="categoria">Categoría</label>
                <div id="categoria-container">
                    <%-- Suponiendo que tienes cargadas las categorías como atributo "categorias" --%>
                    <% List<Categoria> categorias = (List<Categoria>) request.getAttribute("categorias");
                        if (categorias != null) {
                            for (Categoria cat : categorias) {
                    %>
                    <label>
                        <input type="radio" name="categoriaId" value="<%= cat.getId()%>">
                        <%= cat.getNombre()%>
                    </label><br>
                    <%   }
            }%>
                </div>
            </div>


            <div class="modal-footer">
                <button class="btn btn-secondary" type="button" onclick="closeModal()">Cancelar</button>
                <button class="btn btn-primary" type="submit">Guardar</button>
            </div>
        </form>
    </div>
</div>

        <script>


            let editingIndex = -1;
            let selectedProducts = new Set();

            function loadProducts() {
                const tbody = document.getElementById('inventoryBody');
                tbody.innerHTML = '';

                products.forEach((product, index) => {
                    const row = document.createElement('tr');
                    row.innerHTML = `
                        <td>${product.id}</td>
                        <td>${product.name}</td>
                        <td>${product.description}</td>
                        <td>${product.quantity}</td>
                        <td>${product.salePrice}</td>
                        <td>${product.purchasePrice}</td>
                        <td class="action-cell">
                            <button class="btn btn-secondary btn-sm" onclick="editProduct(${index})">Editar</button>
                            <button class="btn btn-danger btn-sm" onclick="deleteProduct(${index})">Eliminar</button>
                        </td>
                        <td>
                            <input type="checkbox" class="checkbox" onchange="toggleSelection(${index})" ${selectedProducts.has(index) ? 'checked' : ''}>
                        </td>
                    `;
                    tbody.appendChild(row);
                });

                updateSelectedCount();
            }

            function openAddModal() {
                document.getElementById('modalTitle').textContent = 'Agregar Producto';
                document.getElementById('productForm').reset();
                editingIndex = -1;
                document.getElementById('productModal').style.display = 'block';
            }

       function editarProducto(id, nombre, descripcion, precio, categoriaId) {
    document.getElementById("productModal").style.display = 'block';
    setTimeout(() => {
        document.getElementById("accion").value = "editar";
        document.getElementById("productoId").value = id;
        document.getElementById("nombre").value = nombre;
        document.getElementById("descripcion").value = descripcion;
        document.getElementById("precio").value = precio;

        // Marcar categoría seleccionada
        const radios = document.getElementsByName("categoriaId");
        radios.forEach(radio => {
            radio.checked = radio.value === categoriaId;
        });
    }, 10);
}







            function closeModal() {
                document.getElementById('productModal').style.display = 'none';
                editingIndex = -1;
            }

            function saveProduct() {
                const form = document.getElementById('productForm');
                if (!form.checkValidity()) {
                    form.reportValidity();
                    return;
                }

                const product = {
                    id: document.getElementById('productId').value,
                    name: document.getElementById('productName').value,
                    description: document.getElementById('productDescription').value,
                    quantity: parseInt(document.getElementById('productQuantity').value),
                    salePrice: parseFloat(document.getElementById('salePrice').value),
                    purchasePrice: parseFloat(document.getElementById('purchasePrice').value)
                };

                if (editingIndex >= 0) {
                    products[editingIndex] = product;
                } else {
                    products.push(product);
                }

                loadProducts();
                closeModal();
            }

            function deleteProduct(index) {
                if (confirm('¿Estás seguro de que quieres eliminar este producto?')) {
                    products.splice(index, 1);
                    selectedProducts.delete(index);
                    // Actualizar índices de productos seleccionados
                    const newSelected = new Set();
                    selectedProducts.forEach(i => {
                        if (i < index)
                            newSelected.add(i);
                        else if (i > index)
                            newSelected.add(i - 1);
                    });
                    selectedProducts = newSelected;
                    loadProducts();
                }
            }

            function toggleSelection(index) {
                if (selectedProducts.has(index)) {
                    selectedProducts.delete(index);
                } else {
                    selectedProducts.add(index);
                }
                updateSelectedCount();
            }


            function searchProducts() {
                const query = document.getElementById('searchInput').value.toLowerCase();
                const tbody = document.getElementById('inventoryBody');
                const rows = tbody.getElementsByTagName('tr');

                for (let row of rows) {
                    const text = row.textContent.toLowerCase();
                    row.style.display = text.includes(query) ? '' : 'none';
                }
            }

            function sortTable(column) {
                // Implementación básica de ordenamiento
                const table = document.getElementById('inventoryTable');
                const tbody = table.getElementsByTagName('tbody')[0];
                const rows = Array.from(tbody.getElementsByTagName('tr'));

                rows.sort((a, b) => {
                    const aVal = a.getElementsByTagName('td')[column].textContent;
                    const bVal = b.getElementsByTagName('td')[column].textContent;
                    return aVal.localeCompare(bVal, undefined, {numeric: true});
                });

                rows.forEach(row => tbody.appendChild(row));
            }

            function generateReport() {
                alert('Generando reporte de ventas...');
            }

            function navigate(section) {
                alert(`Navegando a: ${section}`);
            }

            // Cerrar modal al hacer clic fuera
            window.onclick = function (event) {
                const modal = document.getElementById('productModal');
                if (event.target === modal) {
                    closeModal();
                }
            }

            // Búsqueda en tiempo real
            document.getElementById('searchInput').addEventListener('input', searchProducts);



        </script>
    </body>
</html>