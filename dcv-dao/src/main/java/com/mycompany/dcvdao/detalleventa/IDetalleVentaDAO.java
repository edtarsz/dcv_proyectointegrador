/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.mycompany.dcvdao.detalleventa;

import com.mycompany.dcvconexion.ModelException;
import com.mycompany.dcventidades.DetalleVenta;
import java.util.List;

/**
 *
 * @author Ramos
 */
public interface IDetalleVentaDAO {
    
    public DetalleVenta crearDetalleVenta(DetalleVenta detalleVenta) throws ModelException;
    public DetalleVenta obtenerDetalleVentaPorId(long id);
    public List<DetalleVenta> obtenerTodosLosDetallesVentas();
    public DetalleVenta actualizarVenta(DetalleVenta detalleVenta) throws ModelException;
    public DetalleVenta eliminarVenta(long id) throws ModelException;
}
