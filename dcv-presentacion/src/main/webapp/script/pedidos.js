// Archivo: pedidos.js
document.addEventListener('DOMContentLoaded', function() {
    console.log('Inicializando scripts de pedidos...');
    
    // Manejar clics en los items de pedido
    setupPedidoItemsListeners();
    
    // Manejar cambios de estado de pedido
    setupEstadoDropdowns();
    
    // Configurar búsqueda
    setupSearch();
    
    // Configurar filtros
    setupFiltros();
    
    // Configurar modal de edición
    setupEditModal();
    
    // Manejar errores de HTTP
    checkForHttpErrors();
});

// Función para configurar los listeners de los items de pedido
function setupPedidoItemsListeners() {
    const pedidoItems = document.querySelectorAll('.pedido-item');
    
    pedidoItems.forEach(item => {
        item.addEventListener('click', function() {
            const id = this.getAttribute('data-id');
            if (!id) {
                console.error('ID de pedido no encontrado');
                return;
            }
            
            // Eliminar selección actual
            document.querySelectorAll('.pedido-item.selected').forEach(el => {
                el.classList.remove('selected');
            });
            
            // Añadir selección al item actual
            this.classList.add('selected');
            
            // Cargar detalles del pedido
            cargarDetallesPedido(id);
        });
    });
}

// Función para cargar los detalles de un pedido
function cargarDetallesPedido(id) {
    console.log(`Cargando detalles del pedido ID: ${id}`);
    
    fetch(`SVPedidos?action=getDetalle&id=${id}`, {
        method: 'GET',
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json'
        },
    })
    .then(response => {
        if (!response.ok) {
            throw new Error(`Error HTTP: ${response.status}`);
        }
        return response.json();
    })
    .then(data => {
        if (data.success) {
            mostrarDetallesPedido(data.venta);
        } else {
            mostrarError(`Error al cargar detalles: ${data.message}`);
        }
    })
    .catch(error => {
        console.error('Error al cargar detalles:', error);
        mostrarError(`Error de conexión: ${error.message}`);
    });
}

// Función para mostrar los detalles del pedido en la interfaz
function mostrarDetallesPedido(venta) {
    const detallesContainer = document.querySelector('.pedidos-derecha');
    
    // Mostrar información del cliente y fecha
    let html = `
        <h2 class="detalles-title">DETALLES</h2>
        <div class="detalles-header">
            <div class="detalles-cliente">${venta.cliente.nombreCompleto}</div>
            <div class="detalles-fecha">${formatearFecha(venta.fecha)}</div>
        </div>
        <div class="detalles-items">
    `;
    
    // Mostrar detalles de los productos
    venta.detallesVenta.forEach(detalle => {
        html += `
            <div class="detalle-item">
                <div class="item-info">
                    <div class="item-title">${detalle.producto.nombre}</div>
                    <div class="item-descripcion">Editar Pedido: ${detalle.personalizacion}</div>
                    <div class="item-cantidad">Cantidad: ${detalle.cantidad}</div>
                </div>
                <div class="item-acciones">
                    <div class="item-precio">$${detalle.subtotal}</div>
                    <button class="item-editar" data-id="${detalle.id}">
                        <img src="svg/edit.svg" alt="Editar">
                    </button>
                </div>
            </div>
        `;
    });
    
    // Mostrar total
    html += `
        </div>
        <div class="total-container">
            <div class="total-label">TOTAL</div>
            <div class="total-valor">$${venta.total}</div>
        </div>
    `;
    
    detallesContainer.innerHTML = html;
    
    // Configurar botones de edición
    setupEditButtons();
}

// Formatear fecha para mostrar
function formatearFecha(fechaStr) {
    const fecha = new Date(fechaStr);
    return `${fecha.getDate().toString().padStart(2, '0')}/${(fecha.getMonth() + 1).toString().padStart(2, '0')}/${fecha.getFullYear()}`;
}

// Configurar los dropdowns de estado
function setupEstadoDropdowns() {
    document.querySelectorAll('.pedido-estado').forEach(estadoEl => {
        estadoEl.addEventListener('click', function(e) {
            e.stopPropagation(); // Evitar que se propague al item del pedido
            
            const pedidoId = this.closest('.pedido-item').getAttribute('data-id');
            const estadoActual = this.querySelector('span').textContent.trim();
            
            // Crear y mostrar dropdown
            const dropdown = document.createElement('div');
            dropdown.className = 'estado-dropdown';
            
            const estados = ['En proceso', 'Completado', 'Esperando'];
            estados.forEach(estado => {
                if (estado !== estadoActual) {
                    const option = document.createElement('div');
                    option.className = 'estado-option';
                    option.textContent = estado;
                    option.addEventListener('click', function() {
                        actualizarEstadoPedido(pedidoId, estado);
                    });
                    dropdown.appendChild(option);
                }
            });
            
            // Limpiar dropdowns existentes
            document.querySelectorAll('.estado-dropdown').forEach(el => el.remove());
            
            // Añadir nuevo dropdown
            this.appendChild(dropdown);
            
            // Click fuera para cerrar
            document.addEventListener('click', function closeDropdown(event) {
                if (!event.target.closest('.estado-dropdown') && 
                    !event.target.closest('.pedido-estado')) {
                    dropdown.remove();
                    document.removeEventListener('click', closeDropdown);
                }
            });
        });
    });
}

