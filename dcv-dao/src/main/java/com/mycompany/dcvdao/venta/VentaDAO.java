/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.dcvdao.venta;

import com.mycompany.dcvdao.usuario.*;
import com.mycompany.dcvconexion.IConexion;
import com.mycompany.dcvconexion.ModelException;
import com.mycompany.dcventidades.Venta;
import java.util.logging.Logger;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import java.util.List;
import java.util.logging.Level;

/**
 * @author
 */
public class VentaDAO implements IVentaDAO {

    private static final Logger logger = Logger.getLogger(VentaDAO.class.getName());
    EntityManager entityManager;

    /**
     * Constructor para inicializar la clase `UsuarioDAO`.
     *
     * @param conexion La conexión a la base de datos utilizada para inicializar
     * el `EntityManager`.
     */
    public VentaDAO(IConexion conexion) {
        this.entityManager = conexion.crearConexion();
        logger.info("PostDAO initialized with a new EntityManager.");
    }
    
    @Override
    public Venta crearVenta(Venta venta) throws ModelException {
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            entityManager.persist(venta);
            transaction.commit();
            logger.info("Venta creada con éxito.");
            return venta;
        } catch (Exception e) {
            transaction.rollback();
            logger.log(Level.SEVERE, "Error al crear la venta", e.getMessage());
            throw new ModelException("Error al crear venta", e);
        }
    }

    @Override
    public Venta obtenerVentaPorId(long id) {
        return entityManager.find(Venta.class, id);
    }

    @Override
    public List<Venta> obtenerTodasLasVentas() {
        return entityManager.createQuery("SELECT v FROM Venta v", Venta.class).getResultList();
    }

    @Override
    public Venta actualizarVenta(Venta venta) throws ModelException {
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            entityManager.merge(venta);
            transaction.commit();
            logger.info("Venta actualizada con éxito.");
            return venta;
        } catch (Exception e) {
            transaction.rollback();
            logger.log(Level.SEVERE, "Error al actualizar la venta", e);
            throw new ModelException("Error al actualizar venta", e);
        }
    }

    @Override
    public Venta eliminarVenta(long id) throws ModelException {
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            Venta venta = entityManager.find(Venta.class, id);
            if (venta != null) {
                entityManager.remove(venta);
                logger.info("Venta eliminada con éxito.");
            }
            transaction.commit();
            return venta;
        } catch (Exception e) {
            transaction.rollback();
            logger.log(Level.SEVERE, "Error al eliminar la venta", e);
            throw new ModelException("Error al actualizar venta", e);
        }
    }
}
