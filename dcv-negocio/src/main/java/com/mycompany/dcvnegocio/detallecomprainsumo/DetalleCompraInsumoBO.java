/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.dcvnegocio.detallecomprainsumo;

import com.mycompany.dcvnegocio.usuario.UsuarioBO;
import com.mycompany.dcvconexion.Conexion;
import com.mycompany.dcvconexion.IConexion;
import com.mycompany.dcvconexion.ModelException;
import com.mycompany.dcvdao.DAOFactory;
import com.mycompany.dcvdao.detallecomprainsumo.IDetalleCompraInsumoDAO;
import com.mycompany.dcventidades.DetalleCompraInsumo;
import com.mycompany.dcvexceptions.ControllerException;
import java.util.logging.Level;
import java.util.logging.Logger;
import jakarta.persistence.Persistence;
import java.util.List;

/**
 * @author
 */
public class DetalleCompraInsumoBO implements IDetalleCompraInsumoBO {

    private IDetalleCompraInsumoDAO detalleCompraInsumoDAO;
    IConexion conexion;

    public DetalleCompraInsumoBO() {
        conexion = new Conexion(Persistence.createEntityManagerFactory("detallesConVasosPU"));
        try {
            this.detalleCompraInsumoDAO = DAOFactory.instanciaDetalleCompraInsumoDAO(conexion);
        } catch (ModelException ex) {
            Logger.getLogger(UsuarioBO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
 

@Override
public List<DetalleCompraInsumo> obtenerDetallesPorCompra(long idCompra) throws ControllerException {
    try {
        return detalleCompraInsumoDAO.obtenerDetallesPorCompra(idCompra);
    } catch (ModelException e) {
        throw new ControllerException("Error al obtener los detalles", e);
    }
}

    @Override
    public DetalleCompraInsumo crearDetalleCompraInsumo(DetalleCompraInsumo detalle) throws ControllerException {
         try {
        return detalleCompraInsumoDAO.actualizarDetalleCompraInsumo(detalle);
    } catch (ModelException e) {
        throw new ControllerException("Error al actualizar el detalle", e);
    }
    
    }

    @Override
    public DetalleCompraInsumo actualizarDetalleCompraInsumo(DetalleCompraInsumo detalle) throws ControllerException {
        try {
        return detalleCompraInsumoDAO.actualizarDetalleCompraInsumo(detalle);
    } catch (ModelException e) {
        throw new ControllerException("Error al actualizar el detalle", e);
    }
    
    }

    @Override
    public void eliminarDetalleCompraInsumo(long id) throws ControllerException {
        try {
        detalleCompraInsumoDAO.eliminarDetalleCompraInsumo(id);
    } catch (ModelException e) {
        throw new ControllerException("Error al eliminar el detalle", e);
    }
    
    }

}
