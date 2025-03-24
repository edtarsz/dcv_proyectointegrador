package Servlets;

import com.mycompany.dcventidades.Producto;
import com.mycompany.dcvnegocio.producto.IProductoBO;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebServlet(name = "SVProducto", urlPatterns = {"/SVProducto"})
public class SVProducto extends HttpServlet {
    
    private IProductoBO productoBO;

    private Connection getConnection() throws NamingException, SQLException {
        InitialContext ctx = new InitialContext();
        DataSource ds = (DataSource) ctx.lookup("java:comp/env/jdbc/miBD");
        return ds.getConnection();
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
    } catch (Exception e) {
        e.printStackTrace();

        // Manejo de errores: puedes redirigir a una página de error si es necesario
        response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Hubo un error al obtener los productos.");
    }
}


    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");

        try (Connection conn = getConnection()) {
            if ("updateStock".equals(action)) {
                actualizarStock(request, conn);
            } else if ("addToCart".equals(action)) {
                agregarAlCarrito(request);
                response.sendRedirect("catalogo.jsp");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        response.sendRedirect("catalogo.jsp");
    }



    private void actualizarStock(HttpServletRequest request, Connection conn) throws SQLException {
    String[] ids = request.getParameterValues("id[]");
    String[] cantidades = request.getParameterValues("cantidad[]");

    if (ids != null && cantidades != null) {
        for (int i = 0; i < ids.length; i++) {
            int id = Integer.parseInt(ids[i]);
            int cantidadComprada = Integer.parseInt(cantidades[i]);

            String sql = "UPDATE producto SET stock = stock - ? WHERE id = ?";
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setInt(1, cantidadComprada);
                stmt.setInt(2, id);
                stmt.executeUpdate();
            }
        }
    }
}


private void agregarAlCarrito(HttpServletRequest request) {
    HttpSession session = request.getSession();
    List<Map<String, Object>> carrito = (List<Map<String, Object>>) session.getAttribute("carrito");

    if (carrito == null) {
        carrito = new ArrayList<>();
    }

    // Obtener datos del producto
    int id = Integer.parseInt(request.getParameter("id"));
    String nombre = request.getParameter("nombre");
    double precio = Double.parseDouble(request.getParameter("precio"));
    int cantidad = Integer.parseInt(request.getParameter("cantidad"));

    // Verificar si el producto ya está en el carrito
    boolean existe = false;
    for (Map<String, Object> item : carrito) {
        if ((int) item.get("id") == id) {
            int nuevaCantidad = (int) item.get("cantidad") + cantidad;
            item.put("cantidad", nuevaCantidad);
            existe = true;
            break;
        }
    }

    // Si no existe, agregarlo
    if (!existe) {
        Map<String, Object> producto = new HashMap<>();
        producto.put("id", id);
        producto.put("nombre", nombre);
        producto.put("precio", precio);
        producto.put("cantidad", cantidad);
        carrito.add(producto);
    }

    session.setAttribute("carrito", carrito);
}

}
