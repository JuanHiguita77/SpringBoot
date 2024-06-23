package com.riwi.librosYa.api.dto;

import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DTOUser {

    private long id;
    
    @NotBlank
    @Size(min = 1, max = 50)
    @Schema(description = "Username", example = "john_doe")
    private String username;

    @NotBlank
    @Size(min = 6, max = 100)
    @Schema(description = "Password", example = "password123")
    private String password;

    @NotBlank
    @Email
    @Size(max = 100)
    @Schema(description = "Email", example = "john_doe@example.com")
    private String email;

    @NotBlank
    @Size(max = 100)
    @Schema(description = "Full name", example = "John Doe")
    private String fullName;

    @NotBlank
    @Size(max = 20)
    @Schema(description = "Role", example = "USER")
    private String role;

    @Schema(description = "Reservations list")
    private List<DTOReservation> reservations;

    @Schema(description = "Loans list")
    private List<DTOLoan> loans;

}
