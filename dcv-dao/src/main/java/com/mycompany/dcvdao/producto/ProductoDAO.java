/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.dcvdao.producto;

import com.mycompany.dcvconexion.IConexion;
import com.mycompany.dcvconexion.ModelException;
import com.mycompany.dcventidades.Producto;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Ramos
 */
public class ProductoDAO implements IProductoDAO {

    private static final Logger logger = Logger.getLogger(ProductoDAO.class.getName());
    EntityManager entityManager;

    /**
     * Constructor para inicializar la clase `UsuarioDAO`.
     *
     * @param conexion La conexión a la base de datos utilizada para inicializar
     * el `EntityManager`.
     */
    public ProductoDAO(IConexion conexion) {
        this.entityManager = conexion.crearConexion();
        logger.info("PostDAO initialized with a new EntityManager.");
    }
    
     @Override
    public Producto crearProducto(Producto producto) throws ModelException {
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            entityManager.persist(producto);
            transaction.commit();
            logger.info("Producto creada con éxito.");
            return producto;
        } catch (Exception e) {
            transaction.rollback();
            logger.log(Level.SEVERE, "Error al crear el producto", e);
            throw new ModelException("Error al crear producto", e);
        }
    }

    @Override
    public Producto obtenerProductoPorId(long id) {
        return entityManager.find(Producto.class, id);
    }

    @Override
    public List<Producto> obtenerTodosLosProductos() {
        return entityManager.createQuery("SELECT p FROM Producto p", Producto.class).getResultList();
    }

    @Override
    public Producto actualizarProducto(Producto producto) throws ModelException {
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            entityManager.merge(producto);
            transaction.commit();
            logger.info("Producto actualizada con éxito.");
            return producto;
        } catch (Exception e) {
            transaction.rollback();
            logger.log(Level.SEVERE, "Error al actualizar la producto", e);
            throw new ModelException("Error al actualizar producto", e);
        }
    }

    @Override
    public Producto eliminarProducto(long id) throws ModelException {
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            Producto producto = entityManager.find(Producto.class, id);
            if (producto != null) {
                entityManager.remove(producto);
                logger.info("Producto eliminada con éxito.");
            }
            transaction.commit();
            return producto;
        } catch (Exception e) {
            transaction.rollback();
            logger.log(Level.SEVERE, "Error al eliminar la producto", e);
            throw new ModelException("Error al eliminar producto", e);
        }
    }
}
