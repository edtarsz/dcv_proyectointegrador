/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.mycompany.dcvdao.categoria;

import com.mycompany.dcventidades.Categoria;
import java.util.List;
import com.mycompany.dcvconexion.ModelException;
import com.mycompany.dcventidades.Producto;

/**
 *
 * @author Ramos
 */
public interface ICategoriaDAO {

    public List<Categoria> obtenerTodas();

    public Categoria obtenerPorId(long id);

    public List<Producto> obtenerProductosPorCategoria(long idCategoria);

    public Categoria buscarCategoriaPorNombre(String nombre);

    public boolean categoriaTieneProductos(long idCategoria);

    public boolean eliminarCategoria(long id) throws ModelException;

    public Categoria crearCategoria(Categoria categoria) throws ModelException;
    
    public Categoria actualizarCategoria(Categoria categoria) throws ModelException;

}
