let productoSeleccionado = null;

function seleccionarProducto(id, nombre, descripcion, precio) {
    productoSeleccionado = {id, nombre, descripcion, precio};

    const headerAside = document.querySelector('.header-aside h1');
    const descripcionAside = document.querySelector('.aside-descripcion p');
    const totalAside = document.querySelector('.total-aside h4');

    headerAside.textContent = nombre;
    descripcionAside.textContent = descripcion || 'Sin descripción';
    totalAside.textContent = `Total: $${precio.toFixed(2)}`;

    document.querySelector('.aside-catalogo').style.display = 'flex';

    // Limpiar campos
    document.getElementById('detalles-personalizacion').value = '';
    document.getElementById('extra-personalizacion').value = '';
    document.querySelector('.counter-value').textContent = '1';

    initializeCounterButtons();
}

function initializeCounterButtons() {
    const decreaseBtn = document.querySelector('.decrease');
    const increaseBtn = document.querySelector('.increase');
    const counterValue = document.querySelector('.counter-value');

    if (!decreaseBtn || !increaseBtn || !counterValue)
        return;

    // Remover listeners anteriores
    const newDecreaseBtn = decreaseBtn.cloneNode(true);
    const newIncreaseBtn = increaseBtn.cloneNode(true);

    decreaseBtn.parentNode.replaceChild(newDecreaseBtn, decreaseBtn);
    increaseBtn.parentNode.replaceChild(newIncreaseBtn, increaseBtn);

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
    const extraInput = document.getElementById('extra-personalizacion');
    const extraStr = extraInput.value.trim();
    let costoExtra = 0;

    if (extraStr) {
        if (isNaN(extraStr) || Number(extraStr) < 0) {
            alert('Por favor ingrese un número válido en el campo "Extra"');
            extraInput.focus();
            return;
        }
        costoExtra = parseFloat(extraStr);
    }

    if (!detalles) {
        alert('Por favor, ingrese los detalles de personalización');
        return;
    }

    const datos = {
        action: 'addToCart',
        idProducto: productoSeleccionado.id,
        nombre: productoSeleccionado.nombre,
        descripcion: productoSeleccionado.descripcion,
        precio: productoSeleccionado.precio,
        cantidad: cantidad,
        detalles: detalles,
        extra: costoExtra.toString()
    };

    console.log('Enviando datos:', datos);

    fetch('SVCatalogo', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/x-www-form-urlencoded'
        },
        body: new URLSearchParams(datos).toString()
    })
            .then(response => {
                if (!response.ok) {
                    throw new Error(`Error HTTP: ${response.status}`);
                }
                return response.text();
            })
            .then(text => {
                console.log('Respuesta del servidor:', text);
                try {
                    const data = JSON.parse(text);
                    if (data.success) {
                        alert('Producto agregado al carrito exitosamente');
                        limpiarFormulario();
                        actualizarContadorCarrito(data.carritoSize);
                    } else {
                        throw new Error(data.message || 'Error al agregar al carrito');
                    }
                } catch (e) {
                    console.error('Error al procesar respuesta:', text);
                    throw new Error('Error al procesar la respuesta del servidor');
                }
            })
            .catch(error => {
                console.error('Error:', error);
                alert('Error al agregar al carrito: ' + error.message);
            });
}

function actualizarContadorCarrito(cantidad) {
    const contadorCarrito = document.querySelector('.carrito-count');
    if (contadorCarrito) {
        contadorCarrito.textContent = cantidad;
    }
}

function limpiarFormulario() {
    productoSeleccionado = null;

    const elementos = {
        detalles: document.getElementById('detalles-personalizacion'),
        extra: document.getElementById('extra-personalizacion'),
        contador: document.querySelector('.counter-value'),
        titulo: document.querySelector('.header-aside h1'),
        descripcion: document.querySelector('.aside-descripcion p'),
        total: document.querySelector('.total-aside h4'),
        aside: document.querySelector('.aside-catalogo')
    };

    if (elementos.detalles)
        elementos.detalles.value = '';
    if (elementos.extra)
        elementos.extra.value = '';
    if (elementos.contador)
        elementos.contador.textContent = '1';
    if (elementos.titulo)
        elementos.titulo.textContent = 'PRODUCTO';
    if (elementos.descripcion)
        elementos.descripcion.textContent = '';
    if (elementos.total)
        elementos.total.textContent = 'Total: $0.00';
    if (elementos.aside)
        elementos.aside.style.display = 'none';
}

function actualizarTotal(cantidad) {
    if (productoSeleccionado) {
        const totalElement = document.querySelector('.total-aside h4');
        if (totalElement) {
            const nuevoTotal = (productoSeleccionado.precio * cantidad).toFixed(2);
            totalElement.textContent = `Total: $${nuevoTotal}`;
        }
    }
}

// Inicialización cuando el documento está listo
document.addEventListener('DOMContentLoaded', function () {
    const asideCatalogo = document.querySelector('.aside-catalogo');
    if (asideCatalogo) {
        asideCatalogo.style.display = 'none';
    }
});