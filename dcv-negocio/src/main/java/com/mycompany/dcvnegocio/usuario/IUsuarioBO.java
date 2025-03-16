/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.mycompany.dcvnegocio.usuario;

import com.mycompany.dcventidades.Usuario;
import com.mycompany.dcvexceptions.ControllerException;

/**
 * @author
 */
public interface IUsuarioBO {

    public Usuario crearUsuario(Usuario usuario) throws ControllerException;
}
