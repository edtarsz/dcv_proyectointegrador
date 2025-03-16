/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */
package com.mycompany.dcv.main;

import com.mycompany.dcv.conexion.InitialConfig;
import com.mycompany.dcv.entidades.CompraInsumo;
import com.mycompany.dcv.entidades.Merma;
import com.mycompany.dcv.entidades.Usuario;
import com.mycompany.dcv.entidades.Venta;
import com.mycompany.dcv.exceptions.ControllerException;
import com.mycompany.dcv.facade.usuario.IUsuarioBO;
import com.mycompany.dcv.facade.usuario.UsuarioBO;
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
        usuarioBO.crearUsuario(new Usuario(0, "nombreUsuario", "contrasena", "rol", new ArrayList<CompraInsumo>(), new ArrayList<Merma>(), new ArrayList<Venta>()));
    }
}
