/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.dcvdao.envio;

import com.mycompany.dcvconexion.IConexion;
import com.mycompany.dcvconexion.ModelException;
import com.mycompany.dcventidades.Cliente;
import com.mycompany.dcventidades.Envio;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Ramos
 */
public class EnvioDAO implements IEnvioDAO {

    private static final Logger logger = Logger.getLogger(EnvioDAO.class.getName());
    EntityManager entityManager;

    /**
     * Constructor para inicializar la clase `UsuarioDAO`.
     *
     * @param conexion La conexión a la base de datos utilizada para inicializar
     * el `EntityManager`.
     */
    public EnvioDAO(IConexion conexion) {
        this.entityManager = conexion.crearConexion();
        logger.info("PostDAO initialized with a new EntityManager.");
    }
    
     @Override
    public Envio crearEnvio(Envio envio) throws ModelException {
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            entityManager.persist(envio);
            transaction.commit();
            logger.info("Envio creado con éxito.");
            return envio;
        } catch (Exception e) {
            transaction.rollback();
            logger.log(Level.SEVERE, "Error al registrar el envio", e.getMessage());
            throw new ModelException("Error al registrar envio", e);
        }
    }

    @Override
    public Envio obtenerEnvioPorId(long id) {
        return entityManager.find(Envio.class, id);
    }

    @Override
    public List<Envio> obtenerTodosLosEnvios() {
        return entityManager.createQuery("SELECT e FROM Envio e", Envio.class).getResultList();
    }

    @Override
    public Envio actualizarEnvio(Envio envio) throws ModelException {
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            entityManager.merge(envio);
            transaction.commit();
            logger.info("Envio actualizado con éxito.");
            return envio;
        } catch (Exception e) {
            transaction.rollback();
            logger.log(Level.SEVERE, "Error al actualizar el envio", e);
            throw new ModelException("Error al actualizar envio", e);
        }
    }

    @Override
    public Envio eliminarEnvio(long id) throws ModelException {
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            Envio envio = entityManager.find(Envio.class, id);
            if (envio != null) {
                entityManager.remove(envio);
                logger.info("Envio eliminado con éxito.");
            }
            transaction.commit();
            return envio;
        } catch (Exception e) {
            transaction.rollback();
            logger.log(Level.SEVERE, "Error al eliminar el envio", e);
            throw new ModelException("Error al eliminar envio", e);
        }
    }
}
