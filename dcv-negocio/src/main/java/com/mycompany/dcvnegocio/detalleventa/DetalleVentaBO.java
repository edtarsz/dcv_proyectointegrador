/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.dcvnegocio.detalleventa;

import com.mycompany.dcvnegocio.usuario.UsuarioBO;
import com.mycompany.dcvconexion.Conexion;
import com.mycompany.dcvconexion.IConexion;
import com.mycompany.dcvconexion.ModelException;
import com.mycompany.dcvdao.DAOFactory;
import com.mycompany.dcvdao.detalleventa.IDetalleVentaDAO;
import com.mycompany.dcventidades.DetalleVenta;
import com.mycompany.dcvexceptions.ControllerException;
import java.util.logging.Level;
import java.util.logging.Logger;
import jakarta.persistence.Persistence;

/**
 * @author
 */
public class DetalleVentaBO implements IDetalleVentaBO {

    private IDetalleVentaDAO detalleVentaDAO;
    IConexion conexion;

    public DetalleVentaBO() {
        conexion = new Conexion(Persistence.createEntityManagerFactory("detallesConVasosPU"));
        try {
            this.detalleVentaDAO = DAOFactory.instanciaDetalleVentaDAO(conexion);
        } catch (ModelException ex) {
            Logger.getLogger(UsuarioBO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @Override
    public DetalleVenta crearVenta(DetalleVenta detalleVenta) throws ControllerException {
        try {
            return detalleVentaDAO.crearDetalleVenta(detalleVenta);
        } catch (ModelException ex) {
            Logger.getLogger(UsuarioBO.class.getName()).log(Level.SEVERE, null, ex);
            throw new ControllerException("Error creating comment", ex);
        }
    }
}
