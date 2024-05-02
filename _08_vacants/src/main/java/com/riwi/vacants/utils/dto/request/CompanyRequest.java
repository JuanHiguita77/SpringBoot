package com.riwi.vacants.utils.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

//Clase normal
@Data
@Builder// Patron de diseño opcional para creacion de clases    
@AllArgsConstructor
@NoArgsConstructor
public class CompanyRequest 
{
    //Esta informacion se pasa por el postman en el objeto json
    @NotBlank(message = "Company Name Required")
    @Size(min = 0, max = 40, message = "Company Name is out range length")
    private String name;

    @NotBlank(message = "Company location Required")
    private String location;

    @Size(min = 0, max = 14, message = "Company contact is out range length")
    @NotBlank(message = "Company contact Required")
    private String contact;
}
