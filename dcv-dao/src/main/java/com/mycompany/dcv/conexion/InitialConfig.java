package com.mycompany.dcv.conexion;

import jakarta.persistence.Persistence;

/*
 * @author 
 */
public class InitialConfig {

    public static boolean iniciarConexion() {
        try {
            IConexion conexion = new Conexion(Persistence.createEntityManagerFactory("detallesConVasosPU"));
            conexion.crearConexion();
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
