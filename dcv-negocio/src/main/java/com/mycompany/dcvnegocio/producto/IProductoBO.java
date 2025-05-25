/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.mycompany.dcvnegocio.producto;

import com.mycompany.dcventidades.Producto;
import com.mycompany.dcvexceptions.ControllerException;
import java.util.List;

/**
 * @author
 */
public interface IProductoBO {

    public Producto crearProducto(Producto producto) throws ControllerException;
    public List<Producto> obtenerTodosLosProductos() throws ControllerException;
    public Producto obtenerProductoPorId(long productoId);

    public Producto actualizarProducto(Producto producto) throws ControllerException;
    public Producto eliminarProducto(long id) throws ControllerException;
    public double obtenerPrecioProducto(long idProducto);

}
