/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.mycompany.dcvnegocio.detallecomprainsumo;


import com.mycompany.dcventidades.DetalleCompraInsumo;
import com.mycompany.dcvexceptions.ControllerException;
import java.util.List;

/**
 * @author
 */
public interface IDetalleCompraInsumoBO {
 DetalleCompraInsumo crearDetalleCompraInsumo(DetalleCompraInsumo detalle) throws ControllerException;
    DetalleCompraInsumo actualizarDetalleCompraInsumo(DetalleCompraInsumo detalle) throws ControllerException;
    void eliminarDetalleCompraInsumo(long id) throws ControllerException;
    List<DetalleCompraInsumo> obtenerDetallesPorCompra(long idCompra) throws ControllerException;
}
