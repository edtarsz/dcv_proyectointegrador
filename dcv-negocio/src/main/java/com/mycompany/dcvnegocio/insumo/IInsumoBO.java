/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.mycompany.dcvnegocio.insumo;

import com.mycompany.dcvconexion.ModelException;
import com.mycompany.dcvdao.insumo.InsumoDAO;
import com.mycompany.dcventidades.Insumo;
import com.mycompany.dcvexceptions.ControllerException;
import com.mycompany.dcvnegocio.envio.*;
import java.util.List;

/**
 * @author
 */
public interface IInsumoBO {
Insumo crearInsumo(Insumo insumo) throws ControllerException;
    Insumo actualizarInsumo(Insumo insumo) throws ControllerException;
    void eliminarInsumo(long id) throws ControllerException;
    List<Insumo> obtenerTodosLosInsumos() throws ControllerException;
    Insumo obtenerInsumoPorId(long id) throws ControllerException;

}
