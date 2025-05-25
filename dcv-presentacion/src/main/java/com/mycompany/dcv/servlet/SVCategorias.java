package com.mycompany.dcv.servlet;

// Importa la versión de Gson que estés usando (asumo com.google.gson.Gson ahora)
import com.firework.gson.Gson;
import com.mycompany.dcventidades.Categoria;
import com.mycompany.dcventidades.Producto;
import com.mycompany.dcvnegocio.categoria.ICategoriaBO; // Usar la interfaz
import com.mycompany.dcvnegocio.categoria.CategoriaBO;
import com.mycompany.dcvexceptions.ControllerException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList; // Necesario para crear las listas de mapas
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

@WebServlet(name = "SVCategorias", urlPatterns = {"/SVCategorias"})
public class SVCategorias extends HttpServlet {

    private ICategoriaBO categoriaBO;
    private Gson gson = new Gson(); // Gson se usa para serializar la estructura de Map final
    private static final Logger LOGGER = Logger.getLogger(SVCategorias.class.getName());

    @Override
    public void init() throws ServletException {
        super.init();
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
                    List<Categoria> categoriasEntidades = categoriaBO.obtenerTodas();
                    List<Map<String, Object>> categoriasParaJson = new ArrayList<>(); // Lista de Mapas
                    if (categoriasEntidades != null) {
                        for (Categoria cat : categoriasEntidades) {
                            Map<String, Object> catMap = new HashMap<>();
                            catMap.put("id", cat.getId());
                            catMap.put("nombre", cat.getNombre());
                            catMap.put("descripcion", cat.getDescripcion());
                            // NO AÑADIMOS cat.getProductos() aquí para evitar la recursión
                            categoriasParaJson.add(catMap);
                        }
                        jsonResponse.put("success", true);
                        jsonResponse.put("categorias", categoriasParaJson); // Envía la lista de Mapas
                    } else {
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
                        List<Producto> productosEntidades = categoriaBO.obtenerProductosPorCategoria(idCategoria);
                        List<Map<String, Object>> productosParaJson = new ArrayList<>(); // Lista de Mapas

                        if (productosEntidades != null) {
                            for (Producto prod : productosEntidades) {
                                Map<String, Object> prodMap = new HashMap<>();
                                prodMap.put("id", prod.getId());
                                prodMap.put("nombre", prod.getNombre());
                                prodMap.put("descripcion", prod.getDescripcion());
                                prodMap.put("precio", prod.getPrecio());
                                // NO AÑADIMOS la lista completa de prod.getCategorias() aquí para evitar recursión
                                // Si necesitas algún dato de la categoría del producto (si fuera uno-a-muchos), lo añadirías.
                                // Como es muchos-a-muchos, y este endpoint es para PRODUCTOS de UNA categoría,
                                // usualmente no necesitas reenviar la lista de todas las categorías a las que pertenece cada producto.
                                productosParaJson.add(prodMap);
                            }
                        }
                        // Incluso si productosParaJson está vacío (porque no hay productos), la operación fue exitosa
                        jsonResponse.put("success", true);
                        jsonResponse.put("productos", productosParaJson);
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
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
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
            String descripcion = request.getParameter("descripcion");

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
                        
                        // Preparamos la respuesta con los datos básicos de la categoría guardada
                        Map<String, Object> catMap = new HashMap<>();
                        catMap.put("id", categoriaGuardada.getId());
                        catMap.put("nombre", categoriaGuardada.getNombre());
                        catMap.put("descripcion", categoriaGuardada.getDescripcion());

                        jsonResponse.put("success", true);
                        jsonResponse.put("message", "Categoría agregada exitosamente.");
                        jsonResponse.put("categoria", catMap); // Devolver el mapa simple
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
                        Categoria categoriaAEditar = new Categoria();
                        categoriaAEditar.setId(idEdit);
                        categoriaAEditar.setNombre(nombre.trim());
                        categoriaAEditar.setDescripcion(descripcion != null ? descripcion.trim() : "");

                        Categoria categoriaActualizada = categoriaBO.actualizarCategoria(categoriaAEditar);
                        
                        Map<String, Object> catMap = new HashMap<>();
                        catMap.put("id", categoriaActualizada.getId());
                        catMap.put("nombre", categoriaActualizada.getNombre());
                        catMap.put("descripcion", categoriaActualizada.getDescripcion());

                        jsonResponse.put("success", true);
                        jsonResponse.put("message", "Categoría actualizada exitosamente.");
                        jsonResponse.put("categoria", catMap); // Devolver el mapa simple
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
                            // Esta rama podría no alcanzarse si el BO siempre lanza ControllerException
                            // en caso de no poder eliminar (ej. por estar en uso o no existir).
                            // Si el BO devuelve false por "no encontrada" sin lanzar excepción, esto lo cubriría.
                            jsonResponse.put("success", false);
                            jsonResponse.put("message", "No se pudo eliminar la categoría (verifique que no esté en uso o que exista).");
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
            LOGGER.log(Level.WARNING, "Error de controlador en POST: " + e.getMessage(), e);
            jsonResponse.put("success", false);
            jsonResponse.put("message", e.getMessage()); 
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
        // Descomenta y adapta esto si tu CategoriaBO o Conexion tienen un método para liberar el EMF
        // if (categoriaBO != null && categoriaBO instanceof CategoriaBO) {
        //    ((CategoriaBO) categoriaBO).cerrarConexion(); 
        // }
        super.destroy();
    }
}