<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%-- Al principio de tu Reporte.jsp, antes de <!DOCTYPE html> --%>
<%
    // Si el atributo ventas no está presente, redirigir al servlet
    if (request.getAttribute("ventas") == null) {
        response.sendRedirect("SVReporte");
        return;
    }
%>
<!DOCTYPE html>
<html lang="es">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Reportes de Ventas</title>
        <link rel="stylesheet" href="style/style.css">
        <link rel="stylesheet" href="style/header.css">
        <link rel="stylesheet" href="style/catalogo.css">
        <link rel="stylesheet" href="style/reporte.css">
        <link href="https://fonts.googleapis.com/css2?family=Ubuntu:wght@300;400;500;700&display=swap" rel="stylesheet">
    </head>
    <body>
        <%@ include file="/partials/header.jspf" %>
        <main class="main-content">
            <!-- Encabezado visible solo para impresión -->
            <div class="print-header" style="display: none;">
                <img src="img/logo.png" alt="Tinto & Glam Logo" class="print-logo">
                <div class="print-title">Reporte de Ventas</div>
            </div>
            <div class="page-header">
                <h2>Reportes de Ventas</h2>
                <div class="search-container">
                    <input type="text" class="search-input" placeholder="Buscar en reportes" id="searchInput">
                    <button class="search-button" onclick="searchReports()">🔍</button>
                </div>
                <div class="action-buttons">
                    <button class="btn btn-primary" onclick="window.print()">Descargar PDF</button>
                </div>
            </div>

            <form action="SVReporte" method="get" class="date-filters">
                <label for="fechaInicio">Desde:</label>
                <input type="date" id="fechaInicio" name="fechaInicio" class="date-input" value="${fechaInicio}">
                <label for="fechaFin">Hasta:</label>
                <input type="date" id="fechaFin" name="fechaFin" class="date-input" value="${fechaFin}">
                <button type="submit" class="btn btn-secondary">Filtrar</button>
                <a href="SVReporte" class="btn btn-secondary">Resetear</a>
            </form>

            <!-- Mostrar mensaje de error si existe -->
            <c:if test="${not empty errorMessage}">
                <div style="color: red; background-color: #ffebee; padding: 10px; border-radius: 4px; margin-bottom: 15px;">
                    ${errorMessage}
                </div>
            </c:if>

            <div class="table-container">
                <table class="table" id="reportTable">
                    <thead>
                        <tr>
                            <th>ID Venta</th>
                            <th>Nombre Cliente</th>
                            <th>Descripción</th>
                            <th>Fecha</th>
                            <th>Total</th>
                            <th>Método Pago</th>
                        </tr>
                    </thead>
                    <tbody id="reportBody">
                        <c:choose>
                            <c:when test="${not empty ventas}">
                                <c:forEach var="venta" items="${ventas}">
                                    <tr>
                                        <td>${venta.id}</td>
                                        <td>${venta.cliente.nombreCompleto}</td>
                                        <td>
                                            <c:choose>
                                                <c:when test="${not empty venta.detallesVenta}">
                                                    Venta con ${venta.detallesVenta.size()} producto(s)
                                                </c:when>
                                                <c:otherwise>
                                                    Sin detalles
                                                </c:otherwise>
                                            </c:choose>
                                        </td>
                                        <td><fmt:formatDate value="${venta.fecha}" pattern="yyyy-MM-dd" /></td>
                                        <td>$<fmt:formatNumber value="${venta.total}" pattern="#,##0.00"/></td>
                                        <td>${venta.metodoPago}</td>
                                    </tr>
                                </c:forEach>
                            </c:when>
                            <c:otherwise>
                                <tr>
                                    <td colspan="6" style="text-align: center;">No se encontraron ventas</td>
                                </tr>
                            </c:otherwise>
                        </c:choose>
                    </tbody>
                </table>
            </div>
        </main>

        <script>
            // Función de búsqueda en la tabla
            function searchReports() {
                const query = document.getElementById('searchInput').value.toLowerCase();
                const tbody = document.getElementById('reportBody');
                const rows = tbody.getElementsByTagName('tr');

                for (let row of rows) {
                    const text = row.textContent.toLowerCase();
                    row.style.display = text.includes(query) ? '' : 'none';
                }
            }

            // Búsqueda en tiempo real
            document.getElementById('searchInput').addEventListener('input', searchReports);
        </script>
    </body>
</html>