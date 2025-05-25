/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.mycompany.dcvnegocio.comprainsumo;

import com.mycompany.dcventidades.CompraInsumo;
import com.mycompany.dcvexceptions.ControllerException;
import java.util.List;

/**
 * @author
 */
public interface ICompraInsumoBO {
public CompraInsumo crearCompraInsumo(CompraInsumo compra) throws ControllerException;
    public CompraInsumo actualizarCompraInsumo(CompraInsumo compra) throws ControllerException;
    public void eliminarCompraInsumo(long id) throws ControllerException;
    public List<CompraInsumo> obtenerTodasLasCompras() throws ControllerException;
}
