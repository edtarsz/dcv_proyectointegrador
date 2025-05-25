<%@ page import="java.util.List" %>
<%@ page import="java.util.Map" %>
<%@ page import="com.mycompany.dcventidades.Insumo" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Inventario</title>
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
            <button class="btn btn-secondary" onclick="generateReport()">Reporte de ventas</button>
        </div>
    </div>

    <div class="table-container">
        <table class="table" id="inventoryTable">
            <thead>
            <tr>
                <th>ID del producto</th>
                <th>Nombre</th>
                <th>Descripción</th>
                <th>Cantidad</th>
                <th>Unidad Medida</th>
                <th>Precio Unitario</th>
                <th>Acciones</th>
                <th>Selec.</th>
            </tr>
            </thead>
            <tbody id="inventoryBody">
            <% List<Insumo> insumos = (List<Insumo>) request.getAttribute("insumos");
               Map<Long, Double> precios = (Map<Long, Double>) request.getAttribute("precios");
               Map<Long, String> motivos = (Map<Long, String>) request.getAttribute("motivos");
               if (insumos != null && !insumos.isEmpty()) {
                   for (Insumo ins : insumos) { %>
                <tr>
                    <td><%= ins.getId() %></td>
                    <td><%= ins.getNombre() %></td>
                    <td><%= ins.getDescripcion() %></td>
                    <td><%= ins.getStock() %></td>
                    <td><%= ins.getUnidadMedida() %></td>
                    <td><%= precios.getOrDefault(ins.getId(), 0.0) %></td>
                    <td class="action-cell">
                        <button type="button" class="btn btn-secondary btn-sm"
                                onclick="editarInsumo(
        '<%= ins.getId()%>',
        '<%= ins.getNombre().replace("'", "\\'")%>',
        '<%= ins.getDescripcion().replace("'", "\\'")%>',
        '<%= ins.getStock()%>',
        '<%= ins.getUnidadMedida().replace("'", "\\'")%>',
        '<%= precios.getOrDefault(ins.getId(), 0.0)%>',
        '<%= motivos.getOrDefault(ins.getId(), "")%>'
    )">
                            Editar
                        </button>

                        <form action="SVInventario" method="post" style="display:inline;" onsubmit="return confirm('¿Eliminar este insumo?');">
                            <input type="hidden" name="accion" value="eliminar">
                            <input type="hidden" name="id" value="<%= ins.getId() %>">
                            <button type="submit" class="btn btn-danger btn-sm">Eliminar</button>
                        </form>
                    </td>
                    <td><input type="checkbox" name="seleccionado" value="<%= ins.getId() %>" class="checkbox"></td>
                </tr>
            <% }} else { %>
                <tr><td colspan="8" style="text-align:center;">No hay insumos registrados</td></tr>
            <% } %>
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
        <form id="productForm" action="SVInventario" method="post">
            <input type="hidden" id="accion" name="accion" value="crear">
            <input type="hidden" id="insumoId" name="id">
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
                <input type="number" class="form-input" name="stock" id="stock" required min="1">
            </div>

            <div class="form-group">
                <label class="form-label" for="unidadMedida">Unidad Medida</label>
                <input type="text" class="form-input" name="unidadMedida" id="unidadMedida" required>
            </div>

            <div class="form-group">
                <label class="form-label" for="precioUnitario">Precio Unitario</label>
                <input type="number" class="form-input" name="precioUnitario" id="precioUnitario" required>
            </div>

            <div class="form-group">
                <label class="form-label" for="motivo">Motivo</label>
                <input type="text" class="form-input" name="motivo" id="motivo">
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

          function editarInsumo(id, nombre, descripcion, stock, unidadMedida, precioUnitario, motivo) {
    document.getElementById("productModal").style.display = 'block';

    setTimeout(() => {
        document.getElementById("accion").value = "editar";
        document.getElementById("insumoId").value = id; // ? corregido
        document.getElementById("nombre").value = nombre;
        document.getElementById("descripcion").value = descripcion;
        document.getElementById("stock").value = stock;
        document.getElementById("unidadMedida").value = unidadMedida;

        const precioInput = document.getElementById("precioUnitario");
        if (precioInput) precioInput.value = precioUnitario;

        const motivoInput = document.getElementById("motivo");
        if (motivoInput) motivoInput.value = motivo;
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