/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.dcvnegocio.envio;

import com.mycompany.dcvnegocio.usuario.UsuarioBO;
import com.mycompany.dcvconexion.Conexion;
import com.mycompany.dcvconexion.IConexion;
import com.mycompany.dcvconexion.ModelException;
import com.mycompany.dcvdao.DAOFactory;
import com.mycompany.dcvdao.envio.IEnvioDAO;
import com.mycompany.dcventidades.Envio;
import com.mycompany.dcvexceptions.ControllerException;
import java.util.logging.Level;
import java.util.logging.Logger;
import jakarta.persistence.Persistence;

/**
 * @author
 */
public class EnvioBO implements IEnvioBO {

    private IEnvioDAO envioDAO;
    IConexion conexion;

    public EnvioBO() {
        conexion = new Conexion(Persistence.createEntityManagerFactory("detallesConVasosPU"));
        try {
            this.envioDAO = DAOFactory.instanciaEnvioDAO(conexion);
        } catch (ModelException ex) {
            Logger.getLogger(UsuarioBO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @Override
    public Envio crearEnvio(Envio envio) throws ControllerException {
        try {
            return envioDAO.crearEnvio(envio);
        } catch (ModelException ex) {
            Logger.getLogger(EnvioBO.class.getName()).log(Level.SEVERE, null, ex);
            throw new ControllerException("Error creating envio", ex);
        }
    }
}
