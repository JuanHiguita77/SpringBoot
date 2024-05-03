package com.riwi.BeautyCenter.util.exceptions;

//Se deberia crear una excepsion por cada error que queramos mostrar
//RuntimeException: Clase general de manejo de errores en java
public class IdNotFoundException extends RuntimeException
{
    private static final String ERROR_MESSAGE = "Dont found Entity %s With id typed";

    //Constructor manual: excepsion creada
    public IdNotFoundException(String nameEntity)
    {
        //Pasa el nombre de la entidad al marcador de posicion del error_message
        super(String.format(ERROR_MESSAGE, nameEntity));
    }
}
