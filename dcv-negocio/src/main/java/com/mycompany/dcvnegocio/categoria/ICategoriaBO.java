/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.mycompany.dcvnegocio.categoria;

import com.mycompany.dcventidades.Categoria;
import com.mycompany.dcvexceptions.ControllerException;
import java.util.List;

/**
 * @author
 */
public interface ICategoriaBO {
  public List<Categoria> obtenerTodas() ;
}
