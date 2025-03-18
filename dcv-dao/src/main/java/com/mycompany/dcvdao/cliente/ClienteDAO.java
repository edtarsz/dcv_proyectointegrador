/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.dcvdao.cliente;

import com.mycompany.dcvconexion.IConexion;
import com.mycompany.dcvconexion.ModelException;
import com.mycompany.dcvdao.venta.VentaDAO;
import com.mycompany.dcventidades.Cliente;
import com.mycompany.dcventidades.Venta;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author PC
 */
public class ClienteDAO implements IClienteDAO {
    
    private static final Logger logger = Logger.getLogger(VentaDAO.class.getName());
    EntityManager entityManager;

    /**
     * Constructor para inicializar la clase `ClienteDAO`.
     *
     * @param conexion La conexión a la base de datos utilizada para inicializar
     * el `EntityManager`.
     */
    public ClienteDAO(IConexion conexion) {
        this.entityManager = conexion.crearConexion();
        logger.info("ClienteDAO initialized with a new EntityManager.");
    }
    
    @Override
    public Cliente crearCliente(Cliente cliente) throws ModelException {
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            entityManager.persist(cliente);
            transaction.commit();
            logger.info("Cliente creado con éxito.");
            return cliente;
        } catch (Exception e) {
            transaction.rollback();
            logger.log(Level.SEVERE, "Error al registrar el cliente", e.getMessage());
            throw new ModelException("Error al registrar cliente", e);
        }
    }

    @Override
    public Cliente obtenerClientePorId(long id) {
        return entityManager.find(Cliente.class, id);
    }

    @Override
    public List<Cliente> obtenerTodosLosCliente() {
        return entityManager.createQuery("SELECT c FROM Cliente c", Cliente.class).getResultList();
    }

    @Override
    public Cliente actualizarCliente(Cliente cliente) throws ModelException {
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            entityManager.merge(cliente);
            transaction.commit();
            logger.info("Cliente actualizado con éxito.");
            return cliente;
        } catch (Exception e) {
            transaction.rollback();
            logger.log(Level.SEVERE, "Error al actualizar el cliente", e);
            throw new ModelException("Error al actualizar cliente", e);
        }
    }

    @Override
    public Cliente eliminarCliente(long id) throws ModelException {
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            Cliente cliente = entityManager.find(Cliente.class, id);
            if (cliente != null) {
                entityManager.remove(cliente);
                logger.info("Cliente eliminado con éxito.");
            }
            transaction.commit();
            return cliente;
        } catch (Exception e) {
            transaction.rollback();
            logger.log(Level.SEVERE, "Error al eliminar el cliente", e);
            throw new ModelException("Error al eliminar cliente", e);
        }
    }
}
