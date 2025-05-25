package com.mycompany.dcvdao.categoria;

import com.mycompany.dcvconexion.IConexion;
import com.mycompany.dcvconexion.ModelException;
import com.mycompany.dcventidades.Categoria;
import com.mycompany.dcventidades.Producto;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.NoResultException;
import jakarta.persistence.TypedQuery;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CategoriaDAO implements ICategoriaDAO {

    private static final Logger logger = Logger.getLogger(CategoriaDAO.class.getName());
    private EntityManager entityManager;

    public CategoriaDAO(IConexion conexion) {
        this.entityManager = conexion.crearConexion();
        logger.info("CategoriaDAO initialized with a new EntityManager.");
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
            logger.log(Level.SEVERE, "Error al crear la categoría", e);
            throw new ModelException("Error al crear categoría: " + e.getMessage(), e);
        }
    }

    @Override
    public Categoria obtenerCategoriaPorId(long id) {
        try {
            return entityManager.find(Categoria.class, id);
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error al obtener categoría por ID: " + id, e);
            return null;
        }
    }

    @Override
    public List<Categoria> obtenerTodasLasCategorias() {
        try {
            TypedQuery<Categoria> query = entityManager.createQuery("SELECT c FROM Categoria c ORDER BY c.nombre ASC", Categoria.class);
            return query.getResultList();
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error al obtener todas las categorías", e);
            return List.of(); // Devuelve lista vacía en caso de error
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
            logger.log(Level.SEVERE, "Error al actualizar la categoría", e);
            throw new ModelException("Error al actualizar categoría: " + e.getMessage(), e);
        }
    }

    @Override
    public boolean eliminarCategoria(long id) throws ModelException {
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            Categoria categoria = entityManager.find(Categoria.class, id);
            if (categoria != null) {
                if (categoriaTieneProductos(id)) {
                    logger.warning("Intento de eliminar categoría en uso: " + categoria.getNombre());
                    // No lanzamos excepción aquí, el BO o Servlet lo manejará basado en el retorno
                    transaction.rollback(); // Asegurarse de no dejar la transacción abierta
                    return false;
                }
                entityManager.remove(categoria);
                transaction.commit();
                logger.info("Categoría eliminada con éxito: ID " + id);
                return true;
            } else {
                logger.warning("Intento de eliminar categoría no encontrada: ID " + id);
                transaction.rollback(); // No es estrictamente necesario si no se hizo nada, pero buena práctica
                return false; // No se encontró, no se eliminó
            }
        } catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            logger.log(Level.SEVERE, "Error al eliminar la categoría", e);
            throw new ModelException("Error al eliminar categoría: " + e.getMessage(), e);
        }
    }

    @Override
    public boolean categoriaTieneProductos(long idCategoria) {
        try {
            // Contamos los productos que pertenecen a esta categoría.
            // Producto tiene un campo 'categoria' que es la FK.
            TypedQuery<Long> query = entityManager.createQuery(
                    "SELECT COUNT(p) FROM Producto p WHERE p.categoria.id = :idCategoria", Long.class);
            query.setParameter("idCategoria", idCategoria);
            Long count = query.getSingleResult();
            return count != null && count > 0;
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error al verificar si la categoría tiene productos", e);
            return true; // Asumir que sí tiene en caso de error para prevenir borrados accidentales
        }
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
            logger.log(Level.SEVERE, "Error al buscar categoría por nombre", e);
            return null;
        }
    }

    @Override
    public List<Producto> obtenerProductosPorCategoria(long idCategoria) {
        // Primero, verifica si la categoría siquiera existe para evitar consultas innecesarias a Producto
        Categoria categoria = obtenerCategoriaPorId(idCategoria);
        if (categoria == null) {
            logger.log(Level.WARNING, "Se solicitaron productos para una categoría no existente: ID " + idCategoria);
            return List.of(); // Categoría no existe, por lo tanto no tiene productos
        }

        // Si la categoría existe, entonces consulta sus productos directamente
        try {
            TypedQuery<Producto> query = entityManager.createQuery(
                    "SELECT p FROM Producto p WHERE p.categoria.id = :idCategoria", Producto.class);
            query.setParameter("idCategoria", idCategoria);
            List<Producto> productos = query.getResultList();
            logger.log(Level.INFO, "Productos encontrados para categoría ID {0}: {1}", new Object[]{idCategoria, productos.size()});
            return productos;
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error al obtener productos para la categoría ID " + idCategoria, e);
            return List.of(); // Devuelve lista vacía en caso de error en la consulta de productos
        }
    }
}
