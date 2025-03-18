/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.dcvnegocio.cliente;

import com.mycompany.dcvconexion.Conexion;
import com.mycompany.dcvconexion.IConexion;
import com.mycompany.dcvconexion.ModelException;
import com.mycompany.dcvdao.DAOFactory;
import com.mycompany.dcvdao.cliente.IClienteDAO;
import com.mycompany.dcventidades.Cliente;
import com.mycompany.dcvexceptions.ControllerException;
import jakarta.persistence.Persistence;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author PC
 */
public class ClienteBO implements IClienteBO{
    private IClienteDAO clienteDAO;
    IConexion conexion;

    public ClienteBO() {
        conexion = new Conexion(Persistence.createEntityManagerFactory("detallesConVasosPU"));
        try {
            this.clienteDAO = DAOFactory.instanciaClienteDAO(conexion);
        } catch (ModelException ex) {
            Logger.getLogger(ClienteBO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @Override
    public Cliente crearCliente(Cliente cliente) throws ControllerException {
        try {
            return clienteDAO.crearCliente(cliente);
        } catch (ModelException ex) {
            Logger.getLogger(ClienteBO.class.getName()).log(Level.SEVERE, null, ex);
            throw new ControllerException("Error creating comment", ex);
        }
    }
}
