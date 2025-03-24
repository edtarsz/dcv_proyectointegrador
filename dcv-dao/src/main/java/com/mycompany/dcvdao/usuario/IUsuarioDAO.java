/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.mycompany.dcvdao.usuario;

import com.mycompany.dcvconexion.ModelException;
import com.mycompany.dcventidades.Usuario;

/**
 * @author 
 */
public interface IUsuarioDAO {

    public Usuario crearUsuario(Usuario usuario) throws ModelException;
   public Usuario obtenerUsuarioPorId(long id);
}
