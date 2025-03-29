document.addEventListener('DOMContentLoaded', function() {
    setupCartControls();
});

function setupCartControls() {
    // Configurar botones de cantidad
    document.querySelectorAll('.quantity-control').forEach(control => {
        const decrementBtn = control.querySelector('.decrement');
        const incrementBtn = control.querySelector('.increment');
        const input = control.querySelector('.quantity-input');
        const itemContainer = control.closest('.carrito-item');
        const itemId = itemContainer.dataset.id;

        // Remover onclick si existe
        decrementBtn.removeAttribute('onclick');
        incrementBtn.removeAttribute('onclick');

        decrementBtn.addEventListener('click', () => {
            let cantidad = parseInt(input.value);
            if (cantidad > 1) {
                actualizarCantidadCarrito(itemId, cantidad - 1, itemContainer);
            }
        });

        incrementBtn.addEventListener('click', () => {
            let cantidad = parseInt(input.value);
            actualizarCantidadCarrito(itemId, cantidad + 1, itemContainer);
        });
    });

    // Configurar botones de eliminar
    document.querySelectorAll('.btn-delete').forEach(btn => {
        // Remover onclick si existe
        btn.removeAttribute('onclick');
        
        btn.addEventListener('click', function() {
            const itemContainer = this.closest('.carrito-item');
            const itemId = itemContainer.dataset.id;
            eliminarProductoCarrito(itemId, itemContainer);
        });
    });
}

function actualizarCantidadCarrito(itemId, nuevaCantidad, itemContainer) {
    const input = itemContainer.querySelector('.quantity-input');
    const precioUnitario = parseFloat(itemContainer.querySelector('.col-precio').textContent.replace('$', ''));

    fetch('SVCarrito', {
        method: 'PUT',
        headers: {
            'Content-Type': 'application/x-www-form-urlencoded'
        },
        body: new URLSearchParams({
            idProducto: itemId,
            nuevaCantidad: nuevaCantidad
        })
    })
    .then(response => response.json())
    .then(data => {
        if (data.success) {
            input.value = nuevaCantidad;
            const totalItem = (precioUnitario * nuevaCantidad).toFixed(2);
            itemContainer.querySelector('.col-total').textContent = `$${totalItem}`;
            actualizarTotalGeneral();
        } else {
            throw new Error(data.message);
        }
    })
    .catch(error => {
        console.error('Error:', error);
        alert('Error al actualizar la cantidad');
    });
}

function eliminarProductoCarrito(itemId, itemContainer) {
    if (confirm('¿Está seguro de eliminar este producto del carrito?')) {
        fetch('SVCarrito', {
            method: 'DELETE',
            headers: {
                'Content-Type': 'application/x-www-form-urlencoded'
            },
            body: new URLSearchParams({
                idProducto: itemId
            })
        })
        .then(response => response.json())
        .then(data => {
            if (data.success) {
                itemContainer.remove();
                
                // Actualizar contador de productos
                const productosCount = document.querySelector('.productos-count');
                const cantidadActual = document.querySelectorAll('.carrito-item').length;
                productosCount.textContent = `${cantidadActual} productos`;

                // Actualizar total general
                actualizarTotalGeneral();

                // Si el carrito está vacío, recargar la página
                if (cantidadActual === 0) {
                    location.reload();
                }
            } else {
                throw new Error(data.message);
            }
        })
        .catch(error => {
            console.error('Error:', error);
            alert('Error al eliminar el producto');
        });
    }
}

function actualizarTotalGeneral() {
    const totales = Array.from(document.querySelectorAll('.col-total'))
        .map(el => parseFloat(el.textContent.replace('$', '')));
    
    const subtotal = totales.reduce((acc, curr) => acc + curr, 0).toFixed(2);
    document.querySelector('.total-value').textContent = `$${subtotal}`;
}