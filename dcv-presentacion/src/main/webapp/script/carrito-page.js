document.addEventListener('DOMContentLoaded', function () {
    setupCartControls();
});

function setupCartControls() {
    // Configurar controles de cantidad
    document.querySelectorAll('.quantity-control').forEach(control => {
        const decrementBtn = control.querySelector('.decrement');
        const incrementBtn = control.querySelector('.increment');
        const input = control.querySelector('.quantity-input');
        const carritoItem = control.closest('.carrito-item');

        if (decrementBtn && incrementBtn && input && carritoItem) {
            decrementBtn.addEventListener('click', () => {
                const currentValue = parseInt(input.value);
                if (currentValue > 1) {
                    updateQuantity(carritoItem, currentValue - 1);
                }
            });

            incrementBtn.addEventListener('click', () => {
                const currentValue = parseInt(input.value);
                updateQuantity(carritoItem, currentValue + 1);
            });
        }
    });

    // Configurar botones de eliminar
    document.querySelectorAll('.btn-delete').forEach(btn => {
        btn.addEventListener('click', function (e) {
            e.preventDefault();
            const carritoItem = this.closest('.carrito-item');
            if (carritoItem) {
                deleteItem(carritoItem);
            }
        });
    });

    document.querySelectorAll('.btn-edit').forEach(button => {
        button.addEventListener('click', function () {
            const id = this.getAttribute('data-id');
            let personalizacion = this.getAttribute('data-personalizacion');
            // Si contiene "Extra:", separarlo para mostrar solo la personalización principal
            if (personalizacion.includes('Extra:')) {
                personalizacion = personalizacion.split('Extra:')[0].trim();
            }

            editProductoId.value = id;
            editPersonalizacion.value = personalizacion;
            modal.style.display = 'flex';
        });
    });
    
    // Cerrar modal
    span.onclick = function () {
        modal.style.display = 'none';
    }

    cancelarBtn.onclick = function () {
        modal.style.display = 'none';
    }

// Cerrar si se hace clic fuera del modal
    window.onclick = function (event) {
        if (event.target == modal) {
            modal.style.display = 'none';
        }
    }
}

function updateQuantity(carritoItem, newQuantity) {
    const itemId = carritoItem.dataset.id;
    const datos = new URLSearchParams();
    datos.append('action', 'updateQuantity');
    datos.append('idProducto', itemId);
    datos.append('nuevaCantidad', newQuantity);

    fetch('SVCarrito', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/x-www-form-urlencoded'
        },
        body: datos
    })
            .then(response => response.json())
            .then(data => {
                if (data.success) {
                    // Actualizar la cantidad en el input
                    const input = carritoItem.querySelector('.quantity-input');
                    input.value = newQuantity;

                    // Calcular y actualizar el total del item
                    const precioUnitario = parseFloat(carritoItem.querySelector('.col-precio').textContent.replace('$', '').trim());
                    const nuevoTotalItem = (precioUnitario * newQuantity).toFixed(2);
                    carritoItem.querySelector('.col-total').textContent = `$${nuevoTotalItem}`;

                    // Actualizar el total general y contador
                    updateCartTotal();
                    updateProductCount();
                } else {
                    throw new Error(data.message || 'Error al actualizar cantidad');
                }
            })
            .catch(error => {
                console.error('Error:', error);
                showError(error.message);
            });
}

function deleteItem(carritoItem) {
    if (!confirm('¿Está seguro de eliminar este producto del carrito?')) {
        return;
    }

    const itemId = carritoItem.dataset.id;
    const datos = new URLSearchParams();
    datos.append('action', 'deleteItem');
    datos.append('idProducto', itemId);

    fetch('SVCarrito', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/x-www-form-urlencoded'
        },
        body: datos
    })
            .then(response => response.json())
            .then(data => {
                if (data.success) {
                    // Eliminar el item y actualizar totales
                    carritoItem.remove();
                    updateCartTotal();
                    updateProductCount();

                    // Si no quedan productos, recargar la página
                    const itemsRestantes = document.querySelectorAll('.carrito-item').length;
                    if (itemsRestantes === 0) {
                        window.location.reload();
                    }
                } else {
                    throw new Error(data.message || 'Error al eliminar el producto');
                }
            })
            .catch(error => {
                console.error('Error:', error);
                showError(error.message);
            });
}

function updateCartTotal() {
    try {
        // Obtener y calcular todos los totales
        const totales = Array.from(document.querySelectorAll('.col-total'))
                .map(el => {
                    const valor = parseFloat(el.textContent.replace('$', '').trim());
                    if (isNaN(valor)) {
                        console.error('Valor no numérico encontrado:', el.textContent);
                        return 0;
                    }
                    return valor;
                });

        // Calcular el total general
        const totalGeneral = totales.reduce((sum, current) => sum + current, 0);

        // Actualizar el total en el resumen
        const totalElement = document.querySelector('.total-value');
        if (totalElement) {
            totalElement.textContent = `$${totalGeneral.toFixed(2)}`;
        } else {
            console.error('Elemento total-value no encontrado');
        }
    } catch (error) {
        console.error('Error al actualizar el total:', error);
        showError('Error al calcular el total');
    }
}

