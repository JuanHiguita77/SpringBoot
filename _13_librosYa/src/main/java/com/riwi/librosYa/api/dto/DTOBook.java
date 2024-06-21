package com.riwi.librosYa.api.dto;

import java.util.List;

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
public class DTOBook {
    
    @NotBlank
    @Size(min = 1, max = 100)
    @Schema(description = "Book title", example = "Spring Boot in Action")
    private String title;

    @NotBlank
    @Size(min = 1, max = 100)
    @Schema(description = "Author", example = "Craig Walls")
    private String author;

    @NotNull
    @Schema(description = "Publication year", example = "2016")
    private Integer publicationYear;

    @NotBlank
    @Size(max = 50)
    @Schema(description = "Genre", example = "Programming")
    private String genre;

    @NotBlank
    @Size(max = 20)
    @Schema(description = "ISBN", example = "9781617292545")
    private String isbn;

    @Schema(description = "Reservations list")
    private List<DTOReservation> reservations;

    @Schema(description = "Loans list")
    private List<DTOLoan> loans;
}
