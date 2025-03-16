/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.mycompany.dcv.facade.usuario;

import com.mycompany.dcv.entidades.Usuario;
import com.mycompany.dcv.exceptions.ControllerException;

/**
 * @author
 */
public interface IUsuarioBO {

    public Usuario crearUsuario(Usuario usuario) throws ControllerException;
}
