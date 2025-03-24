/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package com.mycompany.dcv.servlet;

import com.mycompany.dcventidades.Cliente;
import com.mycompany.dcvnegocio.cliente.ClienteBO;
import com.mycompany.dcvnegocio.cliente.IClienteBO;
import com.mycompany.dcvnegocio.producto.IProductoBO;
import com.mycompany.dcvnegocio.producto.ProductoBO;
import com.mycompany.dcvnegocio.venta.IVentaBO;
import com.mycompany.dcvnegocio.venta.VentaBO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 *
 * @author crist
 */
@WebServlet(name = "SVCliente", urlPatterns = {"/SVCliente"})
public class SVCliente extends HttpServlet {

    IClienteBO clienteBO;
    IProductoBO productoBO;
    IVentaBO ventaBO;

    @Override
    public void init() throws ServletException {
        super.init();
        clienteBO = new ClienteBO();
        productoBO = new ProductoBO();
        ventaBO = new VentaBO();
    }

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet SVUsuario</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet SVUsuario at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Capturar los datos del cliente
        String nombre = request.getParameter("nombre");
        String telefono = request.getParameter("telefono");
        String correo = request.getParameter("correo");
        String direccion = request.getParameter("direccion");

        if (nombre == null || telefono == null) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Faltan datos del cliente.");
            return;
        }

        // Crear el cliente
        Cliente cliente = new Cliente(nombre, telefono, correo);
        // Al crear el cliente correctamente, redirigir a la página de venta, pasando el clienteId
        request.getSession().setAttribute("cliente", cliente);
        request.getSession().setAttribute("direccion", direccion);
        response.sendRedirect("Venta.jsp");
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
