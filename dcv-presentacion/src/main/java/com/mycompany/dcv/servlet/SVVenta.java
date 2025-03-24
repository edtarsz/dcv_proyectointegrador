/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package com.mycompany.dcv.servlet;

import com.mycompany.dcventidades.Cliente;
import com.mycompany.dcventidades.DetalleVenta;
import com.mycompany.dcventidades.Producto;
import com.mycompany.dcventidades.Usuario;
import com.mycompany.dcventidades.Venta;
import com.mycompany.dcvnegocio.cliente.ClienteBO;
import com.mycompany.dcvnegocio.cliente.IClienteBO;
import com.mycompany.dcvnegocio.producto.IProductoBO;
import com.mycompany.dcvnegocio.producto.ProductoBO;
import com.mycompany.dcvnegocio.usuario.IUsuarioBO;
import com.mycompany.dcvnegocio.usuario.UsuarioBO;
import com.mycompany.dcvnegocio.venta.IVentaBO;
import com.mycompany.dcvnegocio.venta.VentaBO;
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
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author crist
 */
@WebServlet(name = "SVVenta", urlPatterns = {"/SVVenta"})
public class SVVenta extends HttpServlet {

    private IVentaBO ventaBO;
    private IClienteBO clienteBO;
    private IUsuarioBO usuarioBO;

    @Override
    public void init() throws ServletException {
        super.init();
        ventaBO = new VentaBO();
        new ProductoBO();
        clienteBO = new ClienteBO();
        usuarioBO = new UsuarioBO();
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
            out.println("<title>Servlet SVVenta</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet SVVenta at " + request.getContextPath() + "</h1>");
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

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Imprimir todos los parámetros de la solicitud
        request.getParameterMap().forEach((key, value) -> {
            System.out.println(key + ": " + String.join(", ", value));
        });

        // Ahora intentar leer el clienteId
        String clienteIdStr = request.getParameter("clienteId");
        if (clienteIdStr == null) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "clienteId es requerido.");
            return;
        }

        Long clienteId = Long.valueOf(clienteIdStr);
        System.out.println("clienteId recibido: " + clienteId);

        try {

            // Obtener el cliente de la base de datos utilizando clienteId
            Cliente cliente = clienteBO.obtenerClientePorId(clienteId);

            if (cliente == null) {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Cliente no encontrado.");
                return;
            }

            // Obtener la sesión del carrito (productos seleccionados)
            HttpSession session = request.getSession();
            List<Producto> carrito = (List<Producto>) session.getAttribute("carrito");

            if (carrito == null || carrito.isEmpty()) {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "El carrito está vacío.");
                return;
            }

            // Convertir los productos a detalles de venta
            List<DetalleVenta> detallesVenta = new ArrayList<>();
            for (Producto producto : carrito) {
                DetalleVenta detalle = new DetalleVenta();
                detalle.setProducto(producto);
                detalle.setCantidad(1);  // Suponiendo que la cantidad es 1 para cada producto
                detalle.setSubtotal(producto.getPrecio());  // El subtotal es el precio del producto en este caso
                detallesVenta.add(detalle);
            }

            // Obtener método de pago
            String metodoPago = request.getParameter("metodoPago");

            // Calcular el total de la venta
            double total = detallesVenta.stream().mapToDouble(DetalleVenta::getSubtotal).sum();

            // Obtener el usuarioId (por ejemplo, desde la sesión)
            Long usuarioId = 1L;
            Usuario usuario = usuarioBO.obtenerUsuarioPorId(usuarioId);

            // Crear la venta
            Venta venta = ventaBO.guardarVenta(total, metodoPago, cliente, usuario);

            // Guardar los detalles de la venta
            for (DetalleVenta detalle : detallesVenta) {
                detalle.setVenta(venta);  // Vincula cada detalle con la venta
                ventaBO.guardarDetalleVenta(venta, detalle);
            }

            // Limpiar el carrito de la sesión
            session.removeAttribute("carrito");

            // Redirigir a la página de confirmación
            response.sendRedirect("Pedidos.jsp?mensaje=VentaRegistrada");

        } catch (Exception e) {
            Logger.getLogger(SVVenta.class.getName()).log(Level.SEVERE, "Error al registrar la venta", e);
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error al procesar la venta.");
        }
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
