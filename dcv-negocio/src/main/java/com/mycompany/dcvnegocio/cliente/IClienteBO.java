/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.mycompany.dcvnegocio.cliente;

import com.mycompany.dcventidades.Cliente;
import com.mycompany.dcvexceptions.ControllerException;

/**
 *
 * @author PC
 */
public interface IClienteBO {
    public Cliente crearCliente(Cliente cliente) throws ControllerException;

    public Cliente obtenerClientePorId(Long clienteId);
}
