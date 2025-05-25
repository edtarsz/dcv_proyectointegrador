/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.dcvdao.detallecomprainsumo;

import com.mycompany.dcvconexion.IConexion;
import com.mycompany.dcvconexion.ModelException;
import com.mycompany.dcventidades.DetalleCompraInsumo;
import java.util.logging.Logger;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.NoResultException;
import java.util.List;

/**
 * @author
 */
public class DetalleCompraInsumoDAO implements IDetalleCompraInsumoDAO {

    private static final Logger logger = Logger.getLogger(DetalleCompraInsumoDAO.class.getName());
    EntityManager entityManager;

    /**
     * Constructor para inicializar la clase `UsuarioDAO`.
     *
     * @param conexion La conexión a la base de datos utilizada para inicializar
     * el `EntityManager`.
     */
    public DetalleCompraInsumoDAO(IConexion conexion) {
        this.entityManager = conexion.crearConexion();
        logger.info("PostDAO initialized with a new EntityManager.");
    }
    
    @Override
public DetalleCompraInsumo crearDetalleCompraInsumo(DetalleCompraInsumo detalle) throws ModelException {
    EntityTransaction tx = entityManager.getTransaction();
    try {
        tx.begin();
        entityManager.persist(detalle);
        tx.commit();
        return detalle;
    } catch (Exception e) {
        if (tx.isActive()) tx.rollback();
        throw new ModelException("Error al crear detalle", e);
    }
}

@Override
public DetalleCompraInsumo actualizarDetalleCompraInsumo(DetalleCompraInsumo detalle) throws ModelException {
    EntityTransaction tx = entityManager.getTransaction();
    try {
        tx.begin();
        DetalleCompraInsumo actualizado = entityManager.merge(detalle);
        tx.commit();
        return actualizado;
    } catch (Exception e) {
        if (tx.isActive()) tx.rollback();
        throw new ModelException("Error al actualizar detalle", e);
    }
}

@Override
public void eliminarDetalleCompraInsumo(long id) throws ModelException {
    EntityTransaction tx = entityManager.getTransaction();
    try {
        tx.begin();
        DetalleCompraInsumo detalle = entityManager.find(DetalleCompraInsumo.class, id);
        if (detalle != null) {
            entityManager.remove(detalle);
        }
        tx.commit();
    } catch (Exception e) {
        if (tx.isActive()) tx.rollback();
        throw new ModelException("Error al eliminar detalle", e);
    }
}

@Override
public List<DetalleCompraInsumo> obtenerDetallesPorCompra(long idCompra) throws ModelException {
    try {
        return entityManager.createQuery(
            "SELECT d FROM DetalleCompraInsumo d WHERE d.compraInsumo.id = :idCompra", DetalleCompraInsumo.class)
            .setParameter("idCompra", idCompra)
            .getResultList();
    } catch (Exception e) {
        throw new ModelException("Error al obtener detalles", e);
    }
}

public DetalleCompraInsumo obtenerUltimoDetallePorInsumo(Long insumoId) throws ModelException {
    try {
        return entityManager.createQuery(
            "SELECT d FROM DetalleCompraInsumo d WHERE d.insumo.id = :insumoId ORDER BY d.id DESC", DetalleCompraInsumo.class)
            .setParameter("insumoId", insumoId)
            .setMaxResults(1)
            .getSingleResult();
    } catch (NoResultException e) {
        return null;
    } catch (Exception e) {
        throw new ModelException("Error al obtener el último detalle del insumo", e);
    }
}


}
