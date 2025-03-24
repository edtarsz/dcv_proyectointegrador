function agregarAlCarrito(id, nombre, descripcion, precio, btn) {
    let cantidad = btn.closest('.producto-info').querySelector('.input-cantidad').value;

    fetch('SVCarrito', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/x-www-form-urlencoded'
        },
        body: `idProducto=${id}&nombre=${encodeURIComponent(nombre)}&descripcion=${encodeURIComponent(descripcion)}&precio=${precio}&cantidad=${cantidad}`
    })
            .then(response => response.text())
            .then(data => {
                console.log('Producto agregado al carrito', data);
            })
            .catch(error => console.error('Error:', error));
}

function actualizarCantidad() {
}

function eliminarDelCarrito() {
}
