package com.mycompany.dcv.servlet;

import com.firework.gson.JsonObject;
import com.mycompany.dcventidades.Cliente;
import com.mycompany.dcventidades.DetalleVenta;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.util.List;

@WebServlet(name = "SVDatos", urlPatterns = {"/SVDatos"})
public class SVDatos extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        List<DetalleVenta> carrito = (List<DetalleVenta>) session.getAttribute("carrito");

        if (carrito == null || carrito.isEmpty()) {
            response.sendRedirect("Carrito.jsp");
            return;
        }

        request.getRequestDispatcher("Datos.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");
        HttpSession session = request.getSession();

        try {
            if ("procesarDatos".equals(action)) {
                // Obtener datos del formulario
                String nombreCompleto = request.getParameter("nombre");
                String telefono = request.getParameter("telefono");
                String correo = request.getParameter("correo");

                // Validar datos
                if (nombreCompleto == null || nombreCompleto.trim().isEmpty()
                        || telefono == null || !telefono.matches("[0-9]{10}")
                        || correo == null || !correo.contains("@")) {

                    throw new Exception("Todos los campos son obligatorios y deben ser válidos");
                }

                // Crear objeto Cliente
                Cliente cliente = new Cliente(
                        nombreCompleto.trim(),
                        telefono.trim(),
                        correo.trim()
                );

                // Verificar carrito
                List<DetalleVenta> carrito = (List<DetalleVenta>) session.getAttribute("carrito");
                if (carrito == null || carrito.isEmpty()) {
                    throw new Exception("No hay productos en el carrito");
                }

                // Guardar cliente en sesión
                session.setAttribute("cliente", cliente);

                // Redireccionar a Venta.jsp
                response.sendRedirect("Venta.jsp");
                return;
            }

            // Si llegamos aquí, la acción no fue válida
            throw new Exception("Acción no válida");

        } catch (Exception e) {
            // En caso de error, redirigir de vuelta a Datos.jsp con mensaje de error
            request.setAttribute("error", e.getMessage());
            request.getRequestDispatcher("Datos.jsp").forward(request, response);
        }
    }

    @Override
    public String getServletInfo() {
        return "Servlet para procesar datos del cliente";
    }
}
