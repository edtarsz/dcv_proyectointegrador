/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.dcvdao.venta;

import com.mycompany.dcvdao.usuario.*;
import com.mycompany.dcvconexion.IConexion;
import java.util.logging.Logger;

import jakarta.persistence.EntityManager;

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
}
