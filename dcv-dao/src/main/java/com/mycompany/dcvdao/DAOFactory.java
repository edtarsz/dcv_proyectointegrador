/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.dcvdao;

import com.mycompany.dcvdao.categoria.CategoriaDAO;
import com.mycompany.dcvconexion.IConexion;
import com.mycompany.dcvconexion.ModelException;
import com.mycompany.dcvdao.comprainsumo.CompraInsumoDAO;
import com.mycompany.dcvdao.detallecomprainsumo.DetalleCompraInsumoDAO;
import com.mycompany.dcvdao.detalleventa.DetalleVentaDAO;
import com.mycompany.dcvdao.envio.EnvioDAO;
import com.mycompany.dcvdao.insumo.InsumoDAO;
import com.mycompany.dcvdao.merma.MermaDAO;
import com.mycompany.dcvdao.producto.ProductoDAO;
import com.mycompany.dcvdao.reembolso.ReembolsoDAO;
import com.mycompany.dcvdao.usuario.UsuarioDAO;
import com.mycompany.dcvdao.venta.VentaDAO;

/**
 * @author Eduardo Talavera Ramos
 */
public class DAOFactory {

    private DAOFactory() {
        throw new UnsupportedOperationException("Esta es una clase utilitaria y no debe ser instanciada.");
    }

    public static CategoriaDAO instanciaCategoriaDAO(IConexion conexion) throws ModelException {
        return new CategoriaDAO(conexion);
    }

    public static CompraInsumoDAO instanciaCompraInsumoDAO(IConexion conexion) throws ModelException {
        return new CompraInsumoDAO(conexion);
    }

    public static DetalleCompraInsumoDAO instanciaDetalleCompraInsumoDAO(IConexion conexion) throws ModelException {
        return new DetalleCompraInsumoDAO(conexion);
    }

    public static DetalleVentaDAO instanciaDetalleVentaDAO(IConexion conexion) throws ModelException {
        return new DetalleVentaDAO(conexion);
    }

    public static EnvioDAO instanciaEnvioDAO(IConexion conexion) throws ModelException {
        return new EnvioDAO(conexion);
    }

    public static InsumoDAO instanciaInsumoDAO(IConexion conexion) throws ModelException {
        return new InsumoDAO(conexion);
    }

    public static MermaDAO instanciaMermaDAO(IConexion conexion) throws ModelException {
        return new MermaDAO(conexion);
    }

    public static ProductoDAO instanciaProductoDAO(IConexion conexion) throws ModelException {
        return new ProductoDAO(conexion);
    }

    public static ReembolsoDAO instanciaReembolsoDAO(IConexion conexion) throws ModelException {
        return new ReembolsoDAO(conexion);
    }

    public static UsuarioDAO instanciaUsuarioDAO(IConexion conexion) throws ModelException {
        return new UsuarioDAO(conexion);
    }

    public static VentaDAO instanciaVentaDAO(IConexion conexion) throws ModelException {
        return new VentaDAO(conexion);
    }
}
