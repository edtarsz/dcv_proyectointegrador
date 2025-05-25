package com.mycompany.dcvnegocio.categoria;

import com.mycompany.dcvconexion.Conexion;
import com.mycompany.dcvconexion.IConexion;
import com.mycompany.dcvconexion.ModelException;
import com.mycompany.dcvdao.DAOFactory; // Asegúrate que DAOFactory pueda instanciar CategoriaDAO
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

    public CategoriaBO() {

        this.conexion = new Conexion(Persistence.createEntityManagerFactory("detallesConVasosPU"));
        try {
            this.categoriaDAO = DAOFactory.instanciaCategoriaDAO(this.conexion);
        } catch (ModelException ex) {
            Logger.getLogger(CategoriaBO.class.getName()).log(Level.SEVERE, "Error al instanciar CategoriaDAO", ex);
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
            Logger.getLogger(CategoriaBO.class.getName()).log(Level.SEVERE, "Error al agregar categoría", ex);
            throw new ControllerException("Error en el servidor al agregar categoría: " + ex.getMessage(), ex);
        } catch (ControllerException ce) {
            throw ce;
        } catch (Exception e) {
            Logger.getLogger(CategoriaBO.class.getName()).log(Level.SEVERE, "Error inesperado al agregar categoría", e);
            throw new ControllerException("Error inesperado al agregar categoría.", e);
        }
    }

    @Override
    public Categoria obtenerCategoriaPorId(long id) throws ControllerException {
        try {
            return categoriaDAO.obtenerCategoriaPorId(id);
        } catch (Exception e) {
            Logger.getLogger(CategoriaBO.class.getName()).log(Level.SEVERE, "Error al obtener categoría por ID", e);
            throw new ControllerException("Error al obtener categoría.", e);
        }
    }

    @Override
    public List<Categoria> obtenerTodasLasCategorias() throws ControllerException {
        try {
            return categoriaDAO.obtenerTodasLasCategorias();
        } catch (Exception e) {
            Logger.getLogger(CategoriaBO.class.getName()).log(Level.SEVERE, "Error al obtener todas las categorías", e);
            throw new ControllerException("Error al obtener lista de categorías.", e);
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
            return categoriaDAO.actualizarCategoria(categoria);
        } catch (ModelException ex) {
            Logger.getLogger(CategoriaBO.class.getName()).log(Level.SEVERE, "Error al actualizar categoría", ex);
            throw new ControllerException("Error en el servidor al actualizar categoría: " + ex.getMessage(), ex);
        } catch (ControllerException ce) {
            throw ce;
        } catch (Exception e) {
            Logger.getLogger(CategoriaBO.class.getName()).log(Level.SEVERE, "Error inesperado al actualizar categoría", e);
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
            return categoriaDAO.eliminarCategoria(id);
        } catch (ModelException ex) {
            Logger.getLogger(CategoriaBO.class.getName()).log(Level.SEVERE, "Error al eliminar categoría", ex);
            throw new ControllerException("Error en el servidor al eliminar categoría: " + ex.getMessage(), ex);
        } catch (ControllerException ce) {
            throw ce; // Re-lanzar la excepción de validación
        } catch (Exception e) {
            Logger.getLogger(CategoriaBO.class.getName()).log(Level.SEVERE, "Error inesperado al eliminar categoría", e);
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
            Logger.getLogger(CategoriaBO.class.getName()).log(Level.SEVERE, "Error al obtener productos por categoría", e);
            throw new ControllerException("Error al obtener productos de la categoría.", e);
        }
    }

    public void cerrarConexion() {
        if (this.conexion != null) {
            // Asumiendo que IConexion tiene un método para cerrar el EntityManager
            // o que la clase Conexion lo maneja.
            this.conexion.cerrarConexion();
        }
    }
}
