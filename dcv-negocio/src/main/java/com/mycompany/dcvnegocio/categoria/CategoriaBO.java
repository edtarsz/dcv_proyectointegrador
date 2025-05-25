package com.mycompany.dcvnegocio.categoria;

// ... (tus imports existentes, asegúrate que los nombres de método coincidan con ICategoriaBO) ...
import com.mycompany.dcvconexion.Conexion;
import com.mycompany.dcvconexion.IConexion;
import com.mycompany.dcvconexion.ModelException;
import com.mycompany.dcvdao.DAOFactory;
import com.mycompany.dcvdao.categoria.ICategoriaDAO;
import com.mycompany.dcventidades.Categoria;
import com.mycompany.dcventidades.Producto;
import com.mycompany.dcvexceptions.ControllerException;
import java.util.logging.Level;
import java.util.logging.Logger;
import jakarta.persistence.Persistence;
import java.util.List;


public class CategoriaBO implements ICategoriaBO {

    private ICategoriaDAO categoriaDAO;
    private IConexion conexion; // Mantén una referencia si necesitas cerrar la conexión
    private static final Logger LOGGER = Logger.getLogger(CategoriaBO.class.getName());


    public CategoriaBO() {
        this.conexion = new Conexion(Persistence.createEntityManagerFactory("detallesConVasosPU"));
        try {
            this.categoriaDAO = DAOFactory.instanciaCategoriaDAO(this.conexion); 
        } catch (ModelException ex) {
            LOGGER.log(Level.SEVERE, "Error al instanciar CategoriaDAO", ex);
            throw new RuntimeException("Fallo crítico: No se pudo inicializar CategoriaDAO.", ex);
        }
    }

    // Cambiado nombre para coincidir con tu uso en el Servlet y la interfaz
    @Override
    public List<Categoria> obtenerTodas() throws ControllerException { 
        try {
            return categoriaDAO.obtenerTodas();
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error al obtener todas las categorías desde BO", e);
            throw new ControllerException("Error al obtener lista de categorías.", e);
        }
    }

    // Cambiado nombre para coincidir con tu uso en el Servlet y la interfaz
    @Override
    public Categoria obtenerPorId(long id) throws ControllerException { 
        if (id <= 0) {
            throw new ControllerException("ID de categoría inválido.");
        }
        try {
            return categoriaDAO.obtenerPorId(id);
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error al obtener categoría por ID desde BO: " + id, e);
            throw new ControllerException("Error al obtener detalles de la categoría.", e);
        }
    }

    @Override
    public Categoria agregarCategoria(Categoria categoria) throws ControllerException {
        if (categoria == null || categoria.getNombre() == null || categoria.getNombre().trim().isEmpty()) {
            throw new ControllerException("El nombre de la categoría es obligatorio.");
        }
        try {
            Categoria existente = categoriaDAO.buscarCategoriaPorNombre(categoria.getNombre().trim());
            if (existente != null) {
                throw new ControllerException("Ya existe una categoría con el nombre: " + categoria.getNombre());
            }
            return categoriaDAO.crearCategoria(categoria);
        } catch (ModelException ex) {
            LOGGER.log(Level.SEVERE, "Error de DAO al agregar categoría: "  + categoria.getNombre(), ex);
            throw new ControllerException("Error en el servidor al agregar categoría: " + ex.getMessage(), ex);
        } catch (ControllerException ce) {
             throw ce; 
        }catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error inesperado al agregar categoría: " + categoria.getNombre(), e);
            throw new ControllerException("Error inesperado al agregar categoría.", e);
        }
    }
    
    @Override
    public Categoria actualizarCategoria(Categoria categoria) throws ControllerException {
        if (categoria == null || categoria.getNombre() == null || categoria.getNombre().trim().isEmpty()) {
            throw new ControllerException("El nombre de la categoría es obligatorio para actualizar.");
        }
        if (categoria.getId() <= 0) { 
            throw new ControllerException("ID de categoría inválido para actualizar.");
        }
        try {
            Categoria existenteConMismoNombre = categoriaDAO.buscarCategoriaPorNombre(categoria.getNombre().trim());
            if (existenteConMismoNombre != null && existenteConMismoNombre.getId() != categoria.getId()) {
                throw new ControllerException("Ya existe otra categoría con el nombre: " + categoria.getNombre());
            }
            // Asegurarse que se pasa el objeto Categoria completo al DAO para merge
            Categoria categoriaParaActualizar = categoriaDAO.obtenerPorId(categoria.getId());
            if(categoriaParaActualizar == null){
                throw new ControllerException("La categoría que intenta actualizar no existe (ID: " + categoria.getId() + ")");
            }
            categoriaParaActualizar.setNombre(categoria.getNombre());
            categoriaParaActualizar.setDescripcion(categoria.getDescripcion());
            // Si la relación @ManyToMany es dueña Categoria, y quisieras actualizar productos asociados aquí,
            // tendrías que manejar la colección categoriaParaActualizar.setProductos(...);
            // Pero usualmente la actualización de asociaciones se hace desde el lado que se modifica.

            return categoriaDAO.actualizarCategoria(categoriaParaActualizar);
        } catch (ModelException ex) {
            LOGGER.log(Level.SEVERE, "Error de DAO al actualizar categoría: " + categoria.getNombre(), ex);
            throw new ControllerException("Error en el servidor al actualizar categoría: " + ex.getMessage(), ex);
        } catch (ControllerException ce){
            throw ce; 
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error inesperado al actualizar categoría: " + categoria.getNombre(), e);
            throw new ControllerException("Error inesperado al actualizar categoría.", e);
        }
    }
    
    @Override
    public boolean eliminarCategoria(long id) throws ControllerException {
         if (id <= 0) {
            throw new ControllerException("ID de categoría inválido para eliminar.");
        }
        try {
            if (categoriaDAO.categoriaTieneProductos(id)) {
                 throw new ControllerException("La categoría no puede ser eliminada porque tiene productos asociados.");
            }
            return categoriaDAO.eliminarCategoria(id); // El DAO ahora solo elimina si no tiene productos, o maneja la FK.
        } catch (ModelException ex) {
            LOGGER.log(Level.SEVERE, "Error de DAO al eliminar categoría ID: " + id, ex);
            throw new ControllerException("Error en el servidor al eliminar categoría: " + ex.getMessage(), ex);
        } catch (ControllerException ce){ // Para el "tiene productos asociados"
            throw ce;
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error inesperado al eliminar categoría ID: " + id, e);
            throw new ControllerException("Error inesperado al eliminar categoría.", e);
        }
    }
    
    @Override
    public List<Producto> obtenerProductosPorCategoria(long idCategoria) throws ControllerException {
        if (idCategoria <= 0) {
            throw new ControllerException("ID de categoría inválido.");
        }
        try {
            return categoriaDAO.obtenerProductosPorCategoria(idCategoria);
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error al obtener productos por categoría ID: " + idCategoria, e);
            throw new ControllerException("Error al obtener productos de la categoría.", e);
        }
    }
    
    public void cerrarConexion() {
        if (this.conexion != null && this.conexion instanceof Conexion) {
            // Necesitas un método público en tu clase Conexion para cerrar el EMF si es necesario
            ((Conexion)this.conexion).cerrarConexion();// Ejemplo, el nombre del método debe existir en Conexion
            LOGGER.info("Recursos de conexión cerrados para CategoriaBO.");
        }
    }
}