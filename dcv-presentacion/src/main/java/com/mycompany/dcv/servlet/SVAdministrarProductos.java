/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package com.mycompany.dcv.servlet;

import com.mycompany.dcventidades.Categoria;
import com.mycompany.dcventidades.Producto;
import com.mycompany.dcvnegocio.categoria.CategoriaBO;
import com.mycompany.dcvnegocio.categoria.ICategoriaBO;
import com.mycompany.dcvnegocio.producto.IProductoBO;
import com.mycompany.dcvnegocio.producto.ProductoBO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author crist
 */
@WebServlet(name = "SVAdministrarProductos", urlPatterns = {"/SVAdministrarProductos"})
public class SVAdministrarProductos extends HttpServlet {

    private IProductoBO productoBO;
    private ICategoriaBO categoriaBO;

    @Override
    public void init() throws ServletException {
        productoBO = new ProductoBO();
        categoriaBO= new CategoriaBO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            List<Producto> productos = productoBO.obtenerTodosLosProductos();
            List<Categoria> categorias = categoriaBO.obtenerTodas();
            request.setAttribute("categorias", categorias);
            request.setAttribute("productos", productos);
            request.getRequestDispatcher("Productos.jsp").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error al cargar productos.");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String accion = request.getParameter("accion");
        try {
            if ("crear".equals(accion)) {
                Producto nuevo = new Producto();
                nuevo.setNombre(request.getParameter("nombre"));
                nuevo.setDescripcion(request.getParameter("descripcion"));
                nuevo.setPrecio(Double.parseDouble(request.getParameter("precio")));
                nuevo.setInsumos(new ArrayList<>());
                String categoriaIdStr = request.getParameter("categoriaId");
                if (categoriaIdStr != null && !categoriaIdStr.isEmpty()) {
                    long categoriaId = Long.parseLong(categoriaIdStr);
                    Categoria categoria = categoriaBO.obtenerPorId(categoriaId);
                    List<Categoria> categorias = new ArrayList<>();
                    categorias.add(categoria);
                    nuevo.setCategorias(categorias); // <- Este método debe existir en tu entidad Producto
                }

                productoBO.crearProducto(nuevo);

            } else if ("editar".equals(accion)) {
                long id = Long.parseLong(request.getParameter("id"));
                Producto producto = productoBO.obtenerProductoPorId(id);
                producto.setNombre(request.getParameter("nombre"));
                producto.setDescripcion(request.getParameter("descripcion"));
                producto.setPrecio(Double.parseDouble(request.getParameter("precio")));
                String[] categoriaIds = request.getParameterValues("categoriaId");
                if (categoriaIds != null) {
                    List<Categoria> categorias = new ArrayList<>();
                    for (String catId : categoriaIds) {
                        Categoria categoria = categoriaBO.obtenerPorId(Long.parseLong(catId));
                        categorias.add(categoria);
                    }
                    producto.setCategorias(categorias);
                }

                productoBO.actualizarProducto(producto);
                

            } else if ("eliminar".equals(accion)) {
                 long id = Long.parseLong(request.getParameter("id"));
                Producto producto = productoBO.obtenerProductoPorId(id);
                producto.setActivo(false);  // solo lo marcamos como inactivo
                productoBO.actualizarProducto(producto);
            }

            response.sendRedirect("SVAdministrarProductos");
        } catch (Exception e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Error al procesar producto.");
        }
    }
}
