/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.dcvdao.categoria;

import com.mycompany.dcvconexion.IConexion;
import com.mycompany.dcvconexion.ModelException;
import com.mycompany.dcventidades.Categoria;
import jakarta.persistence.EntityManager;
import java.util.List;
import java.util.logging.Logger;

/**
 *
 * @author Ramos
 */
public class CategoriaDAO implements ICategoriaDAO {

    private static final Logger logger = Logger.getLogger(CategoriaDAO.class.getName());
    EntityManager entityManager;

    /**
     * Constructor para inicializar la clase `UsuarioDAO`.
     *
     * @param conexion La conexión a la base de datos utilizada para inicializar
     * el `EntityManager`.
     */
    public CategoriaDAO(IConexion conexion) {
        this.entityManager = conexion.crearConexion();
        logger.info("PostDAO initialized with a new EntityManager.");
    }
    
     public List<Categoria> obtenerTodas()  {
        
            return entityManager.createQuery("SELECT c FROM Categoria c", Categoria.class)
                     .getResultList();
    }
     
      public Categoria obtenerPorId(long id) {
        return entityManager.find(Categoria.class, id);
    }
}
