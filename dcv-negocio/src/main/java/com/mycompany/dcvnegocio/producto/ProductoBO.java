/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.dcvnegocio.producto;

import com.mycompany.dcvnegocio.usuario.UsuarioBO;
import com.mycompany.dcvconexion.Conexion;
import com.mycompany.dcvconexion.IConexion;
import com.mycompany.dcvconexion.ModelException;
import com.mycompany.dcvdao.DAOFactory;
import com.mycompany.dcvdao.producto.IProductoDAO;
import com.mycompany.dcventidades.Producto;
import com.mycompany.dcvexceptions.ControllerException;
import java.util.logging.Level;
import java.util.logging.Logger;
import jakarta.persistence.Persistence;
import java.util.List;

/**
 * @author
 */
public class ProductoBO implements IProductoBO {

    private IProductoDAO productoDAO;
    IConexion conexion;

    public ProductoBO() {
        conexion = new Conexion(Persistence.createEntityManagerFactory("detallesConVasosPU"));
        try {
            this.productoDAO = DAOFactory.instanciaProductoDAO(conexion);
        } catch (ModelException ex) {
            Logger.getLogger(UsuarioBO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public Producto crearProducto(Producto producto) throws ControllerException {
        try {
            return productoDAO.crearProducto(producto);
        } catch (ModelException ex) {
            Logger.getLogger(ProductoBO.class.getName()).log(Level.SEVERE, null, ex);
            throw new ControllerException("Error creating comment", ex);
        }
    }

    @Override
    public List<Producto> obtenerTodosLosProductos() throws ControllerException {
        return productoDAO.obtenerTodosLosProductos();
        
    }

    @Override
    public Producto obtenerProductoPorId(int productoId) {
        return productoDAO.obtenerProductoPorId(productoId);
    }

    @Override
    public double obtenerPrecioProducto(int idProducto) {
        return productoDAO.obtenerPrecioProducto(idProducto);
    }

    

}
