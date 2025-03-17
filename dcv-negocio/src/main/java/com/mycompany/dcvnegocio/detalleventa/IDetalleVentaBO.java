/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.mycompany.dcvnegocio.detalleventa;

import com.mycompany.dcventidades.DetalleVenta;
import com.mycompany.dcvexceptions.ControllerException;

/**
 * @author
 */
public interface IDetalleVentaBO {

    public DetalleVenta crearVenta(DetalleVenta detalleVenta) throws ControllerException;
}
