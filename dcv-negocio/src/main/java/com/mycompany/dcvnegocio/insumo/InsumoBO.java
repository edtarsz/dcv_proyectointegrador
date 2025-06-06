/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.dcvnegocio.insumo;

import com.mycompany.dcventidades.Insumo;
import com.mycompany.dcvconexion.Conexion;
import com.mycompany.dcvconexion.IConexion;
import com.mycompany.dcvconexion.ModelException;
import com.mycompany.dcvdao.DAOFactory;
import com.mycompany.dcvdao.insumo.IInsumoDAO;
import com.mycompany.dcvdao.insumo.InsumoDAO;
import com.mycompany.dcvexceptions.ControllerException;
import com.mycompany.dcvnegocio.usuario.UsuarioBO;
import jakarta.persistence.Persistence;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class InsumoBO implements IInsumoBO {

    IInsumoDAO insumoDAO;
    IConexion conexion;

    public InsumoBO() {
         conexion = new Conexion(Persistence.createEntityManagerFactory("detallesConVasosPU"));
         try {
            this.insumoDAO = DAOFactory.instanciaInsumoDAO(conexion);
        } catch (ModelException ex) {
            Logger.getLogger(UsuarioBO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public Insumo crearInsumo(Insumo insumo) throws ControllerException {
        try {
            return insumoDAO.crearInsumo(insumo);
        } catch (ModelException e) {
            throw new ControllerException("Error al crear insumo", e);
        }
    }

    @Override
    public Insumo actualizarInsumo(Insumo insumo) throws ControllerException {
        try {
            return insumoDAO.actualizarInsumo(insumo);
        } catch (ModelException e) {
            throw new ControllerException("Error al actualizar insumo", e);
        }
    }

    @Override
    public void eliminarInsumo(long id) throws ControllerException {
        try {
            insumoDAO.eliminarInsumo(id);
        } catch (ModelException e) {
            throw new ControllerException("Error al eliminar insumo", e);
        }
    }

    @Override
    public List<Insumo> obtenerTodosLosInsumos() throws ControllerException {
        try {
            return insumoDAO.obtenerTodosLosInsumos();
        } catch (ModelException e) {
            throw new ControllerException("Error al obtener insumos", e);
        }
    }
 
    @Override
    public Insumo obtenerInsumoPorId(long id) throws ControllerException {
        try {
            return insumoDAO.obtenerPorId(id);
        } catch (ModelException e) {
            throw new ControllerException("Error al obtener insumo por ID", e);
        }
    }

}

