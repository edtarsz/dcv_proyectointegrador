let productoSeleccionado = null;

function seleccionarProducto(id, nombre, descripcion, precio) {
    productoSeleccionado = { id, nombre, descripcion, precio };
    
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

    // Inicializar los controles de cantidad
    initializeCounterButtons();
}

function initializeCounterButtons() {
    const decreaseBtn = document.querySelector('.decrease');
    const increaseBtn = document.querySelector('.increase');
    const counterValue = document.querySelector('.counter-value');

    // Remover listeners anteriores si existen
    decreaseBtn.replaceWith(decreaseBtn.cloneNode(true));
    increaseBtn.replaceWith(increaseBtn.cloneNode(true));

    // Obtener las referencias nuevas
    const newDecreaseBtn = document.querySelector('.decrease');
    const newIncreaseBtn = document.querySelector('.increase');

    newDecreaseBtn.addEventListener('click', () => {
        let value = parseInt(counterValue.textContent);
        if (value > 1) {
            value--;
            counterValue.textContent = value;
            actualizarTotal(value);
        }
    });

    newIncreaseBtn.addEventListener('click', () => {
        let value = parseInt(counterValue.textContent);
        value++;
        counterValue.textContent = value;
        actualizarTotal(value);
    });
}
function agregarAlCarrito() {
    if (!productoSeleccionado) {
        alert('Por favor, seleccione un producto primero');
        return;
    }

    const cantidad = parseInt(document.querySelector('.counter-value').textContent);
    const detalles = document.getElementById('detalles-personalizacion').value.trim();
    const extra = document.getElementById('extra-personalizacion').value.trim();

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

    fetch('SVCarrito', {
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

function initializeCartControls() {
    // Inicializar botones de cantidad
    const decreaseBtn = document.querySelector('.decrease');
    const increaseBtn = document.querySelector('.increase');
    const counterValue = document.querySelector('.counter-value');

    if (decreaseBtn && increaseBtn && counterValue) {
        decreaseBtn.addEventListener('click', () => {
            let value = parseInt(counterValue.textContent);
            if (value > 1) {
                value--;
                counterValue.textContent = value;
                actualizarTotal(value);
            }
        });

        increaseBtn.addEventListener('click', () => {
            let value = parseInt(counterValue.textContent);
            value++;
            counterValue.textContent = value;
            actualizarTotal(value);
        });
    }
}

// Asegúrate de llamar a esta función cuando el documento esté listo
document.addEventListener('DOMContentLoaded', function() {
    initializeCartControls();
});

function actualizarTotal(cantidad) {
    if (productoSeleccionado) {
        const totalElement = document.querySelector('.total-aside h4');
        const nuevoTotal = (productoSeleccionado.precio * cantidad).toFixed(2);
        totalElement.textContent = `Total: $${nuevoTotal}`;
    }
}

// Inicialización cuando el documento está listo
document.addEventListener('DOMContentLoaded', function() {
    initializeCartControls();
    
    if (!productoSeleccionado) {
        document.querySelector('.aside-catalogo').style.display = 'none';
    }
});