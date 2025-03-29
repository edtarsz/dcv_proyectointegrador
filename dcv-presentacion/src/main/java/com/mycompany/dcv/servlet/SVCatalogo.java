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
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            // Aquí obtienes los productos de la base de datos
//            List<Producto> productos = obtenerProductos(); // Implementa este método
//            request.setAttribute("productos", productos);
//            request.getRequestDispatcher("catalogo.jsp").forward(request, response);
        } catch (Exception e) {
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error al cargar el catálogo");
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
}