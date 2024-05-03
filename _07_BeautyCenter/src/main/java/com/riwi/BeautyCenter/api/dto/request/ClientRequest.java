package com.riwi.BeautyCenter.api.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import lombok.Data;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClientRequest 
{
    @Size(min = 3, max = 100)
    @NotBlank(message = "First Name is Required")
    private String first_name;

    @Size(min = 3, max = 100)
    @NotBlank(message = "Second Name is Required")
    private String last_name;

    @Size(min = 10, max = 20)
    private String phone;

    @Size(min = 10, max = 100)
    private String email; 
}
