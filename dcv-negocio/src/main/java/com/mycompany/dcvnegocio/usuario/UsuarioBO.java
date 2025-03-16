/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.dcvnegocio.usuario;

import com.mycompany.dcvconexion.Conexion;
import com.mycompany.dcvconexion.IConexion;
import com.mycompany.dcvconexion.ModelException;
import com.mycompany.dcventidades.Usuario;
import com.mycompany.dcvexceptions.ControllerException;
import com.mycompany.dcvdao.DAOFactory;
import com.mycompany.dcvdao.usuario.IUsuarioDAO;
import java.util.logging.Level;
import java.util.logging.Logger;
import jakarta.persistence.Persistence;

/**
 * @author
 */
public class UsuarioBO implements IUsuarioBO {

    private IUsuarioDAO usuarioDAO;
    IConexion conexion;

    public UsuarioBO() {
        conexion = new Conexion(Persistence.createEntityManagerFactory("detallesConVasosPU"));
        try {
            this.usuarioDAO = DAOFactory.instanciaUsuarioDAO(conexion);
        } catch (ModelException ex) {
            Logger.getLogger(UsuarioBO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public Usuario crearUsuario(Usuario usuario) throws ControllerException {
        try {
            return usuarioDAO.crearUsuario(usuario);
        } catch (ModelException ex) {
            Logger.getLogger(UsuarioBO.class.getName()).log(Level.SEVERE, null, ex);
            throw new ControllerException("Error creating comment", ex);
        }
    }
}