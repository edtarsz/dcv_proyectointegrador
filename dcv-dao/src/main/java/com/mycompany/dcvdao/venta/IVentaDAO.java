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
import java.sql.Date;
import java.util.List;

/**
 * @author
 */
public interface IVentaDAO {
    
    public Venta crearVenta(Venta venta) throws ModelException ;
    public Venta obtenerVentaPorId(long id);
    public List<Venta> obtenerTodasLasVentas() throws ModelException;
    public Venta actualizarVenta(Venta venta) throws ModelException;
    public Venta eliminarVenta(long id) throws ModelException;
    Venta guardarVenta(double total, String metodoPago, Cliente clienteId, Usuario usuarioId) throws ModelException;
    void guardarDetalleVenta(Venta ventaId, DetalleVenta detalle) throws ModelException;

    List<Venta> buscarVentasPorCliente(String nombreCliente) throws ModelException;
    List<Venta> filtrarVentas(Date fechaInicio, Date fechaFin, String estado, Double precioMin, Double precioMax) throws ModelException;
    Venta obtenerVentaPorId(Long id) throws ModelException;
    void actualizarEstadoVenta(Long ventaId, String nuevoEstado) throws ModelException;

    public void actualizarPersonalizacionDetalle(Long detalleId, String nuevaPersonalizacion);

}
