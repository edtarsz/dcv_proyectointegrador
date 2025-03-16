package com.mycompany.dcv.exceptions;

/**
 * @author 
 */
public class ControllerException extends Exception {

    /**
     * Crea una {@code ModelException} con el mensaje especificado.
     *
     * @param message Mensaje que describe el error.
     */
    public ControllerException(String message) {
        super(message);
    }

    /**
     * Crea una {@code ModelException} con el mensaje y causa especificados.
     *
     * @param message Mensaje que describe el error.
     * @param cause   La causa de la excepción.
     */
    public ControllerException(String message, Throwable cause) {
        super(message, cause);
    }

}
