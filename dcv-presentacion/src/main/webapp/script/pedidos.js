document.addEventListener('DOMContentLoaded', function() {
    console.log('Inicializando scripts de pedidos...');

    setupPedidoItemsListeners();
    setupEstadoDropdowns();
    setupSearch();
    setupFiltros();
    setupEditModal();
});

function setupSearch() {
    const searchInput = document.getElementById("search-bar");

    searchInput.addEventListener("input", () => {
        const query = searchInput.value.trim();
        buscarPedidos(query);
    });
}

function buscarPedidos(termino) {
    console.log(`Buscando pedidos con término: "${termino}"`);

    fetch(`SVPedidos?action=buscar&termino=${encodeURIComponent(termino)}`)
        .then(response => {
            if (!response.ok) throw new Error("Error HTTP");
            return response.json();
        })
        .then(data => {
            if (data.success) {
                actualizarListaPedidos(data.ventas);
            } else {
                console.error(data.message);
            }
        })
        .catch(error => console.error("Error al buscar pedidos:", error));
}

function actualizarListaPedidos(ventas) {
    const pedidosLista = document.querySelector(".pedidos-lista");

    if (!ventas.length) {
        pedidosLista.innerHTML = "<div class='no-pedidos'><p>No hay pedidos disponibles</p></div>";
        document.querySelector(".pedidos-derecha").innerHTML = "<h2 class='detalles-title'>DETALLES</h2><div class='no-detalles'><p>Seleccione un pedido para ver sus detalles</p></div>";
        return;
    }

    pedidosLista.innerHTML = ventas.map(venta => `
        <div class="pedido-item" data-id="${venta.id}">
            <div class="pedido-numero">#${venta.id}</div>
            <div class="pedido-cliente">${venta.cliente.nombreCompleto}</div>
            <div class="pedido-fecha">${venta.fecha}</div>
            <div class="pedido-estado ${venta.estado.toLowerCase().replace(/\s+/g, '-')}">
                <span>${venta.estado}</span>
                <img src="svg/arrow-down.svg" alt="Desplegable">
            </div>
        </div>
    `).join("");

    setupPedidoItemsListeners();
    setupEstadoDropdowns();

    const primerPedido = document.querySelector(".pedido-item");
    if (primerPedido) {
        primerPedido.classList.add("selected");
        cargarDetallesPedido(primerPedido.getAttribute("data-id"));
    }
}