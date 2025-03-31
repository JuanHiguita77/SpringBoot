package com.juan.GraphQLCrud.api.dto.user;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDTO {

    @NotBlank(message = "Name is required")
    @Max(value = 50, message = "Name must be less than 50 characters")
    private String name;

    @NotBlank(message = "Email is required")
    private String email;

    @NotBlank(message = "Password is required")
    @Max(value = 30, message = "Password must be less than 30 characters")
    @Min(value = 8, message = "Password must be more than 8 characters")
    private String password;
}
