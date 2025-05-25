/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.dcvdao.comprainsumo;

import com.mycompany.dcvconexion.IConexion;
import com.mycompany.dcvconexion.ModelException;
import com.mycompany.dcventidades.CompraInsumo;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import java.util.List;
import java.util.logging.Logger;

/**
 *
 * @author Ramos
 */
public class CompraInsumoDAO implements ICompraInsumoDAO {

    private static final Logger logger = Logger.getLogger(CompraInsumoDAO.class.getName());
    EntityManager entityManager;

    /**
     * Constructor para inicializar la clase `UsuarioDAO`.
     *
     * @param conexion La conexión a la base de datos utilizada para inicializar
     * el `EntityManager`.
     */
    public CompraInsumoDAO(IConexion conexion) {
        this.entityManager = conexion.crearConexion();
        logger.info("PostDAO initialized with a new EntityManager.");
    }
    
    @Override
public CompraInsumo crearCompraInsumo(CompraInsumo compra) throws ModelException {
    EntityTransaction tx = entityManager.getTransaction();
    try {
        tx.begin();
        entityManager.persist(compra);
        tx.commit();
        return compra;
    } catch (Exception e) {
        if (tx.isActive()) tx.rollback();
        throw new ModelException("Error al crear compra", e);
    }
}

@Override
public CompraInsumo actualizarCompraInsumo(CompraInsumo compra) throws ModelException {
    EntityTransaction tx = entityManager.getTransaction();
    try {
        tx.begin();
        CompraInsumo actualizado = entityManager.merge(compra);
        tx.commit();
        return actualizado;
    } catch (Exception e) {
        if (tx.isActive()) tx.rollback();
        throw new ModelException("Error al actualizar compra", e);
    }
}

@Override
public void eliminarCompraInsumo(long id) throws ModelException {
    EntityTransaction tx = entityManager.getTransaction();
    try {
        tx.begin();
        CompraInsumo compra = entityManager.find(CompraInsumo.class, id);
        if (compra != null) {
            entityManager.remove(compra);
        }
        tx.commit();
    } catch (Exception e) {
        if (tx.isActive()) tx.rollback();
        throw new ModelException("Error al eliminar compra", e);
    }
}

@Override
public List<CompraInsumo> obtenerTodasLasCompras() throws ModelException {
    try {
        return entityManager.createQuery("SELECT c FROM CompraInsumo c", CompraInsumo.class).getResultList();
    } catch (Exception e) {
        throw new ModelException("Error al obtener compras", e);
    }
}

}
