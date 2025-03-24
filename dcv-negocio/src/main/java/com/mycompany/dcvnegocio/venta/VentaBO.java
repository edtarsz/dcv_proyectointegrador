/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.dcvnegocio.venta;

import com.mycompany.dcvnegocio.usuario.UsuarioBO;
import com.mycompany.dcvconexion.Conexion;
import com.mycompany.dcvconexion.IConexion;
import com.mycompany.dcvconexion.ModelException;
import com.mycompany.dcvdao.DAOFactory;
import com.mycompany.dcvdao.venta.IVentaDAO;
import com.mycompany.dcventidades.Cliente;
import com.mycompany.dcventidades.DetalleVenta;
import com.mycompany.dcventidades.Usuario;
import com.mycompany.dcventidades.Venta;
import com.mycompany.dcvexceptions.ControllerException;
import java.util.logging.Level;
import java.util.logging.Logger;
import jakarta.persistence.Persistence;

/**
 * @author
 */
public class VentaBO implements IVentaBO {

    private IVentaDAO ventaDAO;
    IConexion conexion;

    public VentaBO() {
        conexion = new Conexion(Persistence.createEntityManagerFactory("detallesConVasosPU"));
        try {
            this.ventaDAO = DAOFactory.instanciaVentaDAO(conexion);
        } catch (ModelException ex) {
            Logger.getLogger(UsuarioBO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @Override
    public Venta crearVenta(Venta venta) throws ControllerException {
        try {
            return ventaDAO.crearVenta(venta);
        } catch (ModelException ex) {
            Logger.getLogger(VentaBO.class.getName()).log(Level.SEVERE, null, ex);
            throw new ControllerException("Error creating comment", ex);
        }
    }
    
    @Override
    public Venta guardarVenta(double total, String metodoPago, Cliente clienteId, Usuario usuarioId) throws ControllerException {
        try {
            return ventaDAO.guardarVenta(total, metodoPago, clienteId, usuarioId);
        } catch (ModelException ex) {
            Logger.getLogger(VentaBO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public void guardarDetalleVenta(Venta ventaId, DetalleVenta detalle) throws ControllerException {
        try {
            ventaDAO.guardarDetalleVenta(ventaId, detalle);
        } catch (ModelException ex) {
            Logger.getLogger(VentaBO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
   

}
