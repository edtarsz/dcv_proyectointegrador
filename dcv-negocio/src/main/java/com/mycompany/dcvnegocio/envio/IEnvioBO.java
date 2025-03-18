/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.mycompany.dcvnegocio.envio;

import com.mycompany.dcventidades.Envio;
import com.mycompany.dcvexceptions.ControllerException;

/**
 * @author
 */
public interface IEnvioBO {

    public Envio crearEnvio(Envio envio) throws ControllerException;

}
