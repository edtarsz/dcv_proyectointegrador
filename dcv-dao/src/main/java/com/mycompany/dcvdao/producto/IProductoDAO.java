/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.mycompany.dcvdao.producto;

import com.mycompany.dcvconexion.ModelException;
import com.mycompany.dcventidades.Producto;
import java.util.List;

/**
 *
 * @author Ramos
 */
public interface IProductoDAO {

    public Producto crearProducto(Producto producto) throws ModelException;
    public Producto obtenerProductoPorId(long id);
    public List<Producto> obtenerTodosLosProductos();
    public Producto actualizarProducto(Producto producto) throws ModelException;
    public Producto eliminarProducto(long id) throws ModelException;

    public double obtenerPrecioProducto(long idProducto);


}
