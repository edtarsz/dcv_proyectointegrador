package com.mycompany.dcv.conexion;

import jakarta.persistence.EntityManager;

/**
 * Interfaz para crear conexiones a la base de datos utilizando JPA.
 *
 * @author 
 */
public interface IConexion {

    /**
     * Crea y devuelve una nueva conexión a la base de datos utilizando JPA.
     *
     * @return Una instancia de {@link EntityManager} que representa la conexión
     * a la base de datos.
     */
    public EntityManager crearConexion();

    /**
     * Método para cerrar el EntityManagerFactory al finalizar la aplicación
     */
    public void cerrarConexion();
}
