/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.mycompany.dcvnegocio.categoria;

import com.mycompany.dcventidades.Categoria;
import com.mycompany.dcventidades.Producto;
import com.mycompany.dcvexceptions.ControllerException;
import java.util.List;

/**
 * @author
 */
public interface ICategoriaBO {
  public List<Categoria> obtenerTodas() throws ControllerException ;
  public Categoria obtenerPorId(long id) throws ControllerException;
  public Categoria agregarCategoria(Categoria categoria) throws ControllerException;
  public Categoria actualizarCategoria(Categoria categoria) throws ControllerException;
  public boolean eliminarCategoria(long id) throws ControllerException;
  public List<Producto> obtenerProductosPorCategoria(long idCategoria) throws ControllerException;
}
