/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.mycompany.dcvdao.categoria;

import com.mycompany.dcvconexion.ModelException;
import com.mycompany.dcventidades.Categoria;
import com.mycompany.dcventidades.Producto; // Necesario para el método de obtener productos
import java.util.List;

public interface ICategoriaDAO {

    Categoria crearCategoria(Categoria categoria) throws ModelException;

    Categoria obtenerCategoriaPorId(long id);

    List<Categoria> obtenerTodasLasCategorias();

    Categoria actualizarCategoria(Categoria categoria) throws ModelException;

    boolean eliminarCategoria(long id) throws ModelException;

    boolean categoriaTieneProductos(long idCategoria);

    Categoria buscarCategoriaPorNombre(String nombre);
    
    List<Producto> obtenerProductosPorCategoria(long idCategoria);
}
