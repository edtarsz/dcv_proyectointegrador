/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.mycompany.dcvnegocio.venta;

import com.mycompany.dcventidades.Cliente;
import com.mycompany.dcventidades.DetalleVenta;
import com.mycompany.dcventidades.Usuario;
import com.mycompany.dcventidades.Venta;
import com.mycompany.dcvexceptions.ControllerException;

/**
 * @author
 */
public interface IVentaBO {

    public Venta crearVenta(Venta venta) throws ControllerException;
    Venta guardarVenta(double total, String metodoPago, Cliente clienteId, Usuario usuarioId) throws ControllerException;
    void guardarDetalleVenta(Venta ventaId, DetalleVenta detalle) throws ControllerException;
}
