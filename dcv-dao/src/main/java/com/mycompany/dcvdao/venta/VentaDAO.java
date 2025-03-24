/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.dcvdao.venta;

import com.mycompany.dcvconexion.Conexion;
import com.mycompany.dcvdao.usuario.*;
import com.mycompany.dcvconexion.IConexion;
import com.mycompany.dcvconexion.ModelException;
import com.mycompany.dcventidades.Cliente;
import com.mycompany.dcventidades.DetalleVenta;
import com.mycompany.dcventidades.Usuario;
import com.mycompany.dcventidades.Venta;
import java.util.logging.Logger;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.List;
import java.util.logging.Level;

/**
 * @author
 */
public class VentaDAO implements IVentaDAO {
    private static final Logger logger = Logger.getLogger(VentaDAO.class.getName());
    private EntityManager entityManager;

    public VentaDAO(IConexion conexion) {
        this.entityManager = conexion.crearConexion();
        logger.info("VentaDAO initialized with a new EntityManager.");
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
            if (transaction.isActive()) {
                transaction.rollback();
            }
            logger.log(Level.SEVERE, "Error al crear la venta", e);
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
            Venta updatedVenta = entityManager.merge(venta);
            transaction.commit();
            logger.info("Venta actualizada con éxito.");
            return updatedVenta;
        } catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
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
            if (transaction.isActive()) {
                transaction.rollback();
            }
            logger.log(Level.SEVERE, "Error al eliminar la venta", e);
            throw new ModelException("Error al eliminar venta", e);
        }
    }

    /**
     * Guarda una venta en la base de datos usando JPA.
     */
   public Venta guardarVenta(double total, String metodoPago, Cliente cliente, Usuario usuario) {
    
    EntityTransaction tx = entityManager.getTransaction();
    try {
        tx.begin();
        Venta venta = new Venta();
        venta.setFecha(Date.valueOf(LocalDate.now()));
        venta.setTotal(total);
        venta.setMetodoPago(metodoPago);
        venta.setEstado("Pendiente");  // O el estado que prefieras
        venta.setCliente(cliente);
        venta.setUsuario(usuario);

        entityManager.persist(venta);  // Guardar la venta
        tx.commit();  // Confirmar la transacción
        return venta;
    } catch (RuntimeException e) {
        if (tx.isActive()) tx.rollback();
        throw e;
    } finally {
        entityManager.close();
    }
}



    /**
     * Guarda un detalle de venta en la base de datos usando JPA.
     */
    @Override
    public void guardarDetalleVenta(Venta ventaId, DetalleVenta detalle) throws ModelException {
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            detalle.setVenta(ventaId);
            entityManager.persist(detalle);
            transaction.commit();
        } catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            throw new ModelException("Error al guardar detalle de venta", e);
        }
    }
}
