/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */
package com.mycompany.dcvmain;

import com.mycompany.dcvconexion.InitialConfig;
import com.mycompany.dcventidades.CompraInsumo;
import com.mycompany.dcventidades.Merma;
import com.mycompany.dcventidades.Usuario;
import com.mycompany.dcventidades.Venta;
import com.mycompany.dcvexceptions.ControllerException;
import com.mycompany.dcvnegocio.usuario.IUsuarioBO;
import com.mycompany.dcvnegocio.usuario.UsuarioBO;
import java.util.ArrayList;

/**
 *
 * @author Ramos
 */
public class DcvMain {

    private static IUsuarioBO usuarioBO;

    public static void main(String[] args) throws ControllerException {
        InitialConfig.iniciarConexion();

        usuarioBO = new UsuarioBO();
        usuarioBO.crearUsuario(new Usuario("nombreUsuario", "contrasena", "rol", new ArrayList<CompraInsumo>(), new ArrayList<Merma>(), new ArrayList<Venta>()));
        
        
    }
}
