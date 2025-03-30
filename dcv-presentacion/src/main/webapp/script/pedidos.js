document.addEventListener('DOMContentLoaded', function() {
    initializePedidos();
});

function initializePedidos() {
    // Configurar eventos de pedidos
    document.querySelectorAll('.pedido-item').forEach(item => {
        item.addEventListener('click', function() {
            const id = this.dataset.id;
            mostrarDetalle(id);
        });
    });

    // Configurar búsqueda con debounce
    const searchInput = document.getElementById('search-bar');
    if (searchInput) {
        let timeoutId;
        searchInput.addEventListener('input', () => {
            clearTimeout(timeoutId);
            timeoutId = setTimeout(() => {
                const searchTerm = searchInput.value.trim();
                if (searchTerm) {
                    buscarPedidos(searchTerm);
                }
            }, 300);
        });
    }

    // Configurar botón de búsqueda
    const btnBuscar = document.getElementById('btnBuscar');
    if (btnBuscar) {
        btnBuscar.addEventListener('click', () => {
            const searchTerm = document.getElementById('search-bar').value.trim();
            if (searchTerm) {
                buscarPedidos(searchTerm);
            }
        });
    }

    // Configurar filtros
    document.querySelectorAll('.filtro').forEach(filtro => {
        filtro.addEventListener('click', function() {
            const tipoFiltro = this.dataset.filtro;
            aplicarFiltro(tipoFiltro);
        });
    });
}

function mostrarDetalle(id) {
    // Actualizar selección visual
    document.querySelectorAll('.pedido-item').forEach(pedido => {
        pedido.classList.remove('selected');
    });
    const selectedItem = document.querySelector(`[data-id="${id}"]`);
    if (selectedItem) {
        selectedItem.classList.add('selected');
    }

    // Cargar detalles
    const params = new URLSearchParams();
    params.append('action', 'getDetalle');
    params.append('id', id);

    fetch('SVPedidos?' + params.toString())
        .then(response => {
            if (!response.ok) throw new Error('Error en la respuesta del servidor');
            return response.json();
        })
        .then(data => {
            if (data.success) {
                actualizarDetallesPedido(data.venta);
            } else {
                throw new Error(data.message || 'Error al cargar los detalles');
            }
        })
        .catch(error => {
            console.error('Error:', error);
            mostrarError(error.message);
        });
}

function actualizarDetallesPedido(venta) {
    const detallesContainer = document.querySelector('.pedidos-derecha');
    if (!detallesContainer) return;

    // Limpiar contenido anterior
    const detallesContent = `
        <h2 class="detalles-title">DETALLES</h2>
        <div class="detalles-header">
            <div class="detalles-cliente">${venta.cliente.nombreCompleto}</div>
            <div class="detalles-fecha">${new Date(venta.fecha).toLocaleDateString()}</div>
        </div>
        <div class="detalles-items">
            ${venta.detallesVenta.map(detalle => `
                <div class="detalle-item">
                    <div class="item-info">
                        <div class="item-title">${detalle.producto.nombre}</div>
                        <div class="item-descripcion">${detalle.personalizacion}</div>
                    </div>
                    <div class="item-acciones">
                        <div class="item-precio">$${Number(detalle.subtotal).toFixed(2)}</div>
                        <button class="item-editar" data-id="${detalle.id}">
                            <img src="svg/edit.svg" alt="Editar">
                        </button>
                    </div>
                </div>
            `).join('')}
        </div>
        <div class="total-container">
            <div class="total-label">TOTAL</div>
            <div class="total-valor">$${Number(venta.total).toFixed(2)}</div>
        </div>
    `;

    detallesContainer.innerHTML = detallesContent;

    // Configurar eventos de edición
    detallesContainer.querySelectorAll('.item-editar').forEach(btn => {
        btn.addEventListener('click', function() {
            editarDetalle(this.dataset.id);
        });
    });
}

function buscarPedidos(termino) {
    const params = new URLSearchParams();
    params.append('action', 'buscar');
    params.append('termino', termino);

    fetch('SVPedidos?' + params.toString())
        .then(response => {
            if (!response.ok) throw new Error('Error en la respuesta del servidor');
            return response.json();
        })
        .then(data => {
            if (data.success) {
                actualizarListaPedidos(data.ventas);
            } else {
                throw new Error(data.message || 'Error en la búsqueda');
            }
        })
        .catch(error => {
            console.error('Error:', error);
            mostrarError(error.message);
        });
}

function actualizarListaPedidos(ventas) {
    const listaPedidos = document.querySelector('.pedidos-lista');
    if (!listaPedidos) return;

    if (ventas.length === 0) {
        listaPedidos.innerHTML = `
            <div class="no-pedidos">
                <p>No se encontraron pedidos</p>
            </div>
        `;
        return;
    }

    listaPedidos.innerHTML = ventas.map(venta => `
        <div class="pedido-item" data-id="${venta.id}">
            <div class="pedido-numero">#${venta.id}</div>
            <div class="pedido-cliente">${venta.cliente.nombreCompleto}</div>
            <div class="pedido-fecha">${new Date(venta.fecha).toLocaleDateString()}</div>
            <div class="pedido-estado ${venta.estado.toLowerCase()}">
                <span>${venta.estado}</span>
                <img src="svg/arrow-down.svg" alt="Desplegable">
            </div>
        </div>
    `).join('');

    // Reinicializar eventos de los items
    initializePedidos();
}

function aplicarFiltro(tipo) {
    const params = new URLSearchParams();
    params.append('action', 'filtrar');
    params.append('tipo', tipo);

    fetch('SVPedidos?' + params.toString())
        .then(response => {
            if (!response.ok) throw new Error('Error en la respuesta del servidor');
            return response.json();
        })
        .then(data => {
            if (data.success) {
                actualizarListaPedidos(data.ventas);
            } else {
                throw new Error(data.message || 'Error al aplicar el filtro');
            }
        })
        .catch(error => {
            console.error('Error:', error);
            mostrarError(error.message);
        });
}

function mostrarError(mensaje) {
    const errorDiv = document.getElementById('mensajes-error');
    if (errorDiv) {
        errorDiv.textContent = mensaje;
        errorDiv.style.display = 'block';
        setTimeout(() => {
            errorDiv.style.display = 'none';
        }, 3000);
    }
}

function editarDetalle(id) {
    console.log('Editando detalle:', id);
    // Implementar lógica de edición según necesidades
}