// Función para actualizar el estado de un pedido
function actualizarEstadoPedido(ventaId, nuevoEstado) {
    console.log(`Actualizando estado de pedido ${ventaId} a "${nuevoEstado}"`);
    
    // Crear formData para enviar los datos
    const formData = new FormData();
    formData.append('action', 'actualizarEstado');
    formData.append('ventaId', ventaId);
    formData.append('estado', nuevoEstado);
    
    fetch('SVPedidos', {
        method: 'POST',
        body: formData
    })
    .then(response => {
        if (!response.ok) {
            throw new Error(`Error HTTP: ${response.status}`);
        }
        return response.json();
    })
    .then(data => {
        if (data.success) {
            // Actualizar UI
            const estadoEl = document.querySelector(`.pedido-item[data-id="${ventaId}"] .pedido-estado`);
            estadoEl.querySelector('span').textContent = nuevoEstado;
            
            // Actualizar clase CSS
            estadoEl.className = `pedido-estado ${nuevoEstado.toLowerCase().replace(/\s+/g, '-')}`;
            
            // Eliminar dropdown
            const dropdown = estadoEl.querySelector('.estado-dropdown');
            if (dropdown) dropdown.remove();
            
            console.log('Estado actualizado correctamente');
        } else {
            mostrarError(`Error al actualizar estado: ${data.message}`);
        }
    })
    .catch(error => {
        console.error('Error al actualizar estado:', error);
        mostrarError(`Error de conexión: ${error.message}`);
    });
}

// Configurar búsqueda
function setupSearch() {
    const searchBtn = document.getElementById('btnBuscar');
    const searchInput = document.getElementById('search-bar');
    
    if (searchBtn && searchInput) {
        searchBtn.addEventListener('click', function() {
            buscarPedidos(searchInput.value.trim());
        });
        
        searchInput.addEventListener('keypress', function(e) {
            if (e.key === 'Enter') {
                buscarPedidos(this.value.trim());
            }
        });
    }
}

// Función para buscar pedidos
function buscarPedidos(termino) {
    console.log(`Buscando pedidos con término: "${termino}"`);
    
    if (!termino) {
        // Si no hay término, recargar todos los pedidos
        window.location.href = 'SVPedidos';
        return;
    }
    
    fetch(`SVPedidos?action=buscar&termino=${encodeURIComponent(termino)}`)
    .then(response => {
        if (!response.ok) {
            throw new Error(`Error HTTP: ${response.status}`);
        }
        return response.json();
    })
    .then(data => {
        if (data.success) {
            actualizarListaPedidos(data.ventas);
        } else {
            mostrarError(`Error en la búsqueda: ${data.message}`);
        }
    })
    .catch(error => {
        console.error('Error al buscar:', error);
        mostrarError(`Error de conexión: ${error.message}`);
    });
}

// Configurar filtros
function setupFiltros() {
    document.querySelectorAll('.filtro').forEach(filtro => {
        filtro.addEventListener('click', function() {
            const tipoFiltro = this.getAttribute('data-filtro');
            aplicarFiltro(tipoFiltro);
        });
    });
}

// Función para aplicar filtros
function aplicarFiltro(tipo) {
    console.log(`Aplicando filtro: ${tipo}`);
    
    fetch(`SVPedidos?action=filtrar&tipo=${encodeURIComponent(tipo)}`)
    .then(response => {
        if (!response.ok) {
            throw new Error(`Error HTTP: ${response.status}`);
        }
        return response.json();
    })
    .then(data => {
        if (data.success) {
            actualizarListaPedidos(data.ventas);
        } else {
            mostrarError(`Error al filtrar: ${data.message}`);
        }
    })
    .catch(error => {
        console.error('Error al filtrar:', error);
        mostrarError(`Error de conexión: ${error.message}`);
    });
}

