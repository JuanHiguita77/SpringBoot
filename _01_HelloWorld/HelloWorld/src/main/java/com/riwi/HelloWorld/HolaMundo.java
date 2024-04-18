package com.riwi.HelloWorld;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;


//Indica que esta clase será un controlador
@Controller
//Es la ruta general de donde estaran los recursos
@RequestMapping("/controller")

public class HolaMundo 
{
    //Solicitud HTTP del metodo que retorna una data
    @GetMapping("/showMessage")

    @ResponseBody
    public String showMessage()
    {
        return "Hello World";
    }
}
