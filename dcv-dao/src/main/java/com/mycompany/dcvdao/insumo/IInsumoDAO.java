/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.mycompany.dcvdao.insumo;
import com.mycompany.dcventidades.Insumo;
import com.mycompany.dcvconexion.ModelException;
import java.util.List;

/**
 *
 * @author Ramos
 */

public interface IInsumoDAO {
    Insumo crearInsumo(Insumo insumo) throws ModelException;
    Insumo actualizarInsumo(Insumo insumo) throws ModelException;
    void eliminarInsumo(long id) throws ModelException;
    List<Insumo> obtenerTodosLosInsumos() throws ModelException;
}
