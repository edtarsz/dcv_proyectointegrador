package com.mycompany.dcvconexion;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import java.util.logging.Logger;

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
    private static final Logger logger = Logger.getLogger(Conexion.class.getName());
    private final EntityManagerFactory emFactory;
    private EntityManager entityManager;

    public Conexion(EntityManagerFactory emFactory) {
        System.out.println("Conexion: Inicializando con EntityManagerFactory");
        this.emFactory = emFactory;
        if (this.emFactory == null) {
            throw new IllegalArgumentException("EntityManagerFactory no puede ser null");
        }
    }

    @Override
    public EntityManager crearConexion() {
        try {
            System.out.println("Conexion: Creando nuevo EntityManager");
            if (entityManager != null && entityManager.isOpen()) {
                System.out.println("Conexion: Cerrando EntityManager anterior");
                entityManager.close();
            }
            entityManager = emFactory.createEntityManager();
            if (entityManager == null) {
                throw new IllegalStateException("No se pudo crear EntityManager");
            }
            System.out.println("Conexion: EntityManager creado exitosamente");
            return entityManager;
        } catch (Exception e) {
            System.err.println("Conexion: Error al crear EntityManager: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("Error al crear conexión", e);
        }
    }

    @Override
    public void cerrarConexion() {
        try {
            System.out.println("Conexion: Cerrando conexiones");
            if (entityManager != null && entityManager.isOpen()) {
                entityManager.close();
                System.out.println("Conexion: EntityManager cerrado");
            }
            if (emFactory != null && emFactory.isOpen()) {
                emFactory.close();
                System.out.println("Conexion: EntityManagerFactory cerrado");
            }
        } catch (Exception e) {
            System.err.println("Conexion: Error al cerrar conexiones: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
