/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.mycompany.dcvnegocio.producto;

import com.mycompany.dcventidades.Producto;
import com.mycompany.dcvexceptions.ControllerException;

/**
 * @author
 */
public interface IProductoBO {

    public Producto crearProducto(Producto producto) throws ControllerException;
}
