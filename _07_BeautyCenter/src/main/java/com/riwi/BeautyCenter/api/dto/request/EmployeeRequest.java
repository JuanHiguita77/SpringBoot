package com.riwi.BeautyCenter.api.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import lombok.Data;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeRequest 
{
    @Size(min = 3, max = 100)
    @NotBlank(message = "First name required")
    private String first_name;

    @Size(min = 3, max = 100)
    @NotBlank(message = "Last name required")
    private String last_name;

    @Size(min = 10, max = 100)
    @Email(message = "Email required")
    private String email;  

    @Size(min = 9, max = 20)
    @NotBlank(message = "First name required")
    private String phone;

    @Size(min = 3, max = 50)
    @NotBlank(message = "First name required")
    private String role;   
}
