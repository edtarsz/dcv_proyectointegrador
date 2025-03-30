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
                double precio = Double.parseDouble(request.getParameter("precio"));
                int cantidad = Integer.parseInt(request.getParameter("cantidad"));
                String detalles = request.getParameter("detalles");
                String extra = request.getParameter("extra");

                // Crear producto
                Producto producto = new Producto(idProducto, nombre, descripcion, precio);

                // Crear detalle de venta (sin Venta asociada aún)
                DetalleVenta detalleVenta = new DetalleVenta();
                detalleVenta.setProducto(producto);
                detalleVenta.setCantidad(cantidad);
                detalleVenta.setPrecioUnitario(precio);
                detalleVenta.setSubtotal(cantidad * precio);
                detalleVenta.setEsPersonalizado(true);
                detalleVenta.setPersonalizacion(detalles + (extra != null && !extra.isEmpty() ? "\nExtra: " + extra : ""));

                // Obtener o crear el carrito en la sesión
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

    // ... otros métodos del servlet ...
}