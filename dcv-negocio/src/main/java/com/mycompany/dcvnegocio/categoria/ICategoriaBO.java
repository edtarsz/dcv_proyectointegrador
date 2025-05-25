package com.mycompany.dcvnegocio.categoria;

import com.mycompany.dcventidades.Categoria;
import com.mycompany.dcventidades.Producto;
import com.mycompany.dcvexceptions.ControllerException; // Asumo que tienes esta clase de excepción
import java.util.List;

public interface ICategoriaBO {

    Categoria agregarCategoria(Categoria categoria) throws ControllerException;

    Categoria obtenerCategoriaPorId(long id) throws ControllerException;

    List<Categoria> obtenerTodasLasCategorias() throws ControllerException;

    Categoria actualizarCategoria(Categoria categoria) throws ControllerException;

    boolean eliminarCategoria(long id) throws ControllerException;

    List<Producto> obtenerProductosPorCategoria(long idCategoria) throws ControllerException;
}
