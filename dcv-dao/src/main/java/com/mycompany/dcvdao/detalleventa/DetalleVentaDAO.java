/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.dcvdao.detalleventa;

import com.mycompany.dcvconexion.IConexion;
import com.mycompany.dcvconexion.ModelException;
import com.mycompany.dcventidades.DetalleVenta;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Ramos
 */
public class DetalleVentaDAO implements IDetalleVentaDAO {

    private static final Logger logger = Logger.getLogger(DetalleVentaDAO.class.getName());
    EntityManager entityManager;

    /**
     * Constructor para inicializar la clase `UsuarioDAO`.
     *
     * @param conexion La conexión a la base de datos utilizada para inicializar
     * el `EntityManager`.
     */
    public DetalleVentaDAO(IConexion conexion) {
        this.entityManager = conexion.crearConexion();
        logger.info("PostDAO initialized with a new EntityManager.");
    }
    
    @Override
    public DetalleVenta crearDetalleVenta(DetalleVenta detalleVenta) throws ModelException {
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            entityManager.persist(detalleVenta);
            transaction.commit();
            logger.info("Venta creada con éxito.");
            return detalleVenta;
        } catch (Exception e) {
            transaction.rollback();
            logger.log(Level.SEVERE, "Error al crear la venta", e.getMessage());
            throw new ModelException("Error al crear venta", e);
        }
    }

    @Override
    public DetalleVenta obtenerDetalleVentaPorId(long id) {
        return entityManager.find(DetalleVenta.class, id);
    }

    @Override
    public List<DetalleVenta> obtenerTodosLosDetallesVentas() {
        return entityManager.createQuery("SELECT v FROM Venta v", DetalleVenta.class).getResultList();
    }

    @Override
    public DetalleVenta actualizarVenta(DetalleVenta detalleVenta) throws ModelException {
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            entityManager.merge(detalleVenta);
            transaction.commit();
            logger.info("Venta actualizada con éxito.");
            return detalleVenta;
        } catch (Exception e) {
            transaction.rollback();
            logger.log(Level.SEVERE, "Error al actualizar la venta", e);
            throw new ModelException("Error al actualizar venta", e);
        }
    }

    @Override
    public DetalleVenta eliminarVenta(long id) throws ModelException {
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            DetalleVenta detalleVenta = entityManager.find(DetalleVenta.class, id);
            if (detalleVenta != null) {
                entityManager.remove(detalleVenta);
                logger.info("Venta eliminada con éxito.");
            }
            transaction.commit();
            return detalleVenta;
        } catch (Exception e) {
            transaction.rollback();
            logger.log(Level.SEVERE, "Error al eliminar la venta", e);
            throw new ModelException("Error al actualizar venta", e);
        }
    }
}