function updateProductCount() {
    const items = document.querySelectorAll('.carrito-item');
    const countElement = document.querySelector('.productos-count');

    if (countElement) {
        const count = items.length;
        countElement.textContent = `${count} producto${count !== 1 ? 's' : ''}`;
    }
}

function showError(message) {
    const errorDiv = document.getElementById('mensajes-error');
    if (errorDiv) {
        errorDiv.textContent = message;
        errorDiv.style.display = 'block';
        setTimeout(() => {
            errorDiv.style.display = 'none';
        }, 3000);
    } else {
        console.error('Elemento mensajes-error no encontrado');
        alert(message);
    }
}

// Función auxiliar para formatear números
function formatCurrency(amount) {
    return `$${parseFloat(amount).toFixed(2)}`;
}

// Función auxiliar para parsear valores monetarios
function parseCurrency(value) {
    const numero = parseFloat(value.replace('$', '').trim());
    return isNaN(numero) ? 0 : numero;
}

// Manejar el envío del formulario de confirmación
document.getElementById('confirmarCompraForm').addEventListener('submit', function (e) {
    e.preventDefault();

    // Verificar si hay productos en el carrito
    const itemsEnCarrito = document.querySelectorAll('.carrito-item').length;
    if (itemsEnCarrito === 0) {
        showError('No hay productos en el carrito');
        return;
    }

    // Si todo está bien, enviar el formulario
    this.submit();
});

// Inicialización cuando el documento está listo
document.addEventListener('DOMContentLoaded', function () {
    const asideCatalogo = document.querySelector('.aside-catalogo');
    if (asideCatalogo) {
        asideCatalogo.style.display = 'none';
    }

    const modal = document.getElementById('editarModal');
    const span = document.querySelector('.close');
    const cancelarBtn = document.getElementById('cancelarEdicion');
    const editarForm = document.getElementById('editarPersonalizacionForm');
    const editProductoId = document.getElementById('editProductoId');
    const editPersonalizacion = document.getElementById('editPersonalizacion');
    document.querySelectorAll('.btn-edit').forEach(button => {
        button.addEventListener('click', function () {
            const id = this.getAttribute('data-id');
            let personalizacion = this.getAttribute('data-personalizacion');
            // Si contiene "Extra:", separarlo para mostrar solo la personalización principal
            if (personalizacion.includes('Extra:')) {
                personalizacion = personalizacion.split('Extra:')[0].trim();
            }

            editProductoId.value = id;
            editPersonalizacion.value = personalizacion;
            modal.style.display = 'flex';
        });
    });
    span.onclick = function () {
        modal.style.display = 'none';
    }

    cancelarBtn.onclick = function () {
        modal.style.display = 'none';
    }

    window.onclick = function (event) {
        if (event.target == modal) {
            modal.style.display = 'none';
        }
    }

// Manejar el envío del formulario de edición
    editarForm.addEventListener('submit', function (e) {
        e.preventDefault();
        const idProducto = editProductoId.value;
        const nuevaPersonalizacion = editPersonalizacion.value;
        // Enviar la actualización al servidor
        fetch('SVCarrito', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/x-www-form-urlencoded',
            },
            body: new URLSearchParams({
                'action': 'updatePersonalizacion',
                'idProducto': idProducto,
                'personalizacion': nuevaPersonalizacion
            })
        })
                .then(response => response.json())
                .then(data => {
                    if (data.success) {
                        // Actualizar la personalización en la UI
                        const item = document.querySelector(`.carrito-item[data-id="${idProducto}"]`);
                        const descripcion = item.querySelector('.item-description');
                        descripcion.textContent = nuevaPersonalizacion;
                        // Actualizar el atributo data-personalizacion del botón
                        const editBtn = item.querySelector('.btn-edit');
                        editBtn.setAttribute('data-personalizacion', nuevaPersonalizacion);
                        // Mostrar mensaje de éxito
                        mostrarMensaje(data.message, 'success');
                    } else {
                        mostrarMensaje(data.message, 'error');
                    }

                    // Cerrar el modal
                    modal.style.display = 'none';
                })
                .catch(error => {
                    console.error('Error:', error);
                    mostrarMensaje('Error al actualizar la personalización', 'error');
                    modal.style.display = 'none';
                });
        // Función para mostrar mensajes
        function mostrarMensaje(mensaje, tipo) {
            const mensajesDiv = document.getElementById('mensajes-error');
            mensajesDiv.innerHTML = `<div class="mensaje ${tipo}">${mensaje}</div>`;
            mensajesDiv.style.display = 'block';
            // Ocultar después de 3 segundos
            setTimeout(() => {
                mensajesDiv.style.display = 'none';
            }, 3000);
        }

    });
});