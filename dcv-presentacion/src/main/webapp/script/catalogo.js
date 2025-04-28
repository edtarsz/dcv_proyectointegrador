let productoSeleccionado = null;

function seleccionarProducto(id, nombre, descripcion, precio, stock) {
    productoSeleccionado = { id, nombre, descripcion, precio, stock }; 
    
    const headerAside = document.querySelector('.header-aside h1');
    const descripcionAside = document.querySelector('.aside-descripcion p');
    const totalAside = document.querySelector('.total-aside h4');
    
    headerAside.textContent = nombre;
    descripcionAside.textContent = descripcion || 'Sin descripción';
    totalAside.textContent = `Total: $${precio.toFixed(2)}`;
    
    document.querySelector('.aside-catalogo').style.display = 'flex';
    
    document.getElementById('detalles-personalizacion').value = '';
    document.getElementById('extra-personalizacion').value = '';
    document.querySelector('.counter-value').textContent = '1';

    document.querySelector('.stock-message').style.display = 'none';
}

function agregarAlCarrito(btn) {
    if (!productoSeleccionado) {
        alert('Por favor, seleccione un producto primero');
        return;
    }

    const cantidad = parseInt(document.querySelector('.counter-value').textContent);
    const detalles = document.getElementById('detalles-personalizacion').value.trim();
    const extra = document.getElementById('extra-personalizacion').value.trim();
    
    if (cantidad > productoSeleccionado.stock) {
        const stockMessage = document.querySelector('.stock-message');
        stockMessage.textContent = `No hay suficiente stock. Solo hay ${productoSeleccionado.stock} unidades disponibles.`;
        stockMessage.style.display = 'block';
        return; 
    }

    document.querySelector('.stock-message').style.display = 'none';

    if (!detalles) {
        alert('Por favor, ingrese los detalles de personalización');
        return;
    }

    const datos = {
        idProducto: productoSeleccionado.id,
        nombre: productoSeleccionado.nombre,
        descripcion: productoSeleccionado.descripcion,
        precio: productoSeleccionado.precio,
        cantidad: cantidad,
        detalles: detalles,
        extra: extra || ''
    };

    fetch('SVCatalogo', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/x-www-form-urlencoded'
        },
        body: new URLSearchParams(datos).toString()
    })
    .then(response => {
        if (!response.ok) {
            throw new Error('Error en la respuesta del servidor');
        }
        return response.text(); 
    })
    .then(text => {
        try {
            const data = JSON.parse(text); 
            if (data.success) {
                alert('Producto agregado al carrito exitosamente');
                limpiarFormulario();
            } else {
                throw new Error(data.message || 'Error al agregar al carrito');
            }
        } catch (e) {
            console.error('Respuesta del servidor:', text);
            throw new Error('Error al procesar la respuesta del servidor');
        }
    })
    .catch(error => {
        console.error('Error:', error);
        alert(error.message);
    });
}

function limpiarFormulario() {
    productoSeleccionado = null;
    document.getElementById('detalles-personalizacion').value = '';
    document.getElementById('extra-personalizacion').value = '';
    document.querySelector('.counter-value').textContent = '1';
    
    const headerAside = document.querySelector('.header-aside h1');
    const descripcionAside = document.querySelector('.aside-descripcion p');
    const totalAside = document.querySelector('.total-aside h4');
    
    headerAside.textContent = 'PRODUCTO';
    descripcionAside.textContent = '';
    totalAside.textContent = 'Total: $0.00';
    
    document.querySelector('.aside-catalogo').style.display = 'none';
}

document.addEventListener('DOMContentLoaded', () => {
    initializeCounterButtons();
    
    if (!productoSeleccionado) {
        document.querySelector('.aside-catalogo').style.display = 'none';
    }

    const btnAgregar = document.querySelector('.btn-add-carrito');
    if (btnAgregar) {
        btnAgregar.addEventListener('click', agregarAlCarrito);
    }
});
