/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.dcvdao.producto;

import com.mycompany.dcvconexion.IConexion;
import jakarta.persistence.EntityManager;
import java.util.logging.Logger;

/**
 *
 * @author Ramos
 */
public class ProductoDAO implements IProductoDAO {

    private static final Logger logger = Logger.getLogger(ProductoDAO.class.getName());
    EntityManager entityManager;

    /**
     * Constructor para inicializar la clase `UsuarioDAO`.
     *
     * @param conexion La conexión a la base de datos utilizada para inicializar
     * el `EntityManager`.
     */
    public ProductoDAO(IConexion conexion) {
        this.entityManager = conexion.crearConexion();
        logger.info("PostDAO initialized with a new EntityManager.");
    }
}