// Actualizar lista de pedidos con datos recibidos
function actualizarListaPedidos(ventas) {
    const pedidosLista = document.querySelector('.pedidos-lista');
    
    if (ventas.length === 0) {
        pedidosLista.innerHTML = `
            <div class="no-pedidos">
                <p>No hay pedidos disponibles</p>
            </div>
        `;
        
        // Limpiar detalles
        document.querySelector('.pedidos-derecha').innerHTML = `
            <h2 class="detalles-title">DETALLES</h2>
            <div class="no-detalles">
                <p>Seleccione un pedido para ver sus detalles</p>
            </div>
        `;
        
        return;
    }
    
    let html = '';
    ventas.forEach(venta => {
        html += `
            <div class="pedido-item" data-id="${venta.id}">
                <div class="pedido-numero">#${venta.id}</div>
                <div class="pedido-cliente">${venta.cliente.nombreCompleto}</div>
                <div class="pedido-fecha">${formatearFecha(venta.fecha)}</div>
                <div class="pedido-estado ${venta.estado.toLowerCase().replace(/\s+/g, '-')}">
                    <span>${venta.estado}</span>
                    <img src="svg/arrow-down.svg" alt="Desplegable">
                </div>
            </div>
        `;
    });
    
    pedidosLista.innerHTML = html;
    
    // Reconfigurar listeners
    setupPedidoItemsListeners();
    setupEstadoDropdowns();
    
    // Seleccionar el primer pedido
    const primerPedido = document.querySelector('.pedido-item');
    if (primerPedido) {
        primerPedido.classList.add('selected');
        cargarDetallesPedido(primerPedido.getAttribute('data-id'));
    }
}

// Configurar modal de edición
function setupEditModal() {
    const modal = document.getElementById('editarModal');
    const closeButton = modal.querySelector('.close');
    const cancelButton = document.getElementById('cancelarEdicion');
    const form = document.getElementById('editarPersonalizacionForm');
    
    // Cerrar modal
    closeButton.addEventListener('click', function() {
        modal.style.display = 'none';
    });
    
    cancelButton.addEventListener('click', function() {
        modal.style.display = 'none';
    });
    
    // Cerrar al hacer clic fuera del modal
    window.addEventListener('click', function(event) {
        if (event.target === modal) {
            modal.style.display = 'none';
        }
    });
    
    // Manejar envío del formulario
    form.addEventListener('submit', function(e) {
        e.preventDefault();
        
        const detalleId = document.getElementById('editDetalleId').value;
        const personalizacion = document.getElementById('editPersonalizacion').value;
        
        guardarPersonalizacion(detalleId, personalizacion);
    });
}

// Configurar botones de edición
function setupEditButtons() {
    document.querySelectorAll('.item-editar').forEach(button => {
        button.addEventListener('click', function() {
            const detalleId = this.getAttribute('data-id');
            const personalizacion = this.closest('.detalle-item').querySelector('.item-descripcion').textContent.replace('Editar Pedido: ', '');
            
            // Llenar el formulario
            document.getElementById('editDetalleId').value = detalleId;
            document.getElementById('editPersonalizacion').value = personalizacion;
            
            // Mostrar modal
            document.getElementById('editarModal').style.display = 'block';
        });
    });
}

// Guardar cambios en personalizacion
function guardarPersonalizacion(detalleId, personalizacion) {
    console.log(`Guardando personalización para detalle ID: ${detalleId}`);
    
    const formData = new FormData();
    formData.append('action', 'editarPersonalizacion');
    formData.append('idDetalle', detalleId);
    formData.append('personalizacion', personalizacion);
    
    fetch('SVPedidos', {
        method: 'POST',
        body: formData
    })
    .then(response => {
        if (!response.ok) {
            throw new Error(`Error HTTP: ${response.status}`);
        }
        return response.json();
    })
    .then(data => {
        if (data.success) {
            // Ocultar modal
            document.getElementById('editarModal').style.display = 'none';
            
            // Actualizar UI
            const detalleItem = document.querySelector(`.item-editar[data-id="${detalleId}"]`).closest('.detalle-item');
            detalleItem.querySelector('.item-descripcion').textContent = `Editar Pedido: ${personalizacion}`;
            
            console.log('Personalización actualizada correctamente');
        } else {
            mostrarError(`Error al guardar: ${data.message}`);
        }
    })
    .catch(error => {
        console.error('Error al guardar personalización:', error);
        mostrarError(`Error de conexión: ${error.message}`);
    });
}

// Mostrar mensaje de error
function mostrarError(mensaje) {
    const errorContainer = document.getElementById('mensajes-error');
    errorContainer.textContent = mensaje;
    errorContainer.style.display = 'block';
    
    // Ocultar después de 5 segundos
    setTimeout(() => {
        errorContainer.style.display = 'none';
    }, 5000);
}

// Revisar si hay errores HTTP en la página
function checkForHttpErrors() {
    const errorMessages = document.querySelectorAll('.error-message, [class*="error"]');
    errorMessages.forEach(el => {
        const text = el.textContent.trim();
        if (text.includes('HTTP 400') || text.includes('Error HTTP')) {
            console.error('Detectado error HTTP en la página:', text);
            mostrarError('Se ha detectado un error en la página. Intente recargar o contacte al administrador.');
        }
    });
}