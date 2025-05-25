package com.mycompany.dcvdao.categoria;

import com.mycompany.dcvconexion.IConexion;
import com.mycompany.dcvconexion.ModelException;
import com.mycompany.dcventidades.Categoria;
import com.mycompany.dcventidades.Producto;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.NoResultException;
import jakarta.persistence.TypedQuery;
import java.util.ArrayList; // Importar ArrayList
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CategoriaDAO implements ICategoriaDAO {

    private static final Logger logger = Logger.getLogger(CategoriaDAO.class.getName());
    EntityManager entityManager;

    public CategoriaDAO(IConexion conexion) {
        this.entityManager = conexion.crearConexion();
        logger.info("CategoriaDAO initialized with a new EntityManager.");
    }

    @Override
    public List<Categoria> obtenerTodas() { // Cambiado de obtenerTodasLasCategorias para coincidir con ICategoriaDAO y BO
        try {
            TypedQuery<Categoria> query = entityManager.createQuery("SELECT c FROM Categoria c ORDER BY c.nombre ASC", Categoria.class);
            return query.getResultList();
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error al obtener todas las categorías", e);
            return List.of();
        }
    }

    @Override
    public Categoria obtenerPorId(long id) { // Cambiado de obtenerCategoriaPorId
        return entityManager.find(Categoria.class, id);
    }

    @Override
    public List<Producto> obtenerProductosPorCategoria(long idCategoria) {
        Categoria categoria = entityManager.find(Categoria.class, idCategoria);
        if (categoria != null) {
            try {
                // Forzar la inicialización de la colección LAZY dentro de la sesión activa
                // si la relación es LAZY (que es el default para @ManyToMany).
                // Acceder a la colección es suficiente para cargarla si la sesión está activa.
                // Devolver una nueva lista para evitar problemas si la original se modifica fuera de la sesión.
                List<Producto> productos = new ArrayList<>(categoria.getProductos());
                logger.log(Level.INFO, "Productos encontrados para categoría ID {0}: {1}", new Object[]{idCategoria, productos.size()});
                return productos;
            } catch (org.eclipse.persistence.exceptions.DatabaseException e) {
                // Esto puede ocurrir si se intenta acceder a una colección LAZY fuera de una transacción activa
                // o si la entidad está detached.
                logger.log(Level.WARNING, "DatabaseException al acceder a productos (posiblemente LazyInitialization o sesión cerrada) para categoría ID " + idCategoria + ". Intentando consulta JPQL.", e);
                // Intento con consulta JPQL como fallback, aunque lo ideal es manejar bien la sesión.
                try {
                    TypedQuery<Producto> query = entityManager.createQuery(
                        "SELECT p FROM Producto p JOIN p.categorias c WHERE c.id = :idCategoria", Producto.class);
                    query.setParameter("idCategoria", idCategoria);
                    List<Producto> productosFallback = query.getResultList();
                    logger.log(Level.INFO, "Productos encontrados con consulta JPQL para categoría ID {0}: {1}", new Object[]{idCategoria, productosFallback.size()});
                    return productosFallback;
                } catch (Exception e2) {
                     logger.log(Level.SEVERE, "Error con la consulta JPQL para obtener productos de la categoría ID " + idCategoria, e2);
                     return List.of();
                }
            } catch (Exception e) {
                logger.log(Level.SEVERE, "Error general al obtener productos para la categoría ID " + idCategoria, e);
                return List.of();
            }
        }
        logger.log(Level.WARNING, "No se encontró categoría con ID: " + idCategoria + " al buscar sus productos.");
        return List.of();
    }
    
    @Override
    public Categoria buscarCategoriaPorNombre(String nombre) {
        try {
            TypedQuery<Categoria> query = entityManager.createQuery(
                    "SELECT c FROM Categoria c WHERE c.nombre = :nombre", Categoria.class);
            query.setParameter("nombre", nombre);
            return query.getSingleResult();
        } catch (NoResultException e) {
            logger.log(Level.INFO, "No se encontró categoría con nombre: " + nombre);
            return null;
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error al buscar categoría por nombre: " + nombre, e);
            return null;
        }
    }
    
    @Override
    public boolean categoriaTieneProductos(long idCategoria) {
        Categoria categoria = entityManager.find(Categoria.class, idCategoria);
        if (categoria != null) {
            try {
                // Forzar la inicialización de la colección LAZY y verificar si está vacía
                return !categoria.getProductos().isEmpty();
            } catch (Exception e) {
                 logger.log(Level.SEVERE, "Error al verificar si la categoría ID " + idCategoria + " tiene productos. Asumiendo que sí para prevenir borrado.", e);
                 return true; // Fallar de forma segura
            }
        }
        return false; // Categoría no encontrada
    }
    
    @Override
    public boolean eliminarCategoria(long id) throws ModelException {
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            Categoria categoria = entityManager.find(Categoria.class, id);
            if (categoria != null) {
                // Con @ManyToMany, si Categoria es la dueña (tiene @JoinTable),
                // al eliminar la categoría, JPA debería eliminar las entradas
                // correspondientes en la tabla intermedia (categoria_producto).
                // No es necesario desasociar manualmente los productos aquí si la relación
                // y las cascadas están configuradas para ello (o si no hay cascada de eliminación hacia Producto).
                // La verificación de si tiene productos ya se hace en el BO antes de llamar a este método.
                entityManager.remove(categoria);
                transaction.commit();
                logger.info("Categoría eliminada con éxito: ID " + id);
                return true;
            } else {
                logger.warning("Intento de eliminar categoría no encontrada: ID " + id);
                if(transaction.isActive()) transaction.rollback();
                return false;
            }
        } catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            logger.log(Level.SEVERE, "Error al eliminar la categoría ID " + id, e);
            throw new ModelException("Error al eliminar categoría: " + e.getMessage(), e);
        }
    }
    
    @Override
    public Categoria actualizarCategoria(Categoria categoria) throws ModelException {
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            Categoria managedCategoria = entityManager.merge(categoria);
            transaction.commit();
            logger.info("Categoría actualizada con éxito: " + managedCategoria.getNombre());
            return managedCategoria;
        } catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            logger.log(Level.SEVERE, "Error al actualizar la categoría: " + categoria.getNombre(), e);
            throw new ModelException("Error al actualizar categoría: " + e.getMessage(), e);
        }
    }
    
    @Override
    public Categoria crearCategoria(Categoria categoria) throws ModelException {
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            entityManager.persist(categoria);
            transaction.commit();
            logger.info("Categoría creada con éxito: " + categoria.getNombre());
            return categoria;
        } catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            logger.log(Level.SEVERE, "Error al crear la categoría: " + categoria.getNombre(), e);
            throw new ModelException("Error al crear categoría: " + e.getMessage(), e);
        }
    }
}