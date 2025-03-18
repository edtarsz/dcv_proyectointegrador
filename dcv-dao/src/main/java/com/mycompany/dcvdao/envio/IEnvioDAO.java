/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.mycompany.dcvdao.envio;

import com.mycompany.dcvconexion.ModelException;
import com.mycompany.dcventidades.Envio;
import java.util.List;

/**
 *
 * @author Ramos
 */
public interface IEnvioDAO {

    public Envio crearEnvio(Envio envio) throws ModelException;
    public Envio obtenerEnvioPorId(long id);
    public List<Envio> obtenerTodosLosEnvios();
    public Envio actualizarEnvio(Envio envio) throws ModelException;
    public Envio eliminarEnvio(long id) throws ModelException;
    
}
