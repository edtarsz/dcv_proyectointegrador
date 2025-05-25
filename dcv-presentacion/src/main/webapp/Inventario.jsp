<%-- 
    Document   : Inventario
    Created on : 24 may 2025, 2:09:17?a.m.
    Author     : esmeraldamolinaestrada
--%>
<%@ page import="java.util.List" %>
<%@ page import="java.util.Map" %>
<%@ page import="com.mycompany.dcventidades.Insumo" %>
<!DOCTYPE html>
<html lang="es">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Inventario</title>
        <link rel="preconnect" href="https://fonts.googleapis.com">
        <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
        <link href="https://fonts.googleapis.com/css2?family=Ubuntu:ital,wght@0,300;0,400;0,500;0,700;1,300;1,400;1,500;1,700&display=swap" rel="stylesheet">

        <%-- Estilos --%>
        <link rel="stylesheet" href="style/style.css">
        <link rel="stylesheet" href="style/header.css">
        <link rel="stylesheet" href="style/catalogo.css">
        <link rel="stylesheet" href="style/inventario.css">


        <%-- Fonts --%>
        <link rel="preconnect" href="https://fonts.googleapis.com">
        <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
        <link href="https://fonts.googleapis.com/css2?family=Ubuntu:ital,wght@0,300;0,400;0,500;0,700;1,300;1,400;1,500;1,700&display=swap" rel="stylesheet">

        <%-- Cart Script --%>
        <script defer src="script/carrito.js"></script>


    </head>
    <body>
        <%@ include file="/partials/header.jspf" %>

        <!-- Main Content -->
        <main class="main-content">
            <div class="page-header">
                <div class="search-container">
                    <input type="text" class="search-input" placeholder="Buscar producto" id="searchInput">
                    <button class="search-button" onclick="searchProducts()"></button>
                </div>
                <div class="action-buttons">
                    <button class="btn btn-primary" onclick="openAddModal()">Agregar</button>
                    <button class="btn btn-secondary" onclick="generateReport()">Reporte de ventas</button>
                </div>
            </div>

            <div class="table-container">
                <table class="table" id="inventoryTable">
                    <thead>
                        <tr>
                            <th onclick="sortTable(0)">ID del producto <span class="sort-icon"></span></th>
                            <th onclick="sortTable(1)">Nombre <span class="sort-icon"></span></th>
                            <th onclick="sortTable(2)">Descripción <span class="sort-icon"></span></th>
                            <th onclick="sortTable(3)">Cantidad <span class="sort-icon"></span></th>
                            <th onclick="sortTable(4)">Unidad Medida<span class="sort-icon"></span></th>
                            <th onclick="sortTable(5)">Precio Unitario <span class="sort-icon"></span></th>
                            <th>Acciones</th>
                            <th>Selec.</th>
                        </tr>
                    </thead>
                    <%
                        List<Insumo> insumos = (List<Insumo>) request.getAttribute("insumos");
                        Map<Long, Double> precios = (Map<Long, Double>) request.getAttribute("precios");
                    %>

                    <tbody id="inventoryBody">
                        <% if (insumos != null && !insumos.isEmpty()) {
                                for (Insumo ins : insumos) {%>
                        <tr>
                            <td><%= ins.getId()%></td>
                            <td><%= ins.getNombre()%></td>
                            <td><%= ins.getDescripcion()%></td>
                            <td><%= ins.getStock()%></td> 
                            <td><%= ins.getUnidadMedida()%></td>
                            <td>
                                <%= precios.getOrDefault(ins.getId(), 0.0)%>
                            </td>
                            <td class="action-cell">
                                <button type="button" class="btn btn-secondary btn-sm"
                                        onclick="editarInsumo('<%= ins.getId()%>', '<%= ins.getNombre()%>', '<%= ins.getDescripcion()%>', '<%= ins.getStock()%>', '<%= ins.getUnidadMedida()%>')">
                                    Editar
                                </button>

                                <form action="SVInventario" method="post" style="display:inline;" onsubmit="return confirm('¿Eliminar este insumo?');">
                                    <input type="hidden" name="accion" value="eliminar">
                                    <input type="hidden" name="id" value="<%= ins.getId()%>">
                                    <button type="submit" class="btn btn-danger btn-sm">Eliminar</button>
                                </form>
                            </td>

                            <td>
                                <input type="checkbox" name="seleccionado" value="<%= ins.getId()%>" class="checkbox">
                            </td>
                        </tr>
                        <% }
                        } else { %>
                        <tr><td colspan="8" style="text-align:center;">No hay insumos registrados</td></tr>
                        <% } %>
                    </tbody>

                </table>
            </div>

            <div class="bottom-actions">
                <div class="selected-info">
                    <span id="selectedCount">0</span> productos seleccionados
                </div>
                <div class="action-buttons">
                    <button class="btn btn-secondary" onclick="editSelected()">Editar</button>
                    <button class="btn btn-danger" onclick="deleteSelected()">Eliminar</button>
                </div>
            </div>
        </main>



        <!-- Modal para Agregar/Editar -->
        <div class="modal" id="productModal">
            <div class="modal-content">
                <div class="modal-header">
                    <h2 class="modal-title" id="modalTitle">Agregar Producto</h2>
                    <button class="close-btn" onclick="closeModal()">&times;</button>
                </div>

               

                <form id="productForm" action="SVInventario" method="post">
                    <input type="hidden" name="accion" value="crear">
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
                        <label class="form-label" for="stock">Cantidad</label>
                        <input type="number" class="form-input" name="stock" required min="1">
                    </div>

                    <div class="form-group">
                        <label class="form-label" for="unidadMedida">Unidad Medida</label>
                        <input type="text" class="form-input" name="unidadMedida" required min="1">
                    </div>

                    <div class="form-group">
                        <label class="form-label" for="precioUnitario">Precio Unitario</label>
                        <input type="number" class="form-input" name="precioUnitario" required step="0.01">
                    </div>

                    <div class="form-group">
                        <label class="form-label" for="motivo">Motivo</label>
                        <input type="text" class="form-input" name="motivo">
                    </div>
                </form>

                <div class="modal-footer">
                    <button class="btn btn-secondary" onclick="closeModal()">Cancelar</button>
                    <button class="btn btn-primary" onclick="document.getElementById('productForm').submit()">Guardar</button>

                </div>
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

            function editarInsumo(id, nombre, descripcion, stock, unidadMedida) {
    document.getElementById('modalTitle').textContent = 'Editar Producto';

    document.querySelector("input[name='accion']").value = "editar";
    document.getElementById("insumoId").value = id;
    document.getElementById("nombre").value = nombre;
    document.getElementById("descripcion").value = descripcion;
    document.getElementById("stock").value = stock;
    document.getElementById("unidadMedida").value = unidadMedida;
    document.getElementById("precioUnitario").value = precioUnitario;
    document.getElementById("motivo").value = motivo;

    // Si tienes otros campos (como motivo o precio), los puedes limpiar aquí o esconder

    document.getElementById("productModal").style.display = 'block';
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

            function updateSelectedCount() {
                document.getElementById('selectedCount').textContent = selectedProducts.size;
            }

            function editSelected() {
                if (selectedProducts.size === 1) {
                    const index = Array.from(selectedProducts)[0];
                    editProduct(index);
                } else if (selectedProducts.size === 0) {
                    alert('Por favor selecciona un producto para editar');
                } else {
                    alert('Por favor selecciona solo un producto para editar');
                }
            }

            function deleteSelected() {
                if (selectedProducts.size === 0) {
                    alert('Por favor selecciona al menos un producto para eliminar');
                    return;
                }

                if (confirm(`¿Estás seguro de que quieres eliminar ${selectedProducts.size} producto(s)?`)) {
                    const indices = Array.from(selectedProducts).sort((a, b) => b - a);
                    indices.forEach(index => {
                        products.splice(index, 1);
                    });
                    selectedProducts.clear();
                    loadProducts();
                }
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