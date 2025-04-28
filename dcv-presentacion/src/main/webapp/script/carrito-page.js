document.addEventListener('DOMContentLoaded', function () {
    setupCartControls();

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
    const editCostoExtra = document.getElementById('editCostoExtra');

    // Evento para controlar el límite de caracteres
    if (editPersonalizacion) {
        editPersonalizacion.addEventListener('input', function (e) {
            const maxLength = 100;
            const currentLength = this.value.length;

            if (currentLength > maxLength) {
                // Cortar el texto al máximo permitido
                this.value = this.value.substring(0, maxLength);

                // Mostrar alert cuando se intenta exceder el límite
                alert('El texto de personalización no puede superar los 100 caracteres');
            }
        });

        // Prevenir pegar texto que exceda el límite
        editPersonalizacion.addEventListener('paste', function (e) {
            const maxLength = 100;
            const clipboardData = e.clipboardData || window.clipboardData;
            const pastedText = clipboardData.getData('text');

            // Verificar si el texto pegado excedería el límite
            if (this.value.length + pastedText.length > maxLength) {
                e.preventDefault();
                alert('El texto pegado excede el límite de 100 caracteres');
            }
        });
    }

    document.querySelectorAll('.btn-edit').forEach(button => {
        button.addEventListener('click', function () {
            const id = this.getAttribute('data-id');
            let personalizacionCompleta = this.getAttribute('data-personalizacion');
            let personalizacion = personalizacionCompleta;
            let costoExtra = '';

            if (personalizacionCompleta.includes('Extra:')) {
                const partes = personalizacionCompleta.split('Extra:');
                personalizacion = partes[0].trim();
                costoExtra = partes[1].replace('$', '').trim();
            }

            editProductoId.value = id;
            editPersonalizacion.value = personalizacion;
            editCostoExtra.value = costoExtra || "0";
            modal.style.display = 'flex';
        });
    });

    span.onclick = function () {
        modal.style.display = 'none';
    };

    cancelarBtn.onclick = function () {
        modal.style.display = 'none';
    };

    window.onclick = function (event) {
        if (event.target == modal) {
            modal.style.display = 'none';
        }
    };

    editarForm.addEventListener('submit', function (e) {
        e.preventDefault();
        const idProducto = editProductoId.value;
        const nuevaPersonalizacion = editPersonalizacion.value.trim();
        const costoExtra = editCostoExtra.value.trim() || "0";

        // Verificar una vez más al enviar
        if (nuevaPersonalizacion.length > 100) {
            alert('El texto de personalización no puede superar los 100 caracteres');
            return;
        }

        fetch('SVCarrito', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/x-www-form-urlencoded',
            },
            body: new URLSearchParams({
                'action': 'updatePersonalizacion',
                'idProducto': idProducto,
                'personalizacion': nuevaPersonalizacion,
                'costoExtra': costoExtra
            })
        })
                .then(response => response.json())
                .then(data => {
                    if (data.success) {
                        location.reload(); // Refresca la vista con los nuevos datos
                    } else {
                        mostrarMensaje(data.message, 'error');
                    }
                    modal.style.display = 'none';
                })
                .catch(error => {
                    console.error('Error:', error);
                    mostrarMensaje('Error al actualizar la personalización', 'error');
                    modal.style.display = 'none';
                });

        function mostrarMensaje(mensaje, tipo) {
            const mensajesDiv = document.getElementById('mensajes-error');
            mensajesDiv.innerHTML = `<div class="mensaje ${tipo}">${mensaje}</div>`;
            mensajesDiv.style.display = 'block';
            setTimeout(() => {
                mensajesDiv.style.display = 'none';
            }, 3000);
        }
    });
});

function setupCartControls() {
    document.querySelectorAll('.quantity-control').forEach(control => {
        const decrementBtn = control.querySelector('.decrement');
        const incrementBtn = control.querySelector('.increment');
        const input = control.querySelector('.quantity-input');
        const carritoItem = control.closest('.carrito-item');

        if (decrementBtn && incrementBtn && input && carritoItem) {
            decrementBtn.addEventListener('click', () => {
                const currentValue = Math.floor(parseFloat(input.value));
                if (currentValue > 1) {
                    updateQuantity(carritoItem, currentValue - 1);
                } else {
                    showError('La cantidad no puede ser menor a 1');
                }
            });


            incrementBtn.addEventListener('click', () => {
                const currentValue = parseInt(input.value);
                updateQuantity(carritoItem, currentValue + 1);
            });
        }
    });

    document.querySelectorAll('.btn-delete').forEach(btn => {
        btn.addEventListener('click', function (e) {
            e.preventDefault();
            const carritoItem = this.closest('.carrito-item');
            if (carritoItem) {
                deleteItem(carritoItem);
            }
        });
    });
}

function updateQuantity(carritoItem, newQuantity) {
    const itemId = carritoItem.dataset.id;
    const datos = new URLSearchParams();
    datos.append('action', 'updateQuantity');
    datos.append('idProducto', itemId);
    datos.append('nuevaCantidad', newQuantity);

    fetch('SVCarrito', {
        method: 'POST',
        headers: {'Content-Type': 'application/x-www-form-urlencoded'},
        body: datos
    })
            .then(response => response.json())
            .then(data => {
                if (data.success) {
                    const input = carritoItem.querySelector('.quantity-input');
                    input.value = newQuantity;

                    const precioUnitario = parseFloat(carritoItem.querySelector('.col-precio').textContent.replace('$', '').trim());
                    const nuevoTotalItem = (precioUnitario * newQuantity).toFixed(2);
                    carritoItem.querySelector('.col-total').textContent = `$${nuevoTotalItem}`;
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
    if (!confirm('¿Está seguro de eliminar este producto del carrito?'))
        return;

    const itemId = carritoItem.dataset.id;
    const datos = new URLSearchParams();
    datos.append('action', 'deleteItem');
    datos.append('idProducto', itemId);

    fetch('SVCarrito', {
        method: 'POST',
        headers: {'Content-Type': 'application/x-www-form-urlencoded'},
        body: datos
    })
            .then(response => response.json())
            .then(data => {
                if (data.success) {
                    carritoItem.remove();
                    updateCartTotal();
                    updateProductCount();
                    if (document.querySelectorAll('.carrito-item').length === 0) {
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
        const totales = Array.from(document.querySelectorAll('.col-total')).map(el => {
            const valor = parseFloat(el.textContent.replace('$', '').trim());
            return isNaN(valor) ? 0 : valor;
        });
        const totalGeneral = totales.reduce((sum, current) => sum + current, 0);
        const totalElement = document.querySelector('.total-value');
        if (totalElement) {
            totalElement.textContent = `$${totalGeneral.toFixed(2)}`;
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
    let errorDiv = document.getElementById('mensajes-error');
    if (!errorDiv) {
        errorDiv = document.createElement('div');
        errorDiv.id = 'mensajes-error';
        errorDiv.className = 'mensajes-error';
        document.body.appendChild(errorDiv);
    }

    errorDiv.textContent = message;
    errorDiv.style.display = 'block';

    clearTimeout(window._mensajeErrorTimeout);
    window._mensajeErrorTimeout = setTimeout(() => {
        errorDiv.style.display = 'none';
        errorDiv.textContent = '';
    }, 3000);
}


document.getElementById('confirmarCompraForm').addEventListener('submit', function (e) {
    e.preventDefault();
    const itemsEnCarrito = document.querySelectorAll('.carrito-item').length;
    if (itemsEnCarrito === 0) {
        showError('No hay productos en el carrito');
        return;
    }
    this.submit();
});