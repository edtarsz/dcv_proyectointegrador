/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package com.mycompany.dcv.servlet;

import com.mycompany.dcventidades.CompraInsumo;
import com.mycompany.dcventidades.DetalleCompraInsumo;
import com.mycompany.dcventidades.Insumo;
import com.mycompany.dcventidades.Usuario;
import com.mycompany.dcvexceptions.ControllerException;
import com.mycompany.dcvnegocio.comprainsumo.CompraInsumoBO;
import com.mycompany.dcvnegocio.comprainsumo.ICompraInsumoBO;
import com.mycompany.dcvnegocio.detallecomprainsumo.DetalleCompraInsumoBO;
import com.mycompany.dcvnegocio.detallecomprainsumo.IDetalleCompraInsumoBO;
import com.mycompany.dcvnegocio.insumo.IInsumoBO;
import com.mycompany.dcvnegocio.insumo.InsumoBO;
import com.mycompany.dcvnegocio.usuario.IUsuarioBO;
import com.mycompany.dcvnegocio.usuario.UsuarioBO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author crist
 */
@WebServlet(name = "SVInventario", urlPatterns = {"/SVInventario"})
public class SVInventario extends HttpServlet {

    private IInsumoBO insumoBO;
    private ICompraInsumoBO compraInsumoBO;
    private IDetalleCompraInsumoBO detalleBO;
    private IUsuarioBO usuarioBO;

    @Override
    public void init() throws ServletException {
        super.init();
        insumoBO = new InsumoBO();
        compraInsumoBO = new CompraInsumoBO();
        detalleBO = new DetalleCompraInsumoBO();
        usuarioBO = new UsuarioBO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        System.out.println("✅ SVInventario.doGet ejecutado correctamente");


        try {
            List<Insumo> insumos = insumoBO.obtenerTodosLosInsumos();
            System.out.println("Cantidad de insumos: " + insumos.size());
            request.setAttribute("insumos", insumos);
            Map<Long, Double> preciosUnitarios = new HashMap<>();
            for (Insumo insumo : insumos) {
                DetalleCompraInsumo detalle = detalleBO.obtenerUltimoDetallePorInsumo(insumo.getId());
                if (detalle != null) {
                    preciosUnitarios.put(insumo.getId(), detalle.getPrecioUnitario());
                }
            }
            request.setAttribute("precios", preciosUnitarios);

            request.getRequestDispatcher("Inventario.jsp").forward(request, response);
        } catch (ControllerException e) {
            e.printStackTrace(); // ← Este imprime en consola: revisa ahí
            throw new ServletException("❌ Error interno real al cargar insumos", e); // Te permite ver el stack completo en navegador
        }

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String accion = request.getParameter("accion");

        try {
            if ("crear".equals(accion)) {
                // 1. Crear Insumo
                Insumo insumo = new Insumo();
                insumo.setNombre(request.getParameter("nombre"));
                insumo.setDescripcion(request.getParameter("descripcion"));
                insumo.setStock(Integer.parseInt(request.getParameter("stock")));
                insumo.setUnidadMedida(request.getParameter("unidadMedida"));
                insumoBO.crearInsumo(insumo);

                // 2. Crear Compra
                CompraInsumo compra = new CompraInsumo();
                compra.setFecha(new java.sql.Date(System.currentTimeMillis()));
                compra.setMotivo(request.getParameter("motivo"));

                Usuario usuario = usuarioBO.obtenerUsuarioPorId(1L);
                compra.setUsuario(usuario);


                CompraInsumo compraGuardada = compraInsumoBO.crearCompraInsumo(compra);

                // 3. Crear Detalle de Compra
                DetalleCompraInsumo detalle = new DetalleCompraInsumo();
                detalle.setCompraInsumo(compraGuardada);
                detalle.setInsumo(insumo);

                int cantidad = Integer.parseInt(request.getParameter("stock"));
                double precioUnitario = Double.parseDouble(request.getParameter("precioUnitario"));
                detalle.setCantidad(cantidad);
                detalle.setPrecioUnitario(precioUnitario);
                detalle.setSubtotal(cantidad * precioUnitario);

                detalleBO.crearDetalleCompraInsumo(detalle);

            } else if ("editar".equals(accion)) {
                Insumo editado = new Insumo();
                editado.setId(Long.parseLong(request.getParameter("id")));
                editado.setNombre(request.getParameter("nombre"));
                editado.setDescripcion(request.getParameter("descripcion"));
                editado.setStock(Integer.parseInt(request.getParameter("stock")));
                editado.setUnidadMedida(request.getParameter("unidadMedida"));
                insumoBO.actualizarInsumo(editado);

            } else if ("eliminar".equals(accion)) {
                long id = Long.parseLong(request.getParameter("id"));
                insumoBO.eliminarInsumo(id);
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Error procesando el insumo.");
            return;
        }

        response.sendRedirect("SVInventario");
    }
}
