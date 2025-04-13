/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package com.mycompany.dcv.servlet;

import com.firework.gson.JsonObject;
import com.mycompany.dcventidades.DetalleVenta;
import com.mycompany.dcventidades.Producto;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author haloa
 */
@WebServlet(name = "SVCatalogo", urlPatterns = {"/SVCatalogo"})
public class SVCatalogo extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();
        JsonObject jsonResponse = new JsonObject();

        try {
            String action = request.getParameter("action");

            if ("addToCart".equals(action)) {
                // Obtener parámetros
                long idProducto = Long.parseLong(request.getParameter("idProducto"));
                String nombre = request.getParameter("nombre");
                String descripcion = request.getParameter("descripcion");
                double precioBase = Double.parseDouble(request.getParameter("precio"));
                int cantidad = Integer.parseInt(request.getParameter("cantidad"));
                String detalles = request.getParameter("detalles");
                String extraStr = request.getParameter("extra");

                // Procesar costo extra
                double costoExtra = 0.0;
                try {
                    if (extraStr != null && !extraStr.trim().isEmpty()) {
                        costoExtra = Double.parseDouble(extraStr.trim());
                    }
                } catch (NumberFormatException e) {
                    costoExtra = 0.0; // Ignorar si el extra no es numérico
                }

                // Precio final con extra
                double precioFinal = precioBase + costoExtra;
                double subtotal = precioFinal * cantidad;

                // Crear producto
                Producto producto = new Producto(idProducto, nombre, descripcion, precioBase); // Guardas el precio base original

                // Crear detalle de venta
                DetalleVenta detalleVenta = new DetalleVenta();
                detalleVenta.setProducto(producto);
                detalleVenta.setCantidad(cantidad);
                detalleVenta.setPrecioUnitario(precioFinal);
                detalleVenta.setSubtotal(subtotal);
                detalleVenta.setEsPersonalizado(true);
                detalleVenta.setPersonalizacion(detalles + (costoExtra > 0 ? "\nExtra: $" + costoExtra : ""));

                // Obtener o crear carrito
                HttpSession sesion = request.getSession();
                List<DetalleVenta> carrito = (List<DetalleVenta>) sesion.getAttribute("carrito");
                if (carrito == null) {
                    carrito = new ArrayList<>();
                }

                carrito.add(detalleVenta);
                sesion.setAttribute("carrito", carrito);

                jsonResponse.addProperty("success", true);
                jsonResponse.addProperty("message", "Producto agregado al carrito");
                jsonResponse.addProperty("carritoSize", carrito.size());
            } else {
                jsonResponse.addProperty("success", false);
                jsonResponse.addProperty("message", "Acción no reconocida");
            }

        } catch (NumberFormatException e) {
            jsonResponse.addProperty("success", false);
            jsonResponse.addProperty("message", "Error en el formato de los datos");
            e.printStackTrace();
        } catch (Exception e) {
            jsonResponse.addProperty("success", false);
            jsonResponse.addProperty("message", "Error al procesar la solicitud: " + e.getMessage());
            e.printStackTrace();
        }

        out.print(jsonResponse.toString());
        out.flush();
    }

}
