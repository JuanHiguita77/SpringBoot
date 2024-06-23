package com.riwi.librosYa.api.dto;

import java.time.LocalDate;

import io.swagger.v3.oas.annotations.media.Schema;
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
public class DTOLoan {

    private long id;
    
    @NotNull
    @Schema(description = "Loan date")
    private LocalDate loanDate;

    @Schema(description = "Return date")
    private LocalDate returnDate;

    @NotBlank
    @Size(min = 1, max = 20)
    @Schema(description = "Status", example = "ONGOING")
    private String status;

    @NotNull
    @Schema(description = "User ID", example = "1")
    private Long userId;

    @NotNull
    @Schema(description = "Book ID", example = "1")
    private Long bookId;
}
