<%-- 
    Document   : Pedidos
    Created on : 22 mar 2025, 4:25:51 p.m.
    Author     : esmeraldamolinaestrada
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0"> 
        
        <%-- Estilos --%>
        <link rel="stylesheet" href="style/style.css">
        <link rel="stylesheet" href="style/header.css">
        <link rel="stylesheet" href="style/pedidos.css">
        <%-- Fonts --%>
        <link rel="preconnect" href="https://fonts.googleapis.com">
        <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
        <link
            href="https://fonts.googleapis.com/css2?family=Ubuntu:ital,wght@0,300;0,400;0,500;0,700;1,300;1,400;1,500;1,700&display=swap"
            rel="stylesheet">
        <title>Pedidos</title>
        <link rel="preconnect" href="https://fonts.googleapis.com">
        <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
        <link href="https://fonts.googleapis.com/css2?family=Ubuntu:wght@300;400;500;700&display=swap" rel="stylesheet">
             
    </head>
    <body>
        <%@ include file="/partials/header.jspf" %>
        <main class="main-pedidos">
            <div class="pedidos-container">
                <div class="pedidos-izquierda">
                    <h1 class="pedidos-title">Pedidos</h1>
                    
                    <div class="search-filter-container">
                        <div class="search-bar-container">
                            <input type="search" name="search-bar" id="search-bar" placeholder="Buscar por nombre">
                            <button class="search-button">
                                <img src="svg/search.svg" alt="Buscar">
                            </button>
                        </div>
                        
                        <div class="filtros-container">
                            <div class="filtro">
                                <span>Fecha</span>
                                <img src="svg/arrow-down.svg" alt="Desplegable">
                            </div>
                            <div class="filtro">
                                <span>Producto</span>
                                <img src="svg/arrow-down.svg" alt="Desplegable">
                            </div>
                            <div class="filtro">
                                <span>Costo</span>
                                <img src="svg/arrow-down.svg" alt="Desplegable">
                            </div>
                            <div class="filtro">
                                <span>Estado</span>
                                <img src="svg/arrow-down.svg" alt="Desplegable">
                            </div>
                        </div>
                    </div>
                    
                    <div class="pedidos-lista">
                        <div class="pedido-item selected" onclick="mostrarDetalle('01')">
                            <div class="pedido-numero">#01</div>
                            <div class="pedido-cliente">Ana Cristina Castro</div>
                            <div class="pedido-fecha">18/02/2025</div>
                            <div class="pedido-estado completado">
                                <span>COMPLETADO</span>
                                <img src="svg/arrow-down.svg" alt="Desplegable">
                            </div>
                        </div>
                        
                        <div class="pedido-item" onclick="mostrarDetalle('02')">
                            <div class="pedido-numero">#02</div>
                            <div class="pedido-cliente">Esmeralda Molina Estrada</div>
                            <div class="pedido-fecha">18/02/2025</div>
                            <div class="pedido-estado en-proceso">
                                <span>EN PROCESO</span>
                                <img src="svg/arrow-down.svg" alt="Desplegable">
                            </div>
                        </div>
                        
                        <div class="pedido-item" onclick="mostrarDetalle('03')">
                            <div class="pedido-numero">#03</div>
                            <div class="pedido-cliente">Eduardo Talavera Ramos</div>
                            <div class="pedido-fecha">18/02/2025</div>
                            <div class="pedido-estado esperando">
                                <span>ESPERANDO ADELANTO</span>
                                <img src="svg/arrow-down.svg" alt="Desplegable">
                            </div>
                        </div>
                        
                        <div class="pedido-item" onclick="mostrarDetalle('04')">
                            <div class="pedido-numero">#04</div>
                            <div class="pedido-cliente">Diego Alcantar</div>
                            <div class="pedido-fecha">18/02/2025</div>
                            <div class="pedido-estado completado">
                                <span>COMPLETADO</span>
                                <img src="svg/arrow-down.svg" alt="Desplegable">
                            </div>
                        </div>
                    </div>
                </div>
                
                <div class="pedidos-derecha">
                    <h2 class="detalles-title">DETALLES</h2>
                    
                    <div class="detalles-header">
                        <div class="detalles-cliente">Ana Cristina Castro</div>
                        <div class="detalles-fecha">18/02/2025</div>
                    </div>
                    
                    <div class="detalles-items">
                        <div class="detalle-item">
                            <div class="item-info">
                                <div class="item-title">TAZAS</div>
                                <div class="item-descripcion">
                                    Fotografía proporcionada por el cliente (adjunta al pedido)
                                    Texto: "El mejor café es contigo" en fuente cursiva, color negro
                                    Embalaje: Caja protectora individual para cada taza
                                    Instrucciones adicionales: Entrega antes del 5 de abril, con verificación previa del diseño final antes de la impresión.
                                </div>
                            </div>
                            <div class="item-acciones">
                                <div class="item-precio">$110.99</div>
                                <button class="item-editar">
                                    <img src="svg/delete.svg" alt="Editar">
                                </button>
                                 <button class="item-editar">
                                    <img src="svg/edit.svg" alt="Editar">
                                </button>
                            </div>
                        </div>
                        
                        <div class="detalle-item">
                            <div class="item-info">
                                <div class="item-title">TAZAS</div>
                                <div class="item-descripcion">
                                    Fotografía proporcionada por el cliente (adjunta al pedido)
                                    Texto: "El mejor café es contigo" en fuente cursiva, color negro
                                    Embalaje: Caja protectora individual para cada taza
                                    Instrucciones adicionales: Entrega antes del 5 de abril, con verificación previa del diseño final antes de la impresión.
                                </div>
                            </div>
                            <div class="item-acciones">
                                <div class="item-precio">$110.99</div>
                                <button class="item-editar">
                                    <img src="svg/delete.svg" alt="Editar">
                                </button>
                                 <button class="item-editar">
                                    <img src="svg/edit.svg" alt="Editar">
                                </button>
                            
                            </div>
                        </div>
                        
                        <div class="detalle-item">
                            <div class="item-info">
                                <div class="item-title">TAZAS</div>
                                <div class="item-descripcion">
                                    Fotografía proporcionada por el cliente (adjunta al pedido)
                                    Texto: "El mejor café es contigo" en fuente cursiva, color negro
                                </div>
                            </div>
                            <div class="item-acciones">
                                <div class="item-precio">$110.99</div>
                                <button class="item-editar">
                                    <img src="svg/delete.svg" alt="Editar">
                                </button>
                                 <button class="item-editar">
                                    <img src="svg/edit.svg" alt="Editar">
                                </button>
                            </div>
                        </div>
                        
                        <div class="detalle-item">
                            <div class="item-info">
                                <div class="item-title">TAZAS</div>
                                <div class="item-descripcion">
                                    Fotografía proporcionada por el cliente (adjunta al pedido)
                                    Texto: "El mejor café es contigo" en fuente cursiva, color negro
                                </div>
                            </div>
                            <div class="item-acciones">
                                <div class="item-precio">$110.99</div>
                                <button class="item-editar">
                                    <img src="svg/delete.svg" alt="Editar">
                                </button>
                                 <button class="item-editar">
                                    <img src="svg/edit.svg" alt="Editar">
                                </button>
                            </div>
                        </div>
                    </div>
                    
                    <div class="total-container">
                        <div class="total-label">TOTAL</div>
                        <div class="total-valor">$443.96</div>
                    </div>
                </div>
            </div>
        </main>
        
        <script>
            function mostrarDetalle(id) {
                const pedidos = document.querySelectorAll('.pedido-item');
                pedidos.forEach(pedido => {
                    pedido.classList.remove('selected');
                });
                
                event.currentTarget.classList.add('selected');
                
            
            }
        </script>
    </body>
</html>