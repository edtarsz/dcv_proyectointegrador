/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.dcvdao.detallecomprainsumo;

import com.mycompany.dcvconexion.IConexion;
import java.util.logging.Logger;

import jakarta.persistence.EntityManager;

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
}
