package com.mycompany.dcv.servlet;

import com.mycompany.dcventidades.Venta;
import com.mycompany.dcvexceptions.ControllerException;
import com.mycompany.dcvnegocio.venta.VentaBO;
import java.io.IOException;
import java.sql.Date;
import java.util.List;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

@WebServlet(name = "SVReporte", urlPatterns = {"/SVReporte", "/Reporte.jsp"})
public class SVReporte extends HttpServlet {
    
    private static final Logger logger = Logger.getLogger(SVReporte.class.getName());

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            VentaBO venta = new VentaBO();
            
            
            // Parámetros de filtrado
            String fechaInicioStr = request.getParameter("fechaInicio");
            String fechaFinStr = request.getParameter("fechaFin");
            
            List<Venta> ventas = new ArrayList<Venta>();
            
            // Aplicar filtros si se proporcionan fechas
            if (fechaInicioStr != null && !fechaInicioStr.isEmpty() &&
                fechaFinStr != null && !fechaFinStr.isEmpty()) {
                
                try {
                    Date fechaInicio = Date.valueOf(fechaInicioStr);
                    Date fechaFin = Date.valueOf(fechaFinStr);
                    
                    // Usar el método existente filtrarVentas, pasando null para los otros filtros
                    ventas = venta.filtrarVentas(fechaInicio, fechaFin, null, null, null);
                    
                    // Guardar fechas para el formulario
                    request.setAttribute("fechaInicio", fechaInicioStr);
                    request.setAttribute("fechaFin", fechaFinStr);
                } catch (Exception e) {
                    logger.log(Level.SEVERE, "Error al filtrar ventas por fecha", e);
                    try {
                        ventas = venta.obtenerTodasLasVentas();
                    } catch (ControllerException ex) {
                        Logger.getLogger(SVReporte.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            } else {
                // Si no hay filtros, obtener todas las ventas
                ventas = venta.obtenerTodasLasVentas();
            }
            
            // Guardar las ventas en el request
            request.setAttribute("ventas", ventas);
            
            // Redireccionar al JSP
            request.getRequestDispatcher("/Reporte.jsp").forward(request, response);
            
        } catch (ControllerException ex) {
            Logger.getLogger(SVReporte.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Procesar cualquier solicitud POST de la misma manera
        doGet(request, response);
    }
}