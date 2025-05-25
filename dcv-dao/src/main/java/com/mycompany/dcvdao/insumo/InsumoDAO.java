/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.dcvdao.insumo;

import com.mycompany.dcvconexion.IConexion;
import com.mycompany.dcvconexion.ModelException;
import com.mycompany.dcventidades.Insumo;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import java.util.List;
import java.util.logging.Logger;

/**
 *
 * @author Ramos
 */
public class InsumoDAO implements IInsumoDAO {

    private static final Logger logger = Logger.getLogger(InsumoDAO.class.getName());
    EntityManager entityManager;

    /**
     * Constructor para inicializar la clase `UsuarioDAO`.
     *
     * @param conexion La conexión a la base de datos utilizada para inicializar
     * el `EntityManager`.
     */
    public InsumoDAO(IConexion conexion) {
        this.entityManager = conexion.crearConexion();
        logger.info("PostDAO initialized with a new EntityManager.");
    }

    @Override
    public Insumo crearInsumo(Insumo insumo) throws ModelException {
        EntityTransaction tx = entityManager.getTransaction();
        try {
            tx.begin();
            entityManager.persist(insumo);
            tx.commit();
            return insumo;
        } catch (Exception e) {
            if (tx.isActive()) tx.rollback();
            throw new ModelException("Error al crear insumo", e);
        }
    }

    @Override
    public Insumo actualizarInsumo(Insumo insumo) throws ModelException {
        EntityTransaction tx = entityManager.getTransaction();
        try {
            tx.begin();
            Insumo actualizado = entityManager.merge(insumo);
            tx.commit();
            return actualizado;
        } catch (Exception e) {
            if (tx.isActive()) tx.rollback();
            throw new ModelException("Error al actualizar insumo", e);
        }
    }

    @Override
    public void eliminarInsumo(long id) throws ModelException {
        EntityTransaction tx = entityManager.getTransaction();
        try {
            tx.begin();
            Insumo insumo = entityManager.find(Insumo.class, id);
            if (insumo != null) entityManager.remove(insumo);
            tx.commit();
        } catch (Exception e) {
            if (tx.isActive()) tx.rollback();
            throw new ModelException("Error al eliminar insumo", e);
        }
    }

    @Override
    public List<Insumo> obtenerTodosLosInsumos() throws ModelException {
        try {
            return entityManager.createQuery("SELECT i FROM Insumo i", Insumo.class).getResultList();
        } catch (Exception e) {
            throw new ModelException("Error al obtener insumos", e);
        }
    }
}

