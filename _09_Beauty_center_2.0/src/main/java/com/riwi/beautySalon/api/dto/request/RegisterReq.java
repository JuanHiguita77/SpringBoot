package com.riwi.beautySalon.api.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class RegisterReq 
{
    @NotBlank(message = "User name is required")
    @Size(min = 8, max = 150, message = "User needs must be between 8 and 150 characters")
    private String userName;

    @NotBlank(message = "Password is required")
    @Size(min = 8, max = 150, message = "Password needs must be between 8 and 150 characters")
    private String password; 

}
