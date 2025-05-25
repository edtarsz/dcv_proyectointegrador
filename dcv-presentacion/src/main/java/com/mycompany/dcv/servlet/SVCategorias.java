package com.mycompany.dcv.servlet;

// Asegúrate de que esta sea la importación correcta para Gson en tu proyecto.
// La estándar es com.google.gson.Gson;
import com.firework.gson.Gson;
import com.mycompany.dcventidades.Categoria;
import com.mycompany.dcventidades.Producto;
import com.mycompany.dcvnegocio.categoria.CategoriaBO;
import com.mycompany.dcvnegocio.categoria.ICategoriaBO;
import com.mycompany.dcvexceptions.ControllerException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

@WebServlet(name = "SVCategorias", urlPatterns = {"/SVCategorias"})
public class SVCategorias extends HttpServlet {

    private ICategoriaBO categoriaBO;
    private Gson gson = new Gson();
    private static final Logger LOGGER = Logger.getLogger(SVCategorias.class.getName());

    @Override
    public void init() throws ServletException {
        super.init();
        // La inicialización del BO que crea su propia conexión y DAO se mantiene por ahora
        // En un escenario ideal, la gestión de la conexión/EntityManagerFactory sería más centralizada
        this.categoriaBO = new CategoriaBO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();

        String action = request.getParameter("action");
        Map<String, Object> jsonResponse = new HashMap<>();

        try {
            if (action == null) {
                jsonResponse.put("success", false);
                jsonResponse.put("message", "Acción no especificada.");
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                out.print(gson.toJson(jsonResponse));
                out.flush();
                return;
            }

            switch (action) {
                case "getAll":
                    List<Categoria> categorias = categoriaBO.obtenerTodasLasCategorias();
                    if (categorias != null) {
                        jsonResponse.put("success", true);
                        jsonResponse.put("categorias", categorias);
                    } else { // El BO debería devolver lista vacía, no null, pero por si acaso.
                        jsonResponse.put("success", false);
                        jsonResponse.put("message", "No se pudieron obtener las categorías.");
                    }
                    break;
                case "getProductosPorCategoria":
                    String idCategoriaStr = request.getParameter("idCategoria");
                    if (idCategoriaStr == null || idCategoriaStr.trim().isEmpty()) {
                        jsonResponse.put("success", false);
                        jsonResponse.put("message", "ID de categoría no proporcionado.");
                        response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                    } else {
                        long idCategoria = Long.parseLong(idCategoriaStr);
                        List<Producto> productos = categoriaBO.obtenerProductosPorCategoria(idCategoria);
                        jsonResponse.put("success", true); // Asume éxito si no hay excepción
                        jsonResponse.put("productos", productos != null ? productos : List.of());
                    }
                    break;
                default:
                    jsonResponse.put("success", false);
                    jsonResponse.put("message", "Acción GET no reconocida: " + action);
                    response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                    break;
            }
        } catch (NumberFormatException e) {
            LOGGER.log(Level.WARNING, "Error de formato de número en GET", e);
            jsonResponse.put("success", false);
            jsonResponse.put("message", "Error en el formato del ID: " + e.getMessage());
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        } catch (ControllerException e) {
            LOGGER.log(Level.SEVERE, "Error de controlador en GET", e);
            jsonResponse.put("success", false);
            jsonResponse.put("message", e.getMessage());
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST); // O SC_INTERNAL_SERVER_ERROR según el caso
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error inesperado en GET", e);
            jsonResponse.put("success", false);
            jsonResponse.put("message", "Error inesperado en el servidor: " + e.getMessage());
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
        out.print(gson.toJson(jsonResponse));
        out.flush();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();

        String action = request.getParameter("action");
        Map<String, Object> jsonResponse = new HashMap<>();

        try {
            if (action == null) {
                jsonResponse.put("success", false);
                jsonResponse.put("message", "Acción no especificada.");
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                out.print(gson.toJson(jsonResponse));
                out.flush();
                return;
            }

            String nombre = request.getParameter("nombre");
            String descripcion = request.getParameter("descripcion"); // Puede ser null o vacío

            switch (action) {
                case "add":
                    if (nombre == null || nombre.trim().isEmpty()) {
                        jsonResponse.put("success", false);
                        jsonResponse.put("message", "El nombre de la categoría es obligatorio.");
                        response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                    } else {
                        Categoria nuevaCategoria = new Categoria();
                        nuevaCategoria.setNombre(nombre.trim());
                        nuevaCategoria.setDescripcion(descripcion != null ? descripcion.trim() : "");

                        Categoria categoriaGuardada = categoriaBO.agregarCategoria(nuevaCategoria);
                        jsonResponse.put("success", true);
                        jsonResponse.put("message", "Categoría agregada exitosamente.");
                        jsonResponse.put("categoria", categoriaGuardada); // Devolver la categoría con ID
                    }
                    break;
                case "edit":
                    String idEditStr = request.getParameter("id");
                    if (idEditStr == null || idEditStr.trim().isEmpty()) {
                        jsonResponse.put("success", false);
                        jsonResponse.put("message", "ID de categoría no proporcionado para editar.");
                        response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                    } else if (nombre == null || nombre.trim().isEmpty()) {
                        jsonResponse.put("success", false);
                        jsonResponse.put("message", "El nombre de la categoría es obligatorio para editar.");
                        response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                    } else {
                        long idEdit = Long.parseLong(idEditStr);
                        Categoria categoriaAEditar = new Categoria(); // O podrías hacer categoriaBO.obtenerCategoriaPorId(idEdit) primero si el BO no lo hace
                        categoriaAEditar.setId(idEdit);
                        categoriaAEditar.setNombre(nombre.trim());
                        categoriaAEditar.setDescripcion(descripcion != null ? descripcion.trim() : "");

                        Categoria categoriaActualizada = categoriaBO.actualizarCategoria(categoriaAEditar);
                        jsonResponse.put("success", true);
                        jsonResponse.put("message", "Categoría actualizada exitosamente.");
                        jsonResponse.put("categoria", categoriaActualizada);
                    }
                    break;
                case "delete":
                    String idDeleteStr = request.getParameter("id");
                    if (idDeleteStr == null || idDeleteStr.trim().isEmpty()) {
                        jsonResponse.put("success", false);
                        jsonResponse.put("message", "ID de categoría no proporcionado para eliminar.");
                        response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                    } else {
                        long idDelete = Long.parseLong(idDeleteStr);
                        boolean eliminada = categoriaBO.eliminarCategoria(idDelete);
                        if (eliminada) {
                            jsonResponse.put("success", true);
                            jsonResponse.put("message", "Categoría eliminada exitosamente.");
                        } else {
                            // El BO lanzará ControllerException si está en uso, que se captura abajo.
                            // Si el BO devuelve false por "no encontrada", se podría manejar aquí.
                            // Pero dado el diseño actual del BO, si no se elimina por no encontrarla, el DAO podría lanzar error.
                            // La lógica de "no encontrada" vs "en uso" se maneja mejor con excepciones específicas o códigos.
                            jsonResponse.put("success", false);
                            jsonResponse.put("message", "No se pudo eliminar la categoría (podría no existir).");
                        }
                    }
                    break;
                default:
                    jsonResponse.put("success", false);
                    jsonResponse.put("message", "Acción POST no reconocida: " + action);
                    response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                    break;
            }
        } catch (NumberFormatException e) {
            LOGGER.log(Level.WARNING, "Error de formato de número en POST", e);
            jsonResponse.put("success", false);
            jsonResponse.put("message", "Error en el formato del ID: " + e.getMessage());
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        } catch (ControllerException e) {
            LOGGER.log(Level.WARNING, "Error de controlador en POST: " + e.getMessage());
            jsonResponse.put("success", false);
            jsonResponse.put("message", e.getMessage()); // Mensaje de la excepción del BO
            // Podrías establecer un código de estado HTTP más específico si el BO lo indica
            // por ejemplo, si es un conflicto (409) por nombre duplicado.
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error inesperado en POST", e);
            jsonResponse.put("success", false);
            jsonResponse.put("message", "Error inesperado en el servidor: " + e.getMessage());
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
        out.print(gson.toJson(jsonResponse));
        out.flush();
    }

    @Override
    public String getServletInfo() {
        return "Servlet para gestionar operaciones CRUD de Categorías utilizando CategoriaBO";
    }

    @Override
    public void destroy() {
        // Si tu CategoriaBO necesita cerrar recursos (como el EntityManagerFactory en Conexion),
        // deberías hacerlo aquí.
         if (categoriaBO != null && categoriaBO instanceof CategoriaBO) {
             ((CategoriaBO) categoriaBO).cerrarConexion();
         }
        super.destroy();

    }
}
