// categorias.js

// Variable global para almacenar las categorías cargadas del servidor
let categories = [];
// Variables para manejar el estado de edición y eliminación
let currentEditingId = null;
let deletingId = null;

// Función principal para renderizar las tarjetas de categorías en el HTML
function renderCategories(categoriesToRender = categories) {
    const grid = document.getElementById('categoriesGrid');
    if (!grid) {
        console.error('Elemento categoriesGrid no encontrado.');
        return;
    }
    grid.innerHTML = ''; // Limpiar el grid antes de renderizar

    if (!categoriesToRender || categoriesToRender.length === 0) {
        grid.innerHTML = '<p>No hay categorías para mostrar.</p>';
        return;
    }

    categoriesToRender.forEach(category => {
        const categoryCard = document.createElement('div');
        categoryCard.className = 'category-card';
        categoryCard.innerHTML = `
            <div class="category-info">
                <h3>${escapeHTML(category.nombre)}</h3>
                <p>${escapeHTML(category.descripcion) || 'Sin descripción'}</p>
            </div>
            <div class="category-actions">
                <button class="action-btn view-btn" onclick="viewProducts(${category.id})">Ver Productos</button>
                <button class="action-btn edit-btn" onclick="openEditModal(${category.id})">Editar</button>
                <button class="action-btn delete-btn" onclick="openConfirmDeleteModal(${category.id})">Eliminar</button>
            </div>
        `;
        grid.appendChild(categoryCard);
    });
}

// Función para escapar HTML y prevenir XSS simple
function escapeHTML(str) {
    if (str === null || str === undefined) return '';
    return String(str).replace(/[&<>"']/g, function (match) {
        return {
            '&': '&amp;',
            '<': '&lt;',
            '>': '&gt;',
            '"': '&quot;',
            "'": '&#39;'
        }[match];
    });
}


// --- Interacción con el Servidor (Backend) ---

// Cargar categorías desde el servidor al iniciar
function loadCategoriesFromServer() {
    fetch('SVCategorias?action=getAll')
        .then(response => {
            if (!response.ok) throw new Error(`Error HTTP: ${response.status} ${response.statusText}`);
            return response.json();
        })
        .then(data => {
            if (data.success) {
                categories = data.categorias; // Asumiendo que el servlet devuelve { success: true, categorias: [...] }
            } else {
                categories = []; // En caso de error o si no hay categorías
                console.warn(data.message || 'No se recibieron categorías del servidor.');
            }
            renderCategories();
        })
        .catch(error => {
            console.error('Error al cargar categorías:', error);
            alert(`Error al cargar categorías: ${error.message}. Mostrando datos de prueba.`);
            // Opcional: Cargar datos de prueba solo si falla el fetch para desarrollo
            // categories = [ /* tus datos de prueba aquí */ ];
            renderCategories(); // Renderiza vacío o con datos de prueba
        });
}

// Guardar (Agregar o Editar) categoría
function saveCategory(event) {
    event.preventDefault(); // Prevenir envío de formulario tradicional

    const nameInput = document.getElementById('categoryName');
    const descriptionInput = document.getElementById('categoryDescription');

    const name = nameInput.value.trim();
    const description = descriptionInput.value.trim();

    if (!name) {
        alert('El nombre de la categoría es obligatorio.');
        nameInput.focus();
        return;
    }

    const categoryData = {
        nombre: name,
        descripcion: description
    };

    let url = 'SVCategorias';
    let action = '';

    if (currentEditingId) {
        action = 'edit';
        categoryData.id = currentEditingId; // Enviar el ID para la edición
    } else {
        action = 'add';
    }
    url += `?action=${action}`;

    fetch(url, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/x-www-form-urlencoded',
        },
        body: new URLSearchParams(categoryData).toString()
    })
    .then(response => {
        if (!response.ok) throw new Error(`Error HTTP: ${response.status} ${response.statusText}`);
        return response.json();
    })
    .then(data => {
        if (data.success) {
            alert(currentEditingId ? 'Categoría actualizada exitosamente!' : 'Categoría agregada exitosamente!');
            closeModal();
            loadCategoriesFromServer(); // Recargar la lista de categorías
        } else {
            alert('Error: ' + (data.message || 'No se pudo guardar la categoría. Verifique si el nombre ya existe.'));
        }
    })
    .catch(error => {
        console.error('Error al guardar categoría:', error);
        alert(`Error de conexión al guardar categoría: ${error.message}`);
    });
}

