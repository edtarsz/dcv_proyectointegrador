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
 * @author Ramos
 */
@WebServlet(name = "SVCarrito", urlPatterns = {"/SVCarrito"})
public class SVCarrito extends HttpServlet {
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            HttpSession sesion = request.getSession();
            List<DetalleVenta> carrito = (List<DetalleVenta>) sesion.getAttribute("carrito");
            
            if (carrito == null) {
                carrito = new ArrayList<>();
                sesion.setAttribute("carrito", carrito);
            }
            
            request.getRequestDispatcher("Carrito.jsp").forward(request, response);
        } catch (Exception e) {
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error al cargar el carrito");
        }
    }

     @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();
        JsonObject jsonResponse = new JsonObject();

        try {
            // Obtener parámetros
            long idProducto = Long.parseLong(request.getParameter("idProducto"));
            String nombre = request.getParameter("nombre");
            String descripcion = request.getParameter("descripcion");
            double precio = Double.parseDouble(request.getParameter("precio"));
            int cantidad = Integer.parseInt(request.getParameter("cantidad"));
            String detalles = request.getParameter("detalles");
            String extra = request.getParameter("extra");

            // Crear el producto
            Producto producto = new Producto(idProducto, nombre, descripcion, precio);

            // Crear el detalle de venta
            DetalleVenta detalleVenta = new DetalleVenta();
            detalleVenta.setProducto(producto);
            detalleVenta.setCantidad(cantidad);
            detalleVenta.setPrecioUnitario(precio);
            detalleVenta.setSubtotal(cantidad * precio);
            detalleVenta.setEsPersonalizado(true);
            detalleVenta.setPersonalizacion(detalles + "\nExtra: " + extra);

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
            
        } catch (Exception e) {
            jsonResponse.addProperty("success", false);
            jsonResponse.addProperty("message", "Error al agregar al carrito: " + e.getMessage());
        }
        
        out.print(jsonResponse.toString());
        out.flush();
    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("application/json");
        PrintWriter out = response.getWriter();
        JsonObject jsonResponse = new JsonObject();
        
        try {
            long idProducto = Long.parseLong(request.getParameter("idProducto"));
            HttpSession sesion = request.getSession();
            List<DetalleVenta> carrito = (List<DetalleVenta>) sesion.getAttribute("carrito");

            if (carrito != null) {
                carrito.removeIf(detalle -> detalle.getProducto().getId() == idProducto);
                sesion.setAttribute("carrito", carrito);
                jsonResponse.addProperty("success", true);
                jsonResponse.addProperty("message", "Producto eliminado");
            }
        } catch (Exception e) {
            jsonResponse.addProperty("success", false);
            jsonResponse.addProperty("message", e.getMessage());
        }
        
        out.print(jsonResponse.toString());
    }
    
    @Override
protected void doPut(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
    response.setContentType("application/json");
    response.setCharacterEncoding("UTF-8");
    PrintWriter out = response.getWriter();
    JsonObject jsonResponse = new JsonObject();
    
    try {
        long idProducto = Long.parseLong(request.getParameter("idProducto"));
        int nuevaCantidad = Integer.parseInt(request.getParameter("nuevaCantidad"));

        HttpSession sesion = request.getSession();
        List<DetalleVenta> carrito = (List<DetalleVenta>) sesion.getAttribute("carrito");

        if (carrito != null) {
            for (DetalleVenta detalle : carrito) {
                if (detalle.getProducto().getId() == idProducto) {
                    detalle.setCantidad(nuevaCantidad);
                    detalle.setSubtotal(nuevaCantidad * detalle.getPrecioUnitario());
                    break;
                }
            }
            sesion.setAttribute("carrito", carrito);
            jsonResponse.addProperty("success", true);
            jsonResponse.addProperty("message", "Cantidad actualizada");
        }
    } catch (Exception e) {
        jsonResponse.addProperty("success", false);
        jsonResponse.addProperty("message", e.getMessage());
    }
    
    out.print(jsonResponse.toString());
    out.flush();
}
}