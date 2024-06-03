package com.riwi.OnlineLearning.api.dto.request;

import com.riwi.OnlineLearning.utils.enums.Role;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserReq 
{
    @NotBlank
    @Size(min = 1, max = 50, message = "Name between 1 and 50 characters")
    @Schema(description = "Username", example = "juanf")
    private String userName;

    @NotBlank
    @Size(min = 8, max = 255, message = "Password between 8 and 255 characters")
    @Schema(description = "Password")
    private String password;

    @Email
    @NotBlank
    @Schema(description = "Email", example = "juanf.yh@example.com")
    private String email;

    @Size(min = 1, max = 100, message = "Fullname between 1 and 100 characters")
    @Schema(description = "Full name", example = "Juan")
    private String fullName;

    @NotNull
    @Schema(description = "User role", example = "STUDENT || INSTRUCTOR")
    private Role role;
}
