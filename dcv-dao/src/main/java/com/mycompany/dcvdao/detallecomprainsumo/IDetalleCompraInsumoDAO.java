/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.mycompany.dcvdao.detallecomprainsumo;

import com.mycompany.dcvconexion.ModelException;
import com.mycompany.dcventidades.DetalleCompraInsumo;
import java.util.List;

/**
 * @author Eduardo Talavera Ramos
 * @author Ana Cristina Castro Noriega
 * @author Eliana Monge Camara
 * @author Jesús Roberto García Armenta
 */
public interface IDetalleCompraInsumoDAO {
 DetalleCompraInsumo crearDetalleCompraInsumo(DetalleCompraInsumo detalle) throws ModelException;
    DetalleCompraInsumo actualizarDetalleCompraInsumo(DetalleCompraInsumo detalle) throws ModelException;
    void eliminarDetalleCompraInsumo(long id) throws ModelException;
    List<DetalleCompraInsumo> obtenerDetallesPorCompra(long idCompra) throws ModelException;
}
