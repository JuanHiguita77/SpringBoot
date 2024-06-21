package com.riwi.librosYa.util.exceptions;

public class BadRequestException extends RuntimeException 
{
    public BadRequestException(String error)
    {
        super(error);
    }    
}
