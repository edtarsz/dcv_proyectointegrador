/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.dcvdao;

import com.mycompany.dcv.conexion.IConexion;
import com.mycompany.dcv.conexion.ModelException;

/**
 * @author Eduardo Talavera Ramos
 */
public class DAOFactory {

    private DAOFactory() {
        throw new UnsupportedOperationException("Esta es una clase utilitaria y no debe ser instanciada.");
    }

    public static UsuarioDAO instanciaUsuarioDAO(IConexion conexion) throws ModelException {
        return new UsuarioDAO(conexion);
    }
}
