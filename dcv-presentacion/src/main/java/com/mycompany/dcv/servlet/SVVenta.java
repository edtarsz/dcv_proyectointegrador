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
            // Obtener datos del formulario
            String metodoPago = request.getParameter("metodoPago");
            metodoPago = metodoPago.equals("1") ? "Tarjeta de débito" : "Efectivo";
            boolean requiereEnvio = "si".equalsIgnoreCase(request.getParameter("envio"));
            String direccionEnvio = request.getParameter("direccionEnvio");

            // Validar dirección de envío si es requerida
            if (requiereEnvio && (direccionEnvio == null || direccionEnvio.trim().isEmpty())) {
                throw new Exception("La dirección de envío es requerida");
            }

            // Obtener carrito y cliente de la sesión
            HttpSession session = request.getSession();
            List<DetalleVenta> carrito = (List<DetalleVenta>) session.getAttribute("carrito");
            Cliente cliente = (Cliente) session.getAttribute("cliente");

            if (carrito == null || carrito.isEmpty()) {
                throw new Exception("El carrito está vacío");
            }
            if (cliente == null) {
                throw new Exception("No se encontró información del cliente");
            }

            // Crear venta
            Venta venta = new Venta();
            venta.setFecha(Date.valueOf(LocalDate.now()));
            venta.setEstado("En proceso");
            venta.setMetodoPago(metodoPago);
            venta.setCliente(cliente);

            // Calcular total
            double totalVenta = 0.0;
            for (DetalleVenta detalle : carrito) {
                detalle.setVenta(venta);
                totalVenta += detalle.getSubtotal();
            }

            // Manejar envío si es requerido
            if (requiereEnvio) {
                double costoEnvio = 50.0;
                Envio envio = new Envio();
                envio.setDireccionEntrega(direccionEnvio.trim());
                envio.setCliente(cliente);
                envio.setCosto(costoEnvio);
                envio.setVenta(venta);

                List<Envio> envios = new ArrayList<>();
                envios.add(envio);
                venta.setEnvios(envios);
                totalVenta += costoEnvio;
            }

            venta.setTotal(totalVenta);
            venta.setDetallesVenta(new ArrayList<>(carrito));

            // Guardar en la base de datos
            ventaBO.crearVenta(venta);

            // Limpiar sesión
            session.removeAttribute("carrito");
            session.removeAttribute("cliente");
            // Redirigir a confirmación
            response.sendRedirect("SVPedidos?mensaje=VentaRegistrada");
            
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "Error al procesar la venta: " + e.getMessage());
            request.getRequestDispatcher("Venta.jsp").forward(request, response);
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
