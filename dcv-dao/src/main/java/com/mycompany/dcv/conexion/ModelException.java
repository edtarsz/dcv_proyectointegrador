package com.mycompany.dcv.conexion;

/**
 * Excepción personalizada para errores del modelo.
 *
 * Extiende {@link Exception} para permitir manejo específico de errores en la
 * capa de modelo.
 *
 * @author 
 */
public class ModelException extends Exception {

    /**
     * Crea una {@code ModelException} con el mensaje especificado.
     *
     * @param message Mensaje que describe el error.
     */
    public ModelException(String message) {
        super(message);
    }

    /**
     * Crea una {@code ModelException} con el mensaje y causa especificados.
     *
     * @param message Mensaje que describe el error.
     * @param cause La causa de la excepción.
     */
    public ModelException(String message, Throwable cause) {
        super(message, cause);
    }
}
