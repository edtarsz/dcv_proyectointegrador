/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.mycompany.dcvdao.cliente;

import com.mycompany.dcvconexion.ModelException;
import com.mycompany.dcventidades.Cliente;
import java.util.List;

/**
 *
 * @author PC
 */
public interface IClienteDAO {
    public Cliente crearCliente(Cliente cliente) throws ModelException;
    public Cliente obtenerClientePorId(long id);
    public List<Cliente> obtenerTodosLosCliente();
    public Cliente actualizarCliente(Cliente cliente) throws ModelException;
    public Cliente eliminarCliente(long id) throws ModelException;
}
