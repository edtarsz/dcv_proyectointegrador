/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.dcvdao;

import com.mycompany.dcv.conexion.IConexion;
import com.mycompany.dcv.conexion.ModelException;
import com.mycompany.dcv.entidades.Usuario;
import java.util.logging.Logger;

import jakarta.persistence.EntityManager;

/**
 * @author
 */
public class UsuarioDAO implements IUsuarioDAO {

    private static final Logger logger = Logger.getLogger(UsuarioDAO.class.getName());
    EntityManager entityManager;

    /**
     * Constructor para inicializar la clase `UsuarioDAO`.
     *
     * @param conexion La conexión a la base de datos utilizada para inicializar
     * el `EntityManager`.
     */
    public UsuarioDAO(IConexion conexion) {
        this.entityManager = conexion.crearConexion();
        logger.info("PostDAO initialized with a new EntityManager.");
    }

    @Override
    public Usuario crearUsuario(Usuario usuario) throws ModelException {
        try {
            entityManager.getTransaction().begin();
            entityManager.persist(usuario);
            entityManager.flush();
            entityManager.getTransaction().commit();
            logger.info("Usuario creado con ID: " + usuario.getId());
            return usuario;
        } catch (Exception e) {
            if (entityManager.getTransaction().isActive()) {
                entityManager.getTransaction().rollback();
            }
            logger.severe("Error al crear usuario: " + e.getMessage());
            throw new ModelException("Error al crear usuario", e);
        }
    }
}