// Confirmar eliminación de categoría
function confirmDelete() {
    if (deletingId === null) return;

    fetch('SVCategorias?action=delete', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/x-www-form-urlencoded',
        },
        body: new URLSearchParams({ id: deletingId }).toString()
    })
    .then(response => {
        if (!response.ok) throw new Error(`Error HTTP: ${response.status} ${response.statusText}`);
        return response.json();
    })
    .then(data => {
        if (data.success) {
            alert('Categoría eliminada exitosamente!');
            loadCategoriesFromServer(); // Recargar la lista
        } else {
            alert('Error al eliminar categoría: ' + (data.message || 'Verifique que no tenga productos asociados.'));
        }
    })
    .catch(error => {
        console.error('Error al eliminar categoría:', error);
        alert(`Error de conexión al eliminar categoría: ${error.message}`);
    })
    .finally(() => {
        closeConfirmModal();
        deletingId = null;
    });
}

// Ver productos (ejemplo básico, necesitarás implementar cómo mostrar estos productos)
function viewProducts(idCategoria) {
    // Idealmente, esto redirigiría a una nueva página o cargaría productos en un modal/sección.
    // window.location.href = `productos.jsp?idCategoria=${idCategoria}`;
    // O usar fetch para cargarlos dinámicamente:
    fetch(`SVCategorias?action=getProductosPorCategoria&idCategoria=${idCategoria}`)
        .then(response => {
            if (!response.ok) throw new Error(`HTTP error! status: ${response.status}`);
            return response.json();
        })
        .then(data => {
            if (data.success && data.productos) {
                if (data.productos.length > 0) {
                    let productNames = data.productos.map(p => p.nombre).join('\n - ');
                    alert(`Productos en la categoría ID ${idCategoria}:\n - ${productNames}`);
                } else {
                    alert(`No hay productos en la categoría ID ${idCategoria}.`);
                }
            } else {
                alert(data.message || `No se pudieron cargar los productos para la categoría ID ${idCategoria}.`);
            }
        })
        .catch(error => {
            console.error('Error al ver productos:', error);
            alert(`Error al cargar productos de la categoría: ${error.message}`);
        });
}


// --- Manejo de Modales y UI ---

function openAddModal() {
    document.getElementById('modalTitle').textContent = 'Agregar Nueva Categoría';
    const form = document.getElementById('categoryForm');
    if(form) form.reset();
    currentEditingId = null;
    const modal = document.getElementById('categoryModal');
    if(modal) modal.style.display = 'block';
}

function openEditModal(id) {
    const category = categories.find(c => c.id === id);
    if (category) {
        document.getElementById('modalTitle').textContent = 'Editar Categoría';
        const nameInput = document.getElementById('categoryName');
        const descriptionInput = document.getElementById('categoryDescription');
        const form = document.getElementById('categoryForm');

        if(nameInput) nameInput.value = category.nombre;
        if(descriptionInput) descriptionInput.value = category.descripcion;
        
        currentEditingId = id;
        const modal = document.getElementById('categoryModal');
        if(modal) modal.style.display = 'block';
    } else {
        console.error(`Categoría con ID ${id} no encontrada para editar.`);
    }
}

function openConfirmDeleteModal(id) {
    deletingId = id;
    const modal = document.getElementById('confirmModal');
    if(modal) modal.style.display = 'block';
}

function closeModal() {
    const modal = document.getElementById('categoryModal');
    if(modal) modal.style.display = 'none';
    currentEditingId = null; // Asegurarse de resetear el ID de edición
}

function closeConfirmModal() {
    const modal = document.getElementById('confirmModal');
    if(modal) modal.style.display = 'none';
    deletingId = null; // Asegurarse de resetear el ID de eliminación
}


// --- Event Listeners ---

// Búsqueda de categorías
document.addEventListener('DOMContentLoaded', () => {
    const searchInput = document.getElementById('searchInput');
    if (searchInput) {
        searchInput.addEventListener('input', function (e) {
            const searchTerm = e.target.value.toLowerCase();
            if (!categories) return; // Asegurarse que categories está definido
            const filteredCategories = categories.filter(category =>
                (category.nombre && category.nombre.toLowerCase().includes(searchTerm)) ||
                (category.descripcion && category.descripcion.toLowerCase().includes(searchTerm))
            );
            renderCategories(filteredCategories);
        });
    }

    // Listener para el formulario de categoría
    const categoryForm = document.getElementById('categoryForm');
    if (categoryForm) {
        categoryForm.addEventListener('submit', saveCategory);
    }

    // Listener para cerrar modales al hacer clic fuera
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

    // Cargar categorías iniciales
    loadCategoriesFromServer();
});