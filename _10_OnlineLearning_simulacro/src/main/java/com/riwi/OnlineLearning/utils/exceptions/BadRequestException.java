package com.riwi.OnlineLearning.utils.exceptions;

public class BadRequestException extends RuntimeException 
{
    public BadRequestException(String error)
    {
        super(error);
    }    
}
