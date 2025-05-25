/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.dcvnegocio.comprainsumo;

import com.mycompany.dcvnegocio.usuario.UsuarioBO;
import com.mycompany.dcvconexion.Conexion;
import com.mycompany.dcvconexion.IConexion;
import com.mycompany.dcvconexion.ModelException;
import com.mycompany.dcvdao.DAOFactory;
import com.mycompany.dcvdao.comprainsumo.ICompraInsumoDAO;
import com.mycompany.dcventidades.CompraInsumo;
import com.mycompany.dcvexceptions.ControllerException;
import java.util.logging.Level;
import java.util.logging.Logger;
import jakarta.persistence.Persistence;
import java.util.List;

/**
 * @author
 */
public class CompraInsumoBO implements ICompraInsumoBO {

    private ICompraInsumoDAO compraInsumoDAO;
    IConexion conexion;

    public CompraInsumoBO() {
        conexion = new Conexion(Persistence.createEntityManagerFactory("detallesConVasosPU"));
        try {
            this.compraInsumoDAO = DAOFactory.instanciaCompraInsumoDAO(conexion);
        } catch (ModelException ex) {
            Logger.getLogger(UsuarioBO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
   
@Override
public List<CompraInsumo> obtenerTodasLasCompras() throws ControllerException {
    try {
        return compraInsumoDAO.obtenerTodasLasCompras();
    } catch (ModelException e) {
        throw new ControllerException("Error al obtener compras", e);
    }
}

    @Override
    public CompraInsumo crearCompraInsumo(CompraInsumo compra) throws ControllerException {
        try {
        return compraInsumoDAO.crearCompraInsumo(compra);
    } catch (ModelException e) {
        throw new ControllerException("Error al crear la compra", e);
    }
    
    }

    @Override
    public CompraInsumo actualizarCompraInsumo(CompraInsumo compra) throws ControllerException {
    try {
        return compraInsumoDAO.actualizarCompraInsumo(compra);
    } catch (ModelException e) {
        throw new ControllerException("Error al actualizar la compra", e);
    }   
    
    }

    @Override
    public void eliminarCompraInsumo(long id) throws ControllerException {
     try {
        compraInsumoDAO.eliminarCompraInsumo(id);
    } catch (ModelException e) {
        throw new ControllerException("Error al eliminar la compra", e);
    }   
    
    }

}
