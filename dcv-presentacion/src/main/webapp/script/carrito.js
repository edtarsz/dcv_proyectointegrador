function agregarAlCarrito(id, nombre, descripcion, precio, btn) {
    let cantidadInput = btn.closest('.producto-info').querySelector('.input-cantidad');
    let cantidad = cantidadInput ? cantidadInput.value : 1;

    let params = new URLSearchParams({
        idProducto: id,
        nombre: nombre,
        descripcion: descripcion,
        precio: precio,
        cantidad: cantidad
    });

    fetch('SVCarrito', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/x-www-form-urlencoded'
        },
        body: params.toString()
    })
            .then(response => {
                if (!response.ok) {
                    throw new Error('Network response was not ok');
                }
                return response.text();
            })
            .then(data => {
                console.log('Producto agregado al carrito', data);
            })
            .catch(error => {
                console.error('Error:', error);
                alert('No se pudo agregar el producto al carrito');
            });
}

document.addEventListener('DOMContentLoaded', () => {
    document.querySelectorAll('.counter-btn').forEach(button => {
        button.addEventListener('click', function () {
            let inputCantidad = this.closest('.counter-container').querySelector('.input-cantidad');
            let currentValue = parseInt(inputCantidad.value);

            if (this.classList.contains('increase')) {
                inputCantidad.value = currentValue + 1;
            } else if (this.classList.contains('decrease') && currentValue > 1) {
                inputCantidad.value = currentValue - 1;
            }
        });
    });
});

function actualizarCantidad() {
    // TODO
    console.log('Actualizar cantidad');
}

function eliminarDelCarrito() {
    // TODO
    console.log('Eliminar del carrito');
}