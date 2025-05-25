/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.mycompany.dcvdao.categoria;

import com.mycompany.dcventidades.Categoria;
import java.util.List;
import com.mycompany.dcvconexion.ModelException;

/**
 *
 * @author Ramos
 */
public interface ICategoriaDAO {
    public List<Categoria> obtenerTodas();
    public Categoria obtenerPorId(long id);
            
}
