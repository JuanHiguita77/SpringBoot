package com.riwi.vacants.utils.exceptions;

//RuntimeException Clase general de errores en java: Se usa para mostrar errores personalizados
public class idNotFoundException extends RuntimeException
{
    private static final String ERROR_MESSAGE = "Entity Not Found %s";

    public idNotFoundException(String entity)
    {
        super(String.format(ERROR_MESSAGE, entity));
    }
}
