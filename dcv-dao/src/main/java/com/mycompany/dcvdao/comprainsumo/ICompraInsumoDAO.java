/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.mycompany.dcvdao.comprainsumo;

import com.mycompany.dcvconexion.ModelException;
import com.mycompany.dcventidades.CompraInsumo;
import java.util.List;

/**
 *
 * @author Ramos
 */
public interface ICompraInsumoDAO {
     CompraInsumo crearCompraInsumo(CompraInsumo compra) throws ModelException;
    CompraInsumo actualizarCompraInsumo(CompraInsumo compra) throws ModelException;
    void eliminarCompraInsumo(long id) throws ModelException;
    List<CompraInsumo> obtenerTodasLasCompras() throws ModelException;

}
