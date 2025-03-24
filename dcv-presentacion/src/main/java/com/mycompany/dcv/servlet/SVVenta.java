/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package com.mycompany.dcv.servlet;

import com.mycompany.dcventidades.Cliente;
import com.mycompany.dcventidades.DetalleVenta;
import com.mycompany.dcventidades.Envio;
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
import java.sql.Date;
import java.time.LocalDate;
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
    private IProductoBO productoBO;
    private IClienteBO clienteBO;
    private IUsuarioBO usuarioBO;

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
    public void init() throws ServletException {
        super.init();
        ventaBO = new VentaBO();
        productoBO = new ProductoBO();
        clienteBO = new ClienteBO();
        usuarioBO = new UsuarioBO();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            // Obtener parámetros del formulario
            String metodoPago = request.getParameter("metodoPago");
            String envioStr = request.getParameter("envio");
            boolean requiereEnvio = "si".equalsIgnoreCase(envioStr) || "s".equalsIgnoreCase(envioStr);

            // Obtener la sesión del carrito
            HttpSession session = request.getSession();
            List<Producto> carrito = (List<Producto>) session.getAttribute("carrito");

            if (carrito == null || carrito.isEmpty()) {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "El carrito está vacío.");
                return;
            }

            // Crear cliente
            Cliente cliente = (Cliente) request.getSession().getAttribute("cliente");

            // Crear usuario (simplificado)
            Usuario usuario = new Usuario("cliente03", "1234", "Admin", new ArrayList<>(), new ArrayList<>(), new ArrayList<>());
            usuarioBO.crearUsuario(usuario);

            // Crear venta
            Venta venta = new Venta();

            // Crear detalles de venta
            List<DetalleVenta> detallesVenta = new ArrayList<>();
            List<Envio> envios = new ArrayList<>();
            double totalDetalles = 0;

            for (Producto producto : carrito) {
                DetalleVenta detalle = new DetalleVenta(
                        1, // ID de detalle (puede ser generado automáticamente)
                        producto.getPrecio(),
                        producto.getPrecio(), // Subtotal (asumiendo cantidad 1)
                        producto,
                        false, // No personalizado por defecto
                        "", // Sin personalización
                        venta
                );
                detallesVenta.add(detalle);
                totalDetalles += detalle.getSubtotal();
            }

            // Configurar venta
            venta.setDetallesVenta(detallesVenta);
            venta.setFecha(Date.valueOf(LocalDate.now()));
            venta.setUsuario(usuario);
            venta.setCliente(cliente);
            venta.setEstado("En proceso");
            venta.setMetodoPago(metodoPago);

            // Manejar envío si es requerido
            if (requiereEnvio) {
                double costoEnvio = 50.0; // Costo de envío predeterminado
                Envio envio = new Envio();
                envio.setDireccionEntrega( (String) request.getSession().getAttribute("direccion"));
                envio.setCliente(cliente);
                envio.setCosto(costoEnvio);
                envio.setVenta(venta);
                envios.add(envio);
                venta.setEnvios(envios);
                venta.setTotal(totalDetalles + costoEnvio);
            } else {
                venta.setTotal(totalDetalles);
            }
            
            

            // En un escenario real, aquí guardarías la venta en la base de datos
            // Por ahora, solo imprimimos los detalles
            System.out.println("Venta creada:");
            ventaBO.crearVenta(venta);
            System.out.println("Cliente: " + cliente.getNombreCompleto());
            System.out.println("Total: " + venta.getTotal());
            System.out.println("Método de Pago: " + metodoPago);

            // Limpiar el carrito
            session.removeAttribute("carrito");
            session.removeAttribute("cliente");
            session.removeAttribute("direccion");

            // Redirigir a página de confirmación
            response.sendRedirect("Pedidos.jsp?mensaje=VentaRegistrada");

        } catch (Exception e) {
            Logger.getLogger(SVVenta.class.getName()).log(Level.SEVERE, "Error al registrar la venta", e);
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error al procesar la venta: " + e.getMessage());
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
