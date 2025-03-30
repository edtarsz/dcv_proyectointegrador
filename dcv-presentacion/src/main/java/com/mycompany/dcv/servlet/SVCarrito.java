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
            String action = request.getParameter("action");
            HttpSession sesion = request.getSession();
            List<DetalleVenta> carrito = (List<DetalleVenta>) sesion.getAttribute("carrito");

            if (carrito == null) {
                throw new Exception("Carrito no encontrado");
            }

            switch (action) {
                case "deleteItem":
                    handleDeleteItem(carrito, request, jsonResponse);
                    break;
                case "updateQuantity":
                    handleUpdateQuantity(carrito, request, jsonResponse);
                    break;
                default:
                    jsonResponse.addProperty("success", false);
                    jsonResponse.addProperty("message", "Acción no válida");
            }

            sesion.setAttribute("carrito", carrito);

        } catch (Exception e) {
            jsonResponse.addProperty("success", false);
            jsonResponse.addProperty("message", "Error: " + e.getMessage());
            e.printStackTrace();
        }

        out.print(jsonResponse.toString());
        out.flush();
    }

    private void handleDeleteItem(List<DetalleVenta> carrito, HttpServletRequest request, JsonObject jsonResponse) {
        try {
            long idProducto = Long.parseLong(request.getParameter("idProducto"));
            boolean removed = carrito.removeIf(detalle -> detalle.getProducto().getId() == idProducto);

            if (removed) {
                jsonResponse.addProperty("success", true);
                jsonResponse.addProperty("message", "Producto eliminado");
                jsonResponse.addProperty("carritoSize", carrito.size());
            } else {
                jsonResponse.addProperty("success", false);
                jsonResponse.addProperty("message", "Producto no encontrado");
            }
        } catch (NumberFormatException e) {
            jsonResponse.addProperty("success", false);
            jsonResponse.addProperty("message", "ID de producto inválido");
        }
    }

    private void handleUpdateQuantity(List<DetalleVenta> carrito, HttpServletRequest request, JsonObject jsonResponse) {
        try {
            long idProducto = Long.parseLong(request.getParameter("idProducto"));
            int nuevaCantidad = Integer.parseInt(request.getParameter("nuevaCantidad"));

            if (nuevaCantidad < 1) {
                throw new IllegalArgumentException("La cantidad debe ser mayor a 0");
            }

            boolean updated = false;
            for (DetalleVenta detalle : carrito) {
                if (detalle.getProducto().getId() == idProducto) {
                    detalle.setCantidad(nuevaCantidad);
                    detalle.setSubtotal(nuevaCantidad * detalle.getPrecioUnitario());
                    updated = true;
                    break;
                }
            }

            if (updated) {
                jsonResponse.addProperty("success", true);
                jsonResponse.addProperty("message", "Cantidad actualizada");
            } else {
                jsonResponse.addProperty("success", false);
                jsonResponse.addProperty("message", "Producto no encontrado");
            }
        } catch (NumberFormatException e) {
            jsonResponse.addProperty("success", false);
            jsonResponse.addProperty("message", "Valores numéricos inválidos");
        } catch (IllegalArgumentException e) {
            jsonResponse.addProperty("success", false);
            jsonResponse.addProperty("message", e.getMessage());
        }
    }
}
