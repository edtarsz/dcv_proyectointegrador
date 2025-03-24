package Servlets;

import com.mycompany.dcventidades.Producto;
import com.mycompany.dcvexceptions.ControllerException;
import com.mycompany.dcvnegocio.producto.IProductoBO;
import com.mycompany.dcvnegocio.producto.ProductoBO;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

@WebServlet(name = "SVProducto", urlPatterns = {"/SVProducto"})
public class SVProducto extends HttpServlet {

    private IProductoBO productoBO;

    @Override
    public void init() throws ServletException {
        super.init();
        productoBO = new ProductoBO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            // Llamamos al método del negocio/DAO para obtener la lista de productos
            List<Producto> productos = productoBO.obtenerTodosLosProductos();
            System.out.println("Productos obtenidos: " + productos);

            // Asignamos la lista de productos como atributo de la solicitud
            request.setAttribute("productos", productos);

            // Redirigimos la solicitud a la página JSP
            request.getRequestDispatcher("catalogo.jsp").forward(request, response);
        } catch (ControllerException | ServletException | IOException e) {
            // Manejo de errores: puedes redirigir a una página de error si es necesario
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Hubo un error al obtener los productos.");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            String[] idProductos = request.getParameterValues("idProducto[]");
            String[] cantidades = request.getParameterValues("cantidad[]");
            String[] descripciones = request.getParameterValues("descripcion[]");

            // Procesar cada producto
            for (int i = 0; i < idProductos.length; i++) {
                int idProducto = Integer.parseInt(idProductos[i]);
                int cantidad = Integer.parseInt(cantidades[i]);
                String descripcion = descripciones[i];

                // Lógica para guardar en la base de datos
                System.out.println("Guardando producto: ID=" + idProducto + ", cantidad=" + cantidad + ", descripción=" + descripcion);
            }

            // Redirigir a una página de confirmación
            response.sendRedirect("Datos.jsp");

        } catch (Exception e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error al guardar el pedido.");
        }
    }

}
