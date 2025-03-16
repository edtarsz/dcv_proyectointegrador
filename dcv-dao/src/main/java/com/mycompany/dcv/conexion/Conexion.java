package com.mycompany.dcv.conexion;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;

/**
 * Implementación de la interfaz {@link IConexion} para crear conexiones a la
 * base de datos utilizando JPA.
 *
 * Utiliza la configuración definida en el archivo {@code persistence.xml} para
 * establecer la conexión con la base de datos.
 *
 * @author 
 */
public class Conexion implements IConexion {

    private final EntityManagerFactory emFactory;

    public Conexion(EntityManagerFactory emFactory) {
        this.emFactory = emFactory;
    }

    /**
     * Crea y devuelve una nueva conexión a la base de datos utilizando JPA.
     *
     * @return Una instancia de {@link EntityManager} que representa la conexión
     * a la base de datos.
     */
    @Override
    public EntityManager crearConexion() {
        return emFactory.createEntityManager();
    }

    /**
     * Cierra el EntityManagerFactory si está abierto, lo que permite finalizar
     * la conexión a la base de datos.
     */
    @Override
    public void cerrarConexion() {
        if (emFactory != null && emFactory.isOpen()) {
            emFactory.close();
        }
    }
}
