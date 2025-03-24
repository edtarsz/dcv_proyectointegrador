/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.mycompany.dcvdao.venta;

import com.mycompany.dcvconexion.ModelException;
import com.mycompany.dcventidades.Cliente;
import com.mycompany.dcventidades.DetalleVenta;
import com.mycompany.dcventidades.Usuario;
import com.mycompany.dcventidades.Venta;
import java.util.List;

/**
 * @author
 */
public interface IVentaDAO {
    
    public Venta crearVenta(Venta venta) throws ModelException ;
    public Venta obtenerVentaPorId(long id);
    public List<Venta> obtenerTodasLasVentas();
    public Venta actualizarVenta(Venta venta) throws ModelException;
    public Venta eliminarVenta(long id) throws ModelException;
    Venta guardarVenta(double total, String metodoPago, Cliente clienteId, Usuario usuarioId) throws ModelException;
    void guardarDetalleVenta(Venta ventaId, DetalleVenta detalle) throws ModelException;

}